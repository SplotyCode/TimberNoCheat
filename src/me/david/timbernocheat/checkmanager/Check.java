package me.david.timbernocheat.checkmanager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketListener;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.storage.YamlFile;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.logging.Level;

public class Check extends YamlFile implements Listener {

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
        this(name, category, name.toLowerCase());
    }

    private Check(String name, Category category, String path) {
        setRoot(path);
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
        this(name, category, parent.name + "." + name.toLowerCase());
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

    public void startTasks(){}
    public void disable(){}

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
        TimberNoCheat.checkmanager.getExecutor().execute(player, check, vio, other);
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


    @Deprecated
    public void register(int... tasks){
        for(int task : tasks) bukkittasks.add(task);
    }

    public void register(TimberScheduler... schedulers){
        for(TimberScheduler scheduler : schedulers) {
            if(scheduler.getTaskId() == -1){
                TimberNoCheat.instance.log(false, Level.WARNING, "Whoops. The Check '" + getName() + "' has tried to register Scheduler '" + scheduler.getRealName() + "' but has forgetting to start it...");
                TimberNoCheat.instance.log(false, Level.WARNING, "Unfortunately we can not start it because we don't know the mode and the timing...");
                continue;
            }
            bukkittasks.add(scheduler.getTaskId());
        }
    }

    public void registernew(){
        for(PacketListener listener : protocollistener) TimberNoCheat.instance.protocolmanager.addPacketListener(listener);
    }

    public void register(PacketListener... listeners){
        Collections.addAll(protocollistener, listeners);
        for(PacketListener listener : listeners) {
            boolean failed = false;
            for(PacketType type : listener.getReceivingWhitelist().getTypes())
                if(!type.isSupported()) {
                    failed = true;
                    if(protocollistener.contains(listener)) protocollistener.remove(listener);
                }
            for(PacketType type : listener.getSendingWhitelist().getTypes())
                if(!type.isSupported()) {
                    failed = true;
                    if(protocollistener.contains(listener)) protocollistener.remove(listener);
                }
            if(!failed) TimberNoCheat.instance.protocolmanager.addPacketListener(listener);
        }
    }

    public void runLater(TimberScheduler scheduler, int ticks){
        scheduler.runTaskLater(TimberNoCheat.instance, ticks);
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

    public HashMap<UUID, Map.Entry<Long, Long>> getWhitelist() {
        return whitelist;
    }
}
