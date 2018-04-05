import logging, gensim
import pandas as pd

logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

# load id->word mapping (the dictionary), one of the results of step 2 above
id2word = gensim.corpora.Dictionary.load_from_text('../resources/_wordids.txt.bz2')
# load corpus iterator
#mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm')
#mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm.bz2') # use this if you compressed the TFIDF output (recommended)

#print(mm)

#Our idf scores were computed on current Wikipedia corpus (2.4.18 (?))

tfidf = gensim.models.TfidfModel.load('../resources/tfidf.model')

#This was originally used by sba. Does not provide consistent results in the sense that
#the idf score of zebra depends on the second term.
#print tfidf[[(id2word.token2id["zebra"],1),(id2word.token2id["elephant"],1)]]
#print tfidf[[(id2word.token2id["zebra"],1),(id2word.token2id["chair"],1)]]


#Looks like here only the actual idf scores are given.
try:
    print tfidf.idfs.get(id2word.token2id["name"])
except KeyError:
    print "The term was not found."

print tfidf.idfs.get(id2word.token2id["car"])
print tfidf.idfs.get(id2word.token2id["elephant"])

allLabels = pd.read_excel('../../../data/AllLabels.xlsx', header = None)
allLabels.columns=['label']
allLabels['idf'] = 0.0
for index, row in allLabels.iterrows():
    #allLabels.at[rows,]
    #print row['label']
    try:
        allLabels.at[index, 'idf'] = tfidf.idfs.get(id2word.token2id[row['label'].lower()])
    except KeyError:
        allLabels.at[index, 'idf'] = 1.0

print pd.DataFrame.describe(allLabels)
print pd.DataFrame.info(allLabels)

print pd.Series.value_counts(allLabels['idf'])

writer = pd.ExcelWriter('../../../data/AllLabelsIDF.xlsx')
allLabels.to_excel(writer,'Sheet1')
writer.save()