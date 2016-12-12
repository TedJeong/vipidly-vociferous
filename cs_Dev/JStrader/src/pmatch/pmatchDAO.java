package pmatch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import multicamp.DBconnect;


public class pmatchDAO {
	static Connection con;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	static int TABLE_COL_SIZE = 18;
	static String TABLE_NAME = "pmatch";
		
	public pmatchDAO() {
		try {
			con= new DBconnect().getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void pstmtClose() throws SQLException{
		if(pstmt != null){ pstmt.close(); }
	}
	public void closeConnection() 
			throws SQLException{
		if(rs != null){ rs.close(); }
		if(pstmt != null){ pstmt.close(); }
		if(con != null){ con.close(); }
	}
		
			
		// select all
		/*
		ArrayList<pmatchDTO> arr = list();
		for(int i=0;i<arr.size();i++){
			arr.get(i).print_pmatchDTO();
		}
		*/
		// select by id
		/*
		pmatchDTO pmatch = select(1);
		System.out.println(pmatch.getBLK());
		*/
		// select by attributes
		/*
		ArrayList<Integer> arr2 = new ArrayList<Integer>();
		arr2.add(3);
		arr2.add(5);
		ArrayList<String> args2 = new ArrayList<String>();
		args2.add("test_vsteam");
		args2.add("test_pts");
		pmatchDTO pmatch_by_attrs = select_mul(arr2, args2);
		pmatch_by_attrs.print_pmatchDTO();
		*/
		/*
		ArrayList<Integer> arr3 = new ArrayList<Integer>();
		arr3.add(1);
		arr3.add(3);
		arr3.add(5);
		ArrayList<String> args3 = new ArrayList<String>();
		args3.add("101");
		args3.add("3");
		args3.add("10");
		insert(arr3,args3);
		*/
		
		// delete by and
		/*
		ArrayList<Integer> arr4 = new ArrayList<Integer>();
		arr4.add(1);
		ArrayList<String> args4 = new ArrayList<String>();
		args4.add("4");
		delete(arr4,args4);
		*/
		// update
		/*
		ArrayList<Integer> arr5 = new ArrayList<Integer>(); // where
		arr5.add(1);
		arr6.add(3);
		ArrayList<String> args5 = new ArrayList<String>();
		ArrayList<String> args6 = new ArrayList<String>();
		args5.add("100");
		args6.add("¿ì¸®ÆÀ");
		update(arr5,args5,arr6,args6);	
		*/
		
	// select all
	public static ArrayList<pmatchDTO> listAll() {
		ArrayList<pmatchDTO> dtoarray = new ArrayList<pmatchDTO>();
		String sql = "SELECT * FROM pmatch ORDER BY id";
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				String matchdate = rs.getString("matchdate");
				String vsteam = rs.getString("vsteam");
				String matchresult = rs.getString("matchresult");
				String mIN = rs.getString("mins");
				String pTS = rs.getString("pts");
				String tP = rs.getString("tp");
				String fT = rs.getString("ft");
				String oFF = rs.getString("off");
				String dEF = rs.getString("def");
				String rEF = rs.getString("reb");
				String aST = rs.getString("ast");
				String sTL = rs.getString("stl");
				String bLK = rs.getString("blk");
				String tOV = rs.getString("tov");
				String pF = rs.getString("pf");
				String pbNUM = rs.getString("pbnum");
				String prTEAM = rs.getString("prteam");
				
				pmatchDTO vo = new pmatchDTO(id, matchdate, vsteam, matchresult,
						mIN, pTS, tP, fT, oFF, dEF, rEF, aST, sTL,
						bLK, tOV, pF, pbNUM, prTEAM);
				dtoarray.add(vo); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dtoarray;
	}
	
	// select by id
	public static pmatchDTO select(int idx) throws SQLException{
		String sql = "select * from pmatch where id = '"+idx+"'";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		pmatchDTO vo = null;
		while(rs.next()){
			int id = rs.getInt("id");
			String matchdate = rs.getString("matchdate");
			String vsteam = rs.getString("vsteam");
			String matchresult = rs.getString("matchresult");
			String mIN = rs.getString("mins");
			String pTS = rs.getString("pts");
			String tP = rs.getString("tp");
			String fT = rs.getString("ft");
			String oFF = rs.getString("off");
			String dEF = rs.getString("def");
			String rEF = rs.getString("reb");
			String aST = rs.getString("ast");
			String sTL = rs.getString("stl");
			String bLK = rs.getString("blk");
			String tOV = rs.getString("tov");
			String pF = rs.getString("pf");
			String pbNUM = rs.getString("pbnum");
			String prTEAM = rs.getString("prteam");
			
			vo = new pmatchDTO(id, matchdate, vsteam, matchresult,
					mIN, pTS, tP, fT, oFF, dEF, rEF, aST, sTL,
					bLK, tOV, pF, pbNUM, prTEAM);
		}
		if(vo.equals(null)){
			return new pmatchDTO();
		}else{
			return vo;
		}
	}
	
	// select with multiple attributes
	public static ArrayList<pmatchDTO> select_mul(ArrayList<Integer> colnum, ArrayList<String> args) throws SQLException{
		System.out.println("select_mul() : ");
		ArrayList<pmatchDTO> dtoarray = new ArrayList<pmatchDTO>();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map = colmapper();
		
		String sql = "select * from pmatch where ";
		
		for(int i=0;i<colnum.size();i++){
			sql+=map.get(colnum.get(i))+" = '"+args.get(i)+"'";
			if(i != colnum.size()-1){
				sql+=" or ";
			}
		}
		/*
		for(int i=0;i<colnum.size();i++){
			System.out.println(colnum.get(i));
			System.out.println(args.get(i));
		}
		*/
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		pmatchDTO vo = null;
		
		System.out.println(sql);
		
		rs.next();
		while(rs.next()){

			int id = rs.getInt("id");
			String matchdate = rs.getString("matchdate");
			String vsteam = rs.getString("vsteam");
			String matchresult = rs.getString("matchresult");
			String mIN = rs.getString("mins");
			String pTS = rs.getString("pts");
			String tP = rs.getString("tp");
			String fT = rs.getString("ft");
			String oFF = rs.getString("off");
			String dEF = rs.getString("def");
			String rEF = rs.getString("reb");
			String aST = rs.getString("ast");
			String sTL = rs.getString("stl");
			String bLK = rs.getString("blk");
			String tOV = rs.getString("tov");
			String pF = rs.getString("pf");
			String pbNUM = rs.getString("pbnum");
			String prTEAM = rs.getString("prteam");
			
			vo = new pmatchDTO(id, matchdate, vsteam, matchresult,
					mIN, pTS, tP, fT, oFF, dEF, rEF, aST, sTL,
					bLK, tOV, pF, pbNUM, prTEAM);
			vo.print_pmatchDTO();
			dtoarray.add(vo);	
		}

		
		System.out.println("TESTSETS :" + dtoarray.size());

		return dtoarray;
	}
	
	// Insert
	public static void insert(ArrayList<Integer> colnum, ArrayList<String> args) throws SQLException{
		System.out.println("Insert() : ");
		String sql = "insert into pmatch "
				+ "(matchdate, vsteam, matchresult, mins, pts, tp, ft, off, def, reb, ast, stl, blk, tov, pf, pbNUM, prteam) "
				+ "values (";
		int j=0;
		for(int i=2 ;i<=TABLE_COL_SIZE;i++){
			if(colnum.contains(i)){
				if(i == 4 || i == 5){
					sql += "q'$" + args.get(j) + "$'";
				}else{
					sql += "'";
					sql += args.get(j);
					sql += "'";
				}
				j+=1;
			}else{
				if(i!=2)
					sql +="0";
			}
			if(i != TABLE_COL_SIZE){
				sql +=" , ";
			}
			
		}
		sql += "" +")";
		
		System.out.println(sql);
		/*
		for(int i=0;i<colnum.size();i++){
			System.out.println(colnum.get(i));
			System.out.println(args.get(i));
		}*/
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
	}
	// Delete
	public static void delete(ArrayList<Integer> colnum, ArrayList<String> args) throws SQLException{
		System.out.println("delete() : ");
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map = colmapper();
		
		String sql = "delete from pmatch where ";
		
		for(int i=0;i<colnum.size();i++){
			sql+=map.get(colnum.get(i))+" = '"+args.get(i)+"'";
			if(i != colnum.size()-1){
				sql+=" and ";
			}
		}
		System.out.println(sql);
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		pmatchDTO vo = null;
	}
	
	// Update
	public static void update(ArrayList<Integer> colnum_w, 
			ArrayList<String> args_w,
			ArrayList<Integer> colnum_s, 
			ArrayList<String> args_s) throws SQLException{
		System.out.println("update() : ");
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map = colmapper();
		
		String sql = "update pmatch ";
		
		// set 
		sql += "set ";
		for(int i=0;i<colnum_s.size();i++){
			sql+=map.get(colnum_s.get(i))+" = '"+args_s.get(i)+"'";
			if(i != colnum_s.size()-1){
				sql+=" , ";
			}
		}
		
		sql += " where ";
		// where
		for(int i=0;i<colnum_w.size();i++){
			sql+=map.get(colnum_w.get(i))+" = '"+args_w.get(i)+"'";
			if(i != colnum_w.size()-1){
				sql+=" and ";
			}
		}
		
		for(int i=0;i<colnum_s.size();i++){
			System.out.println(colnum_s.get(i));
			System.out.println(args_s.get(i));
		}
		
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		pmatchDTO vo = null;
		
		System.out.println(sql);
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
	}
	
	// column number return
	public static int colreverse(String colname){
			HashMap<String, Integer> invmap = new HashMap<String, Integer>();
			invmap.put("id", 1);
			invmap.put("matchdate", 2);
			invmap.put("vsteam", 3);
			invmap.put("matchresult", 4);
			invmap.put("mIN", 5);
			invmap.put("pTS", 6);
			invmap.put("tP", 7);
			invmap.put("fF", 8);
			invmap.put("oFF", 9);
			invmap.put("dEF", 10);
			invmap.put("rEB", 11);
			invmap.put("aST", 12);
			invmap.put("sTL", 13);
			invmap.put("bLK", 14);
			invmap.put("tOV", 15);
			invmap.put("pF", 16);
			invmap.put("pbNUM",17);
			invmap.put("prTEAM", 18);
			

			invmap.put("matchresult", 4);
			invmap.put("mins", 5);
			invmap.put("pts", 6);
			invmap.put("tp", 7);
			invmap.put("ff", 8);
			invmap.put("of", 9);
			invmap.put("def", 10);
			invmap.put("reb", 11);
			invmap.put("ast", 12);
			invmap.put("stl", 13);
			invmap.put("blk", 14);
			invmap.put("tov", 15);
			invmap.put("pf", 16);
			invmap.put("pbnum",17);
			invmap.put("prteam", 18);
			return invmap.get(colname);
		}
	
	// Hash Mapper
	public static HashMap<Integer, String> colmapper(){
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			map.put(1, "id");
			map.put(2, "matchdate");
			map.put(3, "vsteam");
			map.put(4, "matchresult");
			map.put(5, "mIN");
			map.put(6, "pTS");
			map.put(7, "tP");
			map.put(8, "fF");
			map.put(9, "oFF");
			map.put(10, "dEF");
			map.put(11, "rEB");
			map.put(12, "aST");
			map.put(13, "sTL");
			map.put(14, "bLK");
			map.put(15, "tOV");
			map.put(16, "pF");
			map.put(17, "pbNUM");
			map.put(18, "prTEAM");
			return map;
		}
}