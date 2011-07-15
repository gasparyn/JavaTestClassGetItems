

/**
 * 
 * @author gasparobimba
 *User object user has a name email and server id
 */
public class User {
	
	public String name; 
	public String email;
	public int id; 
	
	public User(String myName,String email, int id){
		this.name = myName;
		this.email=email;
		this.id=id;
		
	}
	
	public String getName(){
		return name;
	}
	public String getEmail(){
		return email;
	}
	public int getID(){
		return id;
	}
	
}
