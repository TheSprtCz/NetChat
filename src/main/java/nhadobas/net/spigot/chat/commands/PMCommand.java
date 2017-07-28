package nhadobas.net.spigot.chat.commands;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nhadobas.net.spigot.chat.NetMain;
import nhadobas.net.spigot.chat.templates.Errors;
import nhadobas.net.spigot.chat.templates.Templates;
import nhadobas.net.spigot.chat.util.SubstitutorUtil;

public class PMCommand extends Command {
    private final static String[] aliases = new String[] { "msgn", "msg" };
    private final NetMain main;

    public PMCommand(NetMain main) {
        super("nmsg", "netchat.pm", aliases);
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            NetMain.sendErrorMessage(sender, Errors.NO_RECEIVER);
            return;
        } else if (args.length == 1) {
            NetMain.sendErrorMessage(sender, Errors.NO_CONTENT);
            return;
        }

        ProxiedPlayer receiver = main.getProxy().getPlayer(args[0]);
        if (receiver == null) {
            NetMain.sendErrorMessage(sender, Errors.NO_SUCH_PLAYER);
            return;
        }

        sendPM((ProxiedPlayer) sender, receiver, NetMain.extractMessage(args, 1), main);
    }

    public static void sendPM(ProxiedPlayer sender, ProxiedPlayer receiver, String message, NetMain main) {
        if (receiver.hasPermission("netchat.pm")) {
            main.getLastReceivers().put(receiver.getName(), sender.getName());

            final Map<String, Object> values = new HashMap<String, Object>();
            values.put("receiver", "me");
            values.put("sender", sender.getName());

            NetMain.sendMessage(receiver, SubstitutorUtil.constructMessage(Templates.PM, values, message));

            values.put("receiver", receiver.getName());
            values.put("sender", "me");
     
            NetMain.sendMessage(sender, SubstitutorUtil.constructMessage(Templates.PM, values, message));

            values.put("receiver", receiver.getName());
            values.put("sender", sender.getName());

            main.getLogger().info(SubstitutorUtil.constructMessage(Templates.PM, values, message).getText());
        }
    }

}
