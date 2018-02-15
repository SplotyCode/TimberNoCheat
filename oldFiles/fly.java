package me.david.TimberNoCheat.checkes.movement;

import com.google.common.collect.Lists;
import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.TimberNoCheat.checktools.FalsePositive;
import me.david.TimberNoCheat.checktools.General;
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
    private final double simplevio;
    private final boolean simple;
    private final boolean glide;
    private final String setback;

    public Fly(){
        super("Fly", Category.MOVEMENT);
        vanilla = getBoolean("vanilla");
        simple = getBoolean("simple.enable");
        simplevio = getDouble("simple.vio");
        glide = getBoolean("glide");
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
    public void playermove(PlayerMoveEvent e){
        final Player p = e.getPlayer();
        final Location to = e.getTo();
        final Location from = e.getFrom();
        if (!TimberNoCheat.checkmanager.isvalid_create(p) || e.isCancelled()) {
            return;
        }
        TimberNoCheat.instance.getMoveprofiler().start("Fly ");
        boolean onGround = false;
        AABBBox playerBox = Api.instance.nms.getBoundingBox(p).expand(0, 0.15, 0);
        for(int x = p.getLocation().getBlockX()-1; x<p.getLocation().getBlockX()+3; x++)
            for(int z = p.getLocation().getBlockZ()-1; z<p.getLocation().getBlockZ()+3; z++)
                for(double y = 0.1;y<1.1;y+=0.1) {
                    Location loc = new Location(p.getWorld(), x, p  .getLocation().getY() - y, z);
                    Block block = loc.getBlock();
                    if(block.getType() != Material.AIR && playerBox.intersectsWith(Api.instance.nms.getBoundingBox(block)))
                        onGround = true;
                }
        System.out.println(onGround);


        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        FalsePositive.FalsePositiveChecks fp = pd.getFalsepositives();
        //System.out.println(simple + " " + inair(p) + " " + (!fp.enderpearl && !fp.hasPiston(60) && !fp.hasSlime(200) && !fp.hasVehicle(60) && !fp.hasRod(40 * 5) && !fp.hasHitorbow(60 * 5) && !fp.hasExplosion(80 * 5) && !p.getAllowFlight() && to.getY() >= from.getY() && (p.getActivePotionEffects().stream().noneMatch(potionEffect -> potionEffect.getType() == PotionEffectType.JUMP) || to.getY() - from.getY() > getJump(p))));
        if(simple && inair(p) && !fp.enderpearl && !fp.hasPiston(60) && !fp.hasSlime(200) && !fp.hasVehicle(60) && !fp.hasRod(40 * 5) && !fp.hasHitorbow(60 * 5) && !fp.hasExplosion(80 * 5) && !p.getAllowFlight() && to.getY() >= from.getY() && (fp.jumpboost(p) || to.getY() - from.getY() > getJump(p))){
            updatevio(this, p, simplevio, " §6CHECK: §bSIMPLE");
            setBack(p);
        }
        if(glide){
            if(p.getAllowFlight() || fp.enderpearl || fp.hasClimp(140) || PlayerUtil.isOnGround(p)){
                pd.setGlide(-1);
                return;
            }
            if (e.getTo().getX() != e.getFrom().getX() || e.getTo().getZ() != e.getFrom().getZ()) {
                double yDiff = e.getFrom().getY() - e.getTo().getY();
                if (yDiff > 0 && yDiff <= 0.16) {
                    if (pd.getGlide() == -1) {
                        pd.setGlide(System.currentTimeMillis());
                        return;
                    }
                    long delay = System.currentTimeMillis() - pd.getGlide();
                    if (delay > 800) {
                        pd.setGlide(System.currentTimeMillis()-250);
                        setBack(p);
                        updatevio(this, p, (0.16D-yDiff+4)*delay/3, " §6CHECK: §bGLIDE/SLOWFALL");
                    }
                } else pd.setGlide(-1);
            }
        }
        if(pd.isHurttime() && PlayerUtil.isOnGround(p) && p.getNoDamageTicks() == 0)
            pd.setHurttime(false);
        if(!pd.isHurttime() && p.getNoDamageTicks() != 0)
            pd.setHurttime(true);
        if(!pd.isHurttime()) pd.setFlycount(0);
        if(PlayerUtil.isOnGround(p)) pd.setFlycount(0);
        else pd.setFlycount(pd.getFlycount()+1);

        TimberNoCheat.instance.getMoveprofiler().end();
    }

    private void setBack(Player p){
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(p);
        switch (setback){
            case "cancel":
                p.teleport(pd.getGenerals().getLastOnGround(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            case "down":
                p.teleport(p.getLocation().subtract(0, (blocksDown(p) > 3?3:blocksDown(p)), 0));
                break;
        }

    }

    private int blocksDown(Player p){
        int blocks = 0;
        while(true){
            if(p.getLocation().clone().subtract(0, blocks, 0).getBlock().getType() != Material.AIR) return blocks;
            else blocks++;
        }
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
