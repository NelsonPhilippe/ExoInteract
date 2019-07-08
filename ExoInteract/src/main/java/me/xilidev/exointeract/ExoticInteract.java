package me.xilidev.exointeract;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.xilidev.exointeract.utils.SqlConnect;
import me.xilidev.exointeract.utils.SqlPerm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExoticInteract extends JavaPlugin implements Listener {

    public SqlConnect sql;
    public SqlPerm sqlP;
    WorldGuardPlugin worldguard;
    WorldEditPlugin we;

    public static ExoticInteract instance;

    public static ExoticInteract getInstance(){

        return instance;
    }

    @Override
    public void onEnable() {
        System.out.println("[ExoticInteract] ON");
        try {
            worldguard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
            we = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
        }catch (Exception e){
            e.printStackTrace();
        }
        instance = this;
        sql = new SqlConnect("jdbc:mysql://", "localhost", "joueur", "root", "");
        sqlP = new SqlPerm("jdbc:mysql://", "localhost", "permission", "root", "");
        sql.connection();
        getServer().getPluginManager().registerEvents(new InteractEvent(sql), this);
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("int").setExecutor(new CommandCore(sql, sqlP));


    }


    @Override
    public void onDisable() {
        sql.disconnect();
        System.out.println("[ExoticInteract] OFF");

    }

    @EventHandler
    public void onJoinServer(PlayerJoinEvent e){
        Player p = e.getPlayer();

        sql.createAccount(p);
    }
}
