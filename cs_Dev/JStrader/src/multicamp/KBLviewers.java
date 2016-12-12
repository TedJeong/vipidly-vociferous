package multicamp;

import data.preprocess.DatasetGenerator;
import pmatch.pmatchDAO;
import pmatch.pmatchDTO;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;


import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


import java.awt.*;
import org.jfree.ui.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.labels.*;
import org.jfree.chart.title.*;
import org.jfree.data.category.*;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

import javax.swing.ButtonGroup;

public class KBLviewers {

	private static final String CENTER = null;
	private JFrame frame;
	private JTable table;
	
	
	//private JFreeChart chart;
	
	//private SpiderWebPlot plot;
	private JPanel panel_2;
	private JLabel player_profile_photo;
	private ChartPanel player_stats_chart;
	private SpiderWebPlot plot;
	


	String series1 = "First";
    String series2 = "Second";
    String series3 = "Third";

    static String category1 = "Agility";
    static String category2 = "Physical";
    static String category3 = "Managing";
    static String category4 = "Shooting";
    static String category5 = "Defense";

    static DefaultCategoryDataset dataset;
    static JFreeChart chart;
	
	// Team infos
	
	String[] kgc_player_bnumber = {"c_9","c_32","c_41","c_55","fd_3","fd_4","fd_10","fd_11","fd_23","gd_0","gd_2","gd_7","gd_28"};
	String[] dongbu_player_bnumber =  {"c_2","c_12","fd_1","fd_7","fd_8","fd_10","fd_13","fd_18","fd_23","fd_32","fd_35","gd_4","gd_11","gd_26"};
	String[] junja_player_bnumber = {"c_0","fd_4","fd_11","fd_12","fd_13","fd_21","fd_24","fd_25","fd_30","gd_3","gd_5","gd_20","gd_30","gd_34"};
	String[] kcc_player_bnumer = {"c_0","c_37","fd_1","fd_6","fd_7","fd_9","fd_14","fd_22","fd_26","gd_3","gd_10","gd_13","gd_17","gd_23","gd_31","gd_34"};
	String[] kt_player_bnumber = {"c_11","c_13","c_15","c_33","fd_6","fd_8","fd_9","fd_21","fd_24","fd_30","gd_0","gd_1","gd_2","gd_4","gd_5","gd_8","gd_10"};
	String[] lg_player_bnumber = {"c_15","c_20","c_32","c_40","fd_2","fd_9","fd_31","gd_1","gd_3","gd_6","gd_7","gd_8","gd_10","gd_11","gd_21"};
	String[] mobis_player_bnumber = {"c_1","c_9","c_13","fd_2","fd_4","fd_5","fd_17","fd_20","fd_33","gd_3","gd_6","gd_7","gd_11","gd_23","gd_25"};
	String[] orion_player_bnumer = {"c_31","fd_4","fd_5","fd_8","fd_11","fd_13","fd_32","fd_33","gd_2","gd_6","gd_7","gd_15","gd_21","gd_55"};
	String[] sam_player_bnumber = {"c_31","fd_13","gd_1","gd_2","gd_4","gd_5","gd_7","gd_9","gd_16","gd_17","gd_21","gd_24"};
	String[] sk_player_bnumber = {"c_12","c_44","fd_1","fd_6","fd_15","fd_21","fd_35","gd_2","gd_3","gd_5","gd_7","gd_11","gd_45"};
	
	//
	//db table infos
	String[] pmatch_table_label = new String[]{"날짜","대진팀","경기결과","Min.","PTS","TP(3P)","FT","OFF","DEF","REB","AST","STL","BLK","TOV","PF","PBNUM"};
	String[] pinfo_table_label = new String[]{"성명","생년월일","신장","소속","포지션","배번"};
	
	
	pmatchDAO pmatchdao = new pmatchDAO();

	// Selection Option
	
	static String myteam_name_selected = null;
	static String vsteam_name_selected = null;
	static String dbtable_name_selected = null;
	static String query_action_seleced = null;
	
	static boolean myteam_flag = false;
	static boolean vsteam_flag = false;
	static boolean dbtable_flag = false;
	static String radio_button_selected = null;
	
	static String tablepbnum = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		// Data Preprocess Start
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KBLviewers window = new KBLviewers();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Data Preprocess End

