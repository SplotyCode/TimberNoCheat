package me.david.timbernocheat.checkbase;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketListener;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.debug.Debuggers;
import me.david.timbernocheat.discord.DiscordManager;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.api.storage.YamlSection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.logging.Level;

public class Check extends YamlSection implements Listener {

    private String name;
    private Category category;
    private long vioDelay;
    private HashMap<UUID, HashMap<Long, Double>> vioCache;
    private HashMap<UUID, Double> violations;
    private HashMap<UUID, HashMap<String, Double>> counts;
    public HashMap<UUID, HashMap<String, Double>> tickCounts;
    private HashMap<UUID, Map.Entry<Long, Long>> whitelist;
    private boolean resetAfter;
    private ArrayList<Violation> vios;
    private ArrayList<Integer> bukkitTasks;
    private ArrayList<PacketListener> protocolListener;
    private int maxPing;
    private int minTps;

    private ArrayList<Check> childes = new ArrayList<>();
    private ArrayList<Check> disabledsChilds = new ArrayList<>();
    private Check parent = null;
    private boolean isChild = false;

    public void registerChilds(Enum[] en) {
        for (Enum enu : en) {
            Check check;
            try {
                check = new Check(enu.name(), category, true, this);
            }catch (Exception ex){
                TimberNoCheat.getInstance().reportException(ex, "Error loading child for '"  + name + "'(childname: '" + enu.name() + "'! The config seems to be the newest version so this may be you error!\nWe will try to still resume the Plugin load(Skip this Child)... You will probably sea errors from this error!", DiscordManager.ErrorType.SUBMODULE_LOAD);
                continue;
            }
            try {
                if(check.getBoolean("enable") && getBoolean("enable")) {
                    childes.add(check);
                    Bukkit.getPluginManager().registerEvents(check, TimberNoCheat.getInstance());
                }
                else disabledsChilds.add(check);
            }catch (Exception ex){
                TimberNoCheat.getInstance().reportException(ex, "Error loading config for module '"  + name + "'(register childes)! The config seems to be the newest version so this may be you error!\nWe will try to still resume the Plugin load... You will probably sea errors from this error!", DiscordManager.ErrorType.SUBMODULE_LOAD);
            }
        }
    }

    public void registerChilds(Check... checks) {
        for(Check check : checks)
            if(check.getBoolean("enable") && getBoolean("enable")) {
                childes.add(check);
                Bukkit.getPluginManager().registerEvents(check, TimberNoCheat.getInstance());
            }
            else disabledsChilds.add(check);
    }

    public void registerChilds(String[] list) {
        for (String name : list) {
            Check check;
            try {
                check = new Check(name, category, true, this);
            }catch (Exception ex){
                TimberNoCheat.getInstance().reportException(ex, "Error loading child for '"  + this.name + "'(childname: '" + name + "'! The config seems to be the newest version so this may be you error!\nWe will try to still resume the Plugin load(Skip this Child)... You will probably sea errors from this error!", DiscordManager.ErrorType.SUBMODULE_LOAD);
                continue;
            }
            try {
                if(check.getBoolean("enable") && getBoolean("enable")) {
                    childes.add(check);
                    Bukkit.getPluginManager().registerEvents(check, TimberNoCheat.getInstance());
                } else disabledsChilds.add(check);
            }catch (Exception ex){
                TimberNoCheat.getInstance().reportException(ex, "Error loading config for module '"  + this.name + "'(register childes)! The config seems to be the newest version so this may be you error!\nWe will try to still resume the Plugin load... You will probably sea errors from this error!", DiscordManager.ErrorType.SUBMODULE_LOAD);
            }
        }
    }

    public Check(String name, Category category) {
        this(name, category, name.toLowerCase());
    }

