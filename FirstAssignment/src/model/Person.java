package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;

public class Person {

	private int id;
	private Reservation reservation;
	private String name;
	private int payed;
	
	private static final String insertStatementString = "INSERT INTO persoana (Idrezervare,nume_persoana,platit)"	+ " VALUES (?,?,?)";
	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	
	
	public Person(int id, Reservation reservationId, String name, int payed) {
		this.id = id;
		this.setReservation(reservationId);
		this.name = name;
		this.setPayed(payed);
	}
	

	public Person(Reservation r, String string, int parseInt) {
		this.reservation=r;
		this.name=string;
		this.payed=parseInt;
	}


	public Person(int id) {
		this.id=id;
	}


	public Person() {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPayed() {
		return payed;
	}


	public void setPayed(int payed) {
		this.payed = payed;
	}
	public Reservation getReservation() {
		return reservation;
	}


	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	/***************************************************************
	 *  BLL
	 ***************************************************************/

	public int insertPerson(){
		return insertPersonDAO(this);
	}

	public void deletePerson(){
		if(this.getId()<=0)
			throw new IllegalArgumentException("Id invalid");
		 deletePersonDAO(this.getId());
	}

	public List<Person> getAll(int cId){
		return this.findByClientDAO(cId);
	}
	public Person seeMissedPayments(){
		//List<Person> listaP=new ArrayList<Person>();
		LocalDateTime now= LocalDateTime.now();
		if (this.getPayed()==0&&this.reservation.getPaymentInfo().getPaymentDate().getYear()==now.getYear())
		{
			if(this.getReservation().getPaymentInfo().getPaymentDate().getMonth()==now.getMonthValue())
			
			{	if(this.getReservation().getPaymentInfo().getPaymentDate().getDay()<now.getDayOfMonth())
					return this;
			}
			else
				if(this.getReservation().getPaymentInfo().getPaymentDate().getMonth()<now.getMonthValue())
					return this;
		}
		else
			{
				if(this.getPayed()==0&&this.reservation.getPaymentInfo().getPaymentDate().getYear()<now.getYear())
					return this;
			}
				
		return null;
	}
	/***************************************************************
	 *  SQL Operation Methods
	 ***************************************************************/

	private static void deletePersonDAO(int id)
	{
	     String delete="DELETE FROM persoana WHERE id_persoana ="+String.valueOf(id);
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
	
	
	private static int insertPersonDAO(Person p)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int reservationId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementString);
			insertStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
			//(id_reservare,id_client,destinatie,nume_hotel,nr_persoane,data_plata,total_plata)
				insertStatement.setInt(1, p.getReservation().getId());
				insertStatement.setString(2, p.getName());
				insertStatement.setInt(3, p.getPayed());				  
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

	private static List<Person> findByClientDAO(int clientId)
	{
		
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		List<Person> persons= new ArrayList<Person>();
		try{
			findStatement=dbConnection.prepareStatement("SELECT * FROM persoana JOIN rezervare ON persoana.Idrezervare=rezervare.id_rezervare where rezervare.Idclient=?");
			findStatement.setInt(1,clientId);
			rs=findStatement.executeQuery();
			while(rs.next())
			{
				Person p=new Person();
				int id= rs.getInt("id_persoana");
				int idR=rs.getInt("Idrezervare");
				String nume=rs.getString("nume_persoana");	
				int platit=rs.getInt("platit");
				p.setId(id);
				Reservation r= new Reservation(idR);
				r=r.findById();
				p.setReservation(r);
				p.setName(nume);
				p.setPayed(platit);
				persons.add(p);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return persons;
	}

	
}
