package pl.vertty.nomenhc.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.TeleportHandler;
import pl.vertty.nomenhc.menu.drop.DropInventory;
import pl.vertty.nomenhc.objects.Teleport;

public class SpawnCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public SpawnCommand() {
        super("spawn", "Komenda spawn", "spawn", "", new String[] { "spawn" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        new Teleport(p.getUniqueId(),System.currentTimeMillis() + 10 * 1000, Bukkit.getScheduler().runTaskLater(GuildPlugin.getPlugin(),() -> {
            Teleport.remove(p.getUniqueId());
            p.teleport(Bukkit.getServer().getWorld("world").getSpawnLocation());
        },10*20));
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
