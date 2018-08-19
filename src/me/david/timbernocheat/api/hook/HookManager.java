package me.david.timbernocheat.api.hook;

import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.checkbase.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HookManager implements Listener {

    private Set<TNCHook> loadedHooks = new HashSet<>();
    private Set<TNCHook> disabledHooks = new HashSet<>();

    public void load(TNCHook hook) {
        if (hook.versionVerifier().verify(TimberNoCheat.getInstance().getVersion())) {
            disabledHooks.remove(hook);
            loadedHooks.add(hook);
        } else {
            String message = hook.getDisplayName() + " is not compatible with " + TimberNoCheat.getInstance().pluginDisplayName();
            TimberNoCheat.getInstance().reportException(new VersionVerifierException(message), message);
            unload(hook);
        }
    }

    public void load(Class<? extends TNCHook> clazz) {
        try {
            TNCHook hook = clazz.newInstance();
            load(hook);
        } catch (InstantiationException | IllegalAccessException e) {
            String message = "Error in Hook Constructor";
            TimberNoCheat.getInstance().reportException(new HookLoadException(message), message);
        }
    }

    public void unload(TNCHook hook) {
        loadedHooks.remove(hook);
        disabledHooks.add(hook);
    }

    @EventHandler
    public void onViolation(final ViolationUpdateEvent event) {
        final Check check = event.getCheck();
        final Player player = event.getPlayer();
        final double afterVio = event.getNewViolation();
        final double addedVio = event.getNewViolation() - event.getOldViolation();

        if (addedVio > 0) {
            loadedHooks.forEach(hook -> hook.violation(check, player, afterVio, addedVio));
        }
    }

    public Collection<TNCHook> getDisabledHooks() {
        return disabledHooks;
    }

    public Collection<TNCHook> getLoadedHooks() {
        return loadedHooks;
    }
}
