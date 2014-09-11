package net.verizon.mflacy.heartratemonitor;

import com.bluekitchen.btstack.BD_ADDR;
import com.bluekitchen.btstack.BT_UUID;
import com.bluekitchen.btstack.BTstack;
import com.bluekitchen.btstack.Event;
import com.bluekitchen.btstack.GATTCharacteristic;
import com.bluekitchen.btstack.GATTCharacteristicDescriptor;
import com.bluekitchen.btstack.GATTService;
import com.bluekitchen.btstack.Util;
import com.bluekitchen.btstack.event.BTstackEventState;
import com.bluekitchen.btstack.event.EventHandler;
import com.bluekitchen.btstack.event.GAPLEAdvertisingReport;
import com.bluekitchen.btstack.event.GATTAllCharacteristicDescriptorsQueryResult;
import com.bluekitchen.btstack.event.GATTCharacteristicQueryResult;
import com.bluekitchen.btstack.event.GATTCharacteristicValueQueryResult;
import com.bluekitchen.btstack.event.GATTIncludedServiceQueryResult;
import com.bluekitchen.btstack.event.GATTIndication;
import com.bluekitchen.btstack.event.GATTLongCharacteristicValueQueryResult;
import com.bluekitchen.btstack.event.GATTNotification;
import com.bluekitchen.btstack.event.GATTQueryComplete;
import com.bluekitchen.btstack.event.GATTServiceQueryResult;
import com.bluekitchen.btstack.event.HCIEventAuthenticationCompleteEvent;
import com.bluekitchen.btstack.event.HCIEventChangeConnectionLinkKeyComplete;
import com.bluekitchen.btstack.event.HCIEventCommandComplete;
import com.bluekitchen.btstack.event.HCIEventCommandStatus;
import com.bluekitchen.btstack.event.HCIEventConnectionComplete;
import com.bluekitchen.btstack.event.HCIEventConnectionRequest;
import com.bluekitchen.btstack.event.HCIEventDisconnectionComplete;
import com.bluekitchen.btstack.event.HCIEventEncryptionChange;
import com.bluekitchen.btstack.event.HCIEventHardwareError;
import com.bluekitchen.btstack.event.HCIEventInquiryComplete;
import com.bluekitchen.btstack.event.HCIEventLEConnectionComplete;
import com.bluekitchen.btstack.event.HCIEventMasterLinkKeyComplete;
import com.movisens.smartgattlib.Characteristic;
import com.movisens.smartgattlib.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author flacy
 */
public class HeartRateConnect implements EventHandler {

    private static final Logger LOG = Logger.getLogger(HeartRateConnect.class.getName());

    private final BTstack stack;

    private final HeartRateConnectContext fsm;

    private int addrType;
    private BD_ADDR addr;
    private int connHandle;

    private final List<GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>>> services;
    private final Map<BT_UUID, GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>>> servMap;

    private GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> currentService;
    private GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor> currentCharacteristic;
    private Iterator<GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>>> svcIter;
    private Iterator<GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> charIterator;

    public HeartRateConnect(BTstack stack) {
        this.stack = stack;
        fsm = new HeartRateConnectContext(this);
        services = new ArrayList<>();
        servMap = new HashMap<>();
    }

    public void init() {
        fsm.enterStartState();
    }

    public boolean isUp(BTstackEventState event) {
        return event.getState() == 2;
    }

    public void startLEScan() {
        LOG.info("Start LE scan.");
        stack.GAPLEScanStart();
    }

    public void stopLEScan() {
        LOG.info("Stop LE scan");
        stack.GAPLEScanStop();
    }

    public void recordAddrInfo(GAPLEAdvertisingReport event) {
        addrType = event.getAddressType();
        addr = event.getAddress();
        LOG.log(Level.INFO, "Adv: type {0}, addr 0x{1}", new Object[]{addrType, addr});
        LOG.log(Level.INFO, "Data: {0}", Util.asHexdump(event.getData()));
    }

    public void performLEConnect() {
        LOG.info("LE connect...");
        stack.GAPLEConnect(addrType, addr);
    }

    public void recordConnectionHandle(HCIEventLEConnectionComplete event) {
        connHandle = event.getConnectionHandle();
        LOG.log(Level.INFO, "Connection complete, status {0}, handle 0x{1}", new Object[]{event.getStatus(), Integer.toHexString(connHandle)});
    }

    public void discoverPrimaryServices() {
        LOG.info("Discover primary services");
        stack.GATTDiscoverPrimaryServices(connHandle);
    }

    public void recordPrimaryService(GATTServiceQueryResult event) {
        LOG.log(Level.INFO, "Service found {0}", event.getService());
        GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> srv = new GATTContainer<>(event.getService());
        servMap.put(event.getService().getUUID(), srv);
    }

    public boolean haveServices() {
        svcIter = servMap.values().iterator();
        return svcIter.hasNext();
    }

    public void discoverNextServiceCharacteristics() {
        currentService = svcIter.next();
        LOG.log(Level.INFO, "Characteristics discovery for {0}", currentService.getName());
        stack.GATTDiscoverCharacteristicsForService(connHandle, currentService.getName());
    }

    public void recordServiceCharacteristic(GATTCharacteristicQueryResult event) {
        LOG.log(Level.INFO, "Characteristic found {0}", event.getCharacteristic());
        GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor> gchar = new GATTContainer<>(event.getCharacteristic());
        currentService.addChild(event.getCharacteristic().getUUID(), gchar);
    }

    public boolean haveMoreServices() {
        return svcIter.hasNext();
    }

