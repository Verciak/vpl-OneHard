package pl.vertty.nomenhc.manager;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import org.apache.commons.lang.Validate;

public class ProtocolSlot {

    private final WrappedGameProfile profile;

    private String text;

    public ProtocolSlot(WrappedGameProfile profile, String text) {
        Validate.isTrue(profile != null, "Profile cannot be null!");
        Validate.isTrue(text != null, "Text cannot be null!");

        this.profile = profile;
        this.text = text;
    }

    public WrappedGameProfile getProfile() {
        return profile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        Validate.isTrue(text != null, "Text cannot be null!");

        this.text = text;
    }

    public void setText(BaseComponent... component) {
        Validate.isTrue(component != null, "Component cannot be null!");

        this.setText(BaseComponent.toLegacyText(component));
    }

}
