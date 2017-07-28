package nhadobas.net.spigot.chat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Command;
import nhadobas.net.spigot.chat.NetMain;

public class ToggleCommand extends Command {
    public final NetMain main;

    public ToggleCommand(NetMain main) {
        super("netchat", "netchat.use", new String[0]);
        this.main = main;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!main.getUsers().contains(sender.getName())) {
            main.getUsers().add(sender.getName());
            sender.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "NetChat focus toggled on."));
            return;
        }

        main.getUsers().remove(sender.getName());
        sender.sendMessage(new TextComponent(ChatColor.DARK_RED + "NetChat focus toggled off."));
    }

}
