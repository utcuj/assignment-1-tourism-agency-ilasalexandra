
package connection;

import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class ConnectionFactory {
	private static final Logger LOGGER=Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER ="com.mysql.jdbc.Driver";
	private static final String DBURL ="jdbc:mysql://localhost:3306/student";
	private static final String USER="root";
	private static final String PASS="root";
	private static ConnectionFactory singleInstance=new ConnectionFactory();

	public ConnectionFactory()
	{
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	
	}
	
	private Connection createConnection()
	{
		Connection connection=null;
		try
		{
			connection=DriverManager.getConnection(DBURL,USER,PASS);

		} catch(SQLException e)
		{
			LOGGER.log(Level.WARNING,"Connection opening error.");
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnection()
	{
		return singleInstance.createConnection();
	}

	public static void close(Connection connection)
	{
		if (connection !=null)
		{
			try{
				connection.close();
			}catch(SQLException e)
			{
				LOGGER.log(Level.WARNING,"Connection closing error.");
			}
		}
	}

	public static void close(Statement statement)
	{
		if (statement!=null)
		{
			try
			{
				statement.close();
			}catch(SQLException e){
				LOGGER.log(Level.WARNING, "Statement closing error.");
			}
		}
	}

	public static void close(ResultSet resultSet)
	{
		if (resultSet!=null)
		{
			try{
				resultSet.close();
			}catch(SQLException e)
			{
				LOGGER.log(Level.WARNING, "ResulSet closing error.");
		    }
		}
	}
}
