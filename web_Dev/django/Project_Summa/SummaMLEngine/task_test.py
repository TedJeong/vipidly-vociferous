# !/usr/bin/python
# -*- coding: utf-8 -*-

from __future__ import absolute_import, unicode_literals
from .celery import app
import matplotlib.pyplot as plt
import numpy as np
from sklearn import datasets, linear_model
import mpld3
import json


from SummaMLEngine.summa_ml_core.ml_core.pipeline_controller import *
from SummaMLEngine.summa_ml_core.ml_core.feature_controller import *
from SummaMLEngine.summa_ml_core.ml_core.pipeline_builder import *
from SummaMLEngine.summa_ml_core.ml_core.visualization_toolbox import *
from SummaMLEngine.summa_ml_core.ml_core.data_holder import *


@app.task
def add(x, y):
    return x + y


@app.task
def mul(x, y):
    return x * y


@app.task
def xsum(numbers):
    return sum(numbers)



@app.task
def plot_ols(x, y):
    """
    =========================================================
    Linear Regression Example
    =========================================================
    This example uses the only the first feature of the `diabetes` dataset, in
    order to illustrate a two-dimensional plot of this regression technique. The
    straight line can be seen in the plot, showing how linear regression attempts
    to draw a straight line that will best minimize the residual sum of squares
    between the observed responses in the dataset, and the responses predicted by
    the linear approximation.

    The coefficients, the residual sum of squares and the variance score are also
    calculated.

    """


    # Code source: Jaques Grobler
    # License: BSD 3 clause

    # Load the diabetes dataset
    diabetes = datasets.load_diabetes()

    # Use only one feature
    diabetes_X = diabetes.data[:, np.newaxis, 2]

    # Split the data into training/testing sets
    diabetes_X_train = diabetes_X[:-20]
    diabetes_X_test = diabetes_X[-20:]

    # Split the targets into training/testing sets
    diabetes_y_train = diabetes.target[:-20]
    diabetes_y_test = diabetes.target[-20:]

    # Create linear regression object
    regr = linear_model.LinearRegression()

    # Train the model using the training sets
    regr.fit(diabetes_X_train, diabetes_y_train)

    # The coefficients
    print('Coefficients: \n', regr.coef_)
    # The mean squared error
    print("Mean squared error: %.2f"
          % np.mean((regr.predict(diabetes_X_test) - diabetes_y_test) ** 2))
    # Explained variance score: 1 is perfect prediction
    print('Variance score: %.2f' % regr.score(diabetes_X_test, diabetes_y_test))

    fig, ax = plt.subplots()

    # Plot outputs
    ax.scatter(diabetes_X_test, diabetes_y_test, color='black')
    ax.plot(diabetes_X_test, regr.predict(diabetes_X_test), color='blue',
             linewidth=3)

    aws = 'Coefficients: {} \n<br/> Mean squared error: {:.2f} \n<br/> Variance score: {:.2f}'.format(regr.coef_,
                                                                np.mean((regr.predict(diabetes_X_test) - diabetes_y_test) ** 2),
                                                                regr.score(diabetes_X_test, diabetes_y_test),
                                                                                           )

    #return json.dumps({"console":aws, "plot":mpld3.fig_to_html(fig)})
    return [aws, mpld3.fig_to_html(fig)]
    #return "test"
    #plt.show()


@app.task
def plot_raw_test(x, y):
    aws = ''
    fig = ''
    print("test")
    dh1 = data_holder()
    dh1.read_data()

    aws += dh1.print_data_table_info("brain_size.csv")

    vt1 = visualization_toolbox(dh1, 'brain_size.csv')
    result = vt1.raw_data_plot(['11','21'])

    aws += result[0]
    fig += result[1]

    return [aws, fig]


@app.task
def plot_feature_pair_comparision(x, y):
    aws = ''
    fig = ''

    ## feature_pair_comparision
    # split 80/20 train-test
    dh1 = data_holder()
    dh1.read_data()
    vt1 = visualization_toolbox(dh1, 'brain_size.csv')
    cal_housing = fetch_california_housing()
    X_train, X_test, y_train, y_test = train_test_split(cal_housing.data,
                                                        cal_housing.target,
                                                        test_size=0.2,
                                                        random_state=1)
    names = cal_housing.feature_names

    print("Training GBRT...")
    clf = GradientBoostingRegressor(n_estimators=100, max_depth=4,
                                    learning_rate=0.1, loss='huber',
                                    random_state=1)
    clf.fit(X_train, y_train)
    result = vt1.feature_pair_comparision(X_train, y_train, names, clf)
    aws += result[0]
    fig += result[1]

    return [aws, fig]


@app.task
def plot_all_test(x, y):
    aws = ''
    fig = ''
    print("all test")

    ## plot_raw
    result = plot_raw_test(x, y)
    aws += result[0]
    fig += result[1]

    ## feature_pair_comparision
    result = plot_feature_pair_comparision(x, y)
    aws += result[0]
    fig += result[1]

    return [aws, fig]