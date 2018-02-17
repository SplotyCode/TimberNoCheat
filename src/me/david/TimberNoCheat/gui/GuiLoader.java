package me.david.TimberNoCheat.gui;

import me.david.TimberNoCheat.TimberNoCheat;
import me.david.TimberNoCheat.config.Permissions;
import me.david.TimberNoCheat.gui.debug.DebugSetting;
import me.david.TimberNoCheat.gui.debug.GuiDebug;
import me.david.TimberNoCheat.gui.items.GuiItemTest;
import me.david.TimberNoCheat.gui.items.GuiItemTools;
import me.david.TimberNoCheat.gui.items.GuiItems;
import me.david.TimberNoCheat.gui.oreNotify.OreNotifyGui;
import me.david.TimberNoCheat.gui.profiler.GuiMoveProfiler;
import me.david.TimberNoCheat.gui.profiler.GuiProfiler;
import me.david.TimberNoCheat.gui.profiler.ProfilerAllScheduler;
import me.david.TimberNoCheat.gui.settings.*;
import org.bukkit.permissions.Permission;

public class GuiLoader {

    public GuiLoader(TimberNoCheat pl){
        pl.addGui("ItemsMulti", Permissions.ITEMS, new GuiItems());
        pl.addGui("ItemsTestMulti", Permissions.ITEMS_TEST, new GuiItemTest());
        pl.addGui("ItemsToolMulti", Permissions.ITEMS_TOOLS, new GuiItemTools());

        pl.addGui("ProfilerMulti", Permissions.PROFILER, new GuiProfiler());
        pl.addGui("ProfilerAllSchedulerMulti", Permissions.PROFILER_ALLSCHEDULER, new ProfilerAllScheduler());
        pl.addGui("MoveProfilerMulti", Permissions.PROFILER_MOVEMENT, new GuiMoveProfiler(pl));

        pl.addGui("DebuggerMulti", Permissions.DEBUGGER, new GuiDebug());
        pl.addGui("DebugSettingMulti", Permissions.DEBUGGER, new DebugSetting());

        pl.addGui("SettingsMulti", Permissions.SETTINGS, new SettingsGui());
        pl.addGui("CheckMulti", Permissions.SETTINGS, new CheckGui());
        pl.addGui("ReloadMulti", Permissions.SETTINGS, new ReloadGui());
        pl.addGui("CustomSettingMulti", Permissions.SETTINGS, new CustomSettingsGui());
        pl.addGui("ViolationMulti", Permissions.SETTINGS, new ViolationGui());

        pl.addGui("OreNotifyMulti", Permissions.ORE_NOTIFY, new OreNotifyGui());

    }
}
