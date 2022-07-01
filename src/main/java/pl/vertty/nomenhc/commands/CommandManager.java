package pl.vertty.nomenhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.help.HelpTopic;
import pl.vertty.nomenhc.helpers.Reflection;
import pl.vertty.nomenhc.listeners.UnknownCommandListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.bukkit.Bukkit.getServer;

public class CommandManager
{
    public static final HashMap<String, Command> commands;

    public static void register(final Command cmd) {
        try {
            final Field bukkitCommandMap = getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(getServer());

            commandMap.register(cmd.getName(), cmd);

            CommandManager.commands.put(cmd.getName(), cmd);
            UnknownCommandListener.registeredCommands.add(cmd.getName());

            if (cmd.getAliases() != null) {
                UnknownCommandListener.registeredCommands.addAll(cmd.getAliases());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    static {
        commands = new HashMap<>();
    }
}

