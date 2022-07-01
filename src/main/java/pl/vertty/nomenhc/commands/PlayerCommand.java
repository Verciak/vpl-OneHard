package pl.vertty.nomenhc.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommand extends Command
{
    public PlayerCommand(final String name, final String description, final String usage, final String permission, final String[] aliases) {
        super(name, description, usage, permission, aliases);
    }

    public boolean onExecute(final CommandSender sender, final String[] args) {
        if (sender instanceof ConsoleCommandSender){
            return this.onCommand((ConsoleCommandSender) sender, args);
        }
        return this.onCommand((Player)sender, args);
    }

    public abstract boolean onCommand(final Player p, final String[] args);

    public abstract boolean onCommand(final ConsoleCommandSender p, final String[] args);
}

