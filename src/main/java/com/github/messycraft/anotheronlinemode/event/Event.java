package com.github.messycraft.anotheronlinemode.event;

import com.github.messycraft.anotheronlinemode.AnotherOnlineMode;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class Event implements Listener {
    @EventHandler
    public void connect(ServerConnectEvent e) {
        if (AnotherOnlineMode.getConfig().getBoolean("alert")) {
            String message = AnotherOnlineMode.getConfig().getString("message");
            message = message.replace("%command%","/" +AnotherOnlineMode.getConfig().getString("command"));
            e.getPlayer().sendMessage(new TextComponent(AnotherOnlineMode.changeColor(message)));
        }
    }

    @EventHandler
    public void pre(PreLoginEvent e) {
        if (AnotherOnlineMode.temp.contains(e.getConnection().getName())) {
            AnotherOnlineMode.temp.remove(e.getConnection().getName());
            List<String> fail = AnotherOnlineMode.getConfig().getStringList("fail");
            for (int i=0; i<fail.size(); i++) {
                String s = fail.get(i);
                s = s.replace("%player%",e.getConnection().getName());
                s = AnotherOnlineMode.changeColor(s);
                if (s.startsWith("/") && !s.equals("/")) {
                    AnotherOnlineMode.getInstance().getProxy().getPluginManager().dispatchCommand(
                            AnotherOnlineMode.getInstance().getProxy().getConsole(), s.substring(1)
                    );
                } else {
                    String finalS = s;
                    AnotherOnlineMode.getInstance().getProxy().getScheduler().runAsync(AnotherOnlineMode.getInstance(),
                        () -> {
                            while (AnotherOnlineMode.getInstance().getProxy().getPlayer(e.getConnection().getName()) == null) {
                                try {
                                    Thread.sleep(200L);
                                } catch (InterruptedException ignored) {}
                            }
                            AnotherOnlineMode.getInstance().getProxy().getPlayer(
                                    e.getConnection().getName()).sendMessage(new TextComponent(finalS));
                        }
                    );
                }
            }
        }
        if (AnotherOnlineMode.wait.contains(e.getConnection().getName())) {
            e.getConnection().setOnlineMode(true);
            AnotherOnlineMode.wait.remove(e.getConnection().getName());
            AnotherOnlineMode.temp.add(e.getConnection().getName());
        } else {
            e.getConnection().setOnlineMode(false);
        }
    }

    @EventHandler
    public void login(PostLoginEvent e) {
        if (AnotherOnlineMode.temp.contains(e.getPlayer().getName())) {
            AnotherOnlineMode.temp.remove(e.getPlayer().getName());
            List<String> success = AnotherOnlineMode.getConfig().getStringList("success");
            for (int i=0; i<success.size(); i++) {
                String s = success.get(i);
                s = s.replace("%player%",e.getPlayer().getName());
                s = AnotherOnlineMode.changeColor(s);
                if (s.startsWith("/") && !s.equals("/")) {
                    AnotherOnlineMode.getInstance().getProxy().getPluginManager().dispatchCommand(
                        AnotherOnlineMode.getInstance().getProxy().getConsole(), s.substring(1)
                    );
                } else {
                    e.getPlayer().sendMessage(new TextComponent(s));
                }
            }
        }
    }
}
