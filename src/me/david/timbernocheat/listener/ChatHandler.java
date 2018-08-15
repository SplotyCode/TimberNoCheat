package me.david.timbernocheat.listener;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.gui.settings.CustomSettingsGui;
import me.david.timbernocheat.gui.settings.SettingsGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event){
        String node = CustomSettingsGui.players.get(event.getPlayer().getUniqueId());
        if(node == null)return;
        SettingsGui.currentCheck.get(event.getPlayer().getUniqueId()).set(node, event.getMessage());
        TimberNoCheat.getInstance().getGuiManager().startMultidefaultStage(event.getPlayer(), "ReloadMulti");
    }
}
