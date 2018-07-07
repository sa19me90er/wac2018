package nl.hu.v1wac.firstapp.persistence;

import java.util.Map;

public class UserPostgresDaoImpl extends BaseDao implements UserDao {
	
	public String findRoleForUser( String Name, String pass){
		
		String role = null;

		 Map<String, Object> result = queryFirst("SELECT role FROM useraccount WHERE username = ? AND password = ?",
				 Name,
				 pass);
		 
		 if(result != null)
			 role = (String)result.get("role");
		 
		return role;
		
	}

}
