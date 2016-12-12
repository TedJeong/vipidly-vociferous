#-*- coding: utf-8 -*-
from pywinauto import application
from pywinauto import timings
import pandas as pd
import time
import os
import sys
from PyQt4.QtCore import *
from PyQt4.QtGui import *
from PyQt4.QAxContainer import *


#<type str>(한글) -> <class 'PyQt4.QtCore.QString'>(unicode)
try:
    _fromUtf8 = QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

# 한글 label 등을 PyQt object에 넣어줌
# MainWindow.setObjectName(_fromUtf8("MainWindow")) 와 같이 사용됨
try:
    _encoding = QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QApplication.translate(context, text, disambig)

class Kiwoom(QAxWidget):

    def __init__(self):
        super(Kiwoom,self).__init__()
        self.setControl("KHOPENAPI.KHOpenAPICtrl.1")
        self.connect(self,SIGNAL("OnEventConnect(int)"),self.OnEventConnect)
        self.connect(self, SIGNAL("OnReceiveTrData(QString, QString, QString, QString, QString, int, QString, \
                                   QString, QString)"), self.OnReceiveTrData)
        self.connect(self,SIGNAL("OnReceiveChejanData(QString,int,QString)"),self.OnReceiveChejanData)

        self.prev_next=None
        self.data_opw00001="9999"
        self.data_opw00018 = {'single': [], 'multi': []}
        self.ohlcv = {}
    def LoginInfo(self):
        # 계좌가 여러개인 경우가 있으므로 delim ; 을 떨어뜨려 준다.
        return self.dynamicCall("GetLoginInfo(QString)",["ACCNO"]).rstrip(';')

    def GetLoginInfo(self,sTag):
        cmd = 'GetLoginInfo(%s)' % sTag
        ret = self.dynamicCall(cmd)
        return ret

    def Login(self):
        app = application.Application()
        #app.start("C:/Kiwoom/KiwoomFlash2/khministarter.exe")

        s = '번개'
        #title = s.decode('utf-8').encode('utf-8')+" Login"
        reload(sys)
        sys.setdefaultencoding('utf-8')
        title = _fromUtf8(s)+' Login'

        #sys.setdefaultencoding('ascii')
        dlg = timings.WaitUntilPasses(20, 0.5, lambda: app.window_(title=title))

        time.sleep(2)

        #id_ctrl = dlg.Edit1
        #id_ctrl.SetFocus()
        #id_ctrl.TypeKeys('wngur523')

        pass_ctrl = dlg.Edit2
        pass_ctrl.SetFocus()
        pass_ctrl.TypeKeys('dldu05')

        #cert_ctrl = dlg.Edit3
        #cert_ctrl.SetFocus()
        #cert_ctrl.TypeKeys('tkfkddldu0523!')

        btn_ctrl = dlg.Button0
        btn_ctrl.Click()

        #time.sleep(50)
        #os.system("taskkill /im khmini.exe")
    def CommConnect(self):
        self.dynamicCall("CommConnect()")
                
        self.login_event_loop = QEventLoop()
        self.login_event_loop.exec_()
    def OnEventConnect(self,errCode):
        if errCode == 0:
            print("connected")
        else:
            print("disconnected")
        self.login_event_loop.exit()
    def SetInputValue(self, sID, sValue):
        self.dynamicCall("SetInputValue(QString, QString)", sID, sValue)
    def CommRqData(self, sRQName, sTRCode, nPrevNext, sScreenNo):
        self.dynamicCall("CommRqData(QString, QString, int, QString)", sRQName, sTRCode, nPrevNext, sScreenNo)
        self.tr_event_loop = QEventLoop()
        self.tr_event_loop.exit()
        """
        try:
            self.tr_event_loop.exit()
        except AttributeError:
            print "commrqdata exception called"
            pass
        """
        """
        while True:
            if self.tr_event_loop.isRunning():
                self.tr_event_loop.exec_()
                break
            pass
        return
        """
    def CommGetData(self, sJongmokCode, sRealType, sFieldName, nIndex, sInnerFiledName):

        # TrCode,RecordName,nIndex,ItemName
        data = self.dynamicCall("CommGetData(QString, QString, QString, int, QString)", sJongmokCode, sRealType,
                                sFieldName, nIndex, sInnerFiledName)
        #print "####################################",data.type
        try:
            data=str(data.toString())
        except:
            return data.toString()
        #print "CommGetData dynamiccall data : "+ data
        return data
    def init_opw00018_data(self):
        self.data_opw00018 = {'single':[],'multi':[]}

    def OnReceiveTrData(self, ScrNo, RQName, TrCode, RecordName, PrevNext, DataLength, ErrorCode, Message, SplmMsg):
        self.prev_next = PrevNext
        if RQName == "opw00001_req": # 예수금상세현황요청
            estimated_day2_deposit = self.CommGetData(TrCode,"",RQName,0,"d+2"+_fromUtf8("추정예수금"))
            #print "OnRecieveTrData esitmated_day2_deposit : "+estimated_day2_deposit
            #(ex) estimated_day2_deposit = ‘000000092313926’
            estimated_day2_deposit = self.change_format(estimated_day2_deposit)
            #print "OnRecieveTrData esitmated_day2_deposit after changed_format : "+estimated_day2_deposit
            self.data_opw00001 = estimated_day2_deposit
            #print "OnRecieveTrData data_opw0001 : " + self.data_opw00001


        if RQName == "opw00018_req": # 계좌평가잔고내역요청
            # Single Data
            single = []
            total_purchase_price = self.CommGetData(TrCode,"",RQName,0,_fromUtf8("총매입금액"))
            total_purchase_price = self.change_format(total_purchase_price)
            single.append(total_purchase_price)

            total_eval_price = self.CommGetData(TrCode,"",RQName,0,_fromUtf8("총평가금액"))
            total_eval_price = self.change_format(total_eval_price)
            single.append(total_eval_price)


            total_eval_profit_loss_price = self.CommGetData(TrCode, "", RQName, 0, _fromUtf8("총평가손익금액"))
            total_eval_profit_loss_price = self.change_format(total_eval_profit_loss_price)
            single.append(total_eval_profit_loss_price)


            total_earning_rate = self.CommGetData(TrCode, "", RQName, 0, _fromUtf8("총수익률(%)"))
            total_earning_rate = self.change_format(total_earning_rate,1)
            single.append(total_earning_rate)

            estimated_deposit = self.CommGetData(TrCode,"",RQName,0,_fromUtf8("추정예탁자산"))
            estimated_deposit = self.change_format(estimated_deposit)
            single.append(estimated_deposit)

            #print "OnRceiveTrDatas :" , total_purchase_price, total_eval_price, total_earning_rate,estimated_deposit

            self.data_opw00018['single'] = single
            #print self.data_opw00018['single']

            # Multi Data
            cnt = self.GetRepeatCnt(TrCode, RQName)
            for i in range(cnt):
                data = []

                item_name = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("종목명"))
                data.append(item_name)

                quantity = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("보유수량"))
                quantity = self.change_format(quantity)
                data.append(quantity)

                purchase_price = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("매입가"))
                purchase_price = self.change_format(purchase_price)
                data.append(purchase_price)

                current_price = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("현재가"))
                current_price = self.change_format(current_price)
                data.append(current_price)

                eval_profit_loss_price = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("평가손익"))
                eval_profit_loss_price = self.change_format(eval_profit_loss_price)
                data.append(eval_profit_loss_price)

                earning_rate = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("수익률(%)"))
                earning_rate = self.change_format(earning_rate, 2)
                data.append(earning_rate)

                self.data_opw00018['multi'].append(data)
            #print self.data_opw00018['multi']

        self.sets(self.data_opw00001,self.data_opw00018)

        if RQName == "opt10081_req": #
            cnt = self.GetRepeatCnt(TrCode, RQName)

            for i in range(cnt):
                date = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("일자"))
                open = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("시가"))
                high = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("고가"))
                low  = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("저가"))
                close  = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("현재가"))
                volume = self.CommGetData(TrCode, "", RQName, i, _fromUtf8("거래량"))
                #print date, self.ohlcv['date'], self.ohlcv
                self.ohlcv['date'].append(date)

                self.ohlcv['open'].append(int(open))
                self.ohlcv['high'].append(int(high))
                self.ohlcv['low'].append(int(low))
                self.ohlcv['close'].append(int(close))
                self.ohlcv['volume'].append(int(volume))
        #self.tr_event_loop.exit()

        try:
            self.tr_event_loop.exit()
        except AttributeError:
            pass

    def prin(self):
        print self.data_opw00001,self.data_opw00018
    def sets(self,data1,data2):
        self.data_opw00001=data1
        self.data_opw00018=data2
    def GetRepeatCnt(self, sTrCode, sRecordName):
        ret = self.dynamicCall("GetRepeatCnt(QString, QString)", sTrCode, sRecordName)
        return int(ret.toInt()[0])
    def GetCodeListByMarket(self,sMarket):
        cmd = 'GetCodeListByMarket("%s")' % sMarket
        ret = self.dynamicCall(cmd)
        ret = str(ret.toString())
        item_codes = ret.split(';')
        return item_codes
    # Input:CodeNumber ; Output:CodeName
    def GetMasterCodeName(self,strCode):
        cmd = 'GetMasterCodeName("%s")' % strCode
        ret = self.dynamicCall(cmd)
        return ret
    def SendOrder(self,sRQName,sScreenNo,sAccNo,nOrderType,sCode,nQty,nPrice,sHogaGb,sOrgOrderNo):
        self.dynamicCall("SendOrder(self, sRQName, sScreenNo, sAccNo, nOrderType, sCode, nQty, nPrice, sHogaGb, sOrgOrderNo)",[sRQName,sScreenNo,sAccNo,nOrderType,sCode,nQty,nPrice,sHogaGb,sOrgOrderNo])
    def GetChejanData(self,nFid):
        cmd = 'GetCheJanData("%s")' % nFid
        ret = self.dynamicCall(cmd)
        return ret
    def OnReceiveChejanData(self,sGubun,nItemCnt,sFidList):
        print ("sGubun: ", sGubun)
        """
        9203    주문번호
        302     종목명
        900     주문수량
        901     주문가격
        902     미체결수량
        910     체결가
        911     체결량
        10      현재가,체결가,실시간종가
        """
        print("주문번호 : ", self.GetChejanData(9203))
        print("종목명 : ", self.GetChejanData(302))
        print("주문수량 : ", self.GetChejanData(900))
        print("주문가격 : ", self.GetChejanData(901))
    def GetConnectState(self):
        ret = self.dynamicCall("GetConnectState()")
        return ret
    def InitOHLCRawData(self):
        self.ohlc = {'date': [], 'open': [], 'high': [], 'low': [], 'close': []}
    def InitOHLCVRawData(self):
        self.ohlcv = {'date': [], 'open': [], 'high': [], 'low': [], 'close': [], 'volume': []}

    def change_format(self,data, percent = 0):
        is_minus_sign = False
        if data[0]=='-':
            is_minus_sign = True
        strip_str = data.lstrip('-0')
        if strip_str == '':
            if percent == 1:
                return '0.00'
            else:
                return '0'
        if percent == 1:
            strip_data = int(strip_str)
            strip_data = strip_data/100
            form = format(strip_data,',.2f')
        elif percent == 2:
            strip_data = float(strip_str)
            form = format(strip_data,',.2f')
        else:
            strip_data = int(strip_str)
            form = format(strip_data, ',d')

        if form.startswith('.'):
            form = '0'+form
        if is_minus_sign:
            form = '-'+form
        return form



