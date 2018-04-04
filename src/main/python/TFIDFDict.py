import logging, gensim
logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)

# load id->word mapping (the dictionary), one of the results of step 2 above
id2word = gensim.corpora.Dictionary.load_from_text('../../../data/_wordids.txt.bz2')
# load corpus iterator
#mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm')
mm = gensim.corpora.MmCorpus('../../../data/_tfidf.mm.bz2') # use this if you compressed the TFIDF output (recommended)

print(mm)
print 'Start'
tfidf = gensim.models.TfidfModel.load('../../../data/_tfidf.mm.bz2')
print 'Done'

#token2id converts token in corresponding id.
#1 is the term frequency
#Open question: Why do we have to give at least two terms?
tfidf[[(id2word.token2id["zebra"],1),(id2word.token2id["elephant"],1)]]
