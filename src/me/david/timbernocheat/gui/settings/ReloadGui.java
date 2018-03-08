package me.david.timbernocheat.gui.settings;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.api.FarbCodes;
import me.david.api.guis.CloseReason;
import me.david.api.guis.standart.BooleanGui;
import me.david.api.sound.Sound;
import me.david.api.sound.SoundCategory;
import me.david.api.utils.ItemStackUtil;
import org.bukkit.entity.Player;

public class ReloadGui extends BooleanGui {
    public ReloadGui() {
        super("ReloadGui", Permissions.SETTINGS, new Sound("SettingsGui", SoundCategory.INVENOTY_OPEN, org.bukkit.Sound.LEVEL_UP, TimberNoCheat.getInstance()), ItemStackUtil.createColoredWool("§aJip", 1, FarbCodes.LIME.getId()), ItemStackUtil.createColoredWool("§cEhhh", 1, FarbCodes.RED.getId()), "§6Reload?");
    }

    @Override
    protected void action(Player p, boolean result) {
        if(SettingsGui.currentCheck.containsKey(p.getUniqueId()))
            SettingsGui.currentCheck.remove(p.getUniqueId());
        if(result) p.performCommand("tnc reload");
        TimberNoCheat.getInstance().getGuimanager().removeMultiGui(p, false, CloseReason.REMOVE);
    }
}
