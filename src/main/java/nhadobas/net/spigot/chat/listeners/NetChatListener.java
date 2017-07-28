package nhadobas.net.spigot.chat.listeners;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import nhadobas.net.spigot.chat.NetMain;
import nhadobas.net.spigot.chat.templates.Templates;
import nhadobas.net.spigot.chat.util.SubstitutorUtil;

public class NetChatListener implements Listener {
    private final NetMain main;
    
    public NetChatListener(NetMain main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(ChatEvent e) {
        if ((e.getSender() instanceof ProxiedPlayer)) {
            ProxiedPlayer sender = (ProxiedPlayer) e.getSender();
            if ((main.getUsers().contains(sender.getName())) && (!e.isCommand()) && (sender.hasPermission("netchat.use"))) {
                e.setCancelled(true);
                // Additional Values for replacement in template
                final Map<String, Object> values = new HashMap<String, Object>();
                values.put("server", sender.getServer().getInfo().getName());
                values.put("sender", sender.getName());

                // Create text component only once
                final TextComponent text = SubstitutorUtil.constructMessage(Templates.CHAT, values, e.getMessage());
                for (ProxiedPlayer receiver : this.main.getProxy().getPlayers()) {
                    NetMain.sendMessage(receiver, text);
                }
                return;
            }
            return;
        }
    }
}
