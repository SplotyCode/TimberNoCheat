package me.david.TimberNoCheat.gui;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.config.Permissions;

public class GuiLoader {

    public GuiLoader(TimberNoCheat pl){
        pl.addGui("ItemsMulti", Permissions.ITEMS, new GuiItems());
        pl.addGui("ItemsTestMulti", Permissions.ITEMS_TEST, new GuiItemTest());
        pl.addGui("ItemsToolMulti", Permissions.ITEMS_TOOLS, new GuiItemTools());

        pl.addGui("ProfilerMulti", Permissions.PROFILER, new GuiProfiler());
        pl.addGui("ProfilerAllSchedulerMulti", Permissions.PROFILER_ALLSCHEDULER, new ProfilerAllScheduler());
        pl.addGui("MoveProfilerMulti", Permissions.PROFILER_MOVEMENT, new GuiMoveProfiler(pl));

        pl.addGui("DebuggerMulti", Permissions.DEBUGGER, new DebugGui());
    }
}
