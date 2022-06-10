package com.github.messycraft.anotheronlinemode;

import com.github.messycraft.anotheronlinemode.command.MainCmd;
import com.github.messycraft.anotheronlinemode.command.UseCmd;
import com.github.messycraft.anotheronlinemode.event.Event;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class AnotherOnlineMode extends Plugin {
    private static Configuration config;
    private static AnotherOnlineMode instance;
    public static Map<String, Integer> times = new HashMap<>();
    public static Set<String> wait = new HashSet<>();
    public static Set<String> temp = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Running AnotherOnlineMode v0.0.1 by ImCur_ .");
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        getProxy().getPluginManager().registerListener(this,new Event());
        getProxy().getPluginManager().registerCommand(this,new MainCmd());
        getProxy().getPluginManager().registerCommand(this,new UseCmd(getConfig().getString("command")));

        Metrics metrics = new Metrics(this, 15396);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Plugin has been unloaded.");
    }

    public static void saveDefaultConfig() {
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdir();
        }
        File file = new File(instance.getDataFolder(),"config.yml");
        if (!file.exists()) {
            try (InputStream in = instance.getResourceAsStream("config.yml")) {
                Files.copy(in,file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reloadConfig() {
        File file = new File(instance.getDataFolder(),"config.yml");
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String changeColor(String s) {
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static AnotherOnlineMode getInstance() {
        return instance;
    }

    public static Configuration getConfig() {
        return config;
    }
}
