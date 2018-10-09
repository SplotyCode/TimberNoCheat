package me.david.timbernocheat.api.hook;

import com.google.common.reflect.ClassPath;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.api.PunishmentEvent;
import me.david.timbernocheat.api.ViolationUpdateEvent;
import me.david.timbernocheat.checkbase.Check;
import me.david.timbernocheat.checkbase.Violation;
import me.david.timbernocheat.defaulthooks.DisabledHook;
import me.david.timbernocheat.exeptions.logging.LogLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages all Hooks
 */
public class HookManager implements Listener {

    private Set<TNCHook> loadedHooks = new HashSet<>();
    private Set<TNCHook> disabledHooks = new HashSet<>();

    private boolean defaultHooksLoaded = false;

    private static HookManager instance = new HookManager();

    private HookManager() {}

    public void loadInternals() {
        if (defaultHooksLoaded) throw new IllegalStateException("Internal Hooks already loaded!");
        defaultHooksLoaded = true;

        /* Taken me 40minutes before i noticed that Plugin classes are not in the normal class loader or in the class loader of the main thread */
        final ClassLoader loader = TimberNoCheat.getInstance().getClass().getClassLoader();
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(loader).getTopLevelClasses("me.david.timbernocheat.defaulthooks")) {
                Class<?> clazz = classInfo.load();
                if (TNCHook.class.isAssignableFrom(clazz) && !clazz.isAnnotationPresent(DisabledHook.class)) {
                    load((Class<? extends TNCHook>) clazz);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load(TNCHook hook) {
        if (hook.versionVerifier().verify(TimberNoCheat.getInstance().getVersion())) {
            disabledHooks.remove(hook);
            loadedHooks.add(hook);
            TimberNoCheat.getInstance().log("Loaded Hook: " + hook.getDisplayName(), LogLevel.INFO);
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
            boolean cancel = loadedHooks.stream().anyMatch(hook ->
                    (hook.check() == null || hook.check() == check) &&
                            hook.violation(check, player, afterVio, addedVio)
            );
            if (cancel) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPunishment(final PunishmentEvent event) {
        final Check check = event.getCheck();
        final Player player = event.getPlayer();
        final Violation.ViolationTypes type = event.getType();
        
        event.setCancelled(loadedHooks.stream().anyMatch(hook ->
                (hook.check() == null || hook.check() == check) &&
                        hook.punishment(check, player, type)
        ));
    }

    public Collection<TNCHook> getDisabledHooks() {
        return disabledHooks;
    }

    public Collection<TNCHook> getLoadedHooks() {
        return loadedHooks;
    }

    public static HookManager getInstance() {
        return instance;
    }
}
