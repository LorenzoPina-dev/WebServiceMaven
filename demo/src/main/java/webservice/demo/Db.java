package webservice.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
    private static Db instance=null;
    private Connection con;
    private Db() throws SQLException{
        
        con=DriverManager.getConnection( "jdbc:mysql://localhost:3306/webservicespring","root","");
    }
    public static Db Instance() throws SQLException{
        if(instance==null)
            instance = new Db();
        return instance;
    }
    public boolean Register(String username, String password) throws SQLException{
        PreparedStatement query = con.prepareStatement("insert into utente (Username,Password) values(?,?)");
        query.setString(1, username);
        query.setString(2,password);
        return query.execute();
    }
    public int GetToken(String username, String password) throws SQLException{
        System.out.println(username+";"+password);
        PreparedStatement query = con.prepareStatement("select Id from utente where Username = ? and Password =?");
        query.setString(1, username);
        query.setString(2,password);
        query.execute();
        ResultSet rs= query.getResultSet();
        if(rs.next()){
                return rs.getInt("Id");
        }
        return -1;
    }
    public boolean SetString(int idUtente, String key, String string) {

        try (PreparedStatement query = con.prepareStatement("insert into string (chiave,testo,idUtente) values(?,?,?)")) {
            query.setString(1, key);
            query.setString(2,string);
            query.setInt(3,idUtente);
            int ris=query.executeUpdate();
            System.out.println(ris);
            return ris==1;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean UpdateString(int idUtente, String key, String string) throws SQLException{
        PreparedStatement query = con.prepareStatement("update string SET testo = ? WHERE chiave = ? and idUtente = ?");
        query.setString(1,string);
        query.setString(2, key);
        query.setInt(3,idUtente);
        int ris=query.executeUpdate();
        System.out.println(ris);
        return ris==1;
    }
    public ResultSet GetString(int idUtente, String key) throws SQLException{
        PreparedStatement query = con.prepareStatement("select testo from string where chiave=? and idUtente=? ");
        query.setString(1, key);
        query.setInt(2, idUtente);
        if(query.execute())
            {
                ResultSet rs= query.getResultSet();
                rs.next();
                return rs;
            }
        return null;
    }
    public boolean DeleteString(int idUtente, String key) throws SQLException{
        PreparedStatement query = con.prepareStatement("delete from string where chiave=? and idUtente=?");
        query.setString(1, key);
        query.setInt(2, idUtente);
        int ris=query.executeUpdate();
        System.out.println(ris);
        return ris==1;
    }
    public ResultSet GetKeys(int idUtente) throws SQLException{
        PreparedStatement query = con.prepareStatement("select chiave from string where idUtente=?");
        query.setInt(1, idUtente);
        if(query.execute())
            {
                ResultSet rs= query.getResultSet();
                rs.next();
                return rs;
            }
        return null;
    }
    public ResultSet GetUtente(String token) throws SQLException
    {
        PreparedStatement query = con.prepareStatement("select Id from utente where Token = ?");
        query.setString(1, token);
        if(query.execute())
            {
                ResultSet rs= query.getResultSet();
                rs.next();
                return rs;
            }
        return null;
    }
    public boolean SetToken(int idUtente,String token) throws SQLException{
        System.out.println(token+";"+idUtente);
        PreparedStatement query = con.prepareStatement("update utente set Token = ? where Id=?");
        query.setString(1, token);
        query.setInt(2, idUtente);
        int ris=query.executeUpdate();
        System.out.println(ris);
        return ris==1;
    }
}