	/**
	 * Create the application.
	 */
	public KBLviewers() {
		initialize();
	}
	
	final String BASE_RESOURCE_DIR = KBLviewers.class.getResource("").getPath();
	
	public String pmatch_rawdatatable_path_generator(String player_bnumber){
		return new String(BASE_RESOURCE_DIR + "player_profiles/KGC/pmatch_raw/"+ player_bnumber +".txt");
	}
	public String pinfo_rawdatatable_path_generator(String pinfo_teamname){
		return new String(BASE_RESOURCE_DIR + "team_profiles/"+pinfo_teamname+".txt");
	}
	
	
	public void pmatch_set_tablemodel(String query){
		ArrayList<pmatchDTO> pmatch_dataset = new ArrayList<pmatchDTO>();
		List<List<String>> pinfo_dataset = new ArrayList<List<String>>();
		
		// table model generation from DB table
		// pmatch db sample

		//String[] pmatch_table_label = new String[]{"날짜","대진팀","경기결과","Min.","PTS","3P","FT","OFF","DEF","REB","AST","STL","BLK","TO","PF"};
		
		if(query.equals("")){
			pmatch_dataset = new ArrayList<pmatchDTO>(pmatchdao.listAll());
		}else if(radio_button_selected.equals("search")){
			ArrayList<Integer> colnum = new ArrayList<Integer>();
			ArrayList<String> args = new ArrayList<String>();
			String[] subquery = query.split(",");
			String colnames = subquery[0];
			String colvalues = subquery[1];  
			
			System.out.println(colnames);
			System.out.println(colvalues);
			
			String[] col_names = colnames.split("\\s|\\t");
			String[] col_values = colvalues.split("\\s|\\t");
			System.out.println("=====");
			
			System.out.println("=====");
			for(String elem: col_names){
				System.out.print(elem + " ");
				colnum.add(pmatchdao.colreverse(elem));
			}
			
			for(int i=1;i<col_values.length;i++){
				System.out.println(col_values[i]);
				args.add(String.valueOf(col_values[i]));
			}
			try {
				/*pmatch_dataset = new ArrayList<pmatchDTO>(pmatchdao.select_mul(colnum, args));
				ArrayList<pmatchDTO> pmatch_datasets = new ArrayList<pmatchDTO>();*/
				pmatch_dataset = (ArrayList<pmatchDTO>)pmatchdao.select_mul(colnum, args).clone();
			} catch (SQLException e) {
				System.out.println("Error in set_table_model");
				e.printStackTrace();
			}
		}
		
		
		
		int pmatch_dataset_col_size = pmatch_dataset.get(0).getFieldsnum();
		int pmatch_dataset_row_size = pmatch_dataset.size();
		
		
		System.out.println(pmatch_dataset_col_size +","+ pmatch_dataset_row_size);
		Object[][] pmatch_table_dataval = new Object[pmatch_dataset_row_size][pmatch_dataset_col_size];
		
		// DB index matching
		int j=-1;
		int f=0;
		for(int i=0;i<pmatch_dataset_row_size;i++){
			for(Object elem : pmatch_dataset.get(i).return_DTO_as_Objectarr()){
				if(f==1){
					pmatch_table_dataval[i][j] = elem;
				}
				f=1;
				j+=1;
				}
			j=-1;
			f=0;
		}
		
		table.setModel(new DefaultTableModel(
			pmatch_table_dataval,
			pmatch_table_label
		));
	}
	
	public void pmatch_select_tablemodel(){
		System.out.println(table.getSelectedRow());
		
	}
	
