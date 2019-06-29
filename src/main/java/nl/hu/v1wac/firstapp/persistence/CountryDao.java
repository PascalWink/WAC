package nl.hu.v1wac.firstapp.persistence;

import java.util.List;

public interface CountryDao {

	public List<Country> findAll();

	public Country findByCode(String code);

	public List<Country> find10LargestPopulations();

	public List<Country> find10LargestSurfaces();

	public boolean update(String id, String hoofdstad, String regio, double surface, int inwoners);

	public boolean delete(String code);
	
	public boolean save(Country country);
}
