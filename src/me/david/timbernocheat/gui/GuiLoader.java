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
import me.david.timbernocheat.gui.profiler.GuiMoveProfiler;
import me.david.timbernocheat.gui.profiler.GuiProfiler;
import me.david.timbernocheat.gui.profiler.ProfilerAllScheduler;
import me.david.timbernocheat.gui.settings.*;

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

        pl.addGui("TriggerBlockMulti", Permissions.BLOCK_TRIGGERS, new TriggerBlockGui());
        pl.addGui("TriggerBlockManageMulti", Permissions.BLOCK_TRIGGERS, new TriggerBlockManageGui());
    }
}
