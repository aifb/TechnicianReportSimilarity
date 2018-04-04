import logging, gensim
logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

# load id->word mapping (the dictionary), one of the results of step 2 above
id2word = gensim.corpora.Dictionary.load_from_text('../resources/_wordids.txt.bz2')
# load corpus iterator
#mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm')
#mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm.bz2') # use this if you compressed the TFIDF output (recommended)

#print(mm)

tfidf = gensim.models.TfidfModel.load('../resources/tfidf.model')

#This was originally used by sba. Does not provide consistent results in the sense that
#the idf score of zebra depends on the second term.
#print tfidf[[(id2word.token2id["zebra"],1),(id2word.token2id["elephant"],1)]]
#print tfidf[[(id2word.token2id["zebra"],1),(id2word.token2id["chair"],1)]]


#Looks like here only the actual idf scores are given.
print tfidf.idfs.get(id2word.token2id["zebra"])
print tfidf.idfs.get(id2word.token2id["chair"])
print tfidf.idfs.get(id2word.token2id["elephant"])