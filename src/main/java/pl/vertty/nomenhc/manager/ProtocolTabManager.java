package pl.vertty.nomenhc.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public final class ProtocolTabManager {

    private final Map<UUID, ProtocolTab> tabMap = new ConcurrentHashMap<>();
    private final Cache<UUID, ProtocolTab> tabCache = CacheBuilder.newBuilder()
                                                                .removalListener(new ProtocolTabRemovalListener(this))
                                                                .expireAfterAccess(5, TimeUnit.MINUTES)
                                                                .softValues()
                                                                .build();

    private final int ping;

    public ProtocolTabManager(int ping) {
        this.ping = ping;
    }

    public ProtocolTab getTablist(Player player) {
        Validate.isTrue(player != null, "Player cannot be null!");

        return this.getTablist(player.getUniqueId());
    }

    public ProtocolTab getTablist(UUID uuid) {
        Validate.isTrue(uuid != null, "UUID cannot be null!");

        ProtocolTab tablist = this.tabCache.getIfPresent(uuid);
        if (tablist == null) {
            tablist = this.tabMap.get(uuid);
            if (tablist != null) {
                tabCache.put(uuid, tablist);
                tabMap.remove(uuid);
            }

            tablist = new ProtocolTab(uuid, this.ping);
            tabCache.put(uuid, tablist);
        }

        return tablist;
    }

    private class ProtocolTabRemovalListener implements RemovalListener<UUID, ProtocolTab> {

        private final ProtocolTabManager manager;

        public ProtocolTabRemovalListener(ProtocolTabManager manager) {
            Validate.isTrue(manager != null, "Manager cannot be null!");

            this.manager = manager;
        }

        @Override
        public void onRemoval(RemovalNotification<UUID, ProtocolTab> notification) {
            UUID key = notification.getKey();
            ProtocolTab value = notification.getValue();

            if (key == null || value == null) {
                return;
            }

            this.manager.tabMap.put(key, value);
        }

    }

}
