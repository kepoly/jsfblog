/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author kepoly
 */
@ManagedBean
@ApplicationScoped
public class DBconn {
    
    public final static String SALT = "THISISArandomSTRINGofCHARACTERSusedTOsaltTHEpasswords";
    
    public static String hashedpass(String input)
    {
       
        try {
            String saltedPass = input + SALT;
            MessageDigest message = MessageDigest.getInstance("SHA1");
            byte[] hash = message.digest(saltedPass.getBytes("UTF-8"));
            StringBuilder str = new StringBuilder();
            for(byte b : hash) {
                String hex = Integer.toHexString(b & 0xff).toUpperCase(); //what the heck is happening here
                if(hex.length() == 1) {
                    str.append("0");
                }
                str.append(hex);
            }
            return str.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(DBconn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
    
    public static Connection getConnection() throws SQLException
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException ex) {
            Logger.getLogger(DBconn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        String host = "IPRO";
        String port = "3306";
        String db = "simpleblog";
        String user = "simpleblog";
        String pass = "February2016";
        String jdbc = String.format("jdbc:mysql://%s:%s/%s", host, port, db);
        return DriverManager.getConnection(jdbc, user, pass);
    }
    
}
