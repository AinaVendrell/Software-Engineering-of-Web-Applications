package managers;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import models.User;
import utils.DAO;

public class ManageUser {
	
	private DAO db = null ;
	
	public ManageUser() {
		try {
			db = new DAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		try {
			super.finalize();
			db.disconnectBD();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	
    // Add new user
    public void addUser(String name, String surname, String gender, String birthday, String username, String email, String pwd) {
        String query = "INSERT INTO users (name,surname,gender,birthday,username,email,pwd) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try {
            statement = db.prepareStatement(query);
            statement.setString(1,name); 
            statement.setString(2,surname);
            statement.setString(3,gender);
            statement.setString(4,birthday);
            statement.setString(5,username);
            statement.setString(6,email);
            statement.setString(7,pwd);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	// Check if all the fields are filled correctly 
	public boolean isComplete(User user) {
	    return(hasValue(user.getName()) &&
	    	   hasValue(user.getSurname()) &&
	    	   hasValue(user.getGender()) &&
	    	   hasValue(user.getBirthday()) &&
		       hasValue(user.getUsername()) &&
	    	   hasValue(user.getEmail()) &&
	    	   hasValue(user.getPwd1()) &&
	    	   hasValue(user.getPwd2()) );
	}
	
	private boolean hasValue(String val) {
		return((val != null) && (!val.equals("")));
	}
		
	// Check if the username and email doesn't exist yet
	public boolean isCorrect(User user) {
		if(usernameExists(user.getUsername())){
			user.setError(0);
			System.out.print("User already exists\n");
		}
		if(emailExists(user.getEmail())){
			user.setError(1);
			System.out.print("Email already exists\n");
		}
		if(user.getError()[0] || user.getError()[1]) {
			System.out.print("user or email already exists\n");
			return false;
		}
		return true;	
	}

	// Check if the username doesn't exist yet
	private boolean usernameExists(String username) {
		String query ="SELECT * FROM Users WHERE username = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();	
			if (!rs.isBeforeFirst() ) {    
			    return false;
			}
			statement.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	// Check if the email doesn't exist yet
	private boolean emailExists(String email) {
		String query ="SELECT * FROM Users WHERE email = ?";
		PreparedStatement statement = null;
		try {
			statement = db.prepareStatement(query);
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();	
			if (!rs.isBeforeFirst() ) {    
			    return false;
			}
			statement.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}