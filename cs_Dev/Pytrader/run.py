#-*- coding: utf-8 -*-

import sys
import time
#import sip
#sip.setapi('QVariant', 2)
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4 import uic
from kiwoom import *


try:
    _fromUtf8 = QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QApplication.translate(context, text, disambig)


form_class = uic.loadUiType("tjTrader.ui")[0]

class MyWindow(QMainWindow, form_class):
    def __init__(self):
        super(MyWindow,self).__init__()
        self.setupUi(self)
        self.setObjectName("MyWindow")
        self.statusBar = QStatusBar(self)
        self.setStatusBar(self.statusBar)
        
        
        self.kiwoom = Kiwoom()

        # 번개 통신 알림영역아이콘에서 계좌비밀번호저장을 통해 번개 Login 화면이 자동으로 넘어간다.
        self.kiwoom.CommConnect()

        self.timer = QTimer(self)
        self.timer.start(1000) # 1 sec
        self.timer.timeout.connect(self.timeout)

        #self.kiwoom.Login()
        # textChanged event call code_changed method

        ## 종목번호로 종목명 얻어오기
        self.lineEdit.textChanged.connect(self.code_changed)

        accounts_num = (self.kiwoom.GetLoginInfo("ACCOUNT_CNT")).toInt()[0]
        accounts=self.kiwoom.GetLoginInfo("ACCNO").toString()
        accounts_list = accounts.split(';')[0:accounts_num]
        self.comboBox.addItems(accounts_list)


        # 현금체결 버튼 클릭시 주문하기
        self.pushButton.clicked.connect(self.send_order)
        self.pushButton_2.clicked.connect(self.check_balance)


        #
        self.timer2 = QTimer(self)
        self.timer2.start(1000*10)
        self.timer2.timeout.connect(self.timeout2)

    def timeout2(self):
        if self.checkBox.isChecked() == True:
            self.check_balance()

    def timeout(self):

        current_time = QTime.currentTime()
        text_time = current_time.toString("hh:mm:ss")
        
        time_msg = _translate("MyWindow","현재시간: " + text_time,None)

        state = self.kiwoom.GetConnectState()
        if state == 1:
            state_msg = "서버 연결 중"
        else:
            state_msg = "서버 미 연결 중"
        self.statusBar.showMessage(_translate("MyWindow",state_msg,None) + " | " + time_msg)

    def code_changed(self):
        code = self.lineEdit.text()
        code_name = self.kiwoom.GetMasterCodeName(code)
        self.lineEdit_2.setText(code_name.toString())
    def send_order(self):
        order_type_lookup={_fromUtf8('신규매수'):1,_fromUtf8('신규매도'):2,_fromUtf8('매수취소'):3,_fromUtf8('매도취소'):4}
        hoga_lookup={_fromUtf8('지정가'):"00",_fromUtf8('시장가'):"03"}
        self.account = self.comboBox.currentText()
        order_type = self.comboBox_2.currentText()
        code = self.lineEdit.text()
        hoga = self.comboBox_3.currentText()
        num = self.spinBox.value()
        price = self.spinBox_2.value()
        self.kiwoom.SendOrder("SendOrder_req","0101",self.account,order_type_lookup[order_type],code,num,price,hoga_lookup[hoga],"")

    def check_balance(self):

        self.account = self.comboBox.currentText()
        # opw00018 보유종목데이터 계좌평가잔고내역요청
        self.kiwoom.init_opw00018_data()
        self.kiwoom.SetInputValue(_fromUtf8('계좌번호'),self.account)
        self.kiwoom.SetInputValue(_fromUtf8('비밀번호'),"dldu05")
        self.kiwoom.CommRqData("opw00018_req","opw00018",0,"2000")
        time.sleep(0.2)
        while self.kiwoom.prev_next == '2':
            time.sleep(0.2)
            self.kiwoom.SetInputValue(_fromUtf8('계좌번호'), self.account)
            self.kiwoom.SetInputValue(_fromUtf8('비밀번호'), "dldu05")
            self.kiwoom.CommRqData("opw00018_req", "opw00018", 2, "2000")

        # opw00001 예수금상세현황요청
        time.sleep(0.2)
        self.kiwoom.SetInputValue(_fromUtf8('계좌번호'),self.account)
        self.kiwoom.SetInputValue(_fromUtf8('비밀번호'),"dldu05")
        self.kiwoom.CommRqData("opw00001_req","opw00001",0,"2000")
        # 예수금 상세현황, 보유종목데이터
        time.sleep(0.2)
        print 'opw00001 :', self.kiwoom.data_opw00001
        self.kiwoom.prin()
        item = QTableWidgetItem(self.kiwoom.data_opw00001)
        item.setTextAlignment(Qt.AlignVCenter | Qt.AlignRight)
        self.tableWidget.setItem(0,0,item)

        for i in range(1,len(self.kiwoom.data_opw00018['single'])):
            print 'opw00018 single',self.kiwoom.data_opw00018['single']
            item = QTableWidgetItem(self.kiwoom.data_opw00018['single'][i-1])
            item.setTextAlignment(Qt.AlignVCenter | Qt.AlignRight)
            self.tableWidget.setItem(0,i,item)
        self.tableWidget.resizeRowsToContents()

        item_count = len(self.kiwoom.data_opw00018['multi'])
        self.tableWidget_2.setRowCount(item_count)

        for j in range(item_count):
            row = self.kiwoom.data_opw00018['multi'][j]
            for i in range(len(row)):
                item = QTableWidgetItem(row[i])
                item.setTextAlignment(Qt.AlignVCenter | Qt.AlignRight)
                self.tableWidget_2.setItem(j,i,item)

        self.tableWidget_2.resizeRowsToContents()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    myWindow = MyWindow()
    myWindow.show()
    app.exec_()
