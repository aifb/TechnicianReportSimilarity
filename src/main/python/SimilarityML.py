import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix
from sklearn import svm
from sklearn.externals import joblib
from sklearn.ensemble import RandomForestClassifier
from sklearn.tree import DecisionTreeClassifier
import seaborn as sns
import numpy as np
from sklearn.metrics import precision_score, recall_score, f1_score, classification_report
from sklearn.model_selection import cross_val_predict, KFold

measures = ['WuPalmer','Resnik','JiangConrath','Lin','LeacockChodorow','Path','Lesk', 'Jena']

#Readable font size
plt.rcParams.update({'font.size': 22})

# index = false to get names of sim measures as row names
similarities = pd.read_excel('../../../result_sim/AggregatedResults_IDFWeighted.xlsx', index_col=0)

#similarities = pd.DataFrame.transpose(similarities)
print(similarities)

# Split by label
similar = similarities.ix[similarities.label == 1.0]
not_similar = similarities.ix[similarities.label == 0.0]
organizational_information = similarities.ix[similarities.label == -1.0]


def scatterplot(x, y):
    plt.plot(similar[x], similar[y], '+', label = 'similar', markersize = 22)
    plt.plot(not_similar[x], not_similar[y], 'o', label = 'not similar', markersize = 22)
    plt.plot(organizational_information[x], organizational_information[y], 'x', label = 'organizational', markersize = 22)
    plt.legend()
    plt.xlabel(x)
    plt.ylabel(y)
    plt.show()


x = 'WuPalmer'
y = 'Path'
#scatterplot(x, y)

def boxplot():
    #First number specifies the number of rows. Second number specifies the number of columns.
    #Specifies which plot within the grid it is.
    plt.subplot(421)
    sns.boxplot(x= 'label',y = 'WuPalmer', data = similarities)
    plt.subplot(422)
    sns.boxplot(x='label', y='Resnik', data = similarities)
    plt.subplot(423)
    sns.boxplot(x='label', y='JiangConrath', data = similarities)
    plt.subplot(424)
    sns.boxplot(x='label', y='Lin', data = similarities)
    plt.subplot(425)
    sns.boxplot(x='label', y='LeacockChodorow', data = similarities)
    plt.subplot(426)
    sns.boxplot(x='label', y='Path', data = similarities)
    plt.subplot(427)
    sns.boxplot(x='label', y='Lesk', data = similarities)
    plt.subplot(428)
    sns.boxplot(x='label', y='Jena', data = similarities)

    plt.show()
#boxplot()

def descriptive_statistics():
    print '+++++Descriptive statistics+++++'
    print 'Total number of instances'
    print similarities.shape[0]
    print 'Number of instances labelled similar'
    print similar.shape[0]
    print 'Number of instances labelled not similar'
    print not_similar.shape[0]
    print 'Number of instances labelled as organizational information'
    print organizational_information.shape[0]

    for measure in measures:
        print measure
        print 'mean: ' + str(np.mean(similarities[measure]))
        print 'sd: '+ str(np.std(similarities[measure]))
descriptive_statistics()


def model_evaluation_one_split():
    similarities_y = similarities['label']
    similarities_X = similarities.drop('label', axis=1)

    X_train, X_test, y_train, y_test = train_test_split(similarities_X, similarities_y, test_size=0.33, random_state=42)

    # Guess it would be three class, just
    clf = svm.SVC()
    #clf = RandomForestClassifier()
    #clf = DecisionTreeClassifier()
    clf.fit(X_train, y_train)
    y_pred = clf.predict(X_test)

    print '+++++Results++++'

    cm = (confusion_matrix(y_test, y_pred))
    print 'Confusion Matrix'
    print '\033[1m'+ '                                                   Actual class'+'\033[0m'
    print '                         organization information (-1)     not similar (0)     similar (1)'
    print '\033[1m'+'Predicted class'+'\033[0m'
    print 'organization information (-1)        '+str(cm[0,0])+ '                            '+str(cm[0,1])+'               '+str(cm[0,2])
    print 'not similar (0)                      '+str(cm[1,0])+ '                            '+str(cm[1,1])+'               '+str(cm[1,2])
    print 'similar (1)                          '+str(cm[2,0])+ '                            '+str(cm[2,1])+'               '+str(cm[2,2])

    print''
    print 'Precision, Recall & F1-Score per class and weighted average.'
    target_names = ['organisational information', 'not similar', 'similar']
    print classification_report(y_test,y_pred, target_names = target_names)

#model_evaluation_one_split()

def model_evaluation_CV():
    y = similarities['label']
    X = similarities.drop('label', axis=1)

    RANDOM_SEED = 5
    prng = np.random.RandomState(RANDOM_SEED)

    kFold = KFold(n_splits=10, shuffle=False, random_state=prng)
    # Guess it would be three class, just
    clf = svm.SVC()
    y_pred = cross_val_predict(clf,X, y, cv = kFold)

    #print y
    #print y_pred
    print '+++++Results++++'

    cm = (confusion_matrix(y, y_pred))
    print 'Confusion Matrix'
    print '\033[1m' + '                                                   Predicted class' + '\033[0m'
    print '                         organization information (-1)     not similar (0)     similar (1)'
    print '\033[1m' + 'Actual class' + '\033[0m'
    print 'organization information (-1)        ' + str(cm[0, 0]) + '                            ' + str(
        cm[0, 1]) + '               ' + str(cm[0, 2])
    print 'not similar (0)                      ' + str(cm[1, 0]) + '                            ' + str(
        cm[1, 1]) + '               ' + str(cm[1, 2])
    print 'similar (1)                          ' + str(cm[2, 0]) + '                            ' + str(
        cm[2, 1]) + '               ' + str(cm[2, 2])

    print''
    print 'Precision, Recall & F1-Score per class and weighted average.'
    target_names = ['organisational information', 'not similar', 'similar']
    print classification_report(y, y_pred, target_names=target_names)
model_evaluation_CV()

def training_productive_model():
    similarities_y = similarities['label']
    similarities_X = similarities.drop('label', axis=1)

    clf = svm.SVC()
    clf.fit(similarities_X, similarities_y)

    joblib.dump(clf, './svm_model.pkl')


training_productive_model()


def label_new_instance():
    #To execute productive model: Run ExcelConverterTFIDF for set of files and reference the
    #according excel file here.
    similarity = pd.read_excel('../../../result_sim/ReportProposal_25.xlsx', index_col=0)
    similarity = pd.DataFrame.transpose(similarity)
    similarity.drop('label', axis=1, inplace=True)
    print(similarity)
    clf = joblib.load('./svm_model.pkl')
    print(clf.predict(similarity))

#Does not work anymore since no Jena results are included
#label_new_instance()