package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
import gui.ResControllerGUI;





public class Agent {

	private int id;
	private String name;
	private int CNP;
	private String adress;
	private List<Client> clients=new ArrayList<Client>();
	private String userName;
	private String userPassword;
	private int admin;
	private List<Date> logInDates;
	
	protected static final Logger LOGGER = Logger.getLogger(Agent.class.getName());
	private static final String insertStatementString = "INSERT INTO agent (nume_agent,adresa,cnp,username,password,admin)"	+ " VALUES (?,?,?,?,?,?)";
	private final static String findStatementString_id ="SELECT * FROM agent where id_agent=?";
	private static final String insertStatementStringLoginDates = "INSERT INTO logindates (Id_agent,logindate)"	+ " VALUES (?,?)";

	/***************************************************************
	 *  Constructors
	 ***************************************************************/
	
	public Agent(int id, String name, int cNP, String adress, ArrayList<Client> clients) {
		this.id = id;
		this.name = name;
		this.CNP = cNP;
		this.adress = adress;
		this.clients = clients;
	
	}
	public Agent(String name,String adress, int cNP ) {
		this.name = name;
		this.CNP = cNP;
		this.adress = adress;
		
	
	}
	public Agent(String name,String adress, int cNP,String username,String password) {
		this.name = name;
		this.CNP = cNP;
		this.adress = adress;
		this.userName=username;
		this.userPassword=password;
	
	}
	public Agent(String name,String adress, int cNP,String username,String password,int admin) {
		this.name = name;
		this.CNP = cNP;
		this.adress = adress;
		this.userName=username;
		this.userPassword=password;
		this.admin=admin;
	}
	public Agent(String username,String password) {
		this.userName=username;
		this.userPassword=password;
	}

