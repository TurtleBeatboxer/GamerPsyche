from sklearn.linear_model import LogisticRegression
import pickle
# model1 = LogisticRegression()
model1 = pickle.load(open(("C:/Users/GAMING/Desktop/Origami projekt/GamerPsyche/Ml_model/model_ser.pkl"), 'rb'))
