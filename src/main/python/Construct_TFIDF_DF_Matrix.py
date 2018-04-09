import logging, gensim
import pandas as pd



logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
id2word = gensim.corpora.Dictionary.load_from_text('../resources/_wordids.txt.bz2')
#Our idf scores were computed on current Wikipedia corpus (2.4.18 (?))
tfidf = gensim.models.TfidfModel.load('../resources/tfidf.model')

def construct_tf_idf_df(number):
    idf_df = pd.read_excel('../../../data/'+str(number)+'_LabelsForIDF.xlsx',header = None)
    #print idf_df

    idf_scores_df= idf_df.copy()

    #Iterate over column names
    for i in range(1,idf_df.shape[1]):
        label = idf_df.iloc[0,i]
        #print label
        try:
            idf_scores_df.at[0,i] = tfidf.idfs.get(id2word.token2id[label.lower()])
        except:
            idf_scores_df.at[0, i] = 1.0

    #Iterate over row names
    for i in range(1,idf_df.shape[0]):
        label = idf_df.iloc[i,0]
        # idf_df.iloc[i,0]
        try:
            idf_scores_df.at[i, 0] = tfidf.idfs.get(id2word.token2id[label.lower()])
        except:
            idf_scores_df.at[i, 0] = 1.0

    #print idf_scores_df

    for i in range (1,idf_df.shape[0]):
        for j in range(1,idf_df.shape[1]):
            idf_df.at[i,j] = idf_scores_df.iloc[i,0] * idf_scores_df.iloc[0,j]
    idf_df.reset_index(drop=True,inplace = True)
    #print idf_df
    return idf_df
construct_tf_idf_df(10)