    public void discoverFirstServiceCharacteristicDescriptor() {
        StringBuilder sb = new StringBuilder();
        sb.append("Characteristics found:");
        for (GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> gservcont : services) {
            for (GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor> gchar : gservcont.getChildren()) {
                sb.append("\n").append(gchar);
            }
        }
        LOG.warning(sb.toString());
        svcIter = servMap.values().iterator();
        currentService = svcIter.next();
        charIterator = currentService.getChildIterator();
        while (!charIterator.hasNext() && svcIter.hasNext()) {
            currentService = svcIter.next();
            charIterator = currentService.getChildIterator();
        }
        if (charIterator.hasNext()) {
            currentCharacteristic = charIterator.next();
            // we can re-use this method.
            discoverNextServiceCharacteristicDescriptor();
        } else {
            LOG.severe("There are no characteristics to have descriptors!!!");
        }
    }

    public void recordCharacteristicDescriptor(GATTAllCharacteristicDescriptorsQueryResult event) {
        GATTCharacteristicDescriptor cd = event.getCharacteristicDescriptor();
        LOG.log(Level.INFO, "Found characteristic descriptor {0}", cd);
        currentCharacteristic.addChild(cd.getUUID(), cd);
    }

    public boolean haveMoreCharacteristics() {
        while (!charIterator.hasNext() && svcIter.hasNext()) {
            currentService = svcIter.next();
            charIterator = currentService.getChildIterator();
        }
        return charIterator.hasNext();
    }

    public void discoverNextServiceCharacteristicDescriptor() {
        currentCharacteristic = charIterator.next();
        LOG.log(Level.INFO, "Discovering characteristic descriptors for {0}", currentCharacteristic.getName());
        stack.GATTDiscoverCharacteristicDescriptors(connHandle, currentCharacteristic.getName());
    }
    
    public void registerForNotifications() {
        LOG.info("Found the following descriptors.");
        for (GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> gATTContainer : services) {
            for (GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor> gcharContainer : gATTContainer.getChildren()) {
                for (GATTCharacteristicDescriptor thingy : gcharContainer.getChildren()) {
                    LOG.log(Level.INFO, "{0}", thingy);
                }
            }
        }
        // Look in service map for the heart rate service.
        BT_UUID hrs = new BT_UUID(Service.HEART_RATE);
        if (servMap.containsKey(hrs)) {
            GATTContainer<GATTService, GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor>> hrsContainer = servMap.get(hrs);
            BT_UUID hrmc = new BT_UUID(Characteristic.HEART_RATE_MEASUREMENT);
            GATTContainer<GATTCharacteristic, GATTCharacteristicDescriptor> child = hrsContainer.getChild(hrmc);
            if (child == null) {
                LOG.log(Level.SEVERE, "Cannot find characteristic {0} in heart rate service map.", hrmc);
            } else {
                LOG.info("Found the correct characteristic.  Here we go.");
                stack.GATTWriteClientCharacteristicConfiguration(connHandle, child.getName(), 3);
            }
        } else {
            LOG.log(Level.SEVERE, "Cannot find {0} in the service map!", hrs);
        }
    }
    
    public void recordNotification(GATTNotification event) {
        LOG.log(Level.INFO, "Received notification: {0}", event);
    }
    
    public void disconnect() {
        stack.GAPDisconnect(connHandle);
    }
    
    public void whine(Event event) {
        LOG.log(Level.SEVERE, "Unexpected event {0} in current state.", event);
    }
    

    @Override
    public void handleEvent(Event event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventInquiryComplete(HCIEventInquiryComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventConnectionComplete(HCIEventConnectionComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventConnectionRequest(HCIEventConnectionRequest event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventDisconnectionComplete(HCIEventDisconnectionComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventAuthenticationCompleteEvent(HCIEventAuthenticationCompleteEvent event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventEncryptionChange(HCIEventEncryptionChange event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventChangeConnectionLinkKeyComplete(HCIEventChangeConnectionLinkKeyComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventMasterLinkKeyComplete(HCIEventMasterLinkKeyComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventCommandComplete(HCIEventCommandComplete event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventCommandStatus(HCIEventCommandStatus event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleHCIEventHardwareError(HCIEventHardwareError event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleBTstackEventState(BTstackEventState event) {
        fsm.handleBTstackEventState(event);
    }

    @Override
    public void handleGATTQueryComplete(GATTQueryComplete event) {
        fsm.handleGATTQueryComplete(event);
    }

    @Override
    public void handleGATTServiceQueryResult(GATTServiceQueryResult event) {
        fsm.handleGATTServiceQueryResult(event);
    }

    @Override
    public void handleGATTCharacteristicQueryResult(GATTCharacteristicQueryResult event) {
        fsm.handleGATTCharacteristicQueryResult(event);
    }

    @Override
    public void handleGATTIncludedServiceQueryResult(GATTIncludedServiceQueryResult event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleGATTAllCharacteristicDescriptorsQueryResult(GATTAllCharacteristicDescriptorsQueryResult event) {
        fsm.handleGATTAllCharacteristicDescriptorsQueryResult(event);
    }

    @Override
    public void handleGATTCharacteristicValueQueryResult(GATTCharacteristicValueQueryResult event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleGATTLongCharacteristicValueQueryResult(GATTLongCharacteristicValueQueryResult event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleGATTNotification(GATTNotification event) {
        fsm.handleGATTNotification(event);
    }

    @Override
    public void handleGATTIndication(GATTIndication event) {
        LOG.log(Level.SEVERE, "Event not handled by state machine: {0}", event);
    }

    @Override
    public void handleGAPLEAdvertisingReport(GAPLEAdvertisingReport event) {
        fsm.handleGAPLEAdvertisingReport(event);
    }

    @Override
    public void handleHCIEventLEConnectionComplete(HCIEventLEConnectionComplete event) {
        fsm.handleHCIEventLEConnectionComplete(event);
    }

}
