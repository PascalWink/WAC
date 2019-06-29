package nl.hu.v1wac.firstapp.persistence;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {

	public List<Country> findAll() {
		List<Country> countries = new ArrayList<Country>();
		try (Connection con = super.getConnection()) {
			String query = "SELECT * FROM country";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet dbResultSet = pstmt.executeQuery();
			while (dbResultSet.next()) {
				String code = dbResultSet.getString("code");
				String iso3 = dbResultSet.getString("iso3");
				String nm = dbResultSet.getString("name");
				String cap = dbResultSet.getString("capital");
				String ct = dbResultSet.getString("continent");
				String reg = dbResultSet.getString("region");
				double sur = dbResultSet.getDouble("surfacearea");
				int pop = dbResultSet.getInt("population");
				String gov = dbResultSet.getString("governmentform");
				double lat = dbResultSet.getDouble("latitude");
				double lng = dbResultSet.getDouble("longitude");
				countries.add(new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return countries;
	}

	public List<Country> find10LargestPopulations() {
		List<Country> countries = new ArrayList<Country>();
		try (Connection con = super.getConnection()) {
			String query = "SELECT * from country order by population desc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet dbResultSet = pstmt.executeQuery();
			int i = 0;
			while (dbResultSet.next() && i<10) {
				i++;
				String code = dbResultSet.getString("code");
				String iso3 = dbResultSet.getString("iso3");
				String nm = dbResultSet.getString("name");
				String cap = dbResultSet.getString("capital");
				String ct = dbResultSet.getString("continent");
				String reg = dbResultSet.getString("region");
				double sur = dbResultSet.getDouble("surfacearea");
				int pop = dbResultSet.getInt("population");
				String gov = dbResultSet.getString("governmentform");
				double lat = dbResultSet.getDouble("latitude");
				double lng = dbResultSet.getDouble("longitude");
				countries.add(new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return countries;
	}

	public java.util.List<Country> find10LargestSurfaces() {
		List<Country> countries = new ArrayList<Country>();
		try (Connection con = super.getConnection()) {
			String query = "SELECT * from country order by surfacearea desc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet dbResultSet = pstmt.executeQuery();
			int i = 0;
			while (dbResultSet.next() && i<10) {
				i++;
				String code = dbResultSet.getString("code");
				String iso3 = dbResultSet.getString("iso3");
				String nm = dbResultSet.getString("name");
				String cap = dbResultSet.getString("capital");
				String ct = dbResultSet.getString("continent");
				String reg = dbResultSet.getString("region");
				double sur = dbResultSet.getDouble("surfacearea");
				int pop = dbResultSet.getInt("population");
				String gov = dbResultSet.getString("governmentform");
				double lat = dbResultSet.getDouble("latitude");
				double lng = dbResultSet.getDouble("longitude");
				countries.add(new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return null;
		}
		return countries;
	}

	public Country findByCode(String codeland) {
		Country land = new Country();
		try (Connection con = super.getConnection()) {
			String query = "SELECT * from country where code = '" + codeland.toUpperCase() + "'";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet dbResultSet = pstmt.executeQuery();
			while (dbResultSet.next()) {
				String code = dbResultSet.getString("code");
				String iso3 = dbResultSet.getString("iso3");
				String nm = dbResultSet.getString("name");
				String cap = dbResultSet.getString("capital");
				String ct = dbResultSet.getString("continent");
				String reg = dbResultSet.getString("region");
				double sur = dbResultSet.getDouble("surfacearea");
				int pop = dbResultSet.getInt("population");
				String gov = dbResultSet.getString("governmentform");
				double lat = dbResultSet.getDouble("latitude");
				double lng = dbResultSet.getDouble("longitude");
				land = new Country(code, iso3, nm, cap, ct, reg, sur, pop, gov, lat, lng);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return land;
	}

	public boolean update(String id, String hoofdstad, String regio, double surface, int inwoners) {
		try (Connection con = super.getConnection()) {
			String updatequery = "UPDATE country SET capital = '"+hoofdstad+"', surfacearea = '"+surface+"', population = '"+inwoners+"' WHERE code = '"+id+"'";
			PreparedStatement pstmt = con.prepareStatement(updatequery);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		System.out.println("komt hiersakjdajsdaoijdisajdojaoidojsaidaojosidjajsoida");

		return false;
	}

	public boolean delete(String code) {
		try (Connection con = super.getConnection()) {
			String query = "DELETE FROM country WHERE code = '"+code+"'";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}

	public boolean save(Country country) {
		try (Connection con = super.getConnection()) {
			country.getCapital();
			String query = "INSERT INTO country(code, iso3, name, capital, continent, region, surfacearea, population, governmentform, localname) VALUES('";
			query += country.getCode() + "','" + country.getIso3() + "','" + country.getName() + "','"
					+ country.getCapital() + "','" + country.getContinent() + "','";
			query += country.getRegion() + "','" + country.getSurface() + "','" + country.getPopulation() + "','"
					+ country.getGovernment() + "', '"+country.getLocalName()+"')";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	}
}
