package data.preprocess;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatasetGenerator {
	public List<List<String>> dataset = new ArrayList<List<String>>();
	public ArrayList<String> row = new ArrayList<String>();
	
	public List<List<String>> getDataset() {
		return dataset;
	}

	public void setDataset(List<List<String>> dataset) {
		this.dataset = dataset;
	}

	public ArrayList<String> getRow() {
		return row;
	}

	public void setRow(ArrayList<String> row) {
		this.row = row;
	}

	public DatasetGenerator() {
		// TODO Auto-generated constructor stub
	}
	
	public List<List<String>> return_data_set(String absolutefilepath) throws IOException{
		FileReader fr = new FileReader(absolutefilepath);
		BufferedReader br = new BufferedReader(fr);
		String s = null;
		int lines = 0;
		while((s=br.readLine()) != null){
			System.out.println(s);
			row = new ArrayList<String>();
			for(String elem:s.split("\\t")){
				row.add(elem);
			}
			this.dataset.add(row);
		}
	
		fr.close();br.close();
		return this.dataset;
	}
	
	public void print_dataset(){

		int rowsize = this.dataset.size();
		int columnsize = this.dataset.get(0).size();
		
		for(int i=0;i<rowsize;i++){
			for(int j=0;j<this.dataset.get(i).size();j++){
				System.out.print(this.dataset.get(i).get(j) + " ");
			}
			System.out.println("");
		}
	}
	
	
}
