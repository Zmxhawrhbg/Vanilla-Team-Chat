package dev.xyze.spigot.teamchat;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev.xyze.spigot.teamchat.commands.TeamChannel;
import dev.xyze.spigot.teamchat.commands.GlobalChannel;
import dev.xyze.spigot.teamchat.commands.GlobalMessage;

public class TeamChat extends JavaPlugin implements Listener
{

    private Set<UUID> teamChannelPlayers;
    private Set<UUID> globalChannelBypass;

    @Override
    public void onEnable() {
        teamChannelPlayers = new HashSet<UUID>();
        globalChannelBypass = new HashSet<UUID>();

        getServer().getPluginManager().registerEvents(this, this);
        
        this.getCommand("tc").setExecutor(new TeamChannel(this));
        this.getCommand("gc").setExecutor(new GlobalChannel(this));
        this.getCommand("gm").setExecutor(new GlobalMessage(this));
    }

    @Override
    public void onDisable() {
    }

    public void addPlayerToTeam(UUID playerUUID) {
        if (!teamChannelPlayers.contains(playerUUID)) {
            teamChannelPlayers.add(playerUUID);
        }
    }

    public boolean isPlayerOnTeam(UUID playerUUID) {
        return teamChannelPlayers.contains(playerUUID);
    }

    public void removePlayerFromTeam(UUID playerUUID) {
        if (teamChannelPlayers.contains(playerUUID)) {
            teamChannelPlayers.remove(playerUUID);
        }
    }

    public void addPlayerToBypass(UUID playerUUID) {
        if (!globalChannelBypass.contains(playerUUID)) {
            globalChannelBypass.add(playerUUID);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(final AsyncPlayerChatEvent chatEvent) {
        try {
            if (chatEvent.getPlayer() != null) {
                // if the player is on a team and the player is not bypassing anything
                if (isPlayerOnTeam(chatEvent.getPlayer().getUniqueId())) {

                    if (globalChannelBypass.contains(chatEvent.getPlayer().getUniqueId())) {
                        // bypass the team chat, make it so that the player just talks in regular chat next time the message
                        globalChannelBypass.remove(chatEvent.getPlayer().getUniqueId());
                        return;
                    }

                    chatEvent.setCancelled(true);
                    String translatedMessage = ChatColor.translateAlternateColorCodes('&', chatEvent.getMessage());

                    // spigot screams if player runs commands async
                    // so this puts it onto the main thread
                    if (chatEvent.isAsynchronous()) {
                        BukkitScheduler scheduler = getServer().getScheduler();
                        scheduler.runTask(this, new Runnable() {
                            @Override
                            public void run() { chatEvent.getPlayer().performCommand("tm " + translatedMessage); }
                        });
                    } else {
                        // if its not async just perform the sync command
                        chatEvent.getPlayer().performCommand("tm " + translatedMessage);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