if __name__ == "__main__":
    app = QApplication(sys.argv)

    kiwoom = Kiwoom()
    kiwoom.CommConnect()
    kiwoom.InitOHLCRawData()

    # TR
    """
    kiwoom.SetInputValue(_fromUtf8("종목코드")), "039490")
    kiwoom.SetInputValue(_fromUtf8("기준일자")), "20160624")
    kiwoom.SetInputValue(_fromUtf8("수정주가구분")), 1)
    kiwoom.CommRqData("opt10081_req", "opt10081", 0, "0101")

    while kiwoom.prev_next == '2':
        time.sleep(0.2)
        kiwoom.SetInputValue(_fromUtf8("종목코드")), "039490")
        kiwoom.SetInputValue(_fromUtf8("기준일자")), "20160624")
        kiwoom.SetInputValue(_fromUtf8("수정주가구분")), 1)
        kiwoom.CommRqData("opt10081_req", "opt10081", 2, "0101")

    df = pd.DataFrame(kiwoom.ohlc, columns=['open', 'high', 'low', 'close'], index=kiwoom.ohlc['date'])
    print(df.head())
    print "finished"
    #con = sqlite3.connect("c:/Users/Joo/stock.db")
    #df.to_sql('039490', con, if_exists='replace')
    """
    kiwoom = Kiwoom()
    kiwoom.CommConnect()

    # opw00018
    kiwoom.init_opw00018_data()
    account = kiwoom.GetLoginInfo(("ACCNO")).rstrip(';')
    kiwoom.SetInputValue("계좌번호", account)

    kiwoom.SetInputValue("비밀번호", "dldu05")
    kiwoom.CommRqData("opw00018_req", "opw00018", 0, "2000")

    while kiwoom.prev_next == '2':
        time.sleep(0.2)
        kiwoom.SetInputValue("계좌번호", account)
        kiwoom.SetInputValue("비밀번호", "dldu05")
        kiwoom.CommRqData("opw00018_req", "opw00018", 2, "2000")

    print(kiwoom.data_opw00018['single'])
    print(kiwoom.data_opw00018['multi'])