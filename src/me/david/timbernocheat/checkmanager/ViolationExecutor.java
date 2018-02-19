package me.david.timbernocheat.checkmanager;

import me.david.api.utils.StringUtil;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.runnable.Tps;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/*
 * This Class is for Punishing a Player
 * The File Parser for the Violation can be found in the Check Constructor
 */
public class ViolationExecutor {

    public void execute(final Player player, final Check check, double vio, String[] other){
        if(player == null) throw new IllegalArgumentException("Arg Player might not be null...");
        final UUID uuid = player.getUniqueId();
        if(check.getWhitelist().containsKey(uuid) && System.currentTimeMillis()-check.getWhitelist().get(uuid).getKey()<check.getWhitelist().get(uuid).getValue()) return;
        boolean down = vio < 0;
        if(((check.getMaxping() > 0 && TimberNoCheat.checkmanager.getping(player) >= check.getMaxping()) || (check.getMintps() > 0 && check.getMintps() <= Tps.getTPS()))) return;
        if(check.getViodelay() > 0 && check.getVioCache().containsKey(uuid) && check.getViolations().containsKey(uuid))
            for(Map.Entry<Long, Double> v : check.getVioCache().get(uuid).entrySet()){
                long delay = System.currentTimeMillis()-v.getKey();
                if(delay > check.getViodelay()){
                    check.getViolations().put(uuid, check.getViolations().get(uuid)-v.getValue());
                    check.getVioCache().get(uuid).remove(v.getKey());
                }
            }
        if(down && check.getViolations().containsKey(uuid) && check.getViolations().get(uuid)-vio < 0) {
            ViolationUpdateEvent e = new ViolationUpdateEvent(player, 0, check.getViolations().getOrDefault(uuid, 0D), check);
            Bukkit.getServer().getPluginManager().callEvent(e);
            if(!e.isCancelled()) {
                check.getViolations().put(uuid, 0D);
                if(check.isChild()) check.getParent().updateVio(check.getParent(), player, vio, other);
            }
            return;
        }
        ViolationUpdateEvent e = new ViolationUpdateEvent(player, check.getViolations().containsKey(uuid)? check.getViolations().get(uuid)+vio:vio, check.getViolations().getOrDefault(uuid, 0D), check);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if(e.isCancelled()) return;
        check.getViolations().put(uuid, e.getNewViolation());
        if(check.isChild()) check.getParent().updateVio(check.getParent(), player, vio, other);
        if(down)return;
        double violation = check.getViolations().get(uuid);
        ArrayList<Violation> triggert = new ArrayList<Violation>();
        for(Violation cvio : check.getVios())
            if(cvio.getLevel() <= violation)
                triggert.add(cvio);
        Bukkit.getScheduler().runTask(TimberNoCheat.instance, () -> {
            boolean canreset = false;
            for(Violation ctrig : triggert) {
                /* Definition for the Types can be read in Vialation.ViolationTypes */
                switch (ctrig.getType()) {
                    case MESSAGE:
                        player.sendMessage(TimberNoCheat.instance.prefix + replaceMarker(ctrig.getRest(), player, check));
                        break;
                    case KICK:
                        TimberNoCheat.checkmanager.notify(player, "[KICK] §bName: §6" + check.getCategory().name() + "_" + check.getName() + " §bPlayer: §6" + player.getName() + " §bTPS: " + TimberNoCheat.checkmanager.getTpsColor() + " §bPING: " + TimberNoCheat.checkmanager.getPingColor(player) + violation + StringUtil.toString(other, ""));
                        kick(player, TimberNoCheat.instance.prefix + replaceMarker(ctrig.getRest(), player, check));
                        canreset = true;
                        break;
                    case NOTIFY:
                        TimberNoCheat.checkmanager.notify(check, " §6LEVEL: §b" + check.getViolations().get(uuid), player, other);
                        break;
                    case CMD:
                        for (String cmd : ctrig.getRest().split(":"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceMarker(cmd, player, check).replaceFirst("/", ""));
                        canreset = true;
                        break;
                    case DAMAGE:
                        player.damage(Double.parseDouble(ctrig.getRest()));
                        break;
                    default:
                        TimberNoCheat.instance.getLogger().log(Level.WARNING, "Unknomn ViolationCheckType: " + ctrig.getType().name());
                        break;
                }
            }
            if(check.isResetafter() && canreset) {
                ViolationUpdateEvent e1 = new ViolationUpdateEvent(player, 0, check.getViolations().get(uuid), check);
                Bukkit.getServer().getPluginManager().callEvent(e1);
                if(e1.isCancelled()){
                    return;
                }
                check.getViolations().put(uuid, e.getNewViolation());
            }
        });
    }

    private void kick(final Player player, final String reason){
        TimberNoCheat.instance.getListenerManager().getFreezeListener().freeze(player, 20*20*20*1000);
        runTask(() -> playEffect(player.getLocation().clone().add(0, 1.8, 0), Effect.ENDER_SIGNAL), 4, 50);
        final Location location = player.getEyeLocation();
        final double pitch = Math.toRadians(location.getYaw() + 90);
        final double yaw = Math.toRadians(location.getPitch() + 90);

        final double posX = Math.sin(pitch) + Math.cos(yaw);
        final double posZ = Math.sin(pitch) + Math.sin(yaw);
        final double posY = Math.cos(pitch);
        for(int i = 0;i < 50;i++){
            final Location spawnLocation = new Location(location.getWorld(), location.getX() + i * posX,
                                                                             location.getY() + i * posY,
                                                                                location.getZ() + i * posZ);
            playEffect(spawnLocation, Effect.FIREWORKS_SPARK);
        }
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, () -> player.kickPlayer(reason), 20*20);
    }

