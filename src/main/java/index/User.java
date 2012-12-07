package index;

public class User {
	private String login;
	private String firstname;
	private String lastname;
	private int id;
	

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

}
