package me.david.TimberNoCheat.checkes.chat;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.checkmanager.Category;
import me.david.TimberNoCheat.checkmanager.Check;
import me.david.TimberNoCheat.checkmanager.PlayerData;
import me.david.api.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Similarity extends Check {

    private final int similarity;
    private final boolean strip_chars;
    private final boolean strip_duplicates;
    private final int minlen;

    public Similarity() {
        super("Similarity", Category.CHAT);
        similarity = getInt("similarity");
        strip_chars = getBoolean("strip_chars");
        strip_duplicates = getBoolean("strip_duplicates");
        minlen = getInt("minlen");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (!TimberNoCheat.checkmanager.isvalid_create(event.getPlayer()) || event.getMessage().startsWith("/")) return;
        String message = event.getMessage();
        if(strip_chars) message = message.replaceAll("[^a-zA-Z0-9\\s]", "");
        if(strip_duplicates){
            message = message.replaceAll("(.)(?=\\1\\1+)", "");
            message = message.replaceAll("(..)(?=\\1\\1+)", "");
            message = message.replaceAll("(...)(?=\\1\\1+)", "");
        }
        PlayerData pd = TimberNoCheat.checkmanager.getPlayerdata(event.getPlayer());
        message = ChatColor.stripColor(message);
        int similarityper = StringUtil.similarity(message, pd.getGenerals().getMessages().get(pd.getGenerals().getMessages().size()-1));
        if(similarityper >= similarity && event.getMessage().length() > minlen) updatevio(this, event.getPlayer(), similarityper/2, " §6SIMILARITY: §b" + similarityper);

    }
}
