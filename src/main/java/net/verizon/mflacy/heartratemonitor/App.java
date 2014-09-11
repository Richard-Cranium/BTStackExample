package net.verizon.mflacy.heartratemonitor;

import com.bluekitchen.btstack.BTstack;
import com.bluekitchen.btstack.EventFactory;
import com.bluekitchen.btstack.Packet;
import com.bluekitchen.btstack.PacketHandler;
import java.util.logging.Logger;

/**
 *
 * @author flacy
 */
public class App implements PacketHandler {

    private static final Logger LOG = Logger.getLogger(App.class.getName());

    private final BTstack stack;
    private final HeartRateConnect hrc;

    public App() {
        stack = new BTstack();
        hrc = new HeartRateConnect(stack);
        hrc.init();
    }
    
    private void init() {
        stack.registerPacketHandler(this);
        stack.setTcpPort(13333);
        if (!stack.connect()) {
            LOG.severe("Unable to connect to BTStack daemon.  Things are not going to work.");
        }
        stack.BTstackSetPowerMode(1);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        App it = new App();
        it.init();
    }

    @Override
    public void handlePacket(Packet packet) {
        EventFactory.eventForPacket(packet).handle(hrc);
    }

}
