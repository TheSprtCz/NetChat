package nhadobas.net.spigot.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import nhadobas.net.spigot.chat.commands.PMCommand;
import nhadobas.net.spigot.chat.commands.ReplyCommand;
import nhadobas.net.spigot.chat.commands.ToggleCommand;
import nhadobas.net.spigot.chat.listeners.NetChatListener;
import nhadobas.net.spigot.chat.util.SubstitutorUtil;

public class NetMain extends Plugin implements Listener {
    private static NetMain main;
    private List<String> users = new ArrayList<String>();
    private Map<String, String> lastReceivers = new HashMap<String, String>();
    public static ServerInfo hub;

    public void onEnable() {
        main = this;
        getProxy().getLogger().log(Level.INFO, "NetChat may or may not enable!");

        // Register all commands
        getProxy().getPluginManager().registerCommand(this, new ReplyCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ToggleCommand(this));
        getProxy().getPluginManager().registerCommand(this, new PMCommand(this));

        // Register all listeners
        getProxy().getPluginManager().registerListener(this, new NetChatListener(this));
        getProxy().getPluginManager().registerListener(this, this);

        getProxy().getLogger().log(Level.INFO, "NetChat is enabled!");
    }

    public void onDisable() {
        getProxy().getLogger().log(Level.INFO, "NetChat is disabling!");
    }

    public static NetMain getPlugin() {
        return main;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public Map<String, String> getLastReceivers() {
        return lastReceivers;
    }

    public void setLastReceivers(Map<String, String> lastReceivers) {
        this.lastReceivers = lastReceivers;
    }

    public static void sendMessage(ProxiedPlayer receiver, TextComponent text) {
        if (receiver.hasPermission("netchat.use")) {
            receiver.sendMessage(text);
        }    
    }

    public static void sendMessage(ProxiedPlayer receiver, ProxiedPlayer sender, String msg, String template) {
        final Map<String, Object> values = new HashMap<String, Object>();
        values.put("receiver", receiver.getName());
        values.put("sender", sender.getName());

        NetMain.sendMessage(receiver, SubstitutorUtil.constructMessage(template, values, msg));
    }

    public static void sendErrorMessage(CommandSender sender, String text) {
        sender.sendMessage(new TextComponent(ChatColor.DARK_RED + text));
    }

    public static String extractMessage(String[] args, int start) {
        return String.join(" ", Arrays.copyOfRange(args, start, args.length));
    }

    // Automatically enable NetChat after join
    @EventHandler
    public void onPlayerJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if (!users.contains(player.getName())) {
            users.add(player.getName());
            player.sendMessage(new TextComponent(ChatColor.DARK_GREEN + "NetChat focus toggled on."));
            return;
        }
    }

}
