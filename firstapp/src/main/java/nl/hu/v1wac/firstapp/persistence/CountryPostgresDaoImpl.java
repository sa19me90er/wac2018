package nl.hu.v1wac.firstapp.persistence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.hu.v1wac.firstapp.model.Country;

public class CountryPostgresDaoImpl extends BaseDao implements CountryDao {

	
	private List<Country> selectCountries(String query) {
		List<Country> results = new ArrayList<Country>();

		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				String code = dbResultSet.getString("code");
				String iso = dbResultSet.getString("iso3");
				String name = dbResultSet.getString("name");
				String capital = dbResultSet.getString("Capital");
				String continent = dbResultSet.getString("continent");
				String region = dbResultSet.getString("region");
				double surface = dbResultSet.getDouble("surfacearea");
				int population = dbResultSet.getInt("population");
				String government = dbResultSet.getString("governmentform");
				double latitude = dbResultSet.getDouble("latitude");
				double longitude = dbResultSet.getDouble("longitude");

				results.add(new Country(code, iso, name, capital, continent, region, surface, population, government,
						latitude, longitude));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return results;

	}

	@Override
    public boolean save(Country country) {
        boolean isSaved = false;
        String query = "INSERT INTO country (code, iso3, name, continent, region, surfacearea,  population,  governmentform,  latitude,longitude, capital) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        try (Connection con = super.getConnection()) {
            statement = con.prepareStatement(query);
            
            statement.setString(1, country.getCode());
            statement.setString(2,country.getIso3());
            statement.setString(3, country.getName());
            statement.setString(4, country.getContinent());
            statement.setString(5, country.getRegion());
            statement.setDouble(6, country.getSurface());
            statement.setInt(7, country.getPopulation());
            statement.setString(8, country.getGovernment());
            statement.setDouble(9, country.getLatitude());
            statement.setDouble(10, country.getLongitude());
            statement.setString(11, country.getCapital());
            
            statement.executeUpdate();
            statement.close();
            
            isSaved = true;
        } catch(SQLException ex) {
            ex.printStackTrace();
            isSaved = false;
            
        }
        return isSaved;
    }
	
//	public Country save(Country country)
//	{
//		nonQuery("INSERT INTO country("+
//				"code, name, continent, region, surfacearea, indepyear, population,"+ 
//				"lifeexpectancy, gnp, gnpold, localname, governmentform, headofstate,"+ 
//				"iso3, latitude, longitude, capital)"+
//			"VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?,?, ?, ?, ?);",
//					country.getCode(),
//					country.getName(),
//					country.getContinent(),
//					country.getRegion(),
//					country.getSurface(),
//					1995,
//					country.getPopulation(),
//					10,
//					70,
//					70,
//					country.getName(),
//					country.getGovernment(),
//					"",
//					country.getIso3(),
//					country.getLatitude(),
//					country.getLongitude(),
//					country.getCapital());
//		
//		return country;
//	}
	
	public Country update(Country country)
	{
		 nonQuery("UPDATE public.country"+ 
   " SET name=?, continent=?, region=?, surfacearea=?, indepyear=?,"+  
       " population=?, lifeexpectancy=?, gnp=?, gnpold=?, localname=?, "+ 
       " governmentform=?, headofstate=?, iso3=?, latitude=?, longitude=?,"+  
       " capital=?"+ 
 " WHERE code = ?;",
			country.getName(),
			country.getContinent(),
			country.getRegion(),
			country.getSurface(),
			1995,
			country.getPopulation(),
			10,
			70,
			70,
			country.getName(),
			country.getGovernment(),
			"",
			country.getIso3(),
			country.getLatitude(),
			country.getLongitude(),
			country.getCapital(),
			country.getCode());
				
		return country;
	}

	public boolean delete(String country)
	{
	
		return nonQuery("DELETE FROM Country WHERE Code = ?",country);
	}
	
//	public boolean delete(Country country)
//	{
//		return nonQuery("DELETE FROM Country WHERE Code = ?",country.getCode());
//	}
//	
	public ArrayList<Country> findAll()
	{
		
		ArrayList<Map<String, Object>> result = query("SELECT Code,iso3,name,capital,continent,region,surfacearea,population,governmentform,latitude,longitude FROM Country");
		ArrayList<Country> returnValue = new ArrayList<Country>();
		for(Map<String, Object> country : result)
		{
			returnValue.add(getByDictonary(country));
		}
		
		return returnValue;
	}
	
	public Country findByCode(String code) {

		Country country = null;
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(
					"SELECT code, iso3, name,capital, continent, region, surfacearea,population,governmentform, latitude,longitude FROM public.country WHERE code =  '"
							+ code + "'");

			while (dbResultSet.next()) {
				country = new Country(dbResultSet.getString("CODE"), dbResultSet.getString("ISO3"),
						dbResultSet.getString("NAME"), dbResultSet.getString("CAPITAL"),
						dbResultSet.getString("CONTINENT"), dbResultSet.getString("REGION"),
						dbResultSet.getDouble("SURFACEAREA"), dbResultSet.getInt("POPULATION"),
						dbResultSet.getString("GOVERNMENTFORM"), dbResultSet.getDouble("LATITUDE"),
						dbResultSet.getDouble("LONGITUDE"));

			}

			dbResultSet.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(country);
		}
		System.out.println(country);
		return country;

	}	
	
	
//	public Country findByCode(String code)
//	{
//		ArrayList<Map<String, Object>> result = query("SELECT Code,iso3,name,capital,continent,region,surfacearea,population,governmentform,latitude,longitude FROM Country where Code = ?", code);
//		
//		if(result == null || result.size() == 0)
//			return null;
//		
//		Map<String, Object> country = result.get(0);
//		return getByDictonary(country);
//		
//	}
	
	
	
	public ArrayList<Country> findTenLargestPopulations()
	{
		ArrayList<Map<String, Object>> result = query("SELECT Code,iso3,name,capital,continent,region,surfacearea,population,governmentform,latitude,longitude FROM Country order by population desc limit 10");
		ArrayList<Country> returnValue = new ArrayList<Country>();
		for(Map<String, Object> country : result)
		{
			returnValue.add(getByDictonary(country));
		}
		
		return returnValue;
	} 
	
	public ArrayList<Country> findTenLargestSurface()
	{
		ArrayList<Map<String, Object>> result = query("SELECT Code,iso3,name,capital,continent::varchar,region,surfacearea,population,governmentform,latitude,longitude FROM Country order by surfacearea desc limit 10");
		ArrayList<Country> returnValue = new ArrayList<Country>();
		for(Map<String, Object> country : result)
		{
			returnValue.add(getByDictonary(country));
		}
		
		return returnValue;
	} 
	
	private Country getByDictonary(Map<String, Object> country)
	{
		String code = (String)country.get("code");
		String iso3 = (String)country.get("iso3");
		String name = (String)country.get("name");
		String capital = (String)country.get("capital");
		String continent = country.get("continent").toString();
		String region = (String)country.get("region");
		double surface = ((BigDecimal)country.get("surfacearea")).doubleValue();
		int population = (int)country.get("population");
		String goverment = (String)country.get("governmentform");
		
		double lat = country.get("latitude") == null ? 0 : ((BigDecimal)country.get("latitude")).doubleValue();
		double lng = country.get("latitude") == null ? 0 : ((BigDecimal)country.get("longitude")).doubleValue();
		
		return new Country(code, iso3, name, capital, continent, region, surface, population, goverment, lat, lng);
	}
	
}
