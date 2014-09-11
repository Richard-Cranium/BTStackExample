package net.verizon.mflacy.heartratemonitor;

import com.bluekitchen.btstack.BT_UUID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author flacy
 * @param <PARENT> The class of the parent GATT object (such as GATTService)
 * @param <CHILD> The class of the child GATT object (such as GATTCharacteristic)
 */
public class GATTContainer<PARENT, CHILD> {
    private final PARENT name;
    private final Map<BT_UUID, CHILD> children;

    public GATTContainer(PARENT name) {
        this.name = name;
        this.children = new HashMap<>();
    }
    
    public void addChild(BT_UUID uuid, CHILD kid) {
        children.put(uuid, kid);
    }
    
    public Iterable<BT_UUID> getChildUUIDs() {
        return children.keySet();
    }
    
    public Iterable<CHILD> getChildren() {
        return children.values();
    }
    
    public CHILD getChild(BT_UUID uuid) {
        return children.get(uuid);
    }

    @Override
    public String toString() {
        return "GATTContainer{" + "name=" + name + ", children=" + children + '}';
    }
    
    public PARENT getName() {
        return name;
    }
    
    public Iterator<CHILD> getChildIterator() {
        return children.values().iterator();
    }
}
