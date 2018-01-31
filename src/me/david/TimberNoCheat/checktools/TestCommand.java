package me.david.TimberNoCheat.checktools;


import me.david.api.commands.CheckBuilder;
import me.david.api.commands.Command;
import me.david.api.commands.checkers.Integercheck;
import me.david.api.commands.checkers.Playercheck;
import me.david.api.commands.checkers.Stringcheck;
import me.david.api.commands.checkers.time.TimeSettings;
import me.david.api.commands.checkers.time.Timecheck;
import me.david.api.webserver.Response;
import org.bukkit.command.CommandSender;

public class TestCommand extends Command {
//new CheckBuilder().addMultiPlayerPermissionChecker({"example"})new ArrayList<>(Arrays.asList(new MultiChoices(new ArrayList<>(Arrays.asList(new Choice("example", new String[]{"ex", "examlple"}, "a.b.c", new ArrayList<>(Arrays.asList(new Timecheck(TimeSettings.PAST, Response.DATE_FORMAT.toString()), new Integercheck(3, 2, true)))), new Choice("error", new String[]{"er", "fehler"}, "a.b.c", new ArrayList<>(Arrays.asList(new Timecheck(TimeSettings.PAST, Response.DATE_FORMAT.toString()), new Stringcheck(20, 10, true), new Playercheck(true, true)))))))))
    public TestCommand() {
        super("Test", new CheckBuilder()
                .addMultiPart("example", new String[]{"ex", "examlple"}, true, "a.b.c", new CheckBuilder().add(new Timecheck(TimeSettings.PAST, Response.DATE_FORMAT.toString())).add(new Integercheck(3, 2, true)))
                .addMultiPart("error", new String[]{"er", "fehler"}, true, "a.b.c", new CheckBuilder().add(new Timecheck(TimeSettings.PAST, Response.DATE_FORMAT.toString())).add(new Stringcheck(20, 10, true)).add(new Playercheck(true, true)))
                .build(), "§bUnaTest §7> §6", false, new String[]{"a", "una", "hi"});
        setDesc("Tolle Beschreibung");
    }

    @Override
    public void execute(CommandSender sender, org.bukkit.command.Command cmd, String[] args) throws Exception {
        if(args.length != 0 && args[0].equals("error")) throw new IllegalAccessException("Fehler oder so");
    }
}
