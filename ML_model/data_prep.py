import pandas as pd
import numpy as np

def data_prep(path1, a = None, b= None):
    data = pd.read_json(path1)
    arguments_for_learning = []
    for i in range(a, b):
        list1 = [data['sepalLength'][i],data['sepalWidth'][i], data['petalLength'][i], data['petalWidth'][i]]
        arguments_for_learning.append(np.array(list1))

    return np.array(arguments_for_learning)

def data_prep1(path, a =None, b=None):
    data = pd.read_json(path)
    values_for_learning = np.array(data['species'][a:b])
    return values_for_learning