	public void pmatch_set_playermodel(String pfile_path, DefaultCategoryDataset datasets){
		System.out.println("pmatch_set_playermodel() : ");
		System.out.println(pfile_path);
		
		frame.getContentPane().remove(panel_2); // !!!!!!!!!!!!!!!!!!!
		
		// update
		
		panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(395, 28, 564, 207);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
		player_profile_photo = new JLabel("profile image");
        player_profile_photo.setBounds(403, 15, 150, 188);
        panel_2.add(player_profile_photo);
        player_profile_photo.setIcon(new ImageIcon(pfile_path));
        
        player_profile_photo.setPreferredSize(new Dimension(200, 188));
        
        
        // Chart value
        
        player_stats_chart.removeAll();
        for(int i=0;i<dataset.getRowCount();i++){
        	dataset.removeRow(i);
        }
        DefaultCategoryDataset dataset = datasets;
         
        plot = new SpiderWebPlot(dataset);
         
        plot.setStartAngle(90);
         
        plot.setInteriorGap(0.20);
         
        plot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
         
        chart = new JFreeChart("", TextTitle.DEFAULT_FONT, plot, false);
         
        ChartUtilities.applyCurrentTheme(chart);
        dataset = (DefaultCategoryDataset) plot.getDataset();
      

        player_stats_chart = new ChartPanel(chart);
        player_stats_chart.setBounds(168, 15, 223, 188);
        panel_2.add(player_stats_chart);
        plot = (SpiderWebPlot) player_stats_chart.getChart().getPlot();
        player_stats_chart.setPreferredSize(new Dimension(200, 200));
        
        //JOptionPane.showMessageDialog(frame, "playemodel");
	}
	
	/*
	public void pmatch_set_playermodel(JFreeChart chart, SpiderWebPlot plot){
		// TODO: 1. if 'pbnum' is in query -> query value -> find file, show
		// TODO: 2. table column selected -> team, pbnum -> show.
		
		File paths = new File("");
		// kblviewer.class.getResource("").getPath() : /C:/Users/Joo/Desktop/multicamp/bin/multicamp/
		String team_name = "KGC";
		String player_bnumber = "c_32";
		
		String pfile_path = BASE_RESOURCE_DIR+"player_profiles/"+team_name+"/photos/"
				+ player_bnumber +".jpg";
		System.out.println(pfile_path);
		String str2 = "C:/Users/Joo/Desktop/multicamp/bin/multicamp/player_profiles/KGC/c_32.jpg";
		
		       
        
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(395, 28, 564, 207);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        ChartPanel player_stats_chart = new ChartPanel(chart);
        player_stats_chart.setBounds(168, 15, 223, 188);
        panel_2.add(player_stats_chart);
        plot = (SpiderWebPlot) player_stats_chart.getChart().getPlot();
        player_stats_chart.setPreferredSize(new Dimension(200, 200));
        JLabel player_profile_photo = new JLabel("profile image");
        player_profile_photo.setBounds(403, 15, 150, 188);
        panel_2.add(player_profile_photo);
        player_profile_photo.setIcon(new ImageIcon(pfile_path));
        
        player_profile_photo.setPreferredSize(new Dimension(200, 188));
        
	}
	*/
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1009, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		// DataSet Table Start
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(32, 264, 927, 266);
		frame.getContentPane().add(scrollPane);
		
        
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setColumnSelectionAllowed(true);
		table.setShowGrid(false);
		table.setCellSelectionEnabled(true);
		
		
		DefaultTableModel table_model = (DefaultTableModel) table.getModel();
		//table.getModel().getRowCount(0) // table refresh
		//table_model.fireTableDataChanged(); // Whole datatable refresh
		//table_model.fireTableCellUpdated(row, column); // cell update
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			int trial=0;
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				//if(trial == 0){
				String tabledate = table.getValueAt(table.getSelectedRow(), 0).toString();
				// shooting
				String tablepts = table.getValueAt(table.getSelectedRow(),4).toString();
				String tableast = table.getValueAt(table.getSelectedRow(),10).toString();
				// defense
				String tablestl = table.getValueAt(table.getSelectedRow(),11).toString();
				String tableblk = table.getValueAt(table.getSelectedRow(),12).toString();
				tablepbnum = table.getValueAt(table.getSelectedRow(), 15).toString();
				
				
				
				
				
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		        dataset.addValue((Integer.parseInt(tablepts) % 5), series1, category1);
		        dataset.addValue((Integer.parseInt(tableast) % 5), series1, category2);
		        dataset.addValue((Integer.parseInt(tablestl) % 5), series1, category3);
		        dataset.addValue((Integer.parseInt(tableblk) % 5), series1, category4);
		        dataset.addValue((Integer.parseInt(tableast) % 5), series1, category5);
		 		
				
				System.out.println(tabledate + " " + tablepbnum + " " + tablepts + " " + tablestl);
				trial+=1;
				
