package index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="user")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "login")
	private String login;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "password")
	private String password;

	

    public User()    
	{  
		
	}
    public User(int id, String login, String firstname, String lastname)    
    {   
    	this.id = id;
    	this.login = login;
    	this.firstname = firstname;
    	this.lastname = lastname;
    }
    public User(String login, String firstname, String lastname)    
    {   
    	this.login = login;
    	this.firstname = firstname;
    	this.lastname = lastname;
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
