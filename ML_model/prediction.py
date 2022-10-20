import model
from data_prep import data_prep

def predict(path):

    predicted_values = model.model1.predict(data_prep(path, a = 140, b=150)).tolist()
    return predicted_values

path = "C:/Users/GAMING/Desktop/datasets/iris.json"
print(predict(path))