package nl.hu.v1wac.firstapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao {

	public String findRoleForUser(String name, String pass) {
		try (Connection con = super.getConnection()) {
			String query = "SELECT * FROM useraccounts WHERE username = '" + name + "' and password = '" +pass+ "'";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet dbResultSet = pstmt.executeQuery();
			while (dbResultSet.next()) {
				String naam = dbResultSet.getString("username");
				return naam;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}
}
