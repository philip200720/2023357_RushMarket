package org.adrianposadas.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection connection;
    private static Conexion instance;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBRushMarket?useSSL=false&serverTimezone=America/Guatemala", "root", "admin");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBRushMarket?useSSL=false&serverTimezone=America/Guatemala", "2023357_IN5BV", "abc123!!");
        }catch(ClassNotFoundException e){
            e.printStackTrace(); 
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static Conexion getInstance(){
        if(instance == null){
            instance = new Conexion();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return connection;
    }
    
    public void setDBConnection(Connection connection){
        this.connection = connection;
    }
}
