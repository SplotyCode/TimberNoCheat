package me.david.TimberNoCheat.checkes.movement;

import com.google.common.collect.Lists;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.FalsePositive;
import me.david.TimberNoCheat.checktools.General;
import me.david.TimberNoCheat.checktools.MaterialHelper;
import me.david.TimberNoCheat.util.SpeedUtil;
import me.david.api.Api;
import me.david.api.nms.AABBBox;
import me.david.api.utils.BlockUtil;
import me.david.api.utils.player.PlayerUtil;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MathHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Fly extends Check {

    private final boolean vanilla;
    private final String setback;

    public Fly(){
        super("Fly", Category.MOVEMENT);
        vanilla = getBoolean("vanilla");
        setback = getString("setbackmethode");
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        if (!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer()) || e.isCancelled()) {
            return;
        }
        if(vanilla && e.getReason().equals("Flying is not enabled on this server")) {
            updatevio(this, e.getPlayer(), 1, " §6CHECK: §bVANILLA");
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerMove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()) return;
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        TimberNoCheat.instance.getMoveprofiler().start("Fly ");
        System.out.println(pd.getGenerals().getTicksInAir() + " " + p.getVelocity().getY());
        TimberNoCheat.instance.getMoveprofiler().end();
    }

    private void setBack(Player p){
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        switch (setback){
            case "cancel":
                p.teleport(pd.getGenerals().getLastOnGround(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            case "down":
                p.teleport(p.getLocation().subtract(0, Math.min(3, groundDistance(p)), 0));
                break;
        }

    }

    private double groundDistance(Player p){
        double distance = 0;
        Location location = p.getLocation();
        while (!location.getBlock().getType().isSolid() && !MaterialHelper.LIQUID.contains(location.getBlock().getType())) {
            location.subtract(0, 0.1, 0);
            distance += 0.1;
        }
        return distance;
    }

    private boolean inair(Player p){
        for(Block b : BlockUtil.getBlocksAround(p.getLocation(), 3))
            if(b.getType() != Material.AIR)
                return false;
        return true;
    }

    private double getJump(Player p) {
        double d;
        if (p.hasPotionEffect(PotionEffectType.JUMP)) {
            int level = SpeedUtil.getPotionEffectLevel(p, PotionEffectType.JUMP);
            switch (level){
                case 1:
                    d = 1.9;
                    break;
                case 2:
                    d = 2.7;
                    break;
                case 3:
                    d = 3.36;
                    break;
                case 4:
                    d = 4.22;
                    break;
                case 5:
                    d = 5.16;
                    break;
                default:
                    d = (level) + 1;
                    break;
            }
        }else return 0;
        return d+1.35;
    }


}
