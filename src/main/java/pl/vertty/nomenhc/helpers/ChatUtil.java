package pl.vertty.nomenhc.helpers;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
    private static final HashMap<Material, String> polishNames;
    public static String nmsver = Bukkit.getServer().getClass().getPackage().getName();

    static {
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
    }
    public static String getPolishMaterial(Material material) {
        String name = polishNames.get(material);
        if (name == null || name.equals(""))
            name = material.name().toLowerCase().replace("_", " ");
        return name;
    }

    public static void removeItems(List<ItemStack> items, Player player) {
        PlayerInventory playerInventory = player.getInventory();
        List<ItemStack> removes = new ArrayList<>();
        for (ItemStack item : items) {
            if (playerInventory.containsAtLeast(item, item.getAmount()))
                removes.add(item);
        }
        if (removes.size() == items.size())
            for (ItemStack item : items) {
                for (ItemStack remove : removes) {
                    if (item.getType().equals(remove.getType()) && item.getData().equals(remove.getData()))
                        playerInventory.removeItem(item);
                }
            }
        removes.clear();
    }

    public static String getItems(List<ItemStack> items) {
        StringBuilder sb = new StringBuilder();
        for (ItemStack item : items)
            sb.append(getPolishMaterial(item.getType())).append(" &7(").append(item.getAmount()).append("&7szt.)").append("&7,&c");
        return ChatUtil.fixColor(sb.toString());
    }
    public static List<ItemStack> getItems(String string, int modifier) {
        List<ItemStack> items = new ArrayList<>();
        for (String s : string.split(";")) {
            String[] split = s.split("-");
            int id = Integer.parseInt(split[0].split(":")[0]);
            int data = Integer.parseInt(split[0].split(":")[1]);
            int amount = Integer.parseInt(split[1]) * modifier;
            items.add(new ItemStack(Material.getMaterial(id), amount, (short)data));
        }
        return items;
    }

    public static boolean checkItems(List<ItemStack> items, Player p) {
        for (ItemStack item : items) {
            if (!p.getInventory().containsAtLeast(item, item.getAmount()))
                return false;
        }
        return true;
    }

    public static void giveItems(Player p, ItemStack... items) {
        PlayerInventory playerInventory = p.getInventory();
        HashMap<Integer, ItemStack> notStored = playerInventory.addItem(items);
        for (Map.Entry<Integer, ItemStack> e : notStored.entrySet())
            p.getWorld().dropItemNaturally(p.getLocation(), e.getValue());
    }
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public static String getDate(long time) {
        return dateFormat.format(new Date(time));
    }

    public static String getTime(long time) {
        return timeFormat.format(new Date(time));
    }

    public static long parseDateDiff(String time, boolean future) {
        try {
            Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
            Matcher m = timePattern.matcher(time);
            int years = 0;
            int months = 0;
            int weeks = 0;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            boolean found = false;
            while (m.find()) {
                if (m.group() != null && !m.group().isEmpty()) {
                    for (int i = 0; i < m.groupCount(); i++) {
                        if (m.group(i) != null && !m.group(i).isEmpty()) {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        continue;
                    if (m.group(1) != null && !m.group(1).isEmpty())
                        years = Integer.parseInt(m.group(1));
                    if (m.group(2) != null && !m.group(2).isEmpty())
                        months = Integer.parseInt(m.group(2));
                    if (m.group(3) != null && !m.group(3).isEmpty())
                        weeks = Integer.parseInt(m.group(3));
                    if (m.group(4) != null && !m.group(4).isEmpty())
                        days = Integer.parseInt(m.group(4));
                    if (m.group(5) != null && !m.group(5).isEmpty())
                        hours = Integer.parseInt(m.group(5));
                    if (m.group(6) != null && !m.group(6).isEmpty())
                        minutes = Integer.parseInt(m.group(6));
                    if (m.group(7) == null)
                        break;
                    if (m.group(7).isEmpty())
                        break;
                    seconds = Integer.parseInt(m.group(7));
                    break;
                }
            }
            if (!found)
                return -1L;
            Calendar c = new GregorianCalendar();
            if (years > 0)
                c.add(1, years * (future ? 1 : -1));
            if (months > 0)
                c.add(2, months * (future ? 1 : -1));
            if (weeks > 0)
                c.add(3, weeks * (future ? 1 : -1));
            if (days > 0)
                c.add(5, days * (future ? 1 : -1));
            if (hours > 0)
                c.add(11, hours * (future ? 1 : -1));
            if (minutes > 0)
                c.add(12, minutes * (future ? 1 : -1));
            if (seconds > 0)
                c.add(13, seconds * (future ? 1 : -1));
            Calendar max = new GregorianCalendar();
            max.add(1, 10);
            if (c.after(max))
                return max.getTimeInMillis();
            return c.getTimeInMillis();
        } catch (Exception e) {
            return -1L;
        }
    }

    public static Player getDamager(final EntityDamageByEntityEvent e) {
        final Entity damager = e.getDamager();
        if (damager instanceof Player) {
            return (Player)damager;
        }
        return null;
    }

    public static void sendActionBar(final Player player, final String m) {
        ActionBarUtil actionBarMessageEvent = new ActionBarUtil(player, fixColor(m));
        Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
        if (actionBarMessageEvent.isCancelled()) {
            return;
        }
        try {
            Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object p = c1.cast(player);
            Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
            Object o = c2.getConstructor(new Class[] { String.class }).newInstance(fixColor(m));
            Object ppoc = c4.getConstructor(new Class[] { c3, byte.class }).newInstance(o, Byte.valueOf((byte)2));
            Method m1 = c1.getDeclaredMethod("getHandle");
            Object h = m1.invoke(p);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendTitle(final Player player, String title, String subtitle) {
        if (title == null)
            title = "";
        if (subtitle == null)
            subtitle = "";
        title = title.replace("&", "");
        subtitle = subtitle.replace("&", "");
        CraftPlayer craftPlayer = (CraftPlayer) player;
        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        (craftPlayer.getHandle()).playerConnection.sendPacket(packetTitle);
        IChatBaseComponent chatSubtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
        PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSubtitle);
        (craftPlayer.getHandle()).playerConnection.sendPacket(packetSubtitle);
    }

    public static String fixColor(final String s) {
        if (s == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', s).replace(">>", "»").replace("<<", "«").replace("*", "\u25cf").replace("{O}", "\u2022");
    }

    public static Collection<String> fixColor(final Collection<String> collection) {
        final Collection<String> local = new ArrayList<String>();
        for (final String s : collection) {
            local.add(fixColor(s));
        }
        return local;
    }

    public static List<String> fColor(final List<String> strings) {
        final List<String> colors = new ArrayList<String>();
        for (final String s : strings) {
            colors.add(fixColor(s));
        }
        return colors;
    }

    public static int floor(final double value) {
        final int i = (int)value;
        return (value < i) ? (i - 1) : i;
    }

    public static double round(final double value, final int decimals) {
        final double p = Math.pow(10.0, decimals);
        return Math.round(value * p) / p;
    }

    public static String[] fixColor(final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            array[i] = fixColor(array[i]);
        }
        return array;
    }

    public static boolean sendMessage(final CommandSender sender, final String message, final String permission) {
        if (sender instanceof ConsoleCommandSender) {
            sendMessage(sender, message);
        }
        return permission != null && permission != "" && sender.hasPermission(permission) && sendMessage(sender, message);
    }

    public static boolean sendMessage(final CommandSender sender, final String message) {
        if (sender instanceof Player) {
            if (message != null || message != "") {
                sender.sendMessage(fixColor(message));
            }
        }
        else {
            sender.sendMessage(fixColor(message));
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> collection, final String message) {
        for (final CommandSender cs : collection) {
            sendMessage(cs, message);
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> collection, final String message, final String permission) {
        for (final CommandSender cs : collection) {
            sendMessage(cs, message, permission);
        }
        return true;
    }

    public static boolean containsIgnoreCase(final String[] array, final String element) {
        for (final String s : array) {
            if (s.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }

    public static boolean isFloat(final String string) {
        return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
    }

    public static boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }


    static {
        (polishNames = new HashMap<>()).put(Material.AIR, " Reka");
        polishNames.put(Material.STONE, " Kamien");
        polishNames.put(Material.GRASS, " Bloki trawy");
        polishNames.put(Material.DIRT, " Ziemia");
        polishNames.put(Material.COBBLESTONE, " Bruk");
        polishNames.put(Material.WOOD, " Deski");
        polishNames.put(Material.SAPLING, " Sadzonka");
        polishNames.put(Material.BEDROCK, " Bedrock");
        polishNames.put(Material.WATER, " Woda");
        polishNames.put(Material.STATIONARY_WATER, " Woda");
        polishNames.put(Material.LAVA, " Lawa");
        polishNames.put(Material.STATIONARY_LAVA, " Lawa");
        polishNames.put(Material.SAND, " Piasek");
        polishNames.put(Material.GRAVEL, " Zwir");
        polishNames.put(Material.GOLD_ORE, " Ruda zlota");
        polishNames.put(Material.IRON_ORE, " Ruda zelaza");
        polishNames.put(Material.COAL_ORE, " Ruda wegla");
        polishNames.put(Material.LOG, " Drewno");
        polishNames.put(Material.LEAVES, " Liscie");
        polishNames.put(Material.SPONGE, " Gabka");
        polishNames.put(Material.GLASS, " Szklo");
        polishNames.put(Material.LAPIS_ORE, " Ruda lipisu");
        polishNames.put(Material.LAPIS_BLOCK, " Blok lapisu");
        polishNames.put(Material.DISPENSER, " Dozownik");
        polishNames.put(Material.SANDSTONE, " Pisakowiec");
        polishNames.put(Material.NOTE_BLOCK, " Note block");
        polishNames.put(Material.BED_BLOCK, " Lozko");
        polishNames.put(Material.POWERED_RAIL, " Zasilane tory");
        polishNames.put(Material.DETECTOR_RAIL, " Tory z czujnikiem");
        polishNames.put(Material.PISTON_STICKY_BASE, " Tlok");
        polishNames.put(Material.WEB, " Pajeczyna");
        polishNames.put(Material.LONG_GRASS, " Trawa");
        polishNames.put(Material.DEAD_BUSH, " Uschneity krzak");
        polishNames.put(Material.PISTON_BASE, " Tlok");
        polishNames.put(Material.PISTON_EXTENSION, " Tlok");
        polishNames.put(Material.WOOL, " Welna");
        polishNames.put(Material.PISTON_MOVING_PIECE, " Tlok");
        polishNames.put(Material.YELLOW_FLOWER, " Tulipan");
        polishNames.put(Material.RED_ROSE, " Roza");
        polishNames.put(Material.BROWN_MUSHROOM, " Brazowy grzyb");
        polishNames.put(Material.RED_MUSHROOM, " Muchomor");
        polishNames.put(Material.GOLD_BLOCK, " Blok zlota");
        polishNames.put(Material.IRON_BLOCK, " Blok zelaza");
        polishNames.put(Material.DOUBLE_STEP, " Podwojna polplytka");
        polishNames.put(Material.STEP, " Polplytka");
        polishNames.put(Material.BRICK, " Cegly");
        polishNames.put(Material.TNT, " Tnt");
        polishNames.put(Material.BOOKSHELF, " Biblioteczka");
        polishNames.put(Material.MOSSY_COBBLESTONE, " Zamszony bruk");
        polishNames.put(Material.OBSIDIAN, " Obsydian");
        polishNames.put(Material.TORCH, " Pochodnia");
        polishNames.put(Material.FIRE, " Ogien");
        polishNames.put(Material.MOB_SPAWNER, " Mob spawner");
        polishNames.put(Material.WOOD_STAIRS, " Drewniane schodki");
        polishNames.put(Material.CHEST, " Skrzynia");
        polishNames.put(Material.REDSTONE_WIRE, " Redstone");
        polishNames.put(Material.DIAMOND_ORE, " Ruda diamentu");
        polishNames.put(Material.DIAMOND_BLOCK, " Blok diamentu");
        polishNames.put(Material.WORKBENCH, " Stol rzemieslniczy");
        polishNames.put(Material.CROPS, " Nasionka");
        polishNames.put(Material.SOIL, " Nasionka");
        polishNames.put(Material.FURNACE, " Piecyk");
        polishNames.put(Material.BURNING_FURNACE, " Piecyk");
        polishNames.put(Material.SIGN_POST, " Tabliczka");
        polishNames.put(Material.WOODEN_DOOR, " Drewniane drzwi");
        polishNames.put(Material.LADDER, " Drabinka");
        polishNames.put(Material.RAILS, " Tory");
        polishNames.put(Material.COBBLESTONE_STAIRS, " brukowe schody");
        polishNames.put(Material.WALL_SIGN, " Tabliczka");
        polishNames.put(Material.LEVER, " Dzwignia");
        polishNames.put(Material.STONE_PLATE, " Plytka naciskowa");
        polishNames.put(Material.IRON_DOOR_BLOCK, " Zelazne drzwi");
        polishNames.put(Material.WOOD_PLATE, " Plytka nasickowa");
        polishNames.put(Material.REDSTONE_ORE, " Ruda redstone");
        polishNames.put(Material.GLOWING_REDSTONE_ORE, " Ruda redstone");
        polishNames.put(Material.REDSTONE_TORCH_OFF, " Czerwona pochodnia");
        polishNames.put(Material.REDSTONE_TORCH_ON, " Czerwona pochodnia");
        polishNames.put(Material.STONE_BUTTON, " Kamienny przycisk");
        polishNames.put(Material.SNOW, " Snieg");
        polishNames.put(Material.ICE, " Lod");
        polishNames.put(Material.SNOW_BLOCK, " Snieg");
        polishNames.put(Material.CACTUS, " Kaktus");
        polishNames.put(Material.CLAY, " Glina");
        polishNames.put(Material.SUGAR_CANE_BLOCK, " Trzcina");
        polishNames.put(Material.JUKEBOX, " Szafa grajaca");
        polishNames.put(Material.FENCE, " Plotek");
        polishNames.put(Material.PUMPKIN, " Dynia");
        polishNames.put(Material.NETHERRACK, " Netherrack");
        polishNames.put(Material.SOUL_SAND, " Pisaek dusz");
        polishNames.put(Material.GLOWSTONE, " Jasnoglaz");
        polishNames.put(Material.PORTAL, " Portal");
        polishNames.put(Material.JACK_O_LANTERN, " Jack'o'latern");
        polishNames.put(Material.CAKE_BLOCK, " Ciasto");
        polishNames.put(Material.DIODE_BLOCK_OFF, " Przekaznik");
        polishNames.put(Material.DIODE_BLOCK_ON, " Przekaznik");
        polishNames.put(Material.STAINED_GLASS, " Utwardzone szklo");
        polishNames.put(Material.TRAP_DOOR, " Wlaz");
        polishNames.put(Material.MONSTER_EGGS, " Jajko potwora");
        polishNames.put(Material.SMOOTH_BRICK, " Cegly");
        polishNames.put(Material.HUGE_MUSHROOM_1, " Duzy grzyb");
        polishNames.put(Material.HUGE_MUSHROOM_2, " Duzy grzyb");
        polishNames.put(Material.IRON_FENCE, " Kraty");
        polishNames.put(Material.THIN_GLASS, " Szyba");
        polishNames.put(Material.MELON_BLOCK, " Arbuz");
        polishNames.put(Material.PUMPKIN_STEM, " Dynia");
        polishNames.put(Material.MELON_STEM, " Arbuz");
        polishNames.put(Material.VINE, " Pnacze");
        polishNames.put(Material.FENCE_GATE, " Furtka");
        polishNames.put(Material.BRICK_STAIRS, " Ceglane schodki");
        polishNames.put(Material.SMOOTH_STAIRS, " Kamienne schodki");
        polishNames.put(Material.MYCEL, " Grzybnia");
        polishNames.put(Material.WATER_LILY, " Lilia wodna");
        polishNames.put(Material.NETHER_BRICK, " Cegly netherowe");
        polishNames.put(Material.NETHER_FENCE, " Netherowy plotek");
        polishNames.put(Material.NETHER_BRICK_STAIRS, " Netherowe schodki");
        polishNames.put(Material.NETHER_WARTS, " Brodawki");
        polishNames.put(Material.ENCHANTMENT_TABLE, " Stol do enchantu");
        polishNames.put(Material.BREWING_STAND, " Stol alchemiczny");
        polishNames.put(Material.CAULDRON, " Kociol");
        polishNames.put(Material.ENDER_PORTAL, " Ender portal");
        polishNames.put(Material.ENDER_PORTAL_FRAME, " Ender portal");
        polishNames.put(Material.ENDER_STONE, " Kamien kresu");
        polishNames.put(Material.DRAGON_EGG, " Jajko smoka");
        polishNames.put(Material.REDSTONE_LAMP_OFF, " Lampa");
        polishNames.put(Material.REDSTONE_LAMP_ON, " Lampa");
        polishNames.put(Material.WOOD_DOUBLE_STEP, " Podwojna drewniana polplytka");
        polishNames.put(Material.WOOD_STEP, " Drewnania polplytka");
        polishNames.put(Material.COCOA, " Kakao");
        polishNames.put(Material.SANDSTONE_STAIRS, " Piaskowe schodki");
        polishNames.put(Material.EMERALD_ORE, " Ruda szmaragdu");
        polishNames.put(Material.ENDER_CHEST, " Skrzynia kresu");
        polishNames.put(Material.TRIPWIRE_HOOK, " Potykacz");
        polishNames.put(Material.TRIPWIRE, " Potykacz");
        polishNames.put(Material.EMERALD_BLOCK, " Blok szmaragdu");
        polishNames.put(Material.SPRUCE_WOOD_STAIRS, " Drewniane schodki");
        polishNames.put(Material.BIRCH_WOOD_STAIRS, " Drewniane schodki");
        polishNames.put(Material.JUNGLE_WOOD_STAIRS, " Drewniane schodki");
        polishNames.put(Material.COMMAND, " Blok polecen");
        polishNames.put(Material.BEACON, " Magiczna latarnia");
        polishNames.put(Material.COBBLE_WALL, " Brukowy plotek");
        polishNames.put(Material.FLOWER_POT, " Doniczka");
        polishNames.put(Material.CARROT, " Marchewka");
        polishNames.put(Material.POTATO, " Ziemniak");
        polishNames.put(Material.WOOD_BUTTON, " drewniany przycisk");
        polishNames.put(Material.SKULL, " Glowa");
        polishNames.put(Material.ANVIL, " Kowadlo");
        polishNames.put(Material.TRAPPED_CHEST, " Skrzynka z pulapka");
        polishNames.put(Material.GOLD_PLATE, " Zlota polplytka");
        polishNames.put(Material.IRON_PLATE, " Zelaza polplytka");
        polishNames.put(Material.REDSTONE_COMPARATOR_OFF, " Komparator");
        polishNames.put(Material.REDSTONE_COMPARATOR_ON, " Komparator");
        polishNames.put(Material.DAYLIGHT_DETECTOR, " Detektor swiatla dziennego");
        polishNames.put(Material.REDSTONE_BLOCK, " Blok redstone");
        polishNames.put(Material.QUARTZ_ORE, " Ruda kwarcu");
        polishNames.put(Material.HOPPER, " Lej");
        polishNames.put(Material.QUARTZ_BLOCK, " Blok kwarcu");
        polishNames.put(Material.QUARTZ_STAIRS, " Lwarcowe schodki");
        polishNames.put(Material.ACTIVATOR_RAIL, " Tory aktywacyjne");
        polishNames.put(Material.DROPPER, " Podajnik");
        polishNames.put(Material.STAINED_CLAY, " Utwardzona glina");
        polishNames.put(Material.STAINED_GLASS_PANE, " Utwardzona szyba");
        polishNames.put(Material.LEAVES_2, " Liscie");
        polishNames.put(Material.LOG_2, " Drewno");
        polishNames.put(Material.ACACIA_STAIRS, " Drewniane schodki");
        polishNames.put(Material.DARK_OAK_STAIRS, " Drewniane schodki");
        polishNames.put(Material.HAY_BLOCK, " Sloma");
        polishNames.put(Material.CARPET, " Dywan");
        polishNames.put(Material.HARD_CLAY, " Glina");
        polishNames.put(Material.COAL_BLOCK, " Blok wegla");
        polishNames.put(Material.PACKED_ICE, " Utwardzony lod");
        polishNames.put(Material.DOUBLE_PLANT, " Sadzonka");
        polishNames.put(Material.IRON_SPADE, " Zelazna lopata");
        polishNames.put(Material.IRON_PICKAXE, " Zelazny kilof");
        polishNames.put(Material.IRON_AXE, " Zelazna siekiera");
        polishNames.put(Material.FLINT_AND_STEEL, " Zapalniczka");
        polishNames.put(Material.APPLE, " Jablko");
        polishNames.put(Material.BOW, " Luk");
        polishNames.put(Material.ARROW, " Strzala");
        polishNames.put(Material.COAL, " Wegiel");
        polishNames.put(Material.DIAMOND, " Diament");
        polishNames.put(Material.IRON_INGOT, " Sztabka zelaza");
        polishNames.put(Material.GOLD_INGOT, " Sztabka zlota");
        polishNames.put(Material.IRON_SWORD, " Zelazny miecz");
        polishNames.put(Material.WOOD_SWORD, " Drewniany miecz");
        polishNames.put(Material.WOOD_SPADE, " Drewniana lopata");
        polishNames.put(Material.WOOD_PICKAXE, " Drewniany kilof");
        polishNames.put(Material.WOOD_AXE, " Drewnania siekiera");
        polishNames.put(Material.STONE_SWORD, " Kamienny miecz");
        polishNames.put(Material.STONE_SPADE, " Kamienna lopata");
        polishNames.put(Material.STONE_PICKAXE, " Kamienny kilof");
        polishNames.put(Material.STONE_AXE, " Kamienna siekiera");
        polishNames.put(Material.DIAMOND_SWORD, " Diamentowy miecz");
        polishNames.put(Material.DIAMOND_SPADE, " Diamentowa lopata");
        polishNames.put(Material.DIAMOND_PICKAXE, " Diamentowy kilof");
        polishNames.put(Material.DIAMOND_AXE, " Diamentowa siekiera");
        polishNames.put(Material.STICK, " Patyk");
        polishNames.put(Material.BOWL, " Miseczka");
        polishNames.put(Material.MUSHROOM_SOUP, " Zupa grzybowa");
        polishNames.put(Material.GOLD_SWORD, " Zloty miecz");
        polishNames.put(Material.GOLD_SPADE, " Zlota lopata");
        polishNames.put(Material.GOLD_PICKAXE, " Zloty kilof");
        polishNames.put(Material.GOLD_AXE, " Zlota siekiera");
        polishNames.put(Material.STRING, " Nitka");
        polishNames.put(Material.FEATHER, " Pioro");
        polishNames.put(Material.SULPHUR, " Proch strzelniczy");
        polishNames.put(Material.WOOD_HOE, " Drewniana motyka");
        polishNames.put(Material.STONE_HOE, " Kamienna motyka");
        polishNames.put(Material.IRON_HOE, " Zelazna motyka");
        polishNames.put(Material.DIAMOND_HOE, " Diemtnowa motyka");
        polishNames.put(Material.GOLD_HOE, " Zlota motyka");
        polishNames.put(Material.SEEDS, " Nasionka");
        polishNames.put(Material.WHEAT, " Pszenica");
        polishNames.put(Material.BREAD, " Chleb");
        polishNames.put(Material.LEATHER_HELMET, " Skorzany helm");
        polishNames.put(Material.LEATHER_CHESTPLATE, " Skorzana klata");
        polishNames.put(Material.LEATHER_LEGGINGS, " Skorzane spodnie");
        polishNames.put(Material.LEATHER_BOOTS, " Skorzane buty");
        polishNames.put(Material.CHAINMAIL_HELMET, " Helm z kolcza");
        polishNames.put(Material.CHAINMAIL_CHESTPLATE, " Klata z kolcza");
        polishNames.put(Material.CHAINMAIL_LEGGINGS, " Spodnie z kolcza");
        polishNames.put(Material.CHAINMAIL_BOOTS, " Buty z kolcza");
        polishNames.put(Material.IRON_HELMET, "Zelazny helm");
        polishNames.put(Material.IRON_CHESTPLATE, " Zelazna klata");
        polishNames.put(Material.IRON_LEGGINGS, " Zelazne spodnie");
        polishNames.put(Material.IRON_BOOTS, " Zelazne buty");
        polishNames.put(Material.DIAMOND_HELMET, " Diamentowy helm");
        polishNames.put(Material.DIAMOND_CHESTPLATE, " Diamentowa klata");
        polishNames.put(Material.DIAMOND_LEGGINGS, " Diamentowe spodnie");
        polishNames.put(Material.DIAMOND_BOOTS, " Diamentowe buty");
        polishNames.put(Material.GOLD_HELMET, " Zloty helm");
        polishNames.put(Material.GOLD_CHESTPLATE, " Zlota klata");
        polishNames.put(Material.GOLD_LEGGINGS, " Zlote spodnie");
        polishNames.put(Material.GOLD_BOOTS, " Zlote buty");
        polishNames.put(Material.FLINT, " Krzemien");
        polishNames.put(Material.PORK, " Schab");
        polishNames.put(Material.GRILLED_PORK, " Pieczony schab");
        polishNames.put(Material.PAINTING, " Obraz");
        polishNames.put(Material.GOLDEN_APPLE, " Zlote jablko");
        polishNames.put(Material.SIGN, " Znak");
        polishNames.put(Material.WOOD_DOOR, " Drewniane drzwi");
        polishNames.put(Material.BUCKET, " Wiaderko");
        polishNames.put(Material.WATER_BUCKET, " Wiaderko wody");
        polishNames.put(Material.LAVA_BUCKET, " Wiaderko lawy");
        polishNames.put(Material.MINECART, " Wagonik");
        polishNames.put(Material.SADDLE, " Siodlo");
        polishNames.put(Material.IRON_DOOR, " Zelazne drzwi");
        polishNames.put(Material.REDSTONE, " Czerwony proszek");
        polishNames.put(Material.SNOW_BALL, " Sniezka");
        polishNames.put(Material.BOAT, " Lodka");
        polishNames.put(Material.LEATHER, " Skora");
        polishNames.put(Material.MILK_BUCKET, " Wiaderko mleka");
        polishNames.put(Material.CLAY_BRICK, " Cegly");
        polishNames.put(Material.CLAY_BALL, " Kulka gliny");
        polishNames.put(Material.SUGAR_CANE, " Trzcina cukrowa");
        polishNames.put(Material.PAPER, " Papier");
        polishNames.put(Material.BOOK, " Ksiazka");
        polishNames.put(Material.SLIME_BALL, " Kulka szlamu");
        polishNames.put(Material.STORAGE_MINECART, " Wagonik");
        polishNames.put(Material.POWERED_MINECART, " Wagonik");
        polishNames.put(Material.EGG, " Jajko");
        polishNames.put(Material.COMPASS, " Kompas");
        polishNames.put(Material.FISHING_ROD, " Wedka");
        polishNames.put(Material.WATCH, " Zegar");
        polishNames.put(Material.GLOWSTONE_DUST, " Jasnopyl");
        polishNames.put(Material.RAW_FISH, " Ryba");
        polishNames.put(Material.COOKED_FISH, " Pieczona ryba");
        polishNames.put(Material.INK_SACK, " Czarny barwnik");
        polishNames.put(Material.BONE, " Kosc");
        polishNames.put(Material.SUGAR, " Cukier");
        polishNames.put(Material.CAKE, " Ciasto");
        polishNames.put(Material.BED, " Lozko");
        polishNames.put(Material.DIODE, " Przekaznik");
        polishNames.put(Material.COOKIE, " Ciastko");
        polishNames.put(Material.MAP, " Mapa");
        polishNames.put(Material.SHEARS, " Nozyce");
        polishNames.put(Material.MELON, " Arbuz");
        polishNames.put(Material.PUMPKIN_SEEDS, " Nasiono dyni");
        polishNames.put(Material.MELON_SEEDS, " Nasiono melona");
        polishNames.put(Material.RAW_BEEF, " Stek");
        polishNames.put(Material.COOKED_BEEF, " Pieczony stek");
        polishNames.put(Material.RAW_CHICKEN, " Kurczak");
        polishNames.put(Material.COOKED_CHICKEN, " Upieczony kurczak");
        polishNames.put(Material.ROTTEN_FLESH, " Zgnile mieso");
        polishNames.put(Material.ENDER_PEARL, " Perla endermana");
        polishNames.put(Material.BLAZE_ROD, " Palka blaza");
        polishNames.put(Material.GHAST_TEAR, " Lza gasta");
        polishNames.put(Material.GOLD_NUGGET, " Zloty samorodek");
        polishNames.put(Material.NETHER_STALK, " Brodawka netherowa");
        polishNames.put(Material.POTION, " Mikstura");
        polishNames.put(Material.GLASS_BOTTLE, " Szklana butelka");
        polishNames.put(Material.SPIDER_EYE, " Oko pajaka");
        polishNames.put(Material.FERMENTED_SPIDER_EYE, " Zfermentowane oko pajaka");
        polishNames.put(Material.BLAZE_POWDER, " Blaze powder");
        polishNames.put(Material.MAGMA_CREAM, " Magmowy krem");
        polishNames.put(Material.BREWING_STAND_ITEM, " Stol alchemiczny");
        polishNames.put(Material.CAULDRON_ITEM, " Kociol");
        polishNames.put(Material.EYE_OF_ENDER, " Sko kresu");
        polishNames.put(Material.SPECKLED_MELON, " Arbuz");
        polishNames.put(Material.MONSTER_EGG, " Jajko spawnujace");
        polishNames.put(Material.EXP_BOTTLE, " Butelka z expem");
        polishNames.put(Material.FIREBALL, " Kula ognia");
        polishNames.put(Material.BOOK_AND_QUILL, " Ksiazka z piorem");
        polishNames.put(Material.WRITTEN_BOOK, " Zapisana ksiazka");
        polishNames.put(Material.EMERALD, " Emerald");
        polishNames.put(Material.ITEM_FRAME, " Ramka na obraz");
        polishNames.put(Material.FLOWER_POT_ITEM, " Doniczka");
        polishNames.put(Material.CARROT_ITEM, " Marchewka");
        polishNames.put(Material.POTATO_ITEM, " Ziemniak");
        polishNames.put(Material.BAKED_POTATO, " Upieczony ziemniak");
        polishNames.put(Material.POISONOUS_POTATO, " Trujacy ziemniak");
        polishNames.put(Material.EMPTY_MAP, " Pusta mapa");
        polishNames.put(Material.GOLDEN_CARROT, " Zlota marchewka");
        polishNames.put(Material.SKULL_ITEM, " Glowa");
        polishNames.put(Material.CARROT_STICK, " Marchewka na patyku");
        polishNames.put(Material.NETHER_STAR, " Gwiazda netherowa");
        polishNames.put(Material.PUMPKIN_PIE, " Placek dyniowy");
        polishNames.put(Material.FIREWORK, " Fajerwerka");
        polishNames.put(Material.FIREWORK_CHARGE, " Fajerwerka");
        polishNames.put(Material.ENCHANTED_BOOK, " Enchantowana ksiazka");
        polishNames.put(Material.REDSTONE_COMPARATOR, " Komperator");
        polishNames.put(Material.NETHER_BRICK_ITEM, " Cegla netherowa");
        polishNames.put(Material.QUARTZ, " Kwarc");
        polishNames.put(Material.EXPLOSIVE_MINECART, " Wagonik z tnt");
        polishNames.put(Material.HOPPER_MINECART, " Wagonik z lejem");
        polishNames.put(Material.IRON_BARDING, " Zelazna motyka");
        polishNames.put(Material.GOLD_BARDING, " Zlota motyka");
        polishNames.put(Material.DIAMOND_BARDING, " Diamentowa motyka");
        polishNames.put(Material.LEASH, " Lasso");
        polishNames.put(Material.NAME_TAG, " Name tag");
        polishNames.put(Material.COMMAND_MINECART, " Wagonik z blokiem polecen");
        polishNames.put(Material.GOLD_RECORD, " Plyta muzyczna");
        polishNames.put(Material.GREEN_RECORD, " [lyta muzyczna");
        polishNames.put(Material.RECORD_3, " Plyta muzyczna");
        polishNames.put(Material.RECORD_4, " Plyta muzyczna");
        polishNames.put(Material.RECORD_5, " Plyta muzyczna");
        polishNames.put(Material.RECORD_6, " Plyta muzyczna");
        polishNames.put(Material.RECORD_7, " Plyta muzyczna");
        polishNames.put(Material.RECORD_8, " Plyta muzyczna");
        polishNames.put(Material.RECORD_9, " Plyta muzyczna");
        polishNames.put(Material.RECORD_10, " Plyta muzyczna");
        polishNames.put(Material.RECORD_11, " Plyta muzyczna");
        polishNames.put(Material.RECORD_12, " Plyta muzyczna");
        polishNames.put(Material.GOLDEN_APPLE, " Kox");
    }

}
