package me.david.TimberNoCheat.checkmanager;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.api.ViolationUpdateEvent;
import me.david.TimberNoCheat.checktools.Tps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

public class Check implements Listener{

    private String name;
    private Category category;
    private long viodelay;
    private HashMap<Player, HashMap<Long, Double>> viochache;
    private HashMap<Player, Double> violations;
    private boolean resetafter;
    private ArrayList<Violation> vios;
    private YamlConfiguration yml;
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
    }
    /*
    get config values
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

    public ArrayList<String> getStringList(String s){
        return (ArrayList<String>) yml.getStringList(name.toLowerCase() + "." + s);
    }
    public void resetvio(Player p){
        if(viochache.containsKey(p))viochache.get(p).clear();
        if(violations.containsKey(p))violations.put(p, 0D);
    }
    public void updatevio(Check c, Player p, double vio, String... other){
        boolean down = vio < 0;
        if(((maxping > 0 && TimberNoCheat.checkmanager.getping(p) >= maxping) || (mintps > 0 && mintps <= Tps.getTPS())))
            return;
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
