package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class Holiday {


	private int id;
	private String destination;
	private String hotelName;

	protected static final Logger LOGGER = Logger.getLogger(Holiday.class.getName());
	private static final String insertStatementString = "INSERT INTO vacanta (destinatie,nume_hotel)"	+ " VALUES (?,?)";
	private final static String findStatementString_id ="SELECT * FROM vacanta WHERE id_vacanta=?";
	
	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	
	public Holiday(int id,String destination, String hotelName) {
		this.id = id;
		this.destination = destination;
		this.hotelName = hotelName;
	}
	

	public Holiday(int hId) {
		this.id=hId;
	}

	/***************************************************************
	 *  Getters and Setters
	 ***************************************************************/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	/***************************************************************
	 *  BLL
	 ***************************************************************/

	public Holiday findById(){
		Holiday r= findByIdDAO(this.getId());
		if(r.getDestination()==null)
			return null;
		return r;
	}
	
	public int insertHoliday(){
		return insertHolidayDAO(this);
	}
	
	public void deleteHoliday(){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista vacanta cu id-ul dat");
		deleteHolidayDAO(this.getId());
	
	}
	
	
	/***************************************************************
	 *  SQL Operation Methods
	 ***************************************************************/

	private static Holiday findByIdDAO(int resId)
	{
		Holiday r=new Holiday(resId);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement(findStatementString_id);
			findStatement.setLong(1,resId);
			rs=findStatement.executeQuery();
			rs.next();
			String destinatie=rs.getString("destinatie");
			String nume=rs.getString("nume_hotel");
			r.setDestination(destinatie);
			r.setHotelName(nume);
			
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return r;
	}
	
	private static int insertHolidayDAO(Holiday r)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int reservationId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementString);
			insertStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
			//(id_reservare,id_client,destinatie,nume_hotel,nr_persoane,data_plata,total_plata)
				insertStatement.setString(2, r.getDestination());
				insertStatement.setString(3, r.getHotelName());

				  
				  if(rs.next())
				    {
					  reservationId = rs.getInt(1);
				    }
			  
				insertStatement.executeUpdate();

			
		    
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		ConnectionFactory.close(insertStatement);
		ConnectionFactory.close(dbConnection);
	
		return reservationId;
	
	}

	private static void deleteHolidayDAO(int id)
	{
	     String delete="DELETE FROM vacanta where id_vacanta ="+String.valueOf(id);
		Connection dbConnection = ConnectionFactory.getConnection();
		try{
			Statement stat=dbConnection.createStatement();
			stat.executeUpdate(delete);
			
		}catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			ConnectionFactory.close(dbConnection);
		}
	}
	
	
}
