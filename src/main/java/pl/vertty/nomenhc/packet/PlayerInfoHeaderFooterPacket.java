package pl.vertty.nomenhc.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.apache.commons.lang.Validate;

public class PlayerInfoHeaderFooterPacket extends Packet {

    public static final PacketType TYPE = PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER;

    public PlayerInfoHeaderFooterPacket() {
        super(new PacketContainer(TYPE), TYPE);
    }

    public PlayerInfoHeaderFooterPacket(PacketContainer packet) {
        super(packet, TYPE);
    }

    public WrappedChatComponent getHeader() {
        return this.getHandle().getChatComponents().read(0);
    }

    public WrappedChatComponent getFooter() {
        return this.getHandle().getChatComponents().read(1);
    }

    public void setHeader(WrappedChatComponent value) {
        Validate.isTrue(value != null, "Value cannot be null!");

        this.getHandle().getChatComponents().write(0, value);
    }

    public void setFooter(WrappedChatComponent value) {
        Validate.isTrue(value != null, "Value cannot be null!");

        this.getHandle().getChatComponents().write(1, value);
    }

}
