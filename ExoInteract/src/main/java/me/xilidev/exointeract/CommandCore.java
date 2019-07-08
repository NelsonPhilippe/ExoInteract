package me.xilidev.exointeract;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.xilidev.exointeract.utils.SqlConnect;
import me.xilidev.exointeract.utils.SqlPerm;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



import java.util.HashSet;


public class CommandCore implements CommandExecutor {

    private SqlConnect sql;
    private SqlPerm sqlP;

    public CommandCore(SqlConnect sql, SqlPerm sqlP) {

        this.sql = sql;
        this.sqlP = sqlP;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {


            Player target = Bukkit.getPlayer(args[3]);
            Player player = (Player) sender;
            Location loca = player.getLocation();
            WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
            RegionContainer container = platform.getRegionContainer();
            RegionManager regionManager = container.get(BukkitAdapter.adapt(loca.getWorld()));
            ProtectedRegion region = regionManager.getRegion(args[4]);
            HashSet<Material> transparent = new HashSet<Material>();
            transparent.add(Material.AIR);
            Block block = player.getTargetBlock(transparent, 5);

            if (cmd.getName().equalsIgnoreCase("int")) {
                if (args.length == 0) {

                    player.sendMessage("§4Command: §2 <set> or <perm>");

                } else if (args.length == 1 ) {
                	
                    player.sendMessage("§4Command: §2 <set> <everyone/JOUEUR> <true/false> <nom de la région>");
                	

                } else if (args.length == 2) {
                	
                	if(cmd.getName().equalsIgnoreCase("perm")){
                		
                		for(int i = 1; ; i++) {
                		sqlP.setPerm(args[2]);
                		
                		}
                	}else {

                    player.sendMessage("§4Command: §2 <set> <everyone/JOUEUR> <true/false> <nom de la région>");

                	}
                } else if (args.length == 3) {

                    player.sendMessage("§4Command: §2 <set> <everyone/JOUEUR> <true/false> <nom de la région>");

                } else if (args.length == 4) {

                    if (cmd.getName().equalsIgnoreCase("set")) {

                        if (cmd.getName().equalsIgnoreCase("everyone")) {

                            //Command for Everyone

                            if (cmd.getName().equalsIgnoreCase("true")) {

                                if (regionManager.hasRegion(args[3]) != false) {


                                    for (Player p : Bukkit.getOnlinePlayers()) {


                                            if (sql.getInteract(player)  == true) {

                                                

                                                    player.sendMessage("§4Ce block est maintenant accessible par tous !");
                                              


                                               
                                                sql.setBlockId(p, block.getType());

                                                if(region != null){

                                                    sql.setZone(p, args[3]);
                                                }

                                            } else if (sql.getInteract(p) == false) {

                                                sql.setInteract(p, true);
                                                sql.setBlockId(p, block.getType());

                                                if(region != null){

                                                    sql.setZone(p, args[3]);
                                                }


                                                player.sendMessage("§4Ce block est maintenant accessible par tous !");

                                            }
                                    }
                                } else {
                                    player.sendMessage("§4La région§l§b " + args[3] + " §r§4est inexistante");
                                    return true;
                                }


                            } else if (cmd.getName().equalsIgnoreCase("false")) {

                                if (regionManager.hasRegion(args[3]) != false) {


                                    for (Player p : Bukkit.getOnlinePlayers()) {



                                            if (sql.getInteract(player)  == false) {

                                                player.sendMessage("§4Ce block n'est accessible par tous !");

                                                sql.setBlockId(target, block.getType());

                                                if(region != null){

                                                    sql.setZone(p, args[3]);
                                                }


                                            } else if (sql.getInteract(player)  == true) {

                                                sql.setInteract(target, false);
                                                sql.setBlockId(target, block.getType());

                                                player.sendMessage("§4Ce block n'est accessible par tous !");

                                                if(region != null){

                                                    sql.setZone(p, args[3]);
                                                }

                                            }
                                        }

                                } else {
                                    player.sendMessage("§4La région§l§b " + args[3] + " §r§4est inexistante");
                                    return true;
                                }


                            }

                        } else if (cmd.getName().equals(target)) {

                            //Command for player
                            if (cmd.getName().equalsIgnoreCase("true")) {

                                if (regionManager.hasRegion(args[3]) != false) {


                                    for (Player p : Bukkit.getOnlinePlayers()) {

                                        if(target != p){

                                            player.sendMessage("Joueur inconnue ou pseudo incorrect");

                                        }else if (sql.getInteract(player)  == true) {

                                            player.sendMessage("§1Ce block n'est accessible par : " + "§l§6" + target + "§1!");

                                            sql.setBlockId(target, block.getType());

                                            if(region != null){

                                                sql.setZone(p, args[3]);
                                            }


                                        } else if (sql.getInteract(player)  == false) {

                                            player.sendMessage("§1Ce block est accessible par : " + "§l§6" + target + " §1!");


                                            sql.setInteract(target, true);
                                            sql.setBlockId(target, block.getType());

                                            if(region != null){

                                                sql.setZone(p, args[3]);
                                            }
                                        }

                                    }
                                    } else{


                                        player.sendMessage("§4La région§l§b " + args[3] + " §r§4est inexistante");
                                        return true;
                                    }


                                } else if (cmd.getName().equalsIgnoreCase("false")) {

                                    if (regionManager.hasRegion(args[3]) != false) {


                                        for (Player p : Bukkit.getOnlinePlayers()) {


                                                if (sql.getInteract(target) == false) {

                                                    player.sendMessage("§1Ce block n'est plus accessible par : " + "§l§6" + target + "§1!");

                                                    sql.setBlockId(target, block.getType());

                                                    if(region != null){

                                                        sql.setZone(p, args[3]);
                                                    }


                                                } else if (sql.getInteract(target) == true) {

                                                    sql.setInteract(target, false);
                                                    sql.setBlockId(target, block.getType());

                                                    if(region != null){

                                                        sql.setZone(p, args[3]);
                                                    }

                                                }

                                        }
                                    } else {
                                        player.sendMessage("§4La région§l§b " + args[3] + " §r§4est inexistante");
                                        return true;
                                    }


                                }
                            }

                        }
                    }
                }

            }
            return true;
        }
    }


        





