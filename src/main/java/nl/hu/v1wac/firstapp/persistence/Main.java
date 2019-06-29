package nl.hu.v1wac.firstapp.persistence;

import java.sql.SQLException;

public class Main {
	public static void main(String[] arg) throws SQLException{		
		CountryPostgresDaoImpl dao = new CountryPostgresDaoImpl();
		System.out.println(dao.findAll());
	}
}
