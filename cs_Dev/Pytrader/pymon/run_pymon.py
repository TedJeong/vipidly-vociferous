#-*- coding: utf-8 -*-

import sys
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4.QAxContainer import *
sys.path.append("C:\Users\Joo\Desktop\porfolio\PythonSystemTrading")
import kiwoom
import time
import pandas as pd

MARKET_KOSPI = 0
MARKET_KOSDAK = 10

class PyMon:
    def __init__(self):
        self.kiwoom = kiwoom.Kiwoom()
        self.kiwoom.CommConnect()
        self.get_code_list()

    def get_code_list(self):
        self.kospi_codes = self.kiwoom.GetCodeListByMarket(MARKET_KOSPI)
        self.kosdak_codes = self.kiwoom.GetCodeListByMarket(MARKET_KOSDAK)

    def run(self):
        print("###run!!")
        print(self.kospi_codes[0:4])
        print(self.kosdak_codes[0:4])

        print(self.get_ohlcv("039490","20161022")) # 키움증권 종목코드


    def get_ohlcv(self,code,start_date):
        # Init data structure
        self.kiwoom.InitOHLCVRawData()
        # Request TR and get data
        self.kiwoom.SetInputValue(kiwoom._fromUtf8("종목코드"), code)
        self.kiwoom.SetInputValue(kiwoom._fromUtf8("기준일자"), start_date)
        self.kiwoom.SetInputValue(kiwoom._fromUtf8("수정주가구분"), 1)
        self.kiwoom.CommRqData("opt10081_req","opt10081",0,"0101")
        time.sleep(0.2)

        df = pd.DataFrame(self.kiwoom.ohlcv,columns=['open','high','low','close','volume'],index=self.kiwoom.ohlcv['date'])
        return df

    def calculate_estimated_dividend_to_treasury(self, code, ind):
        """
        배당률 기반 투자 알고리즘
        국채시가배당률 = 현금배당수익률 / 3년만기국채수익률
        현금배당수익률( %) = 100 x 주가배당금 / 주가
        """
        import webreader

        if ind == 0 :
            estimated_dividend_yield = float(webreader.get_dividend_yield(code))
        elif ind == 1 :
            estimated_dividend_yield = float(webreader.get_estimated_dividend_yield(code))
        if pd.isnull(estimated_dividend_yield):
            estimated_dividend_yield = float(webreader.get_dividend_yield(code))
        current_3year_treasury = float(webreader.get_current_3year_treasury())
        estimated_dividend_to_treasury = estimated_dividend_yield / current_3year_treasury
        return estimated_dividend_to_treasury

    def buy_check_by_dividend_algorithm(self,code):
        import webreader
        estimated_dividend_to_treasury = self.calculate_estimated_dividend_to_treasury(code,1)
        (min_ratio,max_ratio) = webreader.get_min_max_dividend_to_treasury(code)

        if estimated_dividend_to_treasury > max_ratio:
            return (1, estimated_dividend_to_treasury)
        else:
            return (0, estimated_dividend_to_treasury)

    def run_dividend(self):
        buy_list=[]
        for code in self.kospi_codes[:50]: # first 50 items
            time.sleep(0.5) # naver server limit
            ret = self.buy_check_by_dividend_algorithm(code)
            if ret[0] == 1:
                buy_list.append((code,ret[1]))

        sorted_list = sorted(buy_list, key=lambda code: code[1], reverse=True)
        print(sorted_list)

        # Buy list
        buy_list = []
        for i in range(0, 5):
            code = sorted_list[i][0]
            buy_list.append(code)

        self.update_buy_list(buy_list)

if __name__ == "__main__":
    app = QApplication(sys.argv)
    pymon = PyMon()

    #pymon.run()
    import sys
    reload(sys)
    sys.setdefaultencoding('utf-8')
    import warnings
    warnings.filterwarnings("ignore")
    print 'naver '+ '기업현황 상단 현금배당수익률기반 : ', pymon.calculate_estimated_dividend_to_treasury('058470',0)
    print 'naver '+ '기업재무재표기준 예상현금배당수익률기반 : ', pymon.calculate_estimated_dividend_to_treasury('058470',1)
    pymon.run_dividend()


