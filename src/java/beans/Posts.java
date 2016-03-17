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
public class Posts {

    private List<Post> posts;
    private Post currentPost;

    public Posts() 
    {
        currentPost = new Post(-1, -1, "", null, "");
        getPostsFromDB();
    }

    private void getPostsFromDB()
    {
        try (Connection conn = DBconn.getConnection()) {
            posts = new ArrayList<>();
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM posts");
            while(results.next()) {
                Post p = new Post(results.getInt("id"), results.getInt("user_id"), results.getString("title"), results.getTimestamp("created_time"), results.getString("contents"));
                posts.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
            posts = new ArrayList<>();
                    
        }
    }
    
    public List<Post> getPosts()
    {
        return posts;
    }
    
    public Post getCurrentPost()
    {
        return currentPost;
    }
    
    public Post getPostById(int id) 
    {
        for (Post p : posts)
        {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }
    
    public Post getPostByTitle(String title) 
    {
        for (Post p : posts) {
            if(p.getTitle().equals(title)) {
                return p;
            }
        }
        return null;
    }
    
    public String viewPost(Post post) 
    {
        currentPost = post;
        return "viewPost";
    }
    
    public String addPost()
    {
        currentPost = new Post(-1, -1, "", null, "");
        return "editPost";
    }
    public String editPost()
    {
        return "editPost";
    }
    
    public String cancelPost()
    {
        int id = currentPost.getId();
        getPostsFromDB();
        currentPost = getPostById(id);
        return "viewPost";
    }
    
    public String savePost(User user)
    {
        try (Connection conn = DBconn.getConnection()) {
            
        if(currentPost.getId() >= 0) {
            String sql = "UPDATE posts set title = ?, content = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, currentPost.getTitle());
            pstmt.setString(2, currentPost.getContent());
            pstmt.setInt(3, currentPost.getId());
            pstmt.executeUpdate();
        } else {
            String sql = "INSERT INTO posts (user_id, title, created_time, contents) VALUES (?, ?, NOW(), ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, currentPost.getTitle());
            pstmt.setString(3, currentPost.getContent());
            pstmt.executeUpdate();
        }
        } catch (SQLException ex) {
            Logger.getLogger(Posts.class.getName()).log(Level.SEVERE, null, ex);
        }
        getPostsFromDB();
            currentPost = getPostByTitle(currentPost.getTitle());
            return "viewPost";
    }
}
