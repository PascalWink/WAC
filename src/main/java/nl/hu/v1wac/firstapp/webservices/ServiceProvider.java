package nl.hu.v1wac.firstapp.webservices;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import nl.hu.v1wac.firstapp.persistence.WorldService;
import nl.hu.v1wac.firstapp.persistence.Country;

@Path("/countries")
public class ServiceProvider {
	private static WorldService worldService = new WorldService();

	public static WorldService getWorldService() {
		return worldService;
	}
	
	@POST
	@Produces("application/json")
	public Response saveCountry(@FormParam("code") String id,
								@FormParam("iso3") String iso3,
								@FormParam("name") String name,
								@FormParam("continent") String continent,
								@FormParam("region") String region,
								@FormParam("surfacearea") double surfacearea,
								@FormParam("population") int population,
								@FormParam("localname") String localname,
								@FormParam("governmentform") String governmentform,
								@FormParam("capital") String capital){
		boolean country = worldService.saveCountry(id, iso3, name, continent, region, surfacearea, population, localname, governmentform, capital);		
		System.out.println("komt hiersakjdajsdaoijdisajdojaoidojsaidaojosidjajsoida");

		return Response.ok(country).build();
	}
	
	@Path("{land}")
	@PUT
	@Produces("application/json")
	public Response updateLand(@PathParam("land") String id,
								@FormParam("hoofdstad") String hoofdstad,
								@FormParam("regio") String regio,
								@FormParam("oppervlakte") double surface,
								@FormParam("inwoners") int inwoners ){
		System.out.println("komt hiersakjdajsdaoijdisajdojaoidojsaidaojosidjajsoida");

		boolean goed = worldService.updateCountry(id, hoofdstad, regio, surface, inwoners);
		
		if(goed == false) {
			Map<String, String> messages = new HashMap<String, String>();
			messages.put("error", "bestaat niet");
			return Response.status(409).entity(messages).build();
		}
		return Response.ok().build();
	}
	
	@Path("{land}")
	@DELETE
	@Produces("application/json")
	public Response deleteLand(@PathParam("land") String code) {
		if(!worldService.deleteCountry(code)) {
			return Response.status(404).build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Produces("application/json")
	public String getCountries() {
		WorldService service = ServiceProvider.getWorldService();
		List<Country> Countries = service.getAllCountries();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Country country : Countries) {
			
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("naam", country.getCode());
			jab.add(job);
		}
		return jab.build().toString();
	}

	@GET
	@Path("{land}")
	@Produces("application/json")
	public String getCountryInfo(@PathParam("land") String land) {
		WorldService service = ServiceProvider.getWorldService();
		Country country = service.getCountryByCode(land);
		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("code", country.getCode());
		job.add("iso3", country.getIso3());
		job.add("naam", country.getName());
		job.add("continent", country.getContinent());
		job.add("capital", country.getCapital());
		job.add("region", country.getSurface());
		job.add("surface", country.getSurface());
		job.add("population", country.getPopulation());
		job.add("government", country.getGovernment());
		job.add("lat", country.getLatitude());
		job.add("lng", country.getLongitude());		
		return job.build().toString();
	}
	
	@GET
	@Path("largestsurfaces")
	@Produces("application/json")
	public String getLargestSurfaces() {
		WorldService service = ServiceProvider.getWorldService();
		List<Country> top10 = service.get10LargestSurfaces();;
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Country country : top10) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("naam", country.getName());
			job.add("surface", country.getSurface());
			jab.add(job);
		}
		return jab.build().toString();
	}
	
	
	@GET
	@Path("largestpopulations")
	@Produces("application/json")
	public String getLargestPopulations() {
		WorldService service = ServiceProvider.getWorldService();
		List<Country> top10 = service.get10LargestPopulations();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Country land : top10) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("naam" , land.getName());
			jab.add(job);
		}
		return jab.build().toString();
	}
}