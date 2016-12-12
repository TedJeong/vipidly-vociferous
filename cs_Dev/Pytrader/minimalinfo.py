#-*- coding: utf-8 -*-

import sys
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4.QAxContainer import *
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

class MyWindow(QMainWindow):
    def __init__(self):
        super(MyWindow, self).__init__()
        self.setObjectName("mywindow")

        #QMainWindow.setObjectName(_fromUtf8("QMainWindow"))
        #QMainWindow.resize(800, 600)

        self.setWindowTitle("PyStockBasic")
        self.setGeometry(300,300,300,150)

        label = QLabel(_translate("mywindow","종목코드 : ",None),self)
        label.move(20,20)

        self.code_edit = QLineEdit(self)
        self.code_edit.move(80,20)
        self.code_edit.setText("039490")

        btn1 = QPushButton(_translate("mywindow","조회",None),self)
        btn1.move(190,20)

        self.text_edit = QTextEdit(self)
        self.text_edit.setGeometry(10,60,280,80)
        self.text_edit.setEnabled(False)

if __name__=="__main__":
    app = QApplication(sys.argv)
    myWindow = MyWindow()
    myWindow.show()
    app.exec_()