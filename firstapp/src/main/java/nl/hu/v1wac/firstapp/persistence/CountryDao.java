package nl.hu.v1wac.firstapp.persistence;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

//import javax.lang.model.element.VariableElement;
//import javax.validation.constraints.Null;

import nl.hu.v1wac.firstapp.model.Country;

public interface CountryDao
{
	
	
	public boolean save(Country country);
	public Country update(Country country);
	public boolean delete(String country);
	public ArrayList<Country> findAll();
	public Country findByCode(String code) ;
	public ArrayList<Country> findTenLargestPopulations();
	public ArrayList<Country> findTenLargestSurface();
//	public boolean delete(Country country);
	
}