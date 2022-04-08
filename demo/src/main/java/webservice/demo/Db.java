package webservice.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
    private static Db instance=null;
    private Connection con;
    private Db() throws SQLException{
        
        con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/webservicespring?user=root&password=");
    }
    public static Db Instance() throws SQLException{
        if(instance==null)
            instance = new Db();
        return instance;
    }
    public boolean Register(String username, String password) throws SQLException{
        PreparedStatement query = con.prepareStatement("insert into utente (username,password) values(?,?)");
        query.setString(1, username);
        query.setString(2,password);
        return query.execute();
    }
}