	public Agent(int agentId) {
		this.id=agentId;
	}
	public Agent() {
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
	public List<Client> getClients() {
		return clients;
	}
	public void setClients(ArrayList<Client> clients) {
		this.clients = clients;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public List<Date> getLogInDates() {
		return logInDates;
	}
	public void setLogInDates(List<Date> logInDates) throws SQLException {
		this.logInDates = this.listAllDates();
	}
	/***************************************************************
	 *  BLL
	 ***************************************************************/

	public Agent findById(){
		Agent agent= findByIdDAO(this.id);
		if(agent.getName()==null)
			throw new NoSuchElementException("Agentul cu id-ul =" + id + " nu a fost gasit!");
		return agent;
	}
	public int findByCNP(){
		Agent agent= findByCnpDAO(this.getCNP());
		if(agent.getName()==null)
			return -1;
		return agent.getId();
	}
	
	public int insertAgent(){
		if(this.findByCNP()!=-1)
			return -1;
		
		else 
			insertAgentDAO(this);
			return this.findByCNP(); 
		}
	
	public void deleteAgent(){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista agentul cu id-ul dat in baza de date");
		deleteAgentDAO(this.getId());
	
	}
	
	public void updateName(String s){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		updateNameDAO(this.getId(),s);
	}
	
	public void updateAdress(String s){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		updateAdressDAO(this.getId(),s);
	}
	
	public void updateUserName(String s){
		if(this.getId()<=0)
			throw new NoSuchElementException("Id invalid");
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		updateUserNameDAO(this.getId(),s);
	}
	
	public void updatePassword(String s){
		if(findByIdDAO(this.getId())==null)
			throw new NoSuchElementException("Nu exista persoana cu id-ul dat");
		updatePasswordDAO(this.getId(),s);
	}
	public void updateAdmin(int value){
		if(value!=0&&value!=1)
			throw new NoSuchElementException("Valoare invalida");
		updateAdminDAO(this.getId(),value);
	}
	public Agent findbyUserName(String username){
		return findByUserNameDAO(username);
	}
	public int insertLogInDate(Date date){
		return this.insertLogInDateDAO(this.getId(), date);
	}
	public List<Agent> listAllAgents() throws SQLException{
		return this.listAgentsDAO();
	}
	public List<Date> listAllDates() throws SQLException{
		return this.listDatesDAO(this.getId());
	}
	public void createReport(Date begin, Date end ){
		BufferedWriter bw = null;
		FileWriter fw = null;

		
		try {
			fw = new FileWriter("C:\\Users\\Alex\\Desktop\\raport.txt");
			bw = new BufferedWriter(fw);
			String agentData="Agent Data:\nID: "+this.getId()+"\nName: "+this.getName()+"\nCNP: "+this.getCNP()+"\nAdress: "+this.getAdress()+"\nAdmin status: "+this.getAdmin();
			agentData+="\nLoged on:\n";
			List<Date> logInDates=this.listAllDates();
			String addedReservation;
			bw.write(agentData);
			for(Date d:logInDates){
				String logInDate=d.toString()+"\n";
				bw.write(logInDate);
			}
			if(this.getId()==ResControllerGUI.addedRes.getClient().getAgent().getId())
			{
				addedReservation="\nAdded new reservation for client with id: "+ResControllerGUI.addedRes.getClient().getId()+" On:"+ResControllerGUI.modificationDate.toString()+"\n";
				bw.write(addedReservation);
			}

			bw.close();
			fw.close();
			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		

		}
		
	

	/***************************************************************
	 *  SQL Operation Methods
	 ***************************************************************/
	
	private static Agent findByIdDAO(int agentId)
	{
		Agent a=new Agent();
		a.setId(agentId);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement(findStatementString_id);
			findStatement.setLong(1,agentId);
			rs=findStatement.executeQuery();
			if(rs.next())
			
			{	
				String nume=rs.getString("nume_agent");
				int cnp= rs.getInt("cnp");
				String adresa=rs.getString("adresa");
				String username=rs.getString("username");
				String password=rs.getString("password");
				int admin=rs.getInt("admin");
				a.setName(nume);
				a.setCNP(cnp);
				a.setAdress(adresa);
				a.setUserName(username);
				a.setUserPassword(password);
				a.setAdmin(admin);
			}
				
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return a;
	}
	
	private static Agent findByCnpDAO(int agentcnp)
	{
		Agent c=new Agent();
		c.setCNP(agentcnp);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement("SELECT * FROM agent where cnp=?");
			findStatement.setInt(1,agentcnp);
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				int id= rs.getInt("id_agent");
				
				String nume=rs.getString("nume_agent");
				
				String adresa=rs.getString("adresa");
				String username=rs.getString("username");
				String pass=rs.getString("password");

				c.setName(nume);
				c.setId(id);
				c.setAdress(adresa);
				c.setUserName(username);
				c.setUserPassword(pass);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
	
	private static Agent findByUserNameDAO(String username)
	{
		Agent a=new Agent();
		a.setUserName(username);
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement findStatement=null;
		ResultSet rs=null;
		try{
			findStatement=dbConnection.prepareStatement("SELECT * FROM agent where username=?");
			findStatement.setString(1,username);
			rs=findStatement.executeQuery();
			if(rs.next())
			{
				int id=rs.getInt("id_agent");
				String nume=rs.getString("nume_agent");
				int cnp= rs.getInt("cnp");
				String adresa=rs.getString("adresa");
				String password=rs.getString("password");
				int admin=rs.getInt("admin");
				a.setId(id);
				a.setName(nume);
				a.setCNP(cnp);
				a.setAdress(adresa);
				a.setUserName(username);
				a.setUserPassword(password);
				a.setAdmin(admin);
			}
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return a;
	}
	private static int insertAgentDAO(Agent a)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int agentId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementString);
			insertStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
				insertStatement.setString(1, a.getName());
				insertStatement.setString(2, a.getAdress());
				insertStatement.setInt(3, a.getCNP());
				insertStatement.setString(4, a.getUserName());
				insertStatement.setString(5, a.getUserPassword());
				insertStatement.setInt(6, a.getAdmin());
				  
				  if(rs.next())
				    {
				    	agentId = rs.getInt(1);
				    }
			  
				insertStatement.executeUpdate();

			
		    
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		ConnectionFactory.close(insertStatement);
		ConnectionFactory.close(dbConnection);
	
		return agentId;
	
	}

	private static void deleteAgentDAO(int id)
	{
	     String delete="DELETE FROM agent where id_agent ="+String.valueOf(id);
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
	     String update="UPDATE agent SET nume_agent="+aux+" where id_agent ="+String.valueOf(id);
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
	     String update="UPDATE agent SET adresa="+aux+" where id_agent ="+String.valueOf(id);
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
	private static void updateUserNameDAO(int id,String name)
	{
		String aux="'"+name+"'"; 
	     String update="UPDATE agent SET username="+aux+" where id_agent ="+String.valueOf(id);
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
	private static void updateAdminDAO(int id,int value)
	{
	     String update="UPDATE agent SET admin="+value+" where id_agent ="+String.valueOf(id);
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
	private static void updatePasswordDAO(int id,String name)
	{
		String aux="'"+name+"'"; 
	     String update="UPDATE agent SET password="+aux+" where id_agent ="+String.valueOf(id);
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

	private static List<Agent> listAgentsDAO() throws SQLException {
		Connection dbConnection = ConnectionFactory.getConnection();
	    String listClienti = "Select * from agent";
	    Statement statement = dbConnection.createStatement();
	    ResultSet rs = statement.executeQuery(listClienti);
	    Agent agent;
	    List<Agent> agenti = new ArrayList<Agent>();
	    while(rs.next()){
	    	agent=new Agent();
	    	agent.setId(rs.getInt("id_agent"));
	    	agent.setName(rs.getString("nume_agent"));
	    	agent.setAdress(rs.getString("adresa"));
	    	agent.setCNP(rs.getInt("cnp"));
	    	agent.setUserName(rs.getString("username"));
	    	agent.setUserPassword(rs.getString("password"));
	    	agent.setAdmin(rs.getInt("admin"));
	    	agenti.add(agent);
	    }
	    dbConnection.close();
	    return agenti;
	}

	private static int insertLogInDateDAO(int id,Date date)
	{
		Connection dbConnection=ConnectionFactory.getConnection();
		PreparedStatement insertStatement=null;
		int loginDateId=0;
		
		try{
			insertStatement=dbConnection.prepareStatement(insertStatementStringLoginDates);
			insertStatement = dbConnection.prepareStatement(insertStatementStringLoginDates,Statement.RETURN_GENERATED_KEYS);
			  ResultSet rs= insertStatement.getGeneratedKeys();
				insertStatement.setInt(1, id);
				insertStatement.setDate(2, date);
				  
				  if(rs.next())
				    {
					  loginDateId = rs.getInt(1);
				    }
			  
				insertStatement.executeUpdate();

			
		    
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		ConnectionFactory.close(insertStatement);
		ConnectionFactory.close(dbConnection);
	
		return loginDateId;
	
	}
	private static List<Date> listDatesDAO(int id) throws SQLException {
		Connection dbConnection = ConnectionFactory.getConnection();
	    String listClienti = "Select * from logindates where Id_agent="+id;
	    Statement statement = dbConnection.createStatement();
	    ResultSet rs = statement.executeQuery(listClienti);

	    List<Date> dates = new ArrayList<Date>();
	    while(rs.next()){
	    	Date date=new Date(0,0,0);
	    	dates.add(rs.getDate("logindate"));
	    }
	    dbConnection.close();
	    return dates;
	}

	
}
