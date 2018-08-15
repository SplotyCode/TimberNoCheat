package me.david.timbernocheat.checkbase;

import me.david.api.Api;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.debug.log.DebugEntry;
import me.david.timbernocheat.runnable.TimberScheduler;
import me.david.timbernocheat.runnable.Tps;
import me.david.timbernocheat.runnable.countdown.Countdowns;
import me.david.timbernocheat.util.Maths;
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
 * TODO: Use Complex Countdowns to only trigger countdows for the same think(check etc..)
 */
public class ViolationExecutor {

    public boolean execute(final Player player, final Check check, double vio, String[] other){
        if(player == null) throw new IllegalArgumentException("Arg Player might not be null...");
        final UUID uuid = player.getUniqueId();
        if(check.getWhitelist().containsKey(uuid) && System.currentTimeMillis()-check.getWhitelist().get(uuid).getKey()<check.getWhitelist().get(uuid).getValue()) return false;
        boolean down = vio < 0;
        if(((check.getMaxping() > 0 && CheckManager.getInstance().getping(player) >= check.getMaxping()) || (check.getMintps() > 0 && check.getMintps() <= Tps.getTPS()))) return false;
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
            return false;
        }
        ViolationUpdateEvent e = new ViolationUpdateEvent(player, check.getViolations().containsKey(uuid)? check.getViolations().get(uuid)+vio:vio, check.getViolations().getOrDefault(uuid, 0D), check);
        Bukkit.getServer().getPluginManager().callEvent(e);
        if(e.isCancelled()) return false;
        check.getViolations().put(uuid, e.getNewViolation());
        if(check.isChild()) check.getParent().updateVio(check.getParent(), player, vio, other);
        if(down)return false;
        double violation = check.getViolations().get(uuid);
        ArrayList<Violation> triggert = new ArrayList<>();
        for(Violation cvio : check.getVios())
            if(cvio.getLevel() <= violation)
                triggert.add(cvio);
        boolean canReset = false;
        boolean setBack = false;
        ArrayList<String> violationExecutionNames = new ArrayList<>();
        triggert.forEach(violation1 -> violationExecutionNames.add(violation1.getType().name()));
        DebugEntry entry = TimberNoCheat.getInstance().getDebugLogManager().getLast(player);
        if(entry != null) entry.setExecutions(violationExecutionNames.toArray(new String[violationExecutionNames.size()]));
        final String id = triggert.isEmpty()?null:TimberNoCheat.getInstance().getDebugLogManager().generadeDebugId(player);
        for(Violation ctrig : triggert) {
            /* Definition for the Types can be read in Vialation.ViolationTypes */
            switch (ctrig.getType()) {
                case MESSAGE:
                    new TimberScheduler("ViolationExecutor(Message)", () -> player.sendMessage(TimberNoCheat.getInstance().getPrefix() + replaceMarker(ctrig.getRest(), player, check))).runNextTick();
                    break;
                case KICK:
                    new TimberScheduler("ViolationExecutor(Kick)", () -> {
                        if(TimberNoCheat.getInstance().getDebugConfig().isAntiKick()) {
                            Api.getNms().sendMainTitle(player, "§cYou would be kicked now!", -1, -1, -1);
                            Api.getNms().sendSubTitle(player, "§eId: " + id, -1, -1, -1);
                            player.sendMessage(TimberNoCheat.getInstance().getPrefix() + "You would be kicked now! Id: " + id);
                        }else kick(player, replaceMarker(ctrig.getRest(), player, check), check, id);
                    }).runNextTick();
                    canReset = true;
                    break;
                case NOTIFY:
                    new TimberScheduler("ViolationExecutor(Notify)", () -> CheckManager.getInstance().notify(check, " §6LEVEL: §b" + Maths.round(check.getViolations().get(uuid), 2), player, other)).runNextTick();
                    break;
                case CMD:
                    new TimberScheduler("ViolationExecutor(Command)", () -> {
                        for (String cmd : ctrig.getRest().split(":"))
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), replaceMarker(cmd, player, check).replaceFirst("/", ""));
                    }).runNextTick();
                    canReset = true;
                    break;
                case DAMAGE:
                    new TimberScheduler("ViolationExecutor(Damage)", () -> {
                        if (TimberNoCheat.getInstance().getDebugConfig().isWarnOnSetBacks() && TimberNoCheat.getInstance().getCountdownManager().isFinished(Countdowns.MESSAGE_HEALTH.name(), player))
                            player.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Du wurdest gerade von TNC 'gedamaged' weil es vermutet das du einen Hack Client benutzt! " +
                                    "Falls das nicht der Fall ist kannst du es gerne reporten mit dieser Id: '" + id + "'");
                        player.damage(Double.parseDouble(ctrig.getRest()));
                        TimberNoCheat.getInstance().getCountdownManager().setCountdown(Countdowns.MESSAGE_HEALTH.name(), 20*60*2, player, true);
                    }).runNextTick();
                    break;
                case SETBACK:
                    new TimberScheduler("ViolationExecutor(SetBack)", () -> {
                        if (TimberNoCheat.getInstance().getDebugConfig().isWarnOnSetBacks() && TimberNoCheat.getInstance().getCountdownManager().isFinished(Countdowns.MESSAGE_SETBACK.name(), player))
                            player.sendMessage(TimberNoCheat.getInstance().getPrefix() + "Du wurdest gerade von TNC 'geflagdt' weil es vermutet das du einen Hack Client benutzt! " +
                                    "Falls das nicht der Fall ist kannst du es gerne reporten mit dieser Id: '" + id + "'");
                        TimberNoCheat.getInstance().getCountdownManager().setCountdown(Countdowns.MESSAGE_SETBACK.name(), 20*60*2, player, true);
                    }).runNextTick();
                    setBack = true;
                    break;
                default:
                    TimberNoCheat.getInstance().getLogger().log(Level.WARNING, "Unknomn ViolationCheckType: " + ctrig.getType().name());
                    break;
            }
        }

        if(check.isResetafter() && canReset) {
            ViolationUpdateEvent e1 = new ViolationUpdateEvent(player, 0, check.getViolations().get(uuid), check);
            Bukkit.getServer().getPluginManager().callEvent(e1);
            if(e1.isCancelled()){
                return setBack;
            }
            check.getViolations().put(uuid, e.getNewViolation());
        }
        return setBack;
    }

    private void kick(final Player player, final String reason, final Check check, final String id){
        Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), () -> player.kickPlayer("§f--------§b[§9T§cN§eC§7§b]§f--------\n\n\n" +
                                                                                                "§9Timber§cNo§eCheat§7\n\n" +
                                                                                                "§bCheck: §f" + check.displayName() + "\n\n§bGrund: §f" + reason + "\n\n§bDebug-Id: §f" + id + "\n\n\n" +
                                                                                                "§f--------§b[§9T§cN§eC§7§b]§f--------"), 20*10);
        TimberNoCheat.getInstance().getListenerManager().getFreezeListener().freeze(player, 10*1000);
        runTask(() -> player.getWorld().playEffect(player.getLocation().clone().add(0, 1.8, 0), Effect.ENDER_SIGNAL, 1), 20, 9);
        runTask(() -> {
            for(int i = 0;i < 50;i++){
                final Location location = player.getEyeLocation();
                final double pitch = Math.toRadians(location.getYaw() + 90);
                final double yaw = Math.toRadians(location.getPitch() + 90);

                final double posX = Math.sin(pitch) + Math.cos(yaw);
                final double posZ = Math.sin(pitch) + Math.sin(yaw);
                final double posY = Math.cos(pitch);
                final Location spawnLocation = new Location(location.getWorld(), location.getX() + i * posX,
                        location.getY() + i * posY,
                        location.getZ() + i * posZ);
                spawnLocation.getWorld().playEffect(spawnLocation, Effect.LAVA_POP, 1);
            }
        }, 40, 4);
    }

    private void playEffect(final Location location, final Effect effect){
        final World world = location.getWorld();
        world.playEffect(location, effect, BlockFace.SOUTH);
        world.playEffect(location, effect, BlockFace.EAST);
        world.playEffect(location, effect, BlockFace.WEST);
        world.playEffect(location, effect, BlockFace.NORTH);
    }

    private void runTask(final Runnable runnable, final long delay, final int times){
        for(int i = 0;i < times;i++)
            Bukkit.getScheduler().runTaskLater(TimberNoCheat.getInstance(), runnable, i*delay);
    }

    private String replaceMarker(String s, Player p, Check check){
        s = ChatColor.translateAlternateColorCodes('&', s);
        s = s.replaceAll("%player%", p.getName());
        s = s.replaceAll("%uuid%", p.getUniqueId().toString());
        s = s.replaceAll("%ip%", p.getAddress().getAddress().getHostAddress());
        s = s.replaceAll("%ping%", String.valueOf(CheckManager.getInstance().getping(p)));
        s = s.replaceAll("%pingcolor%", String.valueOf(CheckManager.getInstance().getPingColor(p)));
        s = s.replaceAll("%display%", p.getDisplayName());
        s = s.replaceAll("%tapname%", p.getPlayerListName());
        s = s.replaceAll("%vio%", String.valueOf(check.getViolations().getOrDefault(p.getUniqueId(), 0D)));
        s = s.replaceAll("%tps%", String.valueOf(Tps.getTPS()));
        s = s.replaceAll("%tpscolor%", CheckManager.getInstance().getTpsColor());
        return s.replaceAll("%port%", String.valueOf(Bukkit.getPort()));
    }
}
