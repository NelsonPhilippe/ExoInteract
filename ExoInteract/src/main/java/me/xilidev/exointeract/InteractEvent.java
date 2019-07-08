package me.xilidev.exointeract;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.xilidev.exointeract.utils.SqlConnect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class InteractEvent implements Listener{

    private SqlConnect sql;

    public InteractEvent(SqlConnect sql){

        this.sql = sql;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();
        Location loc = player.getLocation();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager manager = container.get(BukkitAdapter.adapt(loc.getWorld()));

        String regionP = manager.toString();

        if(sql.getInteract(player) == false){
            if(sql.getZone(player) == regionP) {

                e.setCancelled(true);
                player.sendMessage(ChatColor.BLUE + "Vous n'avez pas la permission d'interagir avec ce block dans la zone " + sql.getZone(player) + " !");

            }else{
                e.setCancelled(false);
            }
        }else{

            e.setCancelled(false);
        }
    }
}
