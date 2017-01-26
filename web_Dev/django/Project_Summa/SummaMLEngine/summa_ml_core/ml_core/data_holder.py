import matplotlib.pyplot as plt
plt.style.use('ggplot')
import seaborn as sns
import pandas as pd
import numpy as np
import os
import glob


class data_holder:
	"""
		dataset : indicates individual datafile
		datatable : 2 dimensional table with indexes and columns,
			columns contains categorical and continuous variables as features
			indexs contains number of samples

		self.datatable_names : only contains after read_data() 
				basename of the table. left out './raw_data/' 
	"""
	import pandas as pd
	import numpy as np
	import os
	import glob
	def __init__(self, *args):
		self.datatable_names = list(args)
		self.dataset_num=0
		self.datatables = []
		print('='*50)
		print('data_holder is created..')
		print()
		for _ in args:
			self.dataset_num += 1
		print("number of data sets : ", self.dataset_num)
		print("datatable_names : ", self.datatable_names)


	def inspect_data_integrity(self, table, table_name):
		print('Table contains NaN value : ',np.any(table.isnull()),
			)


	def read_data(self):
		"""
		read data
		currently implemented only for csv file
		in ./raw_data directory
		"""
		# select all dataset in ./raw_data
		if self.dataset_num == 0:
			path = os.path.join('raw_data'+'/*.csv')
			datatable_names = glob.glob(path)
			for table_name in datatable_names:
				# table name registration
				table_base_name = os.path.basename(table_name)
				self.datatable_names.append(os.path.basename(table_base_name))
				# table data registration
				df = pd.read_csv(table_name, sep=';', na_values='.')
				self.datatables.append(df)


	def col_exclude(self, table, ex_col_names):
		return table

	
	def trim_data(self, datasets):
		"""
		Trim datasets. NaN
		"""
		return 1

	def categorize_data(self, table_name):
		"""
		differentiate data with categorical and continuous data columns

		input : (self, table_name):
		return : categorical_data_columns, continuous_data_columns	
		"""
		categorical_data_columns=[]
		continuous_data_columns=[]
		table_index = self.datatable_names.index(str(table_name))
		colnames = self.datatables[table_index].columns
		print(colnames)	
		for colname in colnames:
			if 'object' == self.datatables[table_index][colname].dtype:
				categorical_data_columns.append(colname)
			else:
				continuous_data_columns.append(colname)
		
		return categorical_data_columns, continuous_data_columns

	
	def print_data_table_info(self, table_name):		
		table_index = self.datatable_names.index(str(table_name))
		table = self.datatables[table_index]

		print(table_index, table_name)
		print('='*50)
		print("table name : ", str(table_name))
		print("table shape : ", table.shape)
		print("table info : "), table.info()
		print("table columns : \n", table.columns)
		print("table index : \n", table.index)
		print("table head : \n", table.head())
	
		if table.shape[1] <= 30:
			print('='*50)
			print("column-wise infos .. ")

			categorical_data_columns, continuous_data_columns = self.categorize_data(table_name)			
			print("categorical features : ", categorical_data_columns)

			for cat_col in categorical_data_columns:
				uniq = np.unique(table[cat_col])
				print('-'*50)
				print('# col {}\t unique_num {}\t unique_val {}\t'.format(
							cat_col, len(uniq), uniq))
			
			print("continuous features : ", continuous_data_columns)
			print(table.describe())
		print('='*50)
				
			

	def print_data_table_statistic_info(self, table_name):
		"""
			print out basic statistic info of the table
		"""
		table_index = self.datatable_names.index(str(table_name))
		print("table correlation : ", self.datatables[table_index].corr())
