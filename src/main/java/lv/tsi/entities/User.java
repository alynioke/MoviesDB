package lv.tsi.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="user")
public class User 
{
    @Id
    @GeneratedValue
    private int id;
    
    private String login;
    private String firstname;
    private String lastname;
    private String password;

    public int getId() 
    {
        return id;
    }
    
    public void setId(int id) 
    {
        this.id = id;
    }
    
    public String getLogin() 
    {
        return login;
    }
    
    public void setLogin(String login) 
    {
        this.login = login;
    }
    
    public String getFirstname() 
    {
        return firstname;
    }
    
    public void setFirstname(String firstname) 
    {
        this.firstname = firstname;
    }
    
    public String getLastname() 
    {
        return lastname;
    }
    
    public void setLastname(String lastname) 
    {
        this.lastname = lastname;
    }
    
    public String getPassword() 
    {
        return password;
    }
    
    public void setPassword(String password) 
    {
        this.password = password;
    }
}