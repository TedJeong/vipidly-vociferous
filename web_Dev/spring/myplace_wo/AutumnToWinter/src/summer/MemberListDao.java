package summer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.Cookie;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class MemberListDao {
	
	final String TABLE_NAME = "signinlist";
	
	DataSource dataSource;
	public MemberListDao(){
			
		try{
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e){
			// TODO: handle exception
			e.printStackTrace();
		}
	}
		
	public ArrayList<MemberDto> MemberList(){
		ArrayList<MemberDto> mdtos = new ArrayList<MemberDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
		try{
			connection = dataSource.getConnection();
			String query = "select email, password, username, province from "+ TABLE_NAME;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				String username = resultSet.getString("username");
				String province = resultSet.getString("province");
				MemberDto mdto = new MemberDto(email,password,username,province);
				System.out.println(email+password+username+province);
				mdtos.add(mdto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return mdtos;
	}
	

	public void MemberWrite(String email, String password, String username, String province){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = dataSource.getConnection();
			String query = "insert into "+TABLE_NAME+"(email,pw,username,province) "
					+ "values (?,?,?,?)";
			//signinlist_seq.nextval,
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, username);
			preparedStatement.setString(4, province);
			int rn = preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}
	
	public MemberDto MemberView(String inputusername){
		MemberDto mdto = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = dataSource.getConnection();
			String query = "select * from "+TABLE_NAME+" where username = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, inputusername);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()){
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");
				String username = resultSet.getString("username");
				String province = resultSet.getString("province");
				mdto = new MemberDto(email,password,username,province);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return mdto;
	}
	
	public String MemberCheck(String emailorusername, String password){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultset = null;
		try{
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			String query = "select * from "+TABLE_NAME;
			resultset = statement.executeQuery(query);
			System.out.println("query executed.");
			while(resultset.next()){
				String email = resultset.getString("email");
				String pw = resultset.getString("pw");
				String username = resultset.getString("username");
				if((email.equals(emailorusername)||username.equals(emailorusername))&&pw.equals(password)){
					return "true";
				}
				//out.println("email : "+email+" ,pw : "+pw+" ,province : "+province+"<br/>");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		return "false";
	}
	
	public void MemberModify(String emailorusername, String password){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try{
			connection = dataSource.getConnection();
			String query = "update "+TABLE_NAME+" set password = ? "
					+ " where email = ? or username = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, emailorusername);
			preparedStatement.setString(3, emailorusername);
			int rn = preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}
}
