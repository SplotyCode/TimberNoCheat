package me.david.TimberNoCheat.checkmanager;

import com.comphenix.protocol.events.PacketListener;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.ViolationUpdateEvent;
import me.david.TimberNoCheat.checktools.Tps;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;

public class Check implements Listener{

    private String name;
    private Category category;
    private long viodelay;
    private HashMap<Player, HashMap<Long, Double>> viochache;
    private HashMap<Player, Double> violations;
    private HashMap<Player, HashMap<String, Double>> counts;
    private HashMap<Player, HashMap<String, Double>> tickcounts;
    private HashMap<Player, Map.Entry<Long, Long>> whitelist;
    private boolean resetafter;
    private ArrayList<Violation> vios;
    private YamlConfiguration yml;
    private ArrayList<Integer> bukkittasks;
    private ArrayList<PacketListener> protocollistener;
    private int maxping;
    private int mintps;

    public Check(String name, Category category) {
        yml = YamlConfiguration.loadConfiguration(TimberNoCheat.instance.config);
        this.name = name;
        this.category = category;
        this.resetafter = getBoolean("vioresetafteraction");
        this.viodelay = getLong("viocachedelay");
        this.viochache = new HashMap<Player, HashMap<Long, Double>>();
        this.violations = new HashMap<Player, Double>();
        this.counts = new HashMap<Player, HashMap<String, Double>>();
        this.tickcounts = new HashMap<Player, HashMap<String, Double>>();
        this.whitelist = new HashMap<Player, Map.Entry<Long, Long>>();
        this.bukkittasks = new ArrayList<Integer>();
        this.protocollistener = new ArrayList<PacketListener>();
        this.maxping = getInt("ng");
        this.mintps = getInt("min_tps");
        vios = new ArrayList<Violation>();
        ConfigurationSection confsec = yml.getConfigurationSection(name.toLowerCase() + ".vioactions");
        if(confsec == null){
            System.out.println("TNCDebug " + name);
            return;
        }
        for(String cvio : confsec.getKeys(false)) {
            String[] split = getString("vioactions." + cvio).split(":");
            System.out.println(name + " " + Integer.valueOf(cvio) + " " + Violation.ViolationTypes.valueOf(split[0]) + (split.length>=2?split[1]:""));
            System.out.println(name + " " + new Violation(Integer.valueOf(cvio), Violation.ViolationTypes.valueOf(split[0]), split.length>=2?split[1]:"").getType().name());
            vios.add(new Violation(Integer.valueOf(cvio), Violation.ViolationTypes.valueOf(split[0]), split.length>=2?split[1]:""));
        }
        register(Bukkit.getScheduler().runTaskTimer(TimberNoCheat.instance, () -> {
            for(Map.Entry<Player, HashMap<String, Double>> list : tickcounts.entrySet())
                for(Map.Entry<String, Double> values : list.getValue().entrySet())
                    tickcounts.get(list.getKey()).put(values.getKey(), values.getValue()-1 <= 0?0:values.getValue()-1);
        }, 1, 1).getTaskId());
        starttasks();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        final Player p = event.getPlayer();
        if(counts.containsKey(p)) counts.remove(p);
        if(tickcounts.containsKey(p)) tickcounts.remove(p);
    }

    /*
     * get config values
     */
    public String getString(String s){
        return yml.getString(name.toLowerCase() + "." + s);
    }
    public int getInt(String s){
        return yml.getInt(name.toLowerCase() + "." + s);
    }
    public double getDouble(String s){
        return yml.getDouble(name.toLowerCase() + "." + s);
    }
    public long getLong(String s){
        return yml.getLong(name.toLowerCase() + "." + s);
    }
    public boolean getBoolean(String s){
        return yml.getBoolean(name.toLowerCase() + "." + s);
    }
    public double[] getDoubleArray(String s){
        Object[] list = yml.getDoubleList(name.toLowerCase() + "." + s).toArray();
        return ArrayUtils.toPrimitive(Arrays.copyOf(list, list.length, Double[].class));
    }

    public void starttasks(){}


