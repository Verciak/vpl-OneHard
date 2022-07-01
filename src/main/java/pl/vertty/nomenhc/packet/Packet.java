package pl.vertty.nomenhc.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class Packet {

    private final PacketContainer handle;

    protected Packet(PacketContainer handle, PacketType type) {
        Validate.isTrue(handle != null, "Packet handle cannot be null!");
        Validate.isTrue(handle.getType().equals(type), handle.getHandle() + " is not a packet of type "
                                                               + type + "!");

        this.handle = handle;
        this.handle.getModifier().writeDefaults();
    }

    public void sendPacket(Player receiver) {
        Validate.isTrue(receiver != null, "Receiver cannot be null!");
        Validate.isTrue(receiver.isOnline(), "Receiver cannot be offline!");

        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.handle);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Cannot send packet!", ex);
        }
    }

    public void receivePacket(Player sender) {
        Validate.isTrue(sender != null, "Sender cannot be null!");
        Validate.isTrue(sender.isOnline(), "Sender cannot be offline!");

        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, this.handle);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot receive packet!", ex);
        }
    }

    public PacketContainer getHandle() {
        return this.handle;
    }

}
