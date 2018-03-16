import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix
from sklearn import svm
from sklearn.externals import joblib

#index = false to get names of sim measures as row names
similarities = pd.read_excel('../../../result_sim/Report_Proposal_all.xlsx', index_col = 0)

similarities = pd.DataFrame.transpose(similarities)
#print(similarities)

#Split by label
similar = similarities.ix[similarities.label==1.0]
not_similar = similarities.ix[similarities.label==0.0]

def plot_classification_data(x,y):
    plt.plot(similar[x], similar[y], '+')
    plt.plot(not_similar[x], not_similar[y], 'o')
    plt.xlabel(x)
    plt.ylabel(y)
    plt.show()

x = 'WuPalmer'
y = 'Jena'

#plot_classification_data(x, y)

def model_evaluation():
    similarities_y = similarities['label']
    similarities_X = similarities.drop('label', axis = 1)

    X_train, X_test, y_train, y_test = train_test_split(similarities_X, similarities_y, test_size=0.33, random_state=42)

    clf = svm.SVC()
    clf.fit(X_train, y_train)
    y_pred = clf.predict(X_test)

    print(confusion_matrix(y_test, y_pred))
    
#model_training_and_eval()

def training_productive_model():
    similarities_y = similarities['label']
    similarities_X = similarities.drop('label', axis = 1)

    clf = svm.SVC()
    clf.fit(similarities_X, similarities_y)
    
    joblib.dump(clf,'./svm_model.pkl')

#training_productive_model()

def label_new_instance():
    similarity = pd.read_excel('../../../result_sim/ReportProposal_25.xlsx', index_col = 0)
    similarity = pd.DataFrame.transpose(similarity)
    similarity.drop('label', axis = 1, inplace = True)
    print(similarity)
    clf = joblib.load('./svm_model.pkl')
    print(clf.predict(similarity))
label_new_instance()










