import model
from data_prep import data_prep, data_prep1
import pickle
import file_choosing

path1 = 0
# path = file_choosing(path1)
path = "C:/Users/GAMING/Desktop/datasets/iris.json"
model.model1.fit(data_prep(path, a=0, b =138), data_prep1(path, a=0, b=138))
pickle.dump(model.model1, open(("C:/Users/GAMING/Desktop/Origami projekt/GamerPsyche/Ml_model/model_ser.pkl"), 'wb'), protocol=4)

