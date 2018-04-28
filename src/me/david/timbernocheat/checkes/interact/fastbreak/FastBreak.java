package me.david.timbernocheat.checkes.interact.fastbreak;

import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;


//TODO
public class FastBreak extends Check {

    public FastBreak() {
        super("FastBreak", Category.INTERACT);
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent event) {

    }
}