    public ArrayList<String> getStringList(String s){
        return (ArrayList<String>) yml.getStringList(name.toLowerCase() + "." + s);
    }

    public void resetvio(Player p){
        if(viochache.containsKey(p))viochache.get(p).clear();
        if(violations.containsKey(p))violations.put(p, 0D);
    }

    public void whitelist(Player p, Long time){
        whitelist.put(p, new AbstractMap.SimpleEntry<Long, Long>(System.currentTimeMillis(), time));
    }

    public void updatevio(Check c, Player p, double vio, String... other){
        if(System.currentTimeMillis()-whitelist.get(p).getKey()<whitelist.get(p).getValue()) return;
        boolean down = vio < 0;
        if(((maxping > 0 && TimberNoCheat.checkmanager.getping(p) >= maxping) || (mintps > 0 && mintps <= Tps.getTPS()))) return;
        if(viodelay > 0 && viochache.containsKey(p) && violations.containsKey(p))
            for(Map.Entry<Long, Double> v : viochache.get(p).entrySet()){
                long delay = System.currentTimeMillis()-v.getKey();
                if(delay>viodelay){
                    violations.put(p, violations.get(p)-v.getValue());
                    viochache.get(p).remove(v.getKey());
                }
            }
        if(down && getViolations().containsKey(p) && getViolations().get(p)-vio < 0) {
            ViolationUpdateEvent e = new ViolationUpdateEvent(p, 0, violations.getOrDefault(p, 0D), c);
            Bukkit.getServer().getPluginManager().callEvent(e);
            if(!e.isCancelled())violations.put(p, 0D);
            return;
        }
        ViolationUpdateEvent e = new ViolationUpdateEvent(p, violations.containsKey(p)? violations.get(p)+vio:vio, violations.getOrDefault(p, 0D), c);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if(e.isCancelled())
            return;
        violations.put(p, e.getNewviolation());
        if(down)return;
        double violation = violations.get(p);
        ArrayList<Violation> triggert = new ArrayList<Violation>();
        for(Violation cvio : vios)
            if(cvio.getLevel() <= violation)
                triggert.add(cvio);
        Bukkit.getScheduler().runTask(TimberNoCheat.instance, new Runnable() {
            public void run() {
                boolean canreset = false;
                for(Violation ctrig : triggert) {
                    switch (ctrig.getType()) {
                        case MESSAGE:
                            p.sendMessage(TimberNoCheat.instance.prefix + replacemarker(ctrig.getRest(), p));
                            break;
                        case KICK:
                            p.kickPlayer(TimberNoCheat.instance.prefix + replacemarker(ctrig.getRest(), p));
                            canreset = true;
                            break;
                        case NOTIFY:
                            TimberNoCheat.checkmanager.notify(c, " §6LEVEL: §b" + violations.get(p), p, other);
                            break;
                        case CMD:
                            for (String cmd : ctrig.getRest().split(":"))
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacemarker(ctrig.getRest(), p).replaceFirst("/", ""));
                            canreset = true;
                            break;
                        default:
                            TimberNoCheat.instance.getLogger().log(Level.WARNING, "Unknomn ViolationCheckType: " + ctrig.getType().name());
                            break;
                    }
                }
                if(resetafter && canreset) {
                    ViolationUpdateEvent e1 = new ViolationUpdateEvent(p, 0, violations.get(p), c);
                    Bukkit.getServer().getPluginManager().callEvent(e1);
                    if(e1.isCancelled()){
                        return;
                    }
                    violations.put(p, e.getNewviolation());
                }
            }
        });
    }

    public double getCount(Player player, String count){
        if(!counts.containsKey(player)) return -1;
        if(!counts.get(player).containsKey(count)) return -1;
        return counts.get(player).get(count);
    }

    public boolean hasReached(Player player, String count, int value){
        return getCount(player, count) >= value;
    }

    public void setCount(Player player, String count, double value){
        if(!counts.containsKey(player)) counts.put(player, new HashMap<>());
        counts.get(player).put(count, value);
    }

    public void addCount(Player player, String count){
        setCount(player, count, getCount(player, count)+1);
    }

    public void resetCount(Player player, String count){
        setCount(player, count, 0);
    }

    public Double getCountTick(Player player, String count){
        if(!tickcounts.containsKey(player)) return -1d;
        if(!tickcounts.get(player).containsKey(count)) return -1d;
        return tickcounts.get(player).get(count);
    }

    public boolean hasReachedTick(Player player, String count, int value){
        return getCountTick(player, count) >= value;
    }

    public void setCountTick(Player player, String count, double value){
        if(!tickcounts.containsKey(player)) tickcounts.put(player, new HashMap<>());
        tickcounts.get(player).put(count, value);
    }

    public void addCountTick(Player player, String count){
        setCountTick(player, count, getCountTick(player, count)+1);
    }

    public boolean countTickexsits(Player player, String count){
        return getCountTick(player, count) == 0;
    }

    public void resetCountTick(Player player, String count){
        setCountTick(player, count, 0);
    }


    public void register(int... tasks){
        for(int task : tasks) bukkittasks.add(task);
    }

    public void registernew(){
        for(PacketListener listener : protocollistener) TimberNoCheat.instance.protocolmanager.addPacketListener(listener);
    }

    public void register(PacketListener... listeners){
        Collections.addAll(protocollistener, listeners);
        for(PacketListener listener : listeners) TimberNoCheat.instance.protocolmanager.addPacketListener(listener);
    }

    public void disabletasks(){
        for(int task : bukkittasks) Bukkit.getScheduler().cancelTask(task);
    }

    public void disablelisteners(){
        for(PacketListener listener : protocollistener) TimberNoCheat.instance.protocolmanager.removePacketListener(listener);
    }


    public String replacemarker(String s, Player p){
        s = ChatColor.translateAlternateColorCodes('&', s);
        s = s.replaceAll("%player%", p.getName());
        s = s.replaceAll("%uuid%", p.getUniqueId().toString());
        s = s.replaceAll("%ip%", p.getAddress().getAddress().getHostAddress());
        s = s.replaceAll("%ping%", String.valueOf(TimberNoCheat.checkmanager.getping(p)));
        s = s.replaceAll("%pingcolor%", String.valueOf(TimberNoCheat.checkmanager.getpingcolor(p)));
        s = s.replaceAll("%display%", p.getDisplayName());
        s = s.replaceAll("%tapname%", p.getPlayerListName());
        s = s.replaceAll("%vio%", String.valueOf(violations.getOrDefault(p, 0D)));
        s = s.replaceAll("%tps%", String.valueOf(Tps.getTPS()));
        s = s.replaceAll("%tpscolor%", TimberNoCheat.checkmanager.gettpscolor());
        return s.replaceAll("%port%", String.valueOf(Bukkit.getPort()));
    }
    public long getViodelay() {
        return viodelay;
    }

    public void setViodelay(long viodelay) {
        this.viodelay = viodelay;
    }

    public boolean isResetafter() {
        return resetafter;
    }

    public void setResetafter(boolean resetafter) {
        this.resetafter = resetafter;
    }

    public ArrayList<Violation> getVios() {
        return vios;
    }

    public void setVios(ArrayList<Violation> vios) {
        this.vios = vios;
    }

    public YamlConfiguration getYml() {
        return yml;
    }

    public void setYml(YamlConfiguration yml) {
        this.yml = yml;
    }

    public int getMaxping() {
        return maxping;
    }

    public void setMaxping(int maxping) {
        this.maxping = maxping;
    }

    public int getMintps() {
        return mintps;
    }

    public void setMintps(int mintps) {
        this.mintps = mintps;
    }

    public HashMap<Player, HashMap<Long, Double>> getViochache() {
        return viochache;
    }

    public void setViochache(HashMap<Player, HashMap<Long, Double>> viochache) {
        this.viochache = viochache;
    }

    public HashMap<Player, Double> getViolations() {
        return violations;
    }

    public void setViolations(HashMap<Player, Double> violations) {
        this.violations = violations;
    }

    public String getName() {
        return name;
    }

    public void disable(){}

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
