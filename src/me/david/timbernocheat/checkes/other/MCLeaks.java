package me.david.timbernocheat.checkes.other;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import comphenix.packetwrapper.WrapperLoginClientEncryptionBegin;
import comphenix.packetwrapper.WrapperLoginClientStart;
import comphenix.tinyprotocol.Reflection;
import me.david.timbernocheat.TimberNoCheat;
import me.david.timbernocheat.checkmanager.Category;
import me.david.timbernocheat.checkmanager.Check;
import me.david.api.utils.CryptoUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;


import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.*;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MCLeaks extends Check {

    private LoadingCache<String, Boolean> caches = null;
    private LoadingCache<InetSocketAddress, String> names = null;

    private KeyPair serverKey;
    private Object service;

    private Class<?> classHasJoinedMinecraftServerResponse = null;
    private Class<?> classYggdrasilMinecraftSessionService = null;
    private Class<?> classYggdrasilAuthenticationService = null;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        if(!TimberNoCheat.checkmanager.isvalid_create(e.getPlayer())) return;
        final Boolean value = caches.getIfPresent(e.getPlayer().getName());
        if (value != null && !value) {
            updateVio(MCLeaks.this, e.getPlayer(), 1);
        }
    }
    public MCLeaks(){
        super("MCLeaks", Category.OTHER);
        try {
           caches = CacheBuilder.newBuilder()
                    .expireAfterAccess(5, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, Boolean>() {
                        @Override
                        public Boolean load(String uuid) {
                            return null;
                        }
                    });
            caches.invalidateAll();
            names = CacheBuilder.newBuilder()
                    .expireAfterAccess(2, TimeUnit.MINUTES)
                    .build(new CacheLoader<InetSocketAddress, String>() {
                        @Override
                        public String load(InetSocketAddress uuid) {
                            return null;
                        }
                    });

            Class<?> classCraftServer = Reflection.getCraftBukkitClass("CraftServer");
            Class<?> classMinecraftServer = Reflection.getMinecraftClass("MinecraftServer");
            Class<?> classMinecraftSessionService = Reflection.getClass("com.mojang.authlib.minecraft.MinecraftSessionService");
            classHasJoinedMinecraftServerResponse = Reflection.getClass("com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse");
            classYggdrasilMinecraftSessionService = Reflection.getClass("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
            classYggdrasilAuthenticationService = Reflection.getClass("com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService");

            Object console = Reflection.getField(classCraftServer, "console", classMinecraftServer).get(TimberNoCheat.instance.getServer());
            for (Field field : classMinecraftServer.getDeclaredFields()) {
                if (field.getType().getName().equals(KeyPair.class.getName())) {
                    field.setAccessible(true);
                    serverKey = (KeyPair) field.get(console);
                } else if (field.getType().getName().equals(classMinecraftSessionService.getName())) {
                    field.setAccessible(true);
                    service = field.get(console);
                }
            }

            Preconditions.checkNotNull(this.serverKey);
            Preconditions.checkNotNull(this.service);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        register(new PacketAdapter(
                TimberNoCheat.instance,
                ListenerPriority.HIGH,
                PacketType.Login.Client.START,
                PacketType.Login.Client.ENCRYPTION_BEGIN
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                final InetSocketAddress address = event.getPlayer().getAddress();

                if (event.getPacketType() == PacketType.Login.Client.START) {
                    final WrapperLoginClientStart packet = new WrapperLoginClientStart(event.getPacket());
                    names.put(address, packet.getProfile().getName());

                } else if (event.getPacketType() == PacketType.Login.Client.ENCRYPTION_BEGIN) {
                    final WrapperLoginClientEncryptionBegin packet = new WrapperLoginClientEncryptionBegin(event.getPacket());

                    final String name =  names.getIfPresent(address);
                    if (name == null) {
                        TimberNoCheat.instance.getLogger().log(Level.WARNING, "Konnte keinen namen für " + address.getAddress().getHostName() + " bekommen!");
                        return;
                    }

                    final Boolean value =  caches.getIfPresent(name);
                    if (value != null) {
                        return;
                    }

                    final SecretKey secretKey = CryptoUtil.decryptSharedKey(serverKey.getPrivate(), packet.getSharedSecret());
                    final String serverId = (new BigInteger(CryptoUtil.getServerIdHash("", serverKey.getPublic(), secretKey))).toString(16);

                    this.plugin.getServer().getScheduler().runTaskLaterAsynchronously(this.plugin, new Runnable() {
                        @Override
                        public void run() {
                            task(name, serverId, address.getAddress());
                        }
                    }, 60);
                }
            }
        });
    }

    private void task(final String name, final String serverId, final InetAddress address) {
        try {
            boolean result = this.isSafe(name, serverId, address);
            if (result) {
                caches.put(name, true);

            } else {
                caches.put(name, false);
                updateVio(MCLeaks.this, Bukkit.getPlayer(name), 1);
            }
        } catch (Exception ignored) {

        }
    }

    private boolean isSafe(final String name, final String serverId, final InetAddress address) throws UnsupportedEncodingException, MalformedURLException {
        URL url = new URL("https://sessionserver.mojang.com/session/minecraft/hasJoined?"
                + "username=" + URLEncoder.encode(name , "UTF-8")
                + "&serverId=" + URLEncoder.encode(serverId , "UTF-8")
                + "&ip=" + URLEncoder.encode(address.getHostAddress() , "UTF-8"));

        Object service = Reflection.getMethod(this.classYggdrasilMinecraftSessionService, "getAuthenticationService").invoke(this.service);
        Object reponse = Reflection.getMethod(this.classYggdrasilAuthenticationService, "makeRequest", java.net.URL.class, java.lang.Object.class, java.lang.Class.class)
                .invoke(service, url, null, this.classHasJoinedMinecraftServerResponse);

        return (reponse != null) && (Reflection.getMethod(this.classHasJoinedMinecraftServerResponse, "getId").invoke(reponse) != null);
    }
}
