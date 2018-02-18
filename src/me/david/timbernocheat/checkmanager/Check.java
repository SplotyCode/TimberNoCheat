package me.david.timbernocheat.checkmanager;

import com.comphenix.protocol.events.PacketListener;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.runnable.Tps;
import me.david.api.utils.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.logging.Level;

public class Check implements Listener{

    private String name;
    private Category category;
    private long viodelay;
    private HashMap<UUID, HashMap<Long, Double>> vioCache;
    private HashMap<UUID, Double> violations;
    private HashMap<UUID, HashMap<String, Double>> counts;
    public HashMap<UUID, HashMap<String, Double>> tickCounts;
    private HashMap<UUID, Map.Entry<Long, Long>> whitelist;
    private boolean resetafter;
    private ArrayList<Violation> vios;
    private YamlConfiguration yml;
    private ArrayList<Integer> bukkittasks;
    private ArrayList<PacketListener> protocollistener;
    private int maxping;
    private int mintps;

    private ArrayList<Check> childs = new ArrayList<>();
    private ArrayList<Check> diabledsChilds = new ArrayList<>();
    private Check parent = null;
    private boolean isChild = false;

    public void registerChilds(Enum[] en) {
        for (Enum enu : en) {
            Check check = new Check(enu.name(), category, true, this);
            if(check.getBoolean("enabled")) childs.add(check);
            else diabledsChilds.add(check);
        }
    }

    public void registerChilds(Check[] checks) {
        for(Check check : checks)
            if(check.getBoolean("enabled")){
                if(check.getBoolean("enabled")) childs.add(check);
                else diabledsChilds.add(check);
            }
    }

    public void registerChilds(String[] list) {
        for(String str : list) {
            Check check = new Check(str, category, true, this);
            if(check.getBoolean("enabled")) childs.add(check);
            else diabledsChilds.add(check);
        }
    }

    public Check(String name, Category category) {
        yml = YamlConfiguration.loadConfiguration(TimberNoCheat.instance.config);
        this.name = name;
        this.category = category;
        this.resetafter = getBoolean("vioresetafteraction");
        this.viodelay = getLong("viocachedelay");
        this.vioCache = new HashMap<>();
        this.violations = new HashMap<>();
        this.counts = new HashMap<>();
        this.tickCounts = new HashMap<>();
        this.whitelist = new HashMap<>();
        this.bukkittasks = new ArrayList<>();
        this.protocollistener = new ArrayList<>();
        this.maxping = getInt("max_ping");
        this.mintps = getInt("min_tps");
        vios = new ArrayList<>();
        ConfigurationSection confsec = yml.getConfigurationSection(name.toLowerCase() + ".vioactions");
        if(confsec == null) return;
        for(String cvio : confsec.getKeys(false)) {
            String[] split = getString("vioactions." + cvio).split(":");
            vios.add(new Violation(Integer.valueOf(cvio), Violation.ViolationTypes.valueOf(split[0]), split.length>=2?split[1]:""));
        }
        startTasks();
    }

    public Check(String name, Category category, boolean child, Check parent) {
        this(name, category);
        if(parent.isChild) throw new IllegalArgumentException("Childs can not have childs :(");
        isChild = child;
        this.parent = parent;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        final UUID uuid = event.getPlayer().getUniqueId();
        if(counts.containsKey(uuid)) counts.remove(uuid);
        if(tickCounts.containsKey(uuid)) tickCounts.remove(uuid);
    }

