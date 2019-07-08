package me.xilidev.exointeract.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.*;

public class SqlConnect {

    private Connection connection;
    private String urlbase,host,database,user,mdp;


    public SqlConnect(String urlbase, String host, String database, String user, String mdp) {

        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.mdp = mdp;

    }

    public void connection(){
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection(urlbase + host + "/" + database, user, mdp);
                System.out.println("SQL Connection OK");
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public void disconnect(){
        if(isConnected()) {
            try {
                connection.close();
                System.out.println("SQL Connection OFF");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){

        return connection != null;
    }

    public void createAccount(Player player){

        //INSERT UUID
        if(!hasAccount(player)){
            try {
                PreparedStatement q = connection.prepareStatement("INSERT INTO(joueur uuid, block, zone, autorisation) VALUES (?,?,?,?)");
                q.setString(1, player.getUniqueId().toString());
                q.setString(2, "inconnue");
                q.setString(3, "inconnue");
                q.setBoolean(4, false);
                q.execute();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean hasAccount(Player player){

        try {
            PreparedStatement q = connection.prepareStatement("SELECT uuid FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());
            ResultSet resulat = q.executeQuery();
            boolean hasAccount = resulat.next();
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void setInteract(Player player, boolean interact){

        boolean check = false;
        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE autorisation SET autorisation = ? WHERE uuid = ?");
            rs.setBoolean(1, check);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean getInteract(Player player){

        try {
            PreparedStatement q = connection.prepareStatement("SELECT autorisation FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());

            boolean verif = false;
            ResultSet rs = q.executeQuery();
            while(rs.next()){
                verif = rs.getBoolean("autorisation");
            }
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public void setBlockId(Player player, Material material){

        Material block = null;

        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE block SET block = ? WHERE uuid = ?");
            rs.setString(1, block.toString());
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getBlockId(Player player){

        try {
            PreparedStatement q = connection.prepareStatement("SELECT block FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());

            String verif = null;
            ResultSet rs = q.executeQuery();
            while(rs.next()){
                verif = rs.getString("block");
            }
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    public void setZone(Player player, String region){

        String zone = null;

        try {
            PreparedStatement rs = connection.prepareStatement("UPDATE zone SET zone = ? WHERE uuid = ?");
            rs.setString(1, zone);
            rs.setString(2, player.getUniqueId().toString());
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getZone(Player player){

        try {
            PreparedStatement q = connection.prepareStatement("SELECT zone FROM joueurs WHERE uuid = ?");
            q.setString(1, player.getUniqueId().toString());

            String zone = null;
            ResultSet rs = q.executeQuery();
            while(rs.next()){
                zone = rs.getString("zone");
            }
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
