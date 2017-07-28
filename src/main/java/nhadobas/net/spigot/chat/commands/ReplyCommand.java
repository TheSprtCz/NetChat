package nhadobas.net.spigot.chat.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import nhadobas.net.spigot.chat.NetMain;
import nhadobas.net.spigot.chat.templates.Errors;

public class ReplyCommand extends Command {
    private final static String[] aliases = new String[] { "nr", "r", "reply" };
    private final NetMain main;

    public ReplyCommand(NetMain main) {
        super("nreply", "netchat.pm", aliases);
        this.main = main;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String receiverName = main.getLastReceivers().get(sender.getName());
        if (receiverName != null) {
            ProxiedPlayer receiver = main.getProxy().getPlayer(receiverName);
            if (receiver != null) {
                PMCommand.sendPM((ProxiedPlayer) sender, receiver, NetMain.extractMessage(args, 0), main);
            }
        } else {
            NetMain.sendErrorMessage(sender, Errors.NO_REPLY);
        }
    }

}
