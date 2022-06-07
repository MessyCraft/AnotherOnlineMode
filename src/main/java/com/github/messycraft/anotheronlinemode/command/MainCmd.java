package com.github.messycraft.anotheronlinemode.command;

import com.github.messycraft.anotheronlinemode.AnotherOnlineMode;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class MainCmd extends Command {
    public MainCmd() {
        super("onlinemodereload","anotheronlinemode.admin","aomreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        AnotherOnlineMode.reloadConfig();
        sender.sendMessage(
                new TextComponent(AnotherOnlineMode.changeColor(
                "&e&l[AnotherOnlineMode] &6Reload config successfully!"
        )));
    }
}
