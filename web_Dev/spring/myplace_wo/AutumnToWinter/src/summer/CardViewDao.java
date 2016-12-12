package summer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CardViewDao {
	final String TABLE_NAME = "cardview";
	DataSource dataSource;
	
	
	public CardViewDao(){
		try{
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
		}catch(Exception e){
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public ArrayList<CardDto> CardList(){
		ArrayList<CardDto> cdtos = new ArrayList<CardDto>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
		try{
			connection = dataSource.getConnection();
			String query = "select cardnumber, username, title, content, time, hit, category, imagepath from "+ TABLE_NAME;
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				
				int cardnumber = resultSet.getInt("cardnumber");
				String username = resultSet.getString("username");
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				Timestamp time = resultSet.getTimestamp("time");
				int hit = resultSet.getInt("hit");
				String category = resultSet.getString("category");
				String imagepath = resultSet.getString("imagepath");
				
				CardDto cdto = new CardDto(cardnumber,username,title,content,time,hit,category,imagepath);
				
				cdtos.add(cdto);
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
		return cdtos;
	}
	
	public CardDto CardSelect(int cardn, String usern){
		CardDto cdto=null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
	
		try{
			connection = dataSource.getConnection();
			String query = "select cardnumber, username, title, content, time, hit, category, imagepath from "+ TABLE_NAME + " where cardnumber='"+cardn+"'";
			System.out.println(query);
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			
			int cardnumber = resultSet.getInt("cardnumber");
			String username = resultSet.getString("username");
			
			String title = resultSet.getString("title");
			String content = resultSet.getString("content");
			Timestamp time = resultSet.getTimestamp("time");
			int hit = resultSet.getInt("hit");
			String category = resultSet.getString("category");
			String imagepath = resultSet.getString("imagepath");
			
			cdto = new CardDto(cardnumber,username,title,content,time,hit,category,imagepath);
				
			
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
		return cdto;
	}
	
	public void CardWrite(String username,String title,String content, String imagepath){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = dataSource.getConnection();
			String query = "insert into cardview (cardnumber,username,title,content,imagepath)"
					+ " values (cardview_seq.nextval,?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, title);
			preparedStatement.setString(3, content);
			preparedStatement.setString(4, imagepath);
			System.out.println("CardViewDao.CardWrite : "+username+title+content+imagepath);
			
			
			int rn = preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) preparedStatement.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}		
	}
	
	public void CardImageWrite(int cardnumber,String title,String imagepath){
		Connection connection = null;
	}
}
