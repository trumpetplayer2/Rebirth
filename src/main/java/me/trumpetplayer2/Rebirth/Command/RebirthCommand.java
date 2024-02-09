package me.trumpetplayer2.Rebirth.Command;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import me.trumpetplayer2.Rebirth.Main;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedEntityList;
import me.trumpetplayer2.Rebirth.PossesedEntity.PossessedPlayerEntity;

public class RebirthCommand implements CommandExecutor, TabCompleter{
    public void help(CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
        sender.sendMessage(ChatColor.BLUE + "           Rebirth Command Help         ");
        sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
        sender.sendMessage(ChatColor.BLUE + "'/Rebirth Help' -> Shows this area in chat");
        sender.sendMessage(ChatColor.BLUE + "'/Rebirth Save' -> Force saves possession table");
        sender.sendMessage(ChatColor.BLUE + "'/Rebirth Player (Player)' -> Make a player");
        sender.sendMessage(ChatColor.BLUE + "-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-");
    }
    
    public void invalidPermission(CommandSender sender){
        //Invalid perm message
        sender.sendMessage(ChatColor.DARK_RED + "Invalid Permission");
    }
    
    //Command interface
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("Rebirth")) {
            Player p;
            Boolean isPlayer = false;
            if(sender instanceof Player) {
            p = (Player) sender;
            isPlayer = true;
            }
            
            if(args.length == 0) {
                help(sender);
                return false;
            }
            String subCommand = args[0];
            switch(subCommand.toLowerCase()) {
            case "help": help(sender);
                return false;
            case "save" : Main.getInstance().SaveData();
                return false;
          case "player" : if(!isPlayer) {
              if(args.length <= 2) {
              sender.sendMessage("No player specified, please specify a player");
              }
              //Console set player
              if(Bukkit.getPlayerExact(args[1]) != null) {
              Player temp = Bukkit.getPlayerExact(args[1]);
              rebirthPlayer(temp, temp);
              }else {
              sender.sendMessage("Player " + args[1] + " is not online, make sure you enter their entire ign.");
              }
              return false;
          }
          p = (Player) sender;
          if(args.length <= 2) {rebirthPlayer(p, p); return false;}
          else {
              if(Bukkit.getPlayerExact(args[1]) != null) {
              p.sendMessage("Player " + args[1] + " is not online, make sure you enter their entire ign.");
              }else {
              Player temp = Bukkit.getPlayerExact(args[1]);
              rebirthPlayer(p, temp);
              }
          }
          return false;
            
            default: help(sender);
            }
        }
    return false;
}

    private void rebirthPlayer(Player p, @Nullable Player skin) {
        boolean randomSkin = false;
        if(skin == null) {
            randomSkin = true;
            skin = p;
        }
        PossessedPlayerEntity ent = (PossessedPlayerEntity)PossessedEntityList.getPossessedEntity(skin);
        if(randomSkin) {
            //If the entity is a player, run through player instead of entity
            ent.generateRandomSkin();
            ent.generateRandomName();
        }
        PlayerDisguise disguise = (PlayerDisguise) ent.getDisguise();
        p.sendMessage("You are " + ent.getName());
        disguise.setEntity(p);
        disguise.setViewSelfDisguise(true);
        p.setGameMode(GameMode.SURVIVAL);
        disguise.startDisguise();
        p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(ent.getEntityMaxHealth());
        if(p.getUniqueId() == ent.getSkin() && p.getName() == ent.getName()) {
            disguise.removeDisguise(p);
        }
        return;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> completion = new ArrayList<String>();
        if(!cmd.getName().equalsIgnoreCase("Rebirth"))return completion;
        switch(args.length) {
        case 0:
            completion.add("help");
            completion.add("save");
            completion.add("player");
            break;
        case 1:
            switch(args[0].toLowerCase()) {
            case "player":
                for(Player p : Bukkit.getOnlinePlayers()){
                    completion.add(p.getName());
                }
                break;
            }
        }
        
        
        return completion;
    }
}
