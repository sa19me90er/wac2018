package nl.hu.v1wac.firstapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.net.URI;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class BaseDao
{
private DataSource connectionPool;

	public static BaseDao Instance()
	{
		return new BaseDao();
	}

	public BaseDao()
	{
		try
		{
			final String DATABASE_URL_PROP = System.getenv("DATABASE_URL");
			if (DATABASE_URL_PROP != null)
			{
				URI dbUri = new URI(DATABASE_URL_PROP);
				String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
				BasicDataSource pool = new BasicDataSource();
				if (dbUri.getUserInfo() != null)
				{
					pool.setUsername(dbUri.getUserInfo().split(":")[0]);
					pool.setPassword(dbUri.getUserInfo().split(":")[1]);
				}
				pool.setDriverClassName("org.postgresql.Driver");
				pool.setUrl(dbUrl);
				pool.setInitialSize(1);

				connectionPool = pool;
			}
			else
			{
				InitialContext ic = new InitialContext();
				connectionPool = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> queryFirst(String query, Object... parameters)
	{
		ArrayList<Map<String, Object>> result = query(query, parameters);

		if (result == null)
			return null;

		if (result.size() == 0)
			return null;

		return result.get(0);
	}

	public ArrayList<Map<String, Object>> query(String query, Object... parameters)
	{
		ArrayList<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		try
		{
			Connection _connection = getConnection();
			PreparedStatement stmt = _connection.prepareStatement(query);
			int parameterIndex = 1;
			for (Object parameter : parameters)
			{
				stmt.setObject(parameterIndex++, parameter);
			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next())
			{
				Map<String, Object> value = new HashMap<String, Object>();

				ResultSetMetaData rsmd = rs.getMetaData();

				for (int i = 0; i < rsmd.getColumnCount(); i++)
				{
					String name = rsmd.getColumnName(i + 1);
					value.put(name, rs.getObject(name));
				}

				values.add(value);
			}

			rs.close();
			stmt.close();
			_connection.close();
			return values;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	public boolean nonQuery(String query, Object... parameters)
	{
		try
		{
			Connection _connection = getConnection();
			PreparedStatement stmt = _connection.prepareStatement(query);
			int parameterIndex = 1;
			for (Object parameter : parameters)
			{
				stmt.setObject(parameterIndex++, parameter);
			}
			boolean result = stmt.execute();

			stmt.close();
			_connection.close();
			return result;
		}
		catch (Exception ex)
		{
			return false;
		}
	}

	protected final Connection getConnection()
	{
		try
		{
			return connectionPool.getConnection();
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	
}
