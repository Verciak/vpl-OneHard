package pl.vertty.nomenhc.runnable;

import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import pl.vertty.nomenhc.helpers.HologramUpdate;


@AllArgsConstructor
public class DatabaseRunnable implements Runnable {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getScheduler().runTaskTimer(plugin,this,0,20*10);
    }

    @Override
    public void run() {
        DatabaseHelper.synchronize();
        GuildHandler.getGuilds().forEach(HologramUpdate::update);
    }

}
