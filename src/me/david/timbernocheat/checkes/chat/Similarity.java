package me.david.timbernocheat.checkes.chat;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkbase.Category;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.CheckManager;
import me.david.timbernocheat.checkbase.PlayerData;
import me.david.api.utils.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Similarity extends Check {

    private final int SIMILARITY;
    private final boolean STRIP_COLORS;
    private final boolean STRIP_DUPLICATES;
    private final int MINLENGTH;

    public Similarity() {
        super("Similarity", Category.CHAT);
        SIMILARITY = getInt("similarity");
        STRIP_COLORS = getBoolean("strip_chars");
        STRIP_DUPLICATES = getBoolean("strip_duplicates");
        MINLENGTH = getInt("minlen");
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        String message = event.getMessage();

        if (!CheckManager.getInstance().isvalid_create(player) || message.startsWith("/")) return;
        if(STRIP_COLORS) message = message.replaceAll("[^a-zA-Z0-9\\s]", "");
        if(STRIP_DUPLICATES){
            message = message.replaceAll("(.)(?=\\1\\1+)", "");
            message = message.replaceAll("(..)(?=\\1\\1+)", "");
            message = message.replaceAll("(...)(?=\\1\\1+)", "");
        }
        PlayerData pd = CheckManager.getInstance().getPlayerdata(player);
        message = ChatColor.stripColor(message);
        int similarityPercentage = StringUtil.similarity(message, pd.getGenerals().getMessages().get(pd.getGenerals().getMessages().size()-1));
        if(similarityPercentage >= SIMILARITY && event.getMessage().length() > MINLENGTH)
            if(updateVio(this, player, similarityPercentage/2, " §6SIMILARITY: §b" + similarityPercentage))
                event.setCancelled(true);
    }
}
