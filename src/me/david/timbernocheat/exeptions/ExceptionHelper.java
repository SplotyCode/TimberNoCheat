package me.david.timbernocheat.exeptions;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.discord.DiscordManager;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.logging.Level;

public class ExceptionHelper {

    private final TimberNoCheat tnc = TimberNoCheat.getInstance();

    public void reportException(Throwable ex, String message){
        reportException(ex, message, DiscordManager.ErrorType.OTHER);
    }

    public void reportException(Throwable ex, String message, DiscordManager.ErrorType type, MessageEmbed.Field... fields){
        tnc.getLogger().log(Level.WARNING, "Ein Fataler Fehler in der stage '" + tnc.getStartState() + "' ist passiert! Nachicht: " + message);
        if(tnc.isDebug()){
            tnc.getLogger().log(Level.INFO, "Da TNC im debug Mode leuft werden ein Stacktrace direkt in der Konsole ausgegeben! Hier biddde: ");
            ex.printStackTrace();
        }else tnc.getLogger().log(Level.INFO, "Da TNC nicht im debug Mode leuft werden kein Stacktrace direkt in der Konsole ausgegeben!");

        if (tnc.getDiscordManager() == null) {
            tnc.getOnPostLoad().add(() -> tnc.getDiscordManager().sendError(message, ex, type, fields));
        } else {
            tnc.getDiscordManager().sendError(message, ex, type, fields);
        }
    }

}
