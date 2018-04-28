package me.david.timbernocheat.command;

import me.david.api.utils.NumberUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TNCInternal implements CommandExecutor {

    private static TNCInternal instance;
    private HashMap<Long, Listener> handlers = new HashMap<>();
    private long index = 0;

    public interface Listener {
        void onClick(final Player player, String value);
    }

    static {
        instance = new TNCInternal();
    }

    private TNCInternal(){}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player) || args.length != 2 || !NumberUtil.isLong(args[0])) {
            commandSender.sendMessage("Only Internal Stuff...");
            return true;
        }
        Listener listener = handlers.get(Long.valueOf(args[0]));
        if(listener == null) {
            commandSender.sendMessage("Feature not Supported...");
            return true;
        }

        listener.onClick((Player) commandSender, args[1]);

        return true;
    }

    public long addHandler(Listener listener){
        handlers.put(index, listener);
        index++;
        return index-1;
    }


    public static TNCInternal getInstance() {
        return instance;
    }
}
