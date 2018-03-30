package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import gui.LogInGUI;

public class Reservation {
	

	private int id;
	private Client client;
	private Holiday holiday;
	private Payment paymentInfo;
	private List<Person> persons=new ArrayList<Person>();
	
	protected static final Logger LOGGER = Logger.getLogger(Holiday.class.getName());
	private static final String insertStatementString = "INSERT INTO rezervare (Idclient,Idvacanta,total_plata,data_plata)"	+ " VALUES (?,?,?,?)";
	
	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	public Reservation(int id, Client client, Holiday holiday, Payment paymentInfo) {

		this.id = id;
		this.client = client;
		this.holiday = holiday;
		this.paymentInfo = paymentInfo;
	}
	
	public Reservation(Client c, Holiday h,Payment p) {
		this.client=c;
		this.holiday=h;
		this.paymentInfo=p;
	}
	public Reservation(Client c) {
		this.client=c;
	}
	public Reservation() {

	}
	public Reservation(int resId) {

		this.id=resId;
	}
	/***************************************************************
	 *  Getters and Setters
	 ***************************************************************/
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Holiday getHoliday() {
		return holiday;
	}
	public void setHoliday(Holiday holiday) {
		this.holiday = holiday;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Payment getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(Payment paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	
	/***************************************************************
	 *  BLL
	 ***************************************************************/
	public Reservation findById(){
		Reservation r=findByIdDAO(this.getId());
		if(r.getClient()==null)
			return null;
		return r;
	}
	
	
	public int findByClientId(){

		Reservation r=findByClientIdDAO(this.getClient());
		if(r.getHoliday()==null)
			return -1;
		return r.getId();
	}
	
	public int insertReservation(){
		return insertReservationDAO(this);
	}
	
	public  void deleteReservation(){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista rezervarea cu id-ul dat");
		deleteReservationDAO(this.getId());
	
	}
	
	public void updatePayment(int sum){
		if(sum<=0)
			throw new NoSuchElementException("Suma invalida");
	
		updatePaymentDAO(sum,this);
	}
	
	public void addPartialPayment(int sum){
		if(sum<=0)
			throw new NoSuchElementException("Suma invalida");
		this.getPaymentInfo().setTotalPayment(this.getPaymentInfo().getTotalPayment()-sum);
		updatePaymentDAO(this.getPaymentInfo().getTotalPayment()-sum,this);
				
	}
	public List<Reservation> findAllRes() throws SQLException{

		return findReservationsDAO(this.getClient().getId());
	}
	
	/***************************************************************
	 *  SQL Operation Methods
	 ***************************************************************/
	private static Reservation findByIdDAO(int resId)
	{
		String findStatementString_id ="SELECT * FROM rezervare WHERE id_rezervare=?";
		Reservation r=new Reservation(resId);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement(findStatementString_id);
			findStatement.setLong(1,resId);
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				int client_id=rs.getInt("Idclient");
				int id_vacanta=rs.getInt("Idvacanta");
				Payment payment=new Payment(rs.getInt("total_plata"),rs.getDate("data_plata"));
				r.setClient(new Client(client_id));
				r.setHoliday(new Holiday(id_vacanta));
				r.setPaymentInfo(payment);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return r;
	}
	

	private static Reservation findByClientIdDAO(Client c)
	{
		String findStatementString_id ="SELECT * FROM `rezervare` JOIN `client` ON `rezervare`.`Idclient`=`client`.`id_client` WHERE `client`.`id_client`=?";
		Reservation r=new Reservation(c);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;


		try{
			findStatement=dbConnection.prepareStatement(findStatementString_id);
			findStatement.setLong(1,c.getId());
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				r.setId(rs.getInt("id_rezervare"));
				r.setHoliday(new Holiday(rs.getInt("Idvacanta")));
				r.setPaymentInfo(new Payment(rs.getInt("total_plata"),rs.getDate("data_plata")));
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return r;
	}
	
	private static int insertReservationDAO(Reservation r)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int reservationId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementString);
			insertStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
			//(id_reservare,id_client,destinatie,nume_hotel,nr_persoane,data_plata,total_plata)
				insertStatement.setInt(1, r.getClient().getId());
				insertStatement.setInt(2, r.getHoliday().getId());
				insertStatement.setInt(3, r.getPaymentInfo().getTotalPayment());
				insertStatement.setDate(4, r.getPaymentInfo().getPaymentDate());
				  
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

	private static void deleteReservationDAO(int id)
	{
	     String delete="DELETE FROM rezervare where id_rezervare ="+String.valueOf(id);
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
	
	private static void updatePaymentDAO(int sum,Reservation r){
		String update = "UPDATE rezervare SET total_plata="+sum+" where id_rezervare="+String.valueOf(r.getId());
		Connection dbConnection = ConnectionFactory.getConnection();
		try{
			Statement stat=dbConnection.createStatement();
			stat.executeUpdate(update);
			
		}catch (SQLException e) {
			 e.printStackTrace();
		} finally {
			ConnectionFactory.close(dbConnection);
		}
	}
//47,1,213,2018,7,1
	private static List<Reservation> findReservationsDAO(int clientId) throws SQLException{
		Connection dbConnection = ConnectionFactory.getConnection();
	    String listRes = "SELECT * FROM rezervare JOIN client ON rezervare.Idclient=client.id_client WHERE Idclient="+String.valueOf(clientId)+" AND client.Idagent="+LogInGUI.agentAccount.getId();
	    Statement statement = dbConnection.createStatement();
	    ResultSet rs = statement.executeQuery(listRes);
	    List<Reservation> reservations = new ArrayList<Reservation>();

		try {
			while(rs.next()){
				Reservation reservation=new Reservation();
				reservation.setClient(new Client(clientId));
				reservation.setId(rs.getInt("id_rezervare"));
				reservation.setHoliday(new Holiday(rs.getInt("Idvacanta")));
				reservation.setPaymentInfo(new Payment(rs.getInt("total_plata"),rs.getDate("data_plata")));
				reservations.add(reservation);
}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return reservations;
	}
}
