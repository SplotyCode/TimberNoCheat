package me.david.timbernocheat.gui;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.config.Permissions;
import me.david.timbernocheat.gui.blocktrigger.TriggerBlockGui;
import me.david.timbernocheat.gui.blocktrigger.TriggerBlockManageGui;
import me.david.timbernocheat.gui.debug.DebugSetting;
import me.david.timbernocheat.gui.debug.GuiDebug;
import me.david.timbernocheat.gui.items.GuiItemTest;
import me.david.timbernocheat.gui.items.GuiItemTools;
import me.david.timbernocheat.gui.items.GuiItems;
import me.david.timbernocheat.gui.oreNotify.OreNotifyGui;
import me.david.timbernocheat.gui.profiler.*;
import me.david.timbernocheat.gui.settings.*;
import me.david.timbernocheat.gui.violation.GlobalViolationGui;
import me.david.timbernocheat.gui.violation.PlayerViolationGui;

public class GuiLoader {

    public GuiLoader(TimberNoCheat pl){
        pl.addGui("ItemsMulti", Permissions.ITEMS, new GuiItems());
        pl.addGui("ItemsTestMulti", Permissions.ITEMS_TEST, new GuiItemTest());
        pl.addGui("ItemsToolMulti", Permissions.ITEMS_TOOLS, new GuiItemTools());

        pl.addGui("ProfilerMulti", Permissions.PROFILER, new GuiProfiler());
        pl.addGui("ProfilerAllSchedulerMulti", Permissions.PROFILER_ALLSCHEDULER, new ProfilerAllScheduler());
        pl.addGui("MoveProfilerMulti", Permissions.PROFILER_MOVEMENT, new GuiMoveProfiler(pl));
        pl.addGui("EventProfilerMulti", Permissions.PROFILER_EVENT, new EventProfiler());
        pl.addGui("PluginEventProfilerMulti", Permissions.PROFILER_EVENT, new PluginEventProfiler());
        pl.addGui("ProfilerSchedulerMulti", Permissions.PROFILER_SCHEDULER, new SchedulerProfilerGui());

        pl.addGui("DebuggerMulti", Permissions.DEBUGGER, new GuiDebug());
        pl.addGui("DebugSettingMulti", Permissions.DEBUGGER, new DebugSetting());

        pl.addGui("SettingsMulti", Permissions.SETTINGS, new SettingsGui());
        pl.addGui("CheckMulti", Permissions.SETTINGS, new CheckGui());
        pl.addGui("ReloadMulti", Permissions.SETTINGS, new ReloadGui());
        pl.addGui("CustomSettingMulti", Permissions.SETTINGS, new CustomSettingsGui());
        pl.addGui("ViolationMulti", Permissions.SETTINGS, new ViolationGui());

        pl.addGui("OreNotifyMulti", Permissions.ORE_NOTIFY, new OreNotifyGui());

        pl.addGui("TriggerBlockMulti", Permissions.BLOCK_TRIGGERS, new TriggerBlockGui());
        pl.addGui("TriggerBlockManageMulti", Permissions.BLOCK_TRIGGERS, new TriggerBlockManageGui());

        pl.addGui("PlayerViolationMulti", Permissions.VIOLATIONMENU, new PlayerViolationGui());
        pl.addGui("GlobalViolationMulti", Permissions.VIOLATIONMENU, new GlobalViolationGui());
    }
}
