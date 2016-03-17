/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kepoly
 */
@ManagedBean
@SessionScoped
public class Login {
 
    private String user;
    private String pass;
    private boolean loggedIn;
    private User currentUser;
    
    public Login()
    {
        user = null;
        pass = null;
        loggedIn = false;
        currentUser = null;
    }

     public Login(String user, String pass, boolean loggedIn, User currentUser) {
        this.user = user;
        this.pass = pass;
        this.loggedIn = loggedIn;
        this.currentUser = currentUser;
    }
   
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    public String login()
    {
        String passhashed = DBconn.hashedpass(pass);
        
        for(User u : Users.getInstance().getUsers()) {
            if(user.equals(u.getUser()) && passhashed.equals(u.getPass())) {
                loggedIn = true;
                currentUser = u;
                return "index";
            }
        }
        currentUser = null;
        loggedIn = false;
        return "index";
   
    }
    
}
