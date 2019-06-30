package nl.hu.v1wac.firstapp.persistence;

public class Country {
	private String code;
	private String iso3;
	private String name;
	private String capital;
	private String continent;
	private String region;
	private double surface;
	private int population;
	private String government;
	private double latitude;
	private double longitude;
	private String localName;

	public Country() {
		
	}
	
	public Country(String code, String iso3, String nm, String cap, String ct, String reg, double sur, int pop, String gov, double lat, double lng) {
		this.code = code; 
		this.iso3 = iso3;
		this.name = nm;
		this.capital = cap;
		this.continent = ct;
		this.region = reg;
		this.surface = sur;
		this.population = pop;
		this.government = gov;
		this.latitude = lat;
		this.longitude = lng;
	}

	public Country(String id, String iso3, String nm, String capi, String ct, String reg,
			double sur, int pop, String gov, String ln) {
		this.code = id; 
		this.iso3 = iso3;
		this.name = nm;
		this.capital = capi;
		this.continent = ct;
		this.region = reg;
		this.surface = sur;
		this.population = pop;
		this.government = gov;
		this.localName = ln;
	}

	public Country(String id, String nm, String capi, String reg, double sur, int pop) {
		this.code = id; 
		this.name = nm;
		this.capital = capi;
		this.region = reg;
		this.surface = sur;
		this.population = pop;
		}

	public String getCode() {
		return code;
	}
	
	public String getLocalName() {
		return localName;
	}
	
	public String getIso3() {
		return iso3;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCapital() {
		return capital;
	}
	
	public String getContinent() {
		return continent;
	}
	
	public String getRegion() {
		return region;
	}
	
	public double getSurface() {
		return surface;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public String getGovernment() {
		return government;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
}
