package com.github.messycraft.anotheronlinemode.command;

import com.github.messycraft.anotheronlinemode.AnotherOnlineMode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class UseCmd extends Command {
    public UseCmd(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (AnotherOnlineMode.times.get(p.getName()) != null) {
                AnotherOnlineMode.times.put(p.getName(), AnotherOnlineMode.times.get(p.getName()) + 1);
            } else {
                AnotherOnlineMode.times.put(p.getName(), 1);
            }
            if (!p.hasPermission("anotheronlinemode.bypass") && AnotherOnlineMode.times.get(p.getName())
                    > AnotherOnlineMode.getConfig().getInt("times")) {
                p.sendMessage(new TextComponent(
                        AnotherOnlineMode.changeColor(AnotherOnlineMode.getConfig().getString("limit"))
                ));
                return;
            }
            AnotherOnlineMode.wait.add(p.getName());
            p.sendMessage(new TextComponent(
                    AnotherOnlineMode.changeColor(AnotherOnlineMode.getConfig().getString("start"))
            ));
        } else {
           sender.sendMessage(new TextComponent(
                   AnotherOnlineMode.changeColor("&c&l这个命令不能在控制台使用!")
           ));
        }
    }
}
