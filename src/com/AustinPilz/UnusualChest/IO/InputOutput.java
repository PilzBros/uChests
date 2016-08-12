package com.AustinPilz.UnusualChest.IO;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import com.AustinPilz.UnusualChest.UnusualChest;
import com.AustinPilz.UnusualChest.Components.uChest;





public class InputOutput 
{
    public static YamlConfiguration global;
    private static Connection connection;
    
	public InputOutput()
	{
		if (!UnusualChest.instance.getDataFolder().exists()) 
		{
			try 
			{
				(UnusualChest.instance.getDataFolder()).mkdir();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		global = new YamlConfiguration();
	}
   
    
    public void LoadSettings()
	{
    	try {
    		if (!new File(UnusualChest.instance.getDataFolder(),"global.yml").exists()) global.save(new File(UnusualChest.instance.getDataFolder(),"global.yml"));

    		global.load(new File(UnusualChest.instance.getDataFolder(),"global.yml"));
	    	for (Setting s : Setting.values())
	    	{
	    		if (global.get(s.getString()) == null) global.set(s.getString(), s.getDefault());
	    	}
	    	
	    	
	    	global.save(new File (UnusualChest.instance.getDataFolder(),"global.yml"));
	    	

		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
    
    
    
    
    
    public static synchronized Connection getConnection() {
    	if (connection == null) connection = createConnection();
            try
            {
                if(connection.isClosed()) connection = createConnection();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        
    	return connection;
    }
    private static Connection createConnection() {
        
    	try
    	{
                Class.forName("org.sqlite.JDBC");
                Connection ret = DriverManager.getConnection("jdbc:sqlite:" +  new File(UnusualChest.instance.getDataFolder().getPath(), "db.sqlite").getPath());
                ret.setAutoCommit(false);
                return ret;
        } 
        catch (ClassNotFoundException e) 
        {
        	UnusualChest.log.log(Level.SEVERE, UnusualChest.consolePrefix + "Fatal database connectione error (Class)");
        	e.printStackTrace();
        	return null;
        } 
        catch (SQLException e) 
        {
        	UnusualChest.log.log(Level.SEVERE, UnusualChest.consolePrefix + "Fatal database connection error (SQL)");
        	e.printStackTrace();
        	return null;
        }
    }
    
    public static synchronized void freeConnection() {
		Connection conn = getConnection();
        if(conn != null) {
            try {
            	conn.close();
            	conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void prepareDB()
    {
    	Connection conn = getConnection();
        Statement st = null;
        try 
        {
        		st = conn.createStatement();
            	st.executeUpdate("CREATE TABLE IF NOT EXISTS \"unusualChests_chests\" (\"UUID\" VARCHAR, \"Key\" VARCHAR, \"Locked\" INT, \"OwnerUUID\" VARCHAR)");

                conn.commit();
                st.close();

        } 
        catch (SQLException e) 
        {
            UnusualChest.log.log(Level.SEVERE, UnusualChest.consolePrefix + "Error preparing database! (SQL)");
            e.printStackTrace();
        }
        catch (Exception e) 
		{
        	UnusualChest.log.log(Level.SEVERE, UnusualChest.consolePrefix + "Error preparing database! (Unknown)");
		}
    }
    
    public void updateDB()
    {
    	//Update("SELECT Arena  FROM sandfall_signs", "ALTER TABLE sandfall_signs ADD Arena VARCHAR;", "ALTER TABLE sandfall_signs ADD Arena varchar(250);" );
    }
    
    public void Update(String check, String sql)
    {
    	Update(check, sql, sql);
    }
    
    public void Update(String check, String sqlite, String mysql)
    {
    	try
    	{
    		Statement statement = getConnection().createStatement();
			statement.executeQuery(check);
			statement.close();
    	}
    	catch(SQLException ex)
    	{
    		try {
    			String[] query;
    			
    			query = sqlite.split(";");
            	Connection conn = getConnection();
    			Statement st = conn.createStatement();
    			for (String q : query)	
    				st.executeUpdate(q);
    			conn.commit();
    			st.close();
    			UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + "Database upgraded");
    		} 
    		catch (SQLException e)
    		{
    			UnusualChest.log.log(Level.SEVERE, UnusualChest.consolePrefix + "Error while upgrading database to new version");
                e.printStackTrace();
    		}
    	}
        
	}
    
    public void loadChests()
    {
    	try
		{
    		Connection conn;
			PreparedStatement ps = null;
			ResultSet result = null;
			conn = getConnection();
			ps = conn.prepareStatement("SELECT `UUID`, `Key`, `Locked`, `OwnerUUID` FROM `unusualChests_chests`");
			result = ps.executeQuery();
			
			int chests = 0;
			while (result.next())
			{
				UnusualChest.chestController.addChest(new uChest(result.getString("UUID"), result.getString("Key"), result.getBoolean("Locked"), result.getString("OwnerUUID")));
				chests++;
			}
			UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + chests + " chest(s) loaded");
			conn.commit();
            ps.close();
	    }
		catch (SQLException e)
		{
			UnusualChest.log.log(Level.WARNING, UnusualChest.consolePrefix + "Encountered an issue loading chests");
			//System.out.print(e.getMessage());
		}
    }
    
    public void storeChest(uChest chest)
    {
    	try 
    	{
	    	String sql;
			Connection conn = InputOutput.getConnection();
			
			sql = "INSERT INTO unusualChests_chests (`UUID`, `Key`, `Locked`, `OwnerUUID`) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			
			
	        preparedStatement.setString(1, chest.getUUID());
	        preparedStatement.setString(2, chest.getKey());
	        preparedStatement.setBoolean(3, chest.isLocked());
	        preparedStatement.setString(4, chest.getOwnerUUID());
	        preparedStatement.executeUpdate();
	        conn.commit();
	        
	        UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + "Chest stored to DB");
    	}
    	catch (SQLException e) 
		{
    		UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + "Error while storing chest to DB");
			//e.printStackTrace();
	    }
    }
    
    public void deleteChest(uChest chest)
    {
    	try {
			Connection conn = InputOutput.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM unusualChests_chests WHERE UUID = ?");
			ps.setString(1, chest.getUUID());
			ps.executeUpdate();
			conn.commit();
			ps.close();
			
			UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + "Chest removed from DB");
		} catch (SQLException e) {
			UnusualChest.log.log(Level.INFO, UnusualChest.consolePrefix + "Error while removing chest from DB");
			
			e.printStackTrace();
		}
    }
    
    public void updateChest(uChest chest)
    {
    	try 
		{
    		String sql;
    		Connection conn = InputOutput.getConnection();
    		
    		sql = "UPDATE `unusualChests_chests` SET `Key` = ?, `Locked` = ?, `OwnerUUID` = ? WHERE `UUID` = ?";
    		
    		PreparedStatement preparedStatement = conn.prepareStatement(sql);
    	    preparedStatement.setString(1, chest.getKey());
    	    preparedStatement.setBoolean(2, chest.isLocked());
    	    preparedStatement.setString(3, chest.getOwnerUUID());
    	    preparedStatement.setString(4, chest.getUUID());
    	    preparedStatement.executeUpdate();
    	    conn.commit();
	    } 
    	catch (SQLException e) 
    	{
    		UnusualChest.log.log(Level.WARNING,UnusualChest.consolePrefix + "Error while updating chest - " + e.getMessage() );
			
		}
    }
    
    public void purgeChests()
    {
    	try {
			Connection conn = InputOutput.getConnection();
			PreparedStatement ps = conn.prepareStatement("DELETE FROM unusualChests_chests");
			ps.executeUpdate();
			conn.commit();
			
			ps.close();
		} catch (SQLException e) {
			UnusualChest.log.log(Level.WARNING,UnusualChest.consolePrefix + "Error while purging database data (chests) - " + e.getMessage() );
			
			//e.printStackTrace();
		}
    }
    
}