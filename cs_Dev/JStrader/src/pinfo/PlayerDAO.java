package pinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PlayerDAO {
	
		private Connection con;
		PreparedStatement pstmt = null;
		ResultSet rs = null;//���� 
		
		public PlayerDAO() 
		 throws ClassNotFoundException, SQLException{
		
			con= new DbInfoDBConn().getConnection();
			//���Ӱ�ü get ->con 
		}
		public void pstmtClose() throws SQLException{
			if(pstmt != null){ pstmt.close(); }
		}
		public void getAllInfoClose() 
				throws SQLException{
			if(rs != null){ rs.close(); }
			if(pstmt != null){ pstmt.close(); }
			if(con != null){ con.close(); }
		}
		//��ü ����� ����
		public ArrayList<playerDTO> listAll() throws SQLException{//�޼ҵ�
		
		ArrayList<playerDTO> pArray//generic
			=new ArrayList<playerDTO>();
		String sql = "SELECT * FROM pinfo";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String name = rs.getString("player_name"); 
				String birth_date = rs.getString("player_birth_date"); 
				String height = rs.getString("player_height"); 
				String team = rs.getString("player_team"); 
				String position = rs.getString("player_position"); 
				String number = rs.getString("player_number"); 
				String school = rs.getString("player_school"); 

				//table --> ������ �ֱ�
				
				playerDTO pv = new playerDTO(name, birth_date, height, team,
								position, number, school);
					pArray.add(pv);
			}
			return pArray;
		}
			
			public boolean insert_player//�ڷ��Է� �޼ҵ�
			(String name,String birth_date,String height,String team,
					String position,String number,String school){
			 String sql 
			 = "insert into pinfo values(?,?,?,?,?,?,?)";
					try{
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, name);
						pstmt.setString(2, birth_date);
						pstmt.setString(3, height);
						pstmt.setString(4, team);
						pstmt.setString(5, position);
						pstmt.setString(6, number);
						pstmt.setString(7, school);
						pstmt.executeUpdate();
					}catch(SQLException e){
						System.out.println("insert Exception");
						return false;
					}
					return true;
				}

			//�˻��޼ҵ�
			public ArrayList<playerDTO> search_player(String name1) throws SQLException{//�޼ҵ�
				
				ArrayList<playerDTO> pArray//generic
					=new ArrayList<playerDTO>();
				String sql = "SELECT * FROM pinfo WHERE player_name = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, name1);
					rs = pstmt.executeQuery();
					System.out.println(name1);
					while(rs.next()){
						String name = rs.getString("player_name"); 
						String birth_date = rs.getString("player_birth_date"); 
						String height = rs.getString("player_height"); 
						String team = rs.getString("player_team"); 
						String position = rs.getString("player_position"); 
						String number = rs.getString("player_number"); 
						String school = rs.getString("player_school"); 

						//table --> ������ �ֱ�
						System.out.println(name+ birth_date+ height+ team
										+position+ number+ school);
						playerDTO pv = new playerDTO(name, birth_date, height, team,
										position, number, school);
							pArray.add(pv);
					}
					return pArray;
				}
			/*public playerDTO search_player(String name) throws SQLException{
				
				//ArrayList<playerDTO> pArray//generic
					//=new ArrayList<playerDTO>();
				String sql = "SELECT * FROM pinfo WHERE name = ?";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					pstmt.setString(1, name);
					playerDTO pv = null;
				
					while(rs.next()){
						String name1 = rs.getString("player_name"); 
						String birth_date = rs.getString("player_birth_date"); 
						String height = rs.getString("player_height"); 
						String team = rs.getString("player_team"); 
						String position = rs.getString("player_position"); 
						String number = rs.getString("player_number"); 
						String school = rs.getString("player_school"); 

						//table --> ������ �ֱ�
						
						pv = new playerDTO(name1, birth_date, height, team,
										position, number, school);
					}
					if(pv.equals(null)){ 
							return new playerDTO; 
						}else	return pv;  
				}
			*/
			//�����޼ҵ�
			public boolean update_player
				(String name, String name2){
				String sql
				= "update pinfo set player_name=? where player_name=?";
					try{
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, name2);
						pstmt.setString(2, name);
						pstmt.executeUpdate();///////////////
					}catch(SQLException e){
					System.out.println("update Exception");
						return false;
					}
					return true;
				}
			
				//�����޼ҵ�
				public boolean delete_player(String number){
					String sql 
					= "delete from pinfo where player_number=?";
					try{
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, number);
						pstmt.executeUpdate();//////////
					}catch(SQLException e){
					System.out.println("delete Exception");
						return false;
					}
					return true;
				}

	

}
