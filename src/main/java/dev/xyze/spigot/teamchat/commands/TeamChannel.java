package dev.xyze.spigot.teamchat.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import dev.xyze.spigot.teamchat.TeamChat;
import net.md_5.bungee.api.ChatColor;

public class TeamChannel implements CommandExecutor {
  private final TeamChat plugin;

  public TeamChannel(TeamChat plugin) {
    this.plugin = plugin; // Store the plugin in situations where you need it.
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      try {
        final Team team = player.getScoreboard().getEntryTeam(player.getName());
        if (team == null) {
          sender.sendMessage(ChatColor.RED + "You must be on a team to use this command!");
          return true;
        }
      } catch (Exception ex) {
        sender.sendMessage(ChatColor.RED + "You must be on a team to use this command!");
        return true;
      }
      
      UUID playerUUID = player.getUniqueId();

      if (plugin.isPlayerOnTeam(playerUUID)) {
        sender.sendMessage(ChatColor.GRAY + "You are already on the team channel!");
        return true;
      }

      plugin.addPlayerToTeam(playerUUID);
      sender.sendMessage(ChatColor.GREEN + "You have been switched to the team channel!");
      return true;
      
    } else {
      sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
      return false;
    }
  }
}