				String team_name = "KGC";
				String pfile_path = BASE_RESOURCE_DIR+"team_logo/"
						+ "KGC인삼공사" +".png";
				if(tablepbnum != null){
					pfile_path = BASE_RESOURCE_DIR+"player_profiles/"+team_name+"/photos/"
							+ tablepbnum +".jpg";
				}
				pmatch_set_playermodel(pfile_path, dataset);
				//}
			}
		});
		
		
		
		/*
		// pinfo db sample
		String[] pinfo_table_label = new String[]{"성명","생년월일","신장","소속","포지션","배번"};
	
		int pinfo_dataset_col_size = pinfo_dataset.get(0).size();
		int pinfo_dataset_row_size = pinfo_dataset.size();
		System.out.println(pinfo_dataset_col_size +","+ pinfo_dataset_row_size);
		Object[][] pinfo_table_dataval = new String[pinfo_dataset_row_size][pinfo_dataset_col_size];
		
		int colnum=0;
		int rownum=0;
		for(List<String> rowelem : pinfo_dataset){
			for(String rowcolelem : pinfo_dataset.get(rownum) ){
				//System.out.println("forloop:" + rownum+","+colnum);
				pinfo_table_dataval[rownum][colnum] = rowcolelem;
				colnum+=1;
			}
			colnum=0;
			rownum+=1;
		}
		
	
		table.setModel(new DefaultTableModel(
			pinfo_table_dataval,
			pinfo_table_label
		));
	*/	
		// DataSet Table End
		
		// Search DashBoard Start
		
        JPanel SearchPanel = new JPanel();
        SearchPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        SearchPanel.setBounds(32, 28, 346, 207);
        frame.getContentPane().add(SearchPanel);
        SearchPanel.setLayout(null);
        
        JButton executeButton = new JButton("execute");
        executeButton.setBounds(232, 15, 97, 23);
        SearchPanel.add(executeButton);
        
        final JTextPane searchtf = new JTextPane();
        searchtf.setBounds(17, 15, 185, 23);
        SearchPanel.add(searchtf);
            
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(144, 130, 185, 62);
        SearchPanel.add(panel_1);
        panel_1.setLayout(null);
        
        JRadioButton rdbtnSearch = new JRadioButton("search");
        rdbtnSearch.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		System.out.println("search radio selected.");
        		radio_button_selected = "search";
        	}
        });
        rdbtnSearch.setBounds(0, 0, 89, 29);
        panel_1.add(rdbtnSearch);
        
        JRadioButton rdbtnDelete = new JRadioButton("delete");
        rdbtnDelete.setBounds(96, 0, 89, 29);
        rdbtnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("delete radio selected.");
        		radio_button_selected = "delete";
        	}
        });
        panel_1.add(rdbtnDelete);
        
        JRadioButton rdbtnUpdate = new JRadioButton("update");
        rdbtnUpdate.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("update radio selected.");
        		radio_button_selected = "update";
        	}
        });
        rdbtnUpdate.setBounds(0, 33, 89, 29);
        panel_1.add(rdbtnUpdate);
        
        JRadioButton rdbtnInsert = new JRadioButton("insert");
        rdbtnInsert.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("insert radio selected.");
        		radio_button_selected = "insert";
        	}
        });
        rdbtnInsert.setBounds(96, 33, 89, 29);
        panel_1.add(rdbtnInsert);
        
        

		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rdbtnSearch);
		bg1.add(rdbtnDelete);
		bg1.add(rdbtnUpdate);
		bg1.add(rdbtnInsert);
        
        
		String[] vsteam_name = new String[] {"동부프로미", "모비스피버스",
                "삼성썬더스", "SK나이츠", "LG세이커스", "오리온오리온스", "전자랜드엘리펀츠", "KCC이지스", "KGC인삼공사", "KT소닉붐"};
		String[] vsteam_name_w = new String[] {"-","원주동부", "울산모비스",
                "서울삼성", "서울SK", "창원LG", "고양오리온", "인천전자랜드", "전주KCC", "안양KGC", "부산KT"};

		

        JComboBox myteam_comboBox = new JComboBox(vsteam_name_w);
        myteam_comboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {

                JComboBox comboBox = (JComboBox) event.getSource();
                
                Object selected = comboBox.getSelectedItem();
                if(selected.toString().equals("안양KGC")){
                	System.out.println("안양KGC 선택");
                	//String player_name = JOptionPane.showInputDialog(frame,"Put player name", null);
                	myteam_name_selected = "안양KGC";
                	//System.out.println(player_name);
                }
                else if(selected.toString().equals(""))
                	System.out.println("second");
        		myteam_flag = true;
        	}
        });
        myteam_comboBox.setBounds(17, 76, 140, 27);
        SearchPanel.add(myteam_comboBox);
        
		
        JComboBox vsteam_comboBox = new JComboBox(vsteam_name_w);
        vsteam_comboBox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		if(myteam_flag == true){
        			JComboBox comboBox = (JComboBox) event.getSource();
        			System.out.println("상대팀 선택 : ");
                    Object selected = comboBox.getSelectedItem();
                    if(selected.toString().equals("원주동부")){
                    	vsteam_name_selected = "원주동부";
                    	System.out.println("원주동부");
                    }else if(selected.toString().equals("울산모비스")){
                    	vsteam_name_selected = "울산모비스";
                    	System.out.println("울산모비스");
                    }else if(selected.toString().equals("서울삼성")){
                    	vsteam_name_selected = "서울삼성";
                    	System.out.println("서울삼성");
                    }else if(selected.toString().equals("원주동부")){
                    	vsteam_name_selected = "원주동부";
                    	System.out.println("원주동부");
                    }else if(selected.toString().equals("서울SK")){
                    	vsteam_name_selected = "서울KS";
                    	System.out.println("서울SK");
                    }else if(selected.toString().equals("창원LG")){
                    	vsteam_name_selected = "창원LG";
                    	System.out.println("창원LG");
                    }else if(selected.toString().equals("고양오리온")){
                    	vsteam_name_selected = "고양오리온";
                    	System.out.println("고양오리온");
                    }else if(selected.toString().equals("인천전자랜드")){
                    	vsteam_name_selected = "인천전자랜드";
                    	System.out.println("인천전자랜드");
                    }else if(selected.toString().equals("전주KCC")){
                    	vsteam_name_selected = "전주KCC";
                    	System.out.println("전주KCC");
                    }else if(selected.toString().equals("안양KGC")){
                    	vsteam_name_selected = "안양KGC";
                    	System.out.println("안양KGC");
                    }else if(selected.toString().equals("부산KT")){
                    	vsteam_name_selected = "부산KT";
                    	System.out.println("부산KT");
                    }
                    if(vsteam_name_selected.equals(myteam_name_selected)){
                    	JOptionPane.showMessageDialog(frame, "다른 팀을 선택해 주세요.");
                    	vsteam_name_selected = null;
                    }
        		}
        	}
        });
        vsteam_comboBox.setBounds(189, 76, 140, 27);
        
        SearchPanel.add(vsteam_comboBox);
        
        String[] dbtable_name = new String[] {"-","pinfo", "pmatch", "gteam", "steam"};
        JComboBox db_comboBox_1 = new JComboBox(dbtable_name);
        db_comboBox_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
    		
    			dbtable_flag = true;
                JComboBox comboBox = (JComboBox) event.getSource();

                Object selected = comboBox.getSelectedItem();
                if(selected.toString().equals("pinfo")){
                	//String player_name = JOptionPane.showInputDialog(frame,"Put player name", null);
                	//System.out.println(player_name);
                	// TODO: PINFO TABLE SET
                	//pinfo_set_tablemodel();
                	dbtable_name_selected = "pinfo";
                	System.out.println("dbtable name : " + dbtable_name_selected);
                }else if(selected.toString().equals("pmatch")){
                	//pmatch_set_tablemodel("");
                	dbtable_name_selected = "pmatch";
                	System.out.println("dbtable name : " + dbtable_name_selected);
                }else if(selected.toString().equals("gteam")){
                	//pmatch_set_tablemodel("");
                	dbtable_name_selected = "gteam";
                	System.out.println("dbtable name : " + dbtable_name_selected);
                }else if(selected.toString().equals("steam")){
                	//pmatch_set_tablemodel("");
                	dbtable_name_selected = "steam";
                	System.out.println("dbtable name : " + dbtable_name_selected);
	                
        		}
        	}
        });
        db_comboBox_1.setBounds(17, 155, 110, 27);
        SearchPanel.add(db_comboBox_1);
        
        JLabel lblMyTeam = new JLabel("My team");
        lblMyTeam.setBounds(17, 53, 78, 21);
        SearchPanel.add(lblMyTeam);
        
        JLabel lblNewLabel = new JLabel("vs.");
        lblNewLabel.setBounds(189, 53, 78, 21);
        SearchPanel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("\uAC80\uC0C9\uC635\uC158");
        lblNewLabel_1.setBounds(17, 130, 78, 21);
        SearchPanel.add(lblNewLabel_1);
        
        // Search DashBoard End
        
        // Player DashBoard Start
        
        dataset = new DefaultCategoryDataset();
        dataset.addValue(5.0, series1, category1);
        dataset.addValue(5.0, series1, category2);
        dataset.addValue(5.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);
 
        dataset.addValue(2.0, series2, category1);
        dataset.addValue(4.0, series2, category2);
        dataset.addValue(5.0, series2, category3);
        dataset.addValue(3.0, series2, category4);
        dataset.addValue(5.0, series2, category5);
   
         
        plot = new SpiderWebPlot(dataset);
         
        plot.setStartAngle(90);
         
        plot.setInteriorGap(0.20);
         
        plot.setToolTipGenerator(new StandardCategoryToolTipGenerator());
         
        JFreeChart chart = new JFreeChart("", TextTitle.DEFAULT_FONT, plot, false);
         
        ChartUtilities.applyCurrentTheme(chart);
        dataset = (DefaultCategoryDataset) plot.getDataset();
 
        ////////////////////////////////////
        
		File paths = new File("");
		// kblviewer.class.getResource("").getPath() : /C:/Users/Joo/Desktop/multicamp/bin/multicamp/
		String team_name = "KGC";
		String player_bnumber = "c_32";
		// default image
		String pfile_path = BASE_RESOURCE_DIR+"player_profiles/"+team_name+"/photos/"
				+ player_bnumber +".jpg";
		if(tablepbnum != null){
			pfile_path = BASE_RESOURCE_DIR+"player_profiles/"+team_name+"/photos/"
					+ tablepbnum +".jpg";
		}
		System.out.println(pfile_path);
		
		
		
		//CURR
        panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_2.setBounds(395, 28, 564, 207);
        frame.getContentPane().add(panel_2);
        panel_2.setLayout(null);
        
        player_stats_chart = new ChartPanel(chart);
        player_stats_chart.setBounds(168, 15, 223, 188);
        panel_2.add(player_stats_chart);
        plot = (SpiderWebPlot) player_stats_chart.getChart().getPlot();
        player_stats_chart.setPreferredSize(new Dimension(200, 200));
        
        /*
        player_profile_photo = new JLabel("profile image");
        player_profile_photo.setBounds(403, 15, 150, 188);
        panel_2.add(player_profile_photo);
        player_profile_photo.setIcon(new ImageIcon(pfile_path));
        
        player_profile_photo.setPreferredSize(new Dimension(200, 188));
        */
        /////////////////////////////////////////////////
        JPanel player_profile_basic = new JPanel();
        player_profile_basic.setBounds(17, 15, 134, 188);
        panel_2.add(player_profile_basic);
        executeButton.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		String querysets=searchtf.getText(); 
        		//String name = JOptionPane.showInputDialog(frame,"searchquery?"+querysets, null);
        		System.out.println("querysets"
        				+ " : "+querysets);
        		if(radio_button_selected == null){
        			JOptionPane.showMessageDialog(frame, "검색 옵션을 선택해 주세요.");
        		}else if(dbtable_name_selected == null){
            			JOptionPane.showMessageDialog(frame, "DB 검색 옵션을 선택해 주세요.");
        		}else if(radio_button_selected.equals("search")){
        			System.out.println("Query to DB with search option..");
        			pmatch_set_tablemodel(querysets);
        		}
        	}
        		
        });
        executeButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        	}
        });
		
		
        // Player DashBoard end
		
		
		
		
		
		
		
		
		
		/*
		Browser browser = new Browser();
        BrowserView view = new BrowserView(browser);
        
        view.setBounds(431, 49, 150, 188);
        view.setPreferredSize(new Dimension(200, 188));
        frame.getContentPane().add(view);
*/        //browser.loadHTML("<html><body><h1>Hello World!</h1></body></html>");
		
	}
}
