package dev.xyze.spigot.teamchat.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.xyze.spigot.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;

public class GlobalChannel implements CommandExecutor {
  private final TeamChat plugin;

  public GlobalChannel(TeamChat plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      UUID playerUUID = player.getUniqueId();

      if (!plugin.isPlayerOnTeam(playerUUID)) {
        sender.sendMessage(ChatColor.GRAY + "You are already on the global channel!");
        return true;
      }

      plugin.removePlayerFromTeam(playerUUID);
      sender.sendMessage(ChatColor.GREEN + "You have switched to the global channel!");

      return true;

    } else {
      sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
      return false;
    }
  }
}