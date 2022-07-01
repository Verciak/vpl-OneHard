package pl.vertty.nomenhc.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.helpers.ColorHelper;

import java.util.Arrays;

public abstract class Command extends org.bukkit.command.Command
{
    private final String name;
    private final String usage;
    private final String description;
    private final String permission;

    public Command(final String name, final String description, final String usage, final String permission, final String[] aliases) {
        super(name, description, usage, Arrays.asList(aliases));
        this.name = name;
        this.usage = usage;
        this.description = description;
        this.permission = permission;
    }

    public boolean execute(final CommandSender sender, final String label, final String[] args) {
        if (sender instanceof Player) {
            if(!permission.isEmpty()) {
                if (!sender.hasPermission(permission)) {
                    String msg = "&cWymagana permisja do uzycia tej komendy to &4" + permission;
                    sender.sendMessage(ColorHelper.colored(msg));
                    return false;
                }
            }
        }
        return this.onExecute(sender, args);
    }

    public abstract boolean onExecute(final CommandSender p0, final String[] p1);

    public String getName() {
        return this.name;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPermission() {
        return this.permission;
    }

}

