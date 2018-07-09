package nl.hu.v1wac.firstapp.webservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.lang.model.element.VariableElement;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import nl.hu.v1wac.firstapp.persistence.CountryDao;
import nl.hu.v1wac.firstapp.persistence.CountryPostgresDaoImpl;
import nl.hu.v1wac.firstapp.model.Country;
import nl.hu.v1wac.firstapp.model.ServiceProvider;
import nl.hu.v1wac.firstapp.model.WorldService;

@Path("/countries")
public class WorldResource
{
	private CountryDao _countryDao;
	
	public WorldResource()
	{
		_countryDao = new CountryPostgresDaoImpl();
	}
	
	
	
	@GET
	@Produces("application/json")
	public String getWorldResource()
	{
		ArrayList<Country> countries = _countryDao.findAll();
		return getObjects(countries);
	}

	@GET
	@Path("largestsurfaces")
	@Produces("application/json")
	public String getLargestSurfaces()
	{
//		JsonArrayBuilder jab = Json.createArrayBuilder();
		ArrayList<Country> countries = _countryDao.findTenLargestSurface();
		return getObjects(countries);
	
	}

	@GET
	@Path("largestpopulations")
	@Produces("application/json")
	public String getLargestPopulations()
	{
		
		ArrayList<Country> countries = _countryDao.findTenLargestPopulations();
		return getObjects(countries);
	}


	
	@GET
	@Path("{code}")
	@Produces("application/json")
	public String getCountryInfo(@PathParam("code") String code) {
		WorldService service = ServiceProvider.getWorldService();
		Country country = service.getCountryByCode(code);

		if (country == null) {
			throw new WebApplicationException("No such country!");
		}

		JsonObjectBuilder job = Json.createObjectBuilder();
		job.add("code", country.getCode());
		job.add("iso3", country.getIso3());
		job.add("naam", country.getName());
		job.add("continent", country.getContinent());
		job.add("capital", country.getCapital());
		job.add("region", country.getRegion());
		job.add("surface", country.getSurface());
		job.add("population", country.getPopulation());
		job.add("government", country.getGovernment());
		job.add("lat", country.getLatitude());
		job.add("lng", country.getLongitude());

		return job.build().toString();

	}
	
//	@GET
//	@Path("{country}")
//	@Produces("application/json")
//	public String getCn(@PathParam("country") String country)
//	{
//		ArrayList<Country> countries = _countryDao.findByCode(country);
//		return getObjects(countries);
//	}
//	
	@DELETE
	@Path("delete/{code}")
	@Produces("application/json")
	@RolesAllowed("user")
	public void delete(@PathParam("code") String code)
	{
		_countryDao.delete(code);
	}
	
	@PUT
	@Produces("application/json")
	@RolesAllowed("user")
	public Response save(@QueryParam("code") String code,
			@QueryParam("iso3") String iso3,
			@QueryParam("name") String name,
			@QueryParam("capital") String capital,
			@QueryParam("continent") String continent,
			@QueryParam("region") String region,
			@QueryParam("goverment") String goverment,
			@QueryParam("population") int population,
			@QueryParam("surface") double surface,
			@QueryParam("latitude") double lat,
			@QueryParam("longitude") double longitude)
		
	{
		Country c = new Country(code,iso3, name, capital, continent, region, surface, population, goverment, lat, longitude);
		
		
		 
		_countryDao.update(c);
		JsonObjectBuilder job = Json.createObjectBuilder();

		job.add("code", c.getCode());
		job.add("name", c.getName());
		job.add("capital", c.getCapital());
		job.add("surfacearea", c.getSurface());
		job.add("govermentform", c.getGovernment());
		job.add("lat", c.getLatitude());
		job.add("lng", c.getLongitude());
		job.add("iso3", c.getIso3());
		job.add("continent", c.getContinent());
		job.add("region", c.getRegion());
		job.add("population", c.getPopulation());
		
		return Response.ok(c).build();
	}
	
	@POST
	@Produces("application/json")
	@RolesAllowed("user")
	public Response create(@QueryParam("code") String code,
			@QueryParam("iso3") String iso3,
			@QueryParam("name") String name,
			@QueryParam("capital") String capital,
			@QueryParam("continent") String continent,
			@QueryParam("region") String region,
			@QueryParam("goverment") String goverment,
			@QueryParam("population") int population,
			@QueryParam("surface") double surface,
			@QueryParam("latitude") double lat,
			@QueryParam("longitude") double longitude)
	{
		Country c = new Country(code,iso3, name, capital, continent, region, surface, population, goverment, lat, longitude);
		
		
		_countryDao.save(c);
		
		
		JsonObjectBuilder job = Json.createObjectBuilder();

		job.add("code", c.getCode());
		job.add("name", c.getName());
		job.add("capital", c.getCapital());
		job.add("surfacearea", c.getSurface());
		job.add("govermentform", c.getGovernment());
		job.add("lat", c.getLatitude());
		job.add("lng", c.getLongitude());
		job.add("iso3", c.getIso3());
		job.add("continent", c.getContinent());
		job.add("region", c.getRegion());
		job.add("population", c.getPopulation());
		
		return Response.ok(c).build();

	}
	
	
	public String getObjects(ArrayList<Country> countryList){
		JsonArrayBuilder jab = Json.createArrayBuilder();

		for (Country c : countryList)
		{
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("code", c.getCode());
			job.add("name", c.getName());
			job.add("capital", c.getCapital());
			job.add("surfacearea", c.getSurface());
			job.add("govermentform", c.getGovernment());
			job.add("lat", c.getLatitude());
			job.add("lng", c.getLongitude());
			job.add("iso3", c.getIso3());
			job.add("continent", c.getContinent());
			job.add("region", c.getRegion());
			job.add("population", c.getPopulation());

			jab.add(job);
		}

		JsonArray array = jab.build();
		return array.toString();
	}
}