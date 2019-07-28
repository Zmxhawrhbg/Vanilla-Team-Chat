# Vanilla Team Chat

A Bukkit Plugin that creates "channels" for Minecraft 1.14's team message command.
This was created in an effort to make the team message experience a bit better by adding the ability to make the chat perisistent.

Instead of running this:
> /tm hi  
> /tm lets go to this base  
> /tm please
> hey we're going to the village

You can do this:
> /tc
> hi  
> lets go to this base  
> please  
> /gm hey we're going to the village

This saves a bit of typing if you're doing an extended discussion in the team chat.

## It has the following commands:

* `/tc`
  * Permission: `teamchat.team`
  * Description: Switches to team channel for one to talk in there.
* `/gc`
  * Permission: `teamchat.global`
  * Description: Switches back to global channel from team channel.
* `/gm`
  * Permission: `teamchat.global.message`
  * Description: Allows one to quickly message in the global channel while in the team channel.

The plugin has zero requirements, except the server must be on 1.14+ to provide the `tm` command that this plugin relies on.
