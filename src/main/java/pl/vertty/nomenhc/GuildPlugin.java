package pl.vertty.nomenhc;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpTopic;
import org.bukkit.plugin.java.JavaPlugin;
import pl.vertty.nomenhc.commands.Command;
import pl.vertty.nomenhc.commands.CommandManager;
import pl.vertty.nomenhc.commands.user.SchowekCommand;
import pl.vertty.nomenhc.commands.admin.*;
import pl.vertty.nomenhc.commands.guilds.GuildCommand;
import pl.vertty.nomenhc.commands.helper.ClearCommand;
import pl.vertty.nomenhc.commands.user.*;
import pl.vertty.nomenhc.helpers.*;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.listeners.PlayerTabJoinListener;
import pl.vertty.nomenhc.listeners.*;
import pl.vertty.nomenhc.listeners.magic_case.CaseDropListener;
import pl.vertty.nomenhc.listeners.magic_case.CaseKickQuitListener;
import pl.vertty.nomenhc.listeners.magic_case.CasePlaceListener;
import pl.vertty.nomenhc.manager.ProtocolTabManager;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;
import pl.vertty.nomenhc.objects.configs.DropConfig;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.runnable.ActionBarRunnable;
import pl.vertty.nomenhc.runnable.DatabaseRunnable;
import pl.vertty.nomenhc.runnable.GuildExpireRunnable;

import javax.security.auth.login.LoginException;
import java.io.File;

public class GuildPlugin extends JavaPlugin {


    public static GuildPlugin plugin;
    public WorldEditPlugin worldedit;
    private ProtocolTabManager manager;
    public static GuildPlugin getPlugin() {
        return plugin;
    }
    public ProtocolTabManager getManager() {
        return manager;
    }

    @Override
    public void onEnable() {
        plugin = this;
        (new Ticking()).start();
        onLoadConfig();
        onLoadMessages();
        onLoadDropConfig();
        onLoadDiscord();
        final DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.connect();
        databaseHelper.load();
        DropManager.loadDrops();
        this.worldedit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldedit == null) {
            Bukkit.getConsoleSender().sendMessage("§cWymagany WorldEdit na serwerze!");
        }
        this.manager = new ProtocolTabManager(0);
        this.getServer().getPluginManager().registerEvents(new PlayerTabJoinListener(), this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            final Combat c = CombatManager.getCombat(p);
            if (c == null) {
                CombatManager.createCombat(p);
            }
        }
        new KitUtil(this);
         //runnable
        new DatabaseRunnable(this).register();
        new GuildExpireRunnable(this).register();
        new ActionBarRunnable(this).register();
        //new ArmorStandUpdateRunnable().start();

        // listeners
        new GuildBreakCenterListener(this).register();
        new DropListener(this).register();
        new BlockBreakPlaceListener(this).register();
        new CommandBlockListener(this).register();
        new EntityDamageByEntityListener(this).register();
        new EntityExplodeListener(this).register();
        new InventoryOpenListener(this).register();
        new PlayerBucketEmptyFillListener(this).register();
        new PlayerDeathListener(this).register();
        new PlayerJoinListener(this).register();
        new PlayerMoveListener(this).register();
        new PlayerQuitListener(this).register();
        new TeleportCancelListener(this).register();
        new UnknownCommandListener(this).register();
        new WeatherListener(this).register();
        new InventoryClickListener(this).register();
        new AsyncChatListener(this).register();
        new PandoraListener(this).register();
        new CobbleXListener(this).register();
        new CaseDropListener(this).register();
        new CaseKickQuitListener(this).register();
        new CasePlaceListener(this).register();

        // commands
        registerCommand(new CaseCommand());
        registerCommand(new PlayerCommand());
        registerCommand(new GuildCommand());
        registerCommand(new ConfigReloadCommand());
        registerCommand(new TeleportCommand());
        registerCommand(new GamemodeCommand());
        registerCommand(new BanCommand());
        registerCommand(new UnBanCommand());
        registerCommand(new DeviceCommand());
        registerCommand(new DropCommand());
        registerCommand(new RewardCommand());
        registerCommand(new TestCommand());
        registerCommand(new ClearCommand());
        registerCommand(new KitCommand());
        registerCommand(new SchowekCommand());
        registerCommand(new SpawnCommand());
        registerCommand(new TpaCommand());
        registerCommand(new TpacceptCommand());
        registerCommand(new TpDenyCommand());
        registerCommand(new PItemsCommand());
        registerCommand(new WhiteListCommand());
        registerCommand(new TellCommand());
        registerCommand(new ReplyCommand());
        for (HelpTopic cmdLabel : getServer().getHelpMap().getHelpTopics()) {
            UnknownCommandListener.registeredCommands.add(cmdLabel.getName());
        }
        //discord
        registerJDA();
        ReflectionHelper.initialize();
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
        for (final Player p : Bukkit.getOnlinePlayers()) {
            CombatManager.removeCombat(p);
        }
    }
    public static void registerCommand(final Command command) {
        CommandManager.register(command);
    }


    private void registerJDA() {
        try {
            JDABuilder jDABuilder = JDABuilder.createDefault(DiscordConfig.getInstance().Token);
            jDABuilder.setBulkDeleteSplittingEnabled(false);
            if (DiscordConfig.getInstance().activity_enable) {
                if (DiscordConfig.getInstance().activity_name == null) {
                    this.getLogger().warning("[!] Napis aktywnosci nie moze byc null, ustaw go lub wylacz ta opcje.");
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
                if (DiscordConfig.getInstance().activity_type.equals("WATCHING"))
                    jDABuilder.setActivity(Activity.watching(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size()))));
                if (DiscordConfig.getInstance().activity_type.equals("LISTENING"))
                    jDABuilder.setActivity(Activity.listening(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size()))));
                if (DiscordConfig.getInstance().activity_type.equals("PLAYING"))
                    jDABuilder.setActivity(Activity.playing(DiscordConfig.getInstance().activity_name.replace("{ONLINE}", String.valueOf(Bukkit.getOnlinePlayers().size()))));
            }
            jDABuilder.addEventListeners(new ChannelListener(this));
            jDABuilder.build();
        } catch (LoginException loginException) {
            this.getLogger().info("[!] Nieprawidlowy token bota! Sprawdz jego poprawnosc i sprobuj ponownie.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    public void onLoadDropConfig() {
        DropConfig.init(new File("./plugins/vpl-OneHard"));
        DropConfig.load("./plugins/vpl-OneHard/DropConfig.json");
        DropConfig.getInstance().toFile("./plugins/vpl-OneHard/DropConfig.json");
    }

    public void onLoadConfig() {
        Config.init(new File("./plugins/vpl-OneHard"));
        Config.load("./plugins/vpl-OneHard/config.json");
        Config.getInstance().toFile("./plugins/vpl-OneHard/config.json");
    }

    public void onLoadMessages() {
        Messages.init(new File("./plugins/vpl-OneHard"));
        Messages.load("./plugins/vpl-OneHard/messages.json");
        Messages.getInstance().toFile("./plugins/vpl-OneHard/messages.json");
    }

    public void onLoadDiscord() {
        DiscordConfig.init(new File("./plugins/vpl-OneHard"));
        DiscordConfig.load("./plugins/vpl-OneHard/DiscordConfig.json");
        DiscordConfig.getInstance().toFile("./plugins/vpl-OneHard/DiscordConfig.json");
    }

}
