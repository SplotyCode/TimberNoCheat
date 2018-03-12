package me.david.timbernocheat.discord;

import me.david.api.utils.HastebinUtil;
import me.david.timbernocheat.TimberNoCheat;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscordManager {

    private JDA jda;
    private User david;

    public DiscordManager(){
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken("NDIyNzk1ODA4OTg0MDA2NjY3.DYg_Cw.Ib0syElh0hIBuhBbP7Oiid3JroM")
                    //.addEventListener(new MessageHandler(), new GuildHandler())
                    .setAutoReconnect(true)
                    .setMaxReconnectDelay(120)
                    .setEnableShutdownHook(true)
                    .setStatus(OnlineStatus.IDLE)
                    .buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
        david = jda.getUserById(267401892571774977L);
        //sendError("An error", new NullPointerException(), ErrorType.SCHEDULER);
    }

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("kk:mm:ss | dd/MM/yy");

    public void sendError(String message, Throwable ex, ErrorType type, MessageEmbed.Field... fields){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        pw.close();
        try {
            sw.close();
        } catch (IOException e) {
            //Unlikly but if we would call sendError we will probalby couse a loop...
            e.printStackTrace();
        }
        david.openPrivateChannel().queue((channel) -> {
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.RED).
                    setTitle("Wooops - An Exepction").
                    addField(new MessageEmbed.Field("Time", DATE_FORMAT.format(new Date()), true)).
                    addField(new MessageEmbed.Field("Message", ex.getMessage() == null?"Keine Message":ex.getMessage(), true)).
                    addField(new MessageEmbed.Field("Type", type.name(), true)).
                    addField(new MessageEmbed.Field("Address", getServerServer() + "(" + Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort() + ")", true)).
                    addField(new MessageEmbed.Field("Stage", TimberNoCheat.getInstance().getStartState().name() + "(" + TimberNoCheat.getInstance().getStartState().getStateType().name() + ")", true)).
                    setDescription(message);
            for(MessageEmbed.Field field : fields) embed.addField(field);
            String trace = stackTrace;
            if(stackTrace.length()+8+embed.length() > 1024){
                String hastebin = HastebinUtil.paste(trace);
                trace = "Trace was to big: " + (hastebin == null?"Fehler beim Hochladen":"https://hastebin.com/" + hastebin);
            }
            embed.addField(new MessageEmbed.Field("StackTrace", trace, false));
            channel.sendMessage(embed.build()).queue();
        });
    }

    public void sendInfo(String info){
        david.openPrivateChannel().queue((channel) -> {
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.CYAN).
                    setTitle("Information").
                    addField(new MessageEmbed.Field("Time", DATE_FORMAT.format(new Date()), true)).
                    addField(new MessageEmbed.Field("Address", getServerServer() + "(" + Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort() + ")", true)).
                    addField(new MessageEmbed.Field("Stage", TimberNoCheat.getInstance().getStartState().name() + "(" + TimberNoCheat.getInstance().getStartState().getStateType().name() + ")", true)).
                    setDescription(info);
            channel.sendMessage(embed.build()).queue();
        });
    }

    public void sendWarning(String warning){
        david.openPrivateChannel().queue((channel) -> {
            EmbedBuilder embed = new EmbedBuilder().setColor(Color.ORANGE).
                    setTitle("Wooops - A Warning").
                    addField(new MessageEmbed.Field("Time", DATE_FORMAT.format(new Date()), true)).
                    addField(new MessageEmbed.Field("Address", getServerServer() + "(" + Bukkit.getServer().getIp() + ":" + Bukkit.getServer().getPort() + ")", true)).
                    addField(new MessageEmbed.Field("Stage", TimberNoCheat.getInstance().getStartState().name() + "(" + TimberNoCheat.getInstance().getStartState().getStateType().name() + ")", true)).
                    setDescription(warning);
            channel.sendMessage(embed.build()).queue();
        });
    }

    private String getServerServer(){
        switch (Bukkit.getServer().getPort()){
            case 25566:
                return "Grosser Testi";
            case 25565:
                return new File("/home/david/").exists()?"Davids Testi":"TimberMain";
        }
        return "Unknown";
    }

    public enum ErrorType {

        SCHEDULER,
        MODULE,
        SUBMODULE_LOAD,
        OTHER

    }
}