    private void playEffect(final Location location, final Effect effect){
        final World world = location.getWorld();
        world.playEffect(location, effect, BlockFace.SOUTH_EAST);
        world.playEffect(location, effect, BlockFace.SOUTH);
        world.playEffect(location, effect, BlockFace.SOUTH_WEST);
        world.playEffect(location, effect, BlockFace.EAST);
        world.playEffect(location, effect, BlockFace.SELF);
        world.playEffect(location, effect, BlockFace.WEST);
        world.playEffect(location, effect, BlockFace.NORTH_EAST);
        world.playEffect(location, effect, BlockFace.NORTH);
        world.playEffect(location, effect, BlockFace.NORTH_WEST);
    }

    private void runTask(final Runnable runnable, final long delay, final int times){
        for(int i = 0;i < times;i++)
            Bukkit.getScheduler().runTaskLater(TimberNoCheat.instance, runnable, i*delay);
    }

    private String replaceMarker(String s, Player p, Check check){
        s = ChatColor.translateAlternateColorCodes('&', s);
        s = s.replaceAll("%player%", p.getName());
        s = s.replaceAll("%uuid%", p.getUniqueId().toString());
        s = s.replaceAll("%ip%", p.getAddress().getAddress().getHostAddress());
        s = s.replaceAll("%ping%", String.valueOf(TimberNoCheat.checkmanager.getping(p)));
        s = s.replaceAll("%pingcolor%", String.valueOf(TimberNoCheat.checkmanager.getPingColor(p)));
        s = s.replaceAll("%display%", p.getDisplayName());
        s = s.replaceAll("%tapname%", p.getPlayerListName());
        s = s.replaceAll("%vio%", String.valueOf(check.getViolations().getOrDefault(p.getUniqueId(), 0D)));
        s = s.replaceAll("%tps%", String.valueOf(Tps.getTPS()));
        s = s.replaceAll("%tpscolor%", TimberNoCheat.checkmanager.getTpsColor());
        return s.replaceAll("%port%", String.valueOf(Bukkit.getPort()));
    }
}
