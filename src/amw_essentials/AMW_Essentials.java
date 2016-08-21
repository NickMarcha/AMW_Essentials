package amw_Essentials;

import net.risingworld.api.Plugin;
import net.risingworld.api.Server;
import net.risingworld.api.World;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;
import net.risingworld.api.events.player.PlayerChatEvent;
import net.risingworld.api.events.player.PlayerCommandEvent;
import net.risingworld.api.events.player.PlayerSpawnEvent;
import net.risingworld.api.objects.Player;

public class AMW_Essentials extends Plugin implements Listener{
    //Reference to the server and world
    public Server server = Plugin.getServer();
    public World world = Plugin.getWorld();
    @Override
    public void onEnable(){
        //Register event listener
        Plugin.registerEventListener(this);
    }
    @Override
    public void onDisable(){
        //...
    }
    @EventMethod
    public void onCommand(PlayerCommandEvent event){
        Player player = event.getPlayer();
        String command = event.getCommand();
        //split the command
        String[] cmd = command.split(" ");
        //check the command
        if(cmd[0].equals("/help")){
            System.out.println("Commands for AMW V. 1.0.0");
            System.out.println("/teleport x y z");
            System.out.println("/adminmessage message");
        }
        
        else if(cmd[1].equals("/teleport")){
            //check if player provided coordinates
            if(cmd.length == 4){
                float x = Float.parseFloat(cmd[1]);
                float y = Float.parseFloat(cmd[2]);
                float z = Float.parseFloat(cmd[3]);
                
                //teleport player
                player.setPosition(x, y, z);
                
                //send message to player
                player.sendTextMessage("[FFFF00]Teleported to " + x + ", " + y + ", " + z);
            }
            else{
                //inform player that he has to enter coordinates
                player.sendTextMessage("[FF0000]You have to provide coordinates!");
                player.sendTextMessage("[FF0000]USAGE: /tp x y z");
            }
        }
        
//Exampe: send admin message command
        
        else if(cmd[2].equals("/adminmessage")){
            if(cmd.length > 1){
                //get the rest of the message
                String message = command.substring(14);  //remove the first 14 characters
                
                //send to all admins, check all players if they are admins
                for(Player p : server.getAllPlayers()){
                    if(p.isAdmin()){
                        p.sendTextMessage("[FF0000]" + player.getName() + ": " + message);
                    }
                }
            }
            else{
                //inform player that he has to provide a message
                player.sendTextMessage("[FF0000]You have to provide a message");
                player.sendTextMessage("USAGE /adminmessage message");
            }
        }
    }
    @EventMethod
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getChatMessage();
        
        //cancel the event, so the chat message does not show up
        event.setCancelled(true);
        //send your own custom chat message
        server.broadcastTextMessage("[4110DD]" + player.getName() + " said: [FFFFFF]" + message);
    }

    public void onPlayerSpawn (PlayerSpawnEvent event) {
        System.out.println("Welcome to Alpha Moist Waffle, join our community and talk to us on discord. https://discord.gg/py65Kae");
    }
}
