package me.david.timbernocheat.exeptions;

import me.david.api.objects.Pair;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.discord.DiscordManager;
import me.david.timbernocheat.exeptions.logging.BlockingInformation;
import me.david.timbernocheat.exeptions.logging.IdentifierBlockingInformation;
import me.david.timbernocheat.exeptions.logging.Identifiers;
import me.david.timbernocheat.exeptions.logging.LogLevel;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;

public class ExceptionHelper {

    private final TimberNoCheat tnc = TimberNoCheat.getInstance();

    private Set<Pair<? extends Function<BlockingInformation, Boolean>, Long>> loggingBlocked = new HashSet<>();

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

    public void log(String message, LogLevel level, Identifiers identifier) {
        boolean blocked = loggingBlocked.stream().anyMatch(longPair -> {
            long delay = System.currentTimeMillis() - longPair.getTwo();
            IdentifierBlockingInformation information = new IdentifierBlockingInformation(identifier.identifier());
            return longPair.getOne().apply(information) && delay >= identifier.getBlockedTime();
        });
        if (!blocked) {
            log(message, level);
        }
    }

    public void log(String message, LogLevel level) {
        tnc.getLogger().log(level.getLevel(), message);
    }

}
