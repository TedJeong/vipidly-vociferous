#-*- coding: utf-8 -*-

import requests
import pandas as pd
from bs4 import BeautifulSoup
from PyQt4.QtCore import *

import sys

reload(sys)
sys.setdefaultencoding('utf-8')
"""
기업 제무 제표 :
http://companyinfo.stock.naver.com/company/c1010001.aspx?cmp_cd=005930

일반 금융 정보
finance.naver.com
http://finance.daum.net/

http://www.financipe.com/search?keyword=naver&t0=20151029&t1=20161029
일반 부동산 정보
"""

try:
    _fromUtf8 = QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

def get_financial_statements(code):
    """
    재무재표기반 투자알고리즘

    :param code:
    :return:
    """
    #url = "http://companyinfo.stock.naver.com/v1/company/ajax/cF1001.aspx?cmp_cd=%s&fin_typ=0&freq_typ=Y" % (code)
    #html = requests.get(url).text
    import codecs
    f=codecs.open("datasample.html",'r','utf-8')
    #f.write(html)
    html=f.read()
    f.close()
    #print html
    html = html.replace('<th class="bg r01c02 endLine line-bottom"colspan="8">연간</th>', "")
    html = html.replace("<span class='span-sub'>(IFRS연결)</span>", "")
    html = html.replace("<span class='span-sub'>(IFRS별도)</span>", "")
    html = html.replace('\t', '')
    html = html.replace('\n', '')
    html = html.replace('\r', '')
    html = html.replace('2011/12', '2011')
    html = html.replace('2012/12', '2012')
    html = html.replace('2013/12', '2013')
    html = html.replace('2014/12', '2014')
    html = html.replace('2015/12', '2015')
    df_list = pd.read_html(html,index_col='test',encoding='utf-8')
    df = df_list[0]
    #print(df.index)
    #print(df.columns)
    #print(df.ix[5])
    return df


def get_3year_treasury():
    """
    국가지표체계 웹페이지에 나와있는 국고채수익률 3년평균(일별 만기 국채 수익률의 산술평균)
    :return:
    """
    url = "http://www.index.go.kr/strata/jsp/showStblGams3.jsp?stts_cd=107301&idx_cd=1073"

    html = requests.get(url).text
    #print(html)
    soup = BeautifulSoup(html,'lxml')
    tr_data = soup.find_all('tr',id='tr_107301_1')
    td_data = tr_data[0].find_all('td')
    # 1997 년 국고채수익률
    # td_data[0].text
    treasury_3year = {}
    start_year = 1997
    for x in td_data:
        treasury_3year[start_year] = x.text
        start_year += 1
    return treasury_3year

def get_current_3year_treasury():
    """
    3년 만기 국채 수익률 일별 시세
    :return:
    """
    url = "http://info.finance.naver.com/marketindex/interestDailyQuote.nhn?marketindexCd=IRR_GOVT03Y&page=1"
    html = requests.get(url).text

    soup = BeautifulSoup(html, 'lxml')
    tbody_data = soup.find_all('tbody')
    tr_data = tbody_data[0].find_all('tr')
    td_data = tr_data[0].find_all('td')
    return td_data[1].text

def get_dividend_yield(code):
    """
    네이버 finanace 기업 현황
    :param code:
    :return:
    """
    url = "http://companyinfo.stock.naver.com/company/c1010001.aspx?cmp_cd=" + code
    html = requests.get(url).text
    soup = BeautifulSoup(html,'lxml')
    td_data = soup.find_all('td',{'class':'cmp-table-cell td0301'})
    dt_data = td_data[0].find_all('dt')


    dividend_yield = dt_data[5].text
    dividend_yield = dividend_yield.split(' ')[1]
    dividend_yield = dividend_yield[:-1]
    return dividend_yield

def get_estimated_dividend_yield(code):
    df = get_financial_statements(code)

    column = df.columns[5]
    #now = datetime.datetime.now()
    #cur_year = '%d/12(E)' % (now.year)
    #cur_year = df[cur_year]
    cur_year=df[column]

    estimated_dividend_yield = cur_year[u'현금배당수익률']

    return estimated_dividend_yield

import datetime
def get_previous_dividend_yield(code):
    """
    과거 5년치 현금배당수익률
    :param code:
    :return:
    """
    df = get_financial_statements(code)
    dividend_yields = df.ix[u'현금배당수익률']

    ret = {}
    now = datetime.datetime.now()
    #cur_year = now.year
    year = now.year - 5

    #for year in range(cur_year-5, cur_year):
    #    if str(year) in dividend_yields.index:
    #        ret[year] = dividend_yields[str(year)]

    for dividend_yield in dividend_yields.values:
        ret[year] = dividend_yield
        year += 1

    return ret

def get_min_max_dividend_to_treasury(code):
    previous_dividend_yield = get_previous_dividend_yield(code)
    three_years_treasury = get_3year_treasury()
    now = datetime.datetime.now()
    cur_year = now.year
    previous_dividend_to_treasury = {}

    for year in range(cur_year-5, cur_year):
        if year in previous_dividend_yield.keys() and year in three_years_treasury.keys():
            ratio = float(previous_dividend_yield[year])/float(three_years_treasury[year])
            previous_dividend_to_treasury[year] = ratio
    print('국채배당률 : ',previous_dividend_to_treasury)
    min_ratio = min(previous_dividend_to_treasury.values())
    max_ratio = max(previous_dividend_to_treasury.values())
    return min_ratio,max_ratio


if __name__=='__main__':
    #get_financial_statements('035720')#카카오
    #get_dividend_yield('058470')#리노공업
    print get_previous_dividend_yield('035720')