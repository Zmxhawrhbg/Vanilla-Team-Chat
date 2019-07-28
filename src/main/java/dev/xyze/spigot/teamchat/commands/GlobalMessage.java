package dev.xyze.spigot.teamchat.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.xyze.spigot.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;

public class GlobalMessage implements CommandExecutor {
  
  private final TeamChat plugin;

  public GlobalMessage(TeamChat plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      // you can't chat normally cause it'd fire the chat event so instead we toggle then chat how Easy
      UUID playerUUID = player.getUniqueId();

      if (plugin.isPlayerOnTeam(playerUUID)) {
        plugin.addPlayerToBypass(playerUUID);
      }

      player.chat(String.join(" ", args));

      return true;
    } else {
      sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
      return false;
    }
  }
}