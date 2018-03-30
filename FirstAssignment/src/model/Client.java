package model;

import java.sql.Connection;
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

public class Client {


	private int id;
	private Agent agent;
	private String name;
	private int CNP;
	private String adress;
	private List<Reservation> reservations= new ArrayList<Reservation>();
	
	protected static final Logger LOGGER = Logger.getLogger(Client.class.getName());
	private static final String insertStatementString = "INSERT INTO client (nume_client,cnp,adresa,Idagent)"	+ " VALUES (?,?,?,?)";
	private final static String findStatementString_id ="SELECT * FROM client where id_client=?";
	
	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	
	public Client(String name,int cNP,String adress,int agentId) {
		Agent a=new Agent(agentId);
		this.agent=a;
		this.name = name;
		CNP = cNP;
		this.adress = adress;
	}
	
	public Client() {
	}

	public Client(int clientId) {
		this.id=clientId;
	}

	public Client(String string, int parseInt, String string2) {
		this.name=string;
		this.CNP=parseInt;
		this.adress=string2;
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
	public int getCNP() {
		return CNP;
	}
	public void setCNP(int cNP) {
		CNP = cNP;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	/***************************************************************
	 *  BLL
	 ***************************************************************/
	public Client findById(){
		Client client= findByIdDAO(this.getId());
		if(client.getName()==null)
			return null;
		return client;
	}
	
	public int findByCnp(){
		Client client= findByCnpDAO(this.getCNP());
		if(client.getName()==null)
			return -1;
		return client.getId();
	}
	
	public int insertClient(){
		if(this.findByCnp()!=-1)
			return -1;
		
		else 
			insertClientDAO(this);
			return this.findByCnp(); 
	}
	
	public void deleteClient(){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		deleteClientDAO(this.getId());
	
	}
	
	public void updateName(String s){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat in baza de date");
		updateNameDAO(this.getId(),s);
	}
	
	public void updateAdress(String s){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		updateAdressDAO(this.getId(),s);
	}
	public List<Client> getAll() throws SQLException{

		return this.listClientsDAO();
			
	}
	/***************************************************************
	 *  SQL Operation Methods
	 ***************************************************************/
	
	private static Client findByIdDAO(int clientId)
	{
		Client c=new Client();
		c.setId(clientId);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement(findStatementString_id);
			findStatement.setLong(1,clientId);
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				int agentId=rs.getInt("Idagent");
				String nume=rs.getString("nume_client");
				int cnp= rs.getInt("cnp");
				String adresa=rs.getString("adresa");
				Agent a=new Agent(agentId);
				c.setName(nume);
				c.setCNP(cnp);
				c.setAdress(adresa);
				c.setAgent(a);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
	
	private static Client findByCnpDAO(int clientcnp)
	{
		Client c=new Client();
		c.setCNP(clientcnp);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement("SELECT * FROM client where cnp=?");
			findStatement.setInt(1,clientcnp);
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				int id= rs.getInt("id_client");
				int agentId=rs.getInt("Idagent");
				String nume=rs.getString("nume_client");
				
				String adresa=rs.getString("adresa");
				Agent a=new Agent(agentId);
				c.setName(nume);
				c.setId(id);
				c.setAdress(adresa);
				c.setAgent(a);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
	private static int insertClientDAO(Client c)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int clientId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementString);
			insertStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
			  insertStatement.setString(1, c.getName());
			  insertStatement.setInt(2, c.getCNP());
			  insertStatement.setString(3, c.getAdress());
			  insertStatement.setInt(4, c.getAgent().getId());
				
				
				
				  if(rs.next())
				    {
				    	clientId = rs.getInt(1);
				    }
			  
				insertStatement.executeUpdate();

			
		    
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		ConnectionFactory.close(insertStatement);
		ConnectionFactory.close(dbConnection);
		return clientId;
	
	}

	private static void deleteClientDAO(int id)
	{
	     String delete="DELETE FROM client WHERE id_client ="+String.valueOf(id);
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
	
	private static void updateNameDAO(int id,String name)
	{
		String aux="'"+name+"'";
	     String update="UPDATE client SET nume_client= "+aux+" WHERE id_client ="+String.valueOf(id);
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
	
	private static void updateAdressDAO(int id,String name)
	{
		String aux="'"+name+"'"; 
		String update="UPDATE client SET adresa="+aux+" WHERE id_client ="+String.valueOf(id);
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
	private static List<Client> listClientsDAO() throws SQLException {
		Connection dbConnection = ConnectionFactory.getConnection();
	    String listClienti = "Select * from client JOIN agent ON client.Idagent=agent.id_agent WHERE id_agent="+LogInGUI.agentAccount.getId();
	    Statement statement = dbConnection.createStatement();
	    ResultSet rs = statement.executeQuery(listClienti);
	    Client client;
	    List<Client> clienti = new ArrayList<Client>();
	    while(rs.next()){
	         client=new Client();
	         client.setId(rs.getInt("id_client"));
	         client.setAgent(new Agent(rs.getInt("Idagent")));
	         client.setName(rs.getString("nume_client"));
	         client.setAdress(rs.getString("adresa"));
	         client.setCNP(rs.getInt("cnp"));
	
	         clienti.add(client);
	    }
	    dbConnection.close();
	    return clienti;
	}


}
