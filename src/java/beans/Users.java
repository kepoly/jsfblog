/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
public class Users {
    
    private List<User> users;
    private static Users instance;
    
    public Users()
    {
        getUsersFromDB();
        instance = this;
    }
    
    private void getUsersFromDB()
    {
        try (Connection conn = DBconn.getConnection()) {
            users = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM users");
            while(results.next()) {
                User u = new User(results.getInt("id"), results.getString("username"), results.getString("passhash"));
                users.add(u);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            users = new ArrayList<>();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public static Users getInstance() {
        return instance;
    }
    
    public String getUsernameById(int id)
    {
        for (User u : users) {
            if(u.getId() == id) {
                return u.getUser();
            }
        }
        return null;
    }
    
    public int getUserIdByUsername(String user) 
    {
        for (User u : users) {
            if(u.getUser().equals(user)) {
                return u.getId();
            }
        }
        return -1;
    }
    
    
    
}
