package pl.vertty.nomenhc.helpers;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.DataException;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.objects.Guild;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GuildSchematic {

    public static void pasteSchematic(Player p) {
        GuildPlugin.getPlugin().worldedit.getSession(Bukkit.getPlayerExact(p.getName()));
        LocalPlayer lp = GuildPlugin.getPlugin().worldedit.wrapPlayer(Bukkit.getPlayerExact(p.getName()));
        try {
//Your schematic must be in your plugins folder!
            SchematicFormat sf = SchematicFormat
                    .getFormat(new File(GuildPlugin.getPlugin().getDataFolder(), "guild.schematic"));
            CuboidClipboard cc = sf.load(new File(GuildPlugin.getPlugin().getDataFolder(), "guild.schematic"));
            Guild g = GuildHandler.get(p.getName());
            Location loc = new Location(Bukkit.getWorld("world"), g.getCenterX(), g.getCenterY(), g.getCenterZ());
            EditSession es = new EditSession(new BukkitWorld(loc.getWorld()), 999999999);
            cc.paste(es, BukkitUtil.toVector(loc), true);
        } catch (DataException | IOException | MaxChangedBlocksException e) {
            e.printStackTrace();
        }
    }
}