    /*
     * get config values
     */
    protected String getString(String s){
        return yml.getString((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }
    protected int getInt(String s){
        return yml.getInt((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }
    protected double getDouble(String s){
        return yml.getDouble((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }
    protected long getLong(String s){
        return yml.getLong((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }
    protected Long[] getLongList(String s){
        return yml.getLongList((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s).toArray(new Long[0]);
    }

    protected boolean getBoolean(String s){
        return yml.getBoolean((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }

    public ArrayList<String> getCustomSettings(){
        ArrayList<String> list = new ArrayList<>();
        String basePath = (isChild?parent.name + ".":"") + this.name.toLowerCase();
        for(String key : yml.getConfigurationSection(basePath).getKeys(false)){
            if(key.equals("enable") || key.equals("max_ping") || key.equals("min_tps") || key.equals("vioresetafteraction") || key.equals("viocachedelay") || key.equals("vioactions")) continue;
            ConfigurationSection section = yml.getConfigurationSection(basePath + "." + key);
            if(section == null) list.add(basePath + key);
            else for(String key2 : section.getKeys(true)) list.add(basePath + key2);
        }
        return list;
    }

    public void setSetting(String name, Object value){
        yml.set((isChild?parent.name + ".":"") + this.name.toLowerCase() + "." + name, value);
    }

    protected double[] getDoubleArray(String s){
        Object[] list = yml.getDoubleList((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s).toArray();
        return ArrayUtils.toPrimitive(Arrays.copyOf(list, list.length, Double[].class));
    }

    public void startTasks(){}


    protected ArrayList<String> getStringList(String s){
        return (ArrayList<String>) yml.getStringList((isChild?parent.name + ".":"") + name.toLowerCase() + "." + s);
    }

    public void resetVio(final Player player){
        resetVio(player.getUniqueId());
    }

    public void resetVio(final UUID uuid){
        if(vioCache.containsKey(uuid)) vioCache.get(uuid).clear();
        if(violations.containsKey(uuid))violations.put(uuid, 0D);
    }

    public void whitelist(Player player, Long time){
        whitelist.put(player.getUniqueId(), new AbstractMap.SimpleEntry<>(System.currentTimeMillis(), time));
    }

    public Check getChildByString(String name){
        for(Check check : childs)
            if(check.name.equalsIgnoreCase(name))
                return check;
        return null;
    }

    public Check getChildbyEnum(Enum en){
        for(Check check : childs)
            if(check.name.equalsIgnoreCase(en.name()))
                return check;
        return null;
    }

    public void updatevioChild(String c, Player p, double vio, String... other){
        updateVio(getChildByString(c), p, vio, other);
    }

    public void updateVio(Check check, Player player, double vio, String... other){
        if(player == null) throw new IllegalArgumentException("Arg Player might not be null...");
        final UUID uuid = player.getUniqueId();
        if(whitelist.containsKey(uuid) && System.currentTimeMillis()-whitelist.get(uuid).getKey()<whitelist.get(uuid).getValue()) return;
        boolean down = vio < 0;
        if(((maxping > 0 && TimberNoCheat.checkmanager.getping(player) >= maxping) || (mintps > 0 && mintps <= Tps.getTPS()))) return;
        if(viodelay > 0 && vioCache.containsKey(uuid) && violations.containsKey(uuid))
            for(Map.Entry<Long, Double> v : vioCache.get(uuid).entrySet()){
                long delay = System.currentTimeMillis()-v.getKey();
                if(delay>viodelay){
                    violations.put(uuid, violations.get(uuid)-v.getValue());
                    vioCache.get(uuid).remove(v.getKey());
                }
            }
        if(down && getViolations().containsKey(uuid) && getViolations().get(uuid)-vio < 0) {
            ViolationUpdateEvent e = new ViolationUpdateEvent(player, 0, violations.getOrDefault(uuid, 0D), check);
            Bukkit.getServer().getPluginManager().callEvent(e);
            if(!e.isCancelled()) {
                violations.put(uuid, 0D);
                if(isChild) parent.updateVio(parent, player, vio, other);
            }
            return;
        }
        ViolationUpdateEvent e = new ViolationUpdateEvent(player, violations.containsKey(uuid)? violations.get(uuid)+vio:vio, violations.getOrDefault(uuid, 0D), check);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if(e.isCancelled())
            return;
        violations.put(uuid, e.getNewViolation());
        if(isChild) parent.updateVio(parent, player, vio, other);
        if(down)return;
        double violation = violations.get(uuid);
        ArrayList<Violation> triggert = new ArrayList<Violation>();
        for(Violation cvio : vios)
            if(cvio.getLevel() <= violation)
                triggert.add(cvio);
        Bukkit.getScheduler().runTask(TimberNoCheat.instance, () -> {
            boolean canreset = false;
            for(Violation ctrig : triggert) {
                switch (ctrig.getType()) {
                    case MESSAGE:
                        player.sendMessage(TimberNoCheat.instance.prefix + replacemarker(ctrig.getRest(), player));
                        break;
                    case KICK:
                        TimberNoCheat.checkmanager.notify(player, "[KICK] §bName: §6" + getCategory().name() + "_" + getName() + " §bPlayer: §6" + player.getName() + " §bTPS: " + TimberNoCheat.checkmanager.gettpscolor() + " §bPING: " + TimberNoCheat.checkmanager.getpingcolor(player) + violation + StringUtil.toString(other, ""));
                        player.kickPlayer(TimberNoCheat.instance.prefix + replacemarker(ctrig.getRest(), player));
                        canreset = true;
                        break;
                    case NOTIFY:
                        TimberNoCheat.checkmanager.notify(check, " §6LEVEL: §b" + violations.get(uuid), player, other);
                        break;
                    case CMD:
                        for (String cmd : ctrig.getRest().split(":"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replacemarker(cmd, player).replaceFirst("/", ""));
                        canreset = true;
                        break;
                    default:
                        TimberNoCheat.instance.getLogger().log(Level.WARNING, "Unknomn ViolationCheckType: " + ctrig.getType().name());
                        break;
                }
            }
            if(resetafter && canreset) {
                ViolationUpdateEvent e1 = new ViolationUpdateEvent(player, 0, violations.get(uuid), check);
                Bukkit.getServer().getPluginManager().callEvent(e1);
                if(e1.isCancelled()){
                    return;
                }
                violations.put(uuid, e.getNewViolation());
            }
        });
    }

    public double getCount(Player player, String count){
        final UUID uuid = player.getUniqueId();
        if(!counts.containsKey(uuid)) return -1;
        if(!counts.get(uuid).containsKey(count)) return -1;
        return counts.get(uuid).get(count);
    }

    public boolean hasReached(Player player, String count, int value){
        return getCount(player, count) >= value;
    }

    public void setCount(Player player, String count, double value){
        final UUID uuid = player.getUniqueId();
        if(!counts.containsKey(uuid)) counts.put(uuid, new HashMap<>());
        counts.get(uuid).put(count, value);
    }

    public void addCount(Player player, String count){
        setCount(player, count, getCount(player, count)+1);
    }

    public void resetCount(Player player, String count){
        setCount(player, count, 0);
    }

    protected Double getCountTick(Player player, String count){
        final UUID uuid = player.getUniqueId();
        if(!tickCounts.containsKey(uuid)) return -1d;
        if(!tickCounts.get(uuid).containsKey(count)) return -1d;
        return tickCounts.get(uuid).get(count);
    }

    public boolean hasReachedTick(Player player, String count, int value){
        return getCountTick(player, count) >= value;
    }

    protected void setCountTick(Player player, String count, double value){
        final UUID uuid = player.getUniqueId();
        if(!tickCounts.containsKey(uuid)) tickCounts.put(uuid, new HashMap<>());
        tickCounts.get(uuid).put(count, value);
    }

    public void addCountTick(Player player, String count){
        setCountTick(player, count, getCountTick(player, count)+1);
    }

    protected boolean countTickexsits(Player player, String count){
        return getCountTick(player, count) == 0;
    }

    protected void resetCountTick(Player player, String count){
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

    public void disableTasks(){
        for(int task : bukkittasks) Bukkit.getScheduler().cancelTask(task);
    }

    public void disableListeners(){
        for(PacketListener listener : protocollistener) TimberNoCheat.instance.protocolmanager.removePacketListener(listener);
    }

    public double getViolation(Player player){
        return violations.getOrDefault(player.getUniqueId(), 0d);
    }

    public double getViolation(UUID uuid){
        return violations.getOrDefault(uuid, 0d);
    }


    private String replacemarker(String s, Player p){
        s = ChatColor.translateAlternateColorCodes('&', s);
        s = s.replaceAll("%player%", p.getName());
        s = s.replaceAll("%uuid%", p.getUniqueId().toString());
        s = s.replaceAll("%ip%", p.getAddress().getAddress().getHostAddress());
        s = s.replaceAll("%ping%", String.valueOf(TimberNoCheat.checkmanager.getping(p)));
        s = s.replaceAll("%pingcolor%", String.valueOf(TimberNoCheat.checkmanager.getpingcolor(p)));
        s = s.replaceAll("%display%", p.getDisplayName());
        s = s.replaceAll("%tapname%", p.getPlayerListName());
        s = s.replaceAll("%vio%", String.valueOf(violations.getOrDefault(p.getUniqueId(), 0D)));
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

    public HashMap<UUID, HashMap<Long, Double>> getVioCache() {
        return vioCache;
    }

    public void setVioCache(HashMap<UUID, HashMap<Long, Double>> vioCache) {
        this.vioCache = vioCache;
    }

    public HashMap<UUID, Double> getViolations() {
        return violations;
    }

    public void setViolations(HashMap<UUID, Double> violations) {
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

    public boolean isChild() {
        return isChild;
    }

    public Check getParent() {
        return parent;
    }

    public ArrayList<Check> getChilds() {
        return childs;
    }

    public ArrayList<Check> getDiabledsChilds() {
        return diabledsChilds;
    }
}
