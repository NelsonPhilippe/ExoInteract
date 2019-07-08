package me.xilidev.exointeract.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class SqlPerm {
	
	private Connection connect;
	private String urlbase,host,database,user,mdp;
	
	
	public SqlPerm(String urlbase, String host, String database, String user, String mdp) {

        this.urlbase = urlbase;
        this.host = host;
        this.database = database;
        this.user = user;
        this.mdp = mdp;

    }
	
	public void connection(){
        if(!isConnected()) {
            try {
                connect = DriverManager.getConnection(urlbase + host + "/" + database, user, mdp);
                System.out.println("SQL Connection OK");
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    public void disconnect(){
        if(isConnected()) {
            try {
                connect.close();
                System.out.println("SQL Connection OFF");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){

        return connect != null;
    }
    
    public void createData(int id){

        //INSERT UUID
        if(!hasAccount(id)){
            try {
                PreparedStatement q = connect.prepareStatement("INSERT INTO(permission id, perm) VALUES (?,?)");
                q.setInt(1, id);
                q.setString(2, "inconnue");
                q.execute();
                q.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
    
    public boolean hasAccount(int id){

        try {
            PreparedStatement q = connect.prepareStatement("SELECT id FROM permission WHERE id = ?");
            q.setInt(1, id);
            ResultSet resulat = q.executeQuery();
            boolean hasAccount = resulat.next();
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public void setPerm(int id, String permission){

        String perm = null;

        try {
            PreparedStatement rs = connect.prepareStatement("UPDATE perm SET perm = ? WHERE id = ?");
            rs.setInt(1, id);
            rs.setString(2, perm);
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getPerm(int id){

        try {
            PreparedStatement q = connect.prepareStatement("SELECT perm FROM permission WHERE id = ?");
            q.setInt(1, id);
            
            String zone = null;
            ResultSet rs = q.executeQuery();
            while(rs.next()){
                zone = rs.getString("perm");
            }
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getId(int id) {
    	
    	try {
            PreparedStatement q = connect.prepareStatement("SELECT perm FROM permission WHERE id = ?");
            q.setInt(1, id);
            
            String idd = null;
            ResultSet rs = q.executeQuery();
            while(rs.next()){
                idd = rs.getString("id");
            }
            q.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    
    public void setId(int id){

        String perm = null;

        try {
            PreparedStatement rs = connect.prepareStatement("UPDATE perm SET perm = ? WHERE id = ?");
            rs.setInt(1, id);
            rs.executeUpdate();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    

}