    private Check(String name, Category category, String path) {
        super(TimberNoCheat.getInstance().getConfig().getYamlSection(path));
        this.name = name;
        this.category = category;
        this.vioCache = new HashMap<>();
        this.violations = new HashMap<>();
        this.counts = new HashMap<>();
        this.tickCounts = new HashMap<>();
        this.whitelist = new HashMap<>();
        this.bukkitTasks = new ArrayList<>();
        this.protocolListener = new ArrayList<>();
        vios = new ArrayList<>();
        try {
           //for(String s: getKeys(false)) System.out.println(name + " " + s);
            //System.out.println(name + " " + Arrays.toString(getKeys(true).toArray(new String[0])));
            this.maxPing = getInt("max_ping");
            this.minTps = getInt("min_tps");
            this.resetAfter = getBoolean("vioresetafteraction");
            this.vioDelay = getLong("viocachedelay");
            ConfigurationSection confsec = getConfigurationSection(".vioactions");
            if(confsec == null) return;
            for(String cvio : confsec.getKeys(false)) {
                String[] split = getString("vioactions." + cvio).split(":");
                vios.add(new Violation(Integer.valueOf(cvio), Violation.ViolationTypes.valueOf(split[0]), split.length>=2?split[1]:""));
            }
        }catch (NullPointerException ex){
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Error loading config for module '"  + name + "'! The config seems to be the newest version so this may be you error");
            TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "We will try to still resume the Plugin load... You will probably sea errors from this error!");
            if(TimberNoCheat.getInstance().isDebug()){
                TimberNoCheat.getInstance().getLogger().log(Level.INFO, "Da TNC im debug Mode leuft wird ein Stacktrace direkt in der Konsole ausgegeben! Hier biddde: ");
                ex.printStackTrace();
            }else TimberNoCheat.getInstance().getLogger().log(Level.INFO, "Da TNC im debug Mode leuft wird kein Stacktrace direkt in der Konsole ausgegeben!");
        }
        if (getBoolean("enabled")) startTasks();
    }

    public Check(String name, Category category, boolean child, Check parent) {
        this(name, category, parent.name.toLowerCase() + "." + name.toLowerCase());
        if(parent.isChild) throw new IllegalArgumentException("Childs can not have childes :(");
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
        for(String key : getKeys(false)){
            if(key.equals("enable") || key.equals("max_ping") || key.equals("min_tps") || key.equals("vioresetafteraction") || key.equals("viocachedelay") || key.equals("vioactions")) continue;
            ConfigurationSection section = getConfigurationSection(key);
            if(section == null) list.add(key);
            else list.addAll(section.getKeys(true));
        }
        return list;
    }

    public void setSetting(String name, Object value){
        set(name, value);
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
        for(Check check : childes)
            if (check.name.equalsIgnoreCase(name))
                return check;
        for(Check check : disabledsChilds)
            if (check.name.equalsIgnoreCase(name))
                return check;
        return null;
    }

    public Check getChildbyEnum(Enum en){
        //System.out.println(this.name + " " + en.name() + " " + childes.size());
        for(Check check : childes) {
           // System.out.println(check.getName() + " " + en.name());
            if (check.name.equalsIgnoreCase(en.name()))
                return check;
        }
        for(Check check : disabledsChilds) {
            // System.out.println(check.getName() + " " + en.name());
            if (check.name.equalsIgnoreCase(en.name()))
                return check;
        }
        return null;
    }

    public String displayName(){
        return (parent != null?parent.getName() + "_":"") + name;
    }

    public void updatevioChild(String c, Player p, double vio, String... other){
        updateVio(getChildByString(c), p, vio, other);
    }

    public boolean updateVio(Check check, Player player, double vio, String... other){
        boolean result = CheckManager.getInstance().getExecutor().execute(player, check, vio, other);
        TimberNoCheat.getInstance().getDebugger().sendDebug(Debuggers.VIOLATION_DEBUG, check.displayName() + " with result " + result + " " + String.join(", ", other));
        return result;
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
        for(int task : tasks) bukkitTasks.add(task);
    }

    public void register(TimberScheduler... schedulers){
        for(TimberScheduler scheduler : schedulers) {
            if(scheduler.getTaskId() == -1){
                TimberNoCheat.getInstance().log(false, Level.WARNING, "Whoops. The Check '" + getName() + "' has tried to register Scheduler '" + scheduler.getRealName() + "' but has forgetting to start it...");
                TimberNoCheat.getInstance().log(false, Level.WARNING, "Unfortunately we can not start it because we don't know the mode and the timing...");
                continue;
            }
            bukkitTasks.add(scheduler.getTaskId());
        }
    }

    public void registernew(){
        for(PacketListener listener : protocolListener) TimberNoCheat.getInstance().getProtocolManager().addPacketListener(listener);
    }

    public void register(PacketListener... listeners){
        if (!getBoolean("enabled")) return;
        Collections.addAll(protocolListener, listeners);
        for(PacketListener listener : listeners) {
            boolean failed = false;
            for(PacketType type : listener.getReceivingWhitelist().getTypes())
                if(!type.isSupported()) {
                    failed = true;
                    if(protocolListener.contains(listener)) protocolListener.remove(listener);
                }
            for(PacketType type : listener.getSendingWhitelist().getTypes())
                if(!type.isSupported()) {
                    failed = true;
                    if(protocolListener.contains(listener)) protocolListener.remove(listener);
                }
            if(!failed) TimberNoCheat.getInstance().getProtocolManager().addPacketListener(listener);
        }
    }

    public void runLater(TimberScheduler scheduler, int ticks){
        scheduler.runTaskLater(TimberNoCheat.getInstance(), ticks);
    }

    public void disableTasks(){
        for(int task : bukkitTasks) Bukkit.getScheduler().cancelTask(task);
    }

    public void disableListeners(){
        for(PacketListener listener : protocolListener) TimberNoCheat.getInstance().getProtocolManager().removePacketListener(listener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Check check = (Check) o;

        if (isChild() != check.isChild()) return false;
        if (getName() != null ? !getName().equals(check.getName()) : check.getName() != null) return false;
        return getParent() != null ? getParent().equals(check.getParent()) : check.getParent() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    public double getViolation(Player player){
        return violations.getOrDefault(player.getUniqueId(), 0d);
    }

    public double getViolation(UUID uuid){
        return violations.getOrDefault(uuid, 0d);
    }

    public long getVioDelay() {
        return vioDelay;
    }

    public void setVioDelay(long vioDelay) {
        this.vioDelay = vioDelay;
    }

    public boolean isResetAfter() {
        return resetAfter;
    }

    public void setResetAfter(boolean resetAfter) {
        this.resetAfter = resetAfter;
    }

    public ArrayList<Violation> getVios() {
        return vios;
    }

    public void setVios(ArrayList<Violation> vios) {
        this.vios = vios;
    }

    public int getMaxPing() {
        return maxPing;
    }

    public void setMaxPing(int maxPing) {
        this.maxPing = maxPing;
    }

    public int getMinTps() {
        return minTps;
    }

    public void setMinTps(int minTps) {
        this.minTps = minTps;
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

    public ArrayList<Check> getChildes() {
        return childes;
    }

    public ArrayList<Check> getDisabledsChilds() {
        return disabledsChilds;
    }

    public HashMap<UUID, Map.Entry<Long, Long>> getWhitelist() {
        return whitelist;
    }
}
