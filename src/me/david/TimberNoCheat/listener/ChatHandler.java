package me.david.TimberNoCheat.listener;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.gui.settings.CustomSettingsGui;
import me.david.TimberNoCheat.gui.settings.SettingsGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event){
        String node = CustomSettingsGui.players.get(event.getPlayer().getUniqueId());
        if(node == null)return;
        SettingsGui.currentCheck.get(event.getPlayer().getUniqueId()).getYml().set(node, event.getMessage());
        TimberNoCheat.instance.guimanager.startMultidefaultStage(event.getPlayer(), "ReloadMulti");
    }
}
