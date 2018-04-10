import pandas as pd
import glob
import re
import Construct_TFIDF_DF_Matrix
import logging, gensim

class Construct_IDF_Matrix:
    def __init__(self):
        logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', level=logging.INFO)
        self.id2word = gensim.corpora.Dictionary.load_from_text('../resources/_wordids.txt.bz2')
        # Our idf scores were computed on current Wikipedia corpus (2.4.18 (?))
        self.tfidf = gensim.models.TfidfModel.load('../resources/tfidf.model')


    def construct_idf_df(self, number):
        #There is a corresponding method in App.java which produces this Excel.
        idf_df = pd.read_excel('../../../data/' + str(number) + '_LabelsForIDF.xlsx', header=None)
        # print idf_df

        idf_scores_df = idf_df.copy()

        # Iterate over column names and get idf scores corresponding to labels
        for i in range(1, idf_df.shape[1]):
            label = idf_df.iloc[0, i]
            # print label
            try:
                idf_scores_df.at[0, i] = self.tfidf.idfs.get(self.id2word.token2id[label.lower()])
            except:
                idf_scores_df.at[0, i] = 1.0

        # Iterate over row names and get idf scores corresponding to labels
        for i in range(1, idf_df.shape[0]):
            label = idf_df.iloc[i, 0]
            # idf_df.iloc[i,0]
            try:
                idf_scores_df.at[i, 0] = self.tfidf.idfs.get(self.id2word.token2id[label.lower()])
            except:
                idf_scores_df.at[i, 0] = 1.0

        # print idf_scores_df

        #Multiply the idf scores of the labels to get a matrix to be multiplied with similarities
        for i in range(1, idf_df.shape[0]):
            for j in range(1, idf_df.shape[1]):
                idf_df.at[i, j] = idf_scores_df.iloc[i, 0] * idf_scores_df.iloc[0, j]
        idf_df.reset_index(drop=True, inplace=True)
        print idf_df
        return idf_df

# constructor = Construct_TFID_DF_Matrix()
# constructor.construct_tf_idf_df(10)

class ExcelConverterIDF:
    def __init__(self):
        columns = ['id', 'WuPalmer', 'Resnik', 'JiangConrath', 'Lin', 'LeacockChodorow', 'Path', 'Lesk', 'Jena']
        self.df = pd.DataFrame(columns=columns)
        self.row_index = 0

        self.Jenaxls = pd.ExcelFile('../../../data/JenaResults.xlsx')

    IDF_Constructor = Construct_IDF_Matrix()

    #Jena results are stored in separate Excel and it is necessary to convert the distances to similarities
    def findJenaSheetAndPreprocess(self, id):
        # Conversion to string since sheet names are strings. Else numbering is used.
        sheet = pd.read_excel(self.Jenaxls, str(id))

        # Distance of zero is replaced by 1 to obtain similarity of 1 when inverting
        #TBD: Find way to convert s.t. distance of 1 and 0 are not equal
        sheet.replace(0, 1, inplace=True)

        # Transform distance to similarity
        sheet = sheet.transform(lambda x: 1 / x)
        # print sheet

        # Distance/Similarity of -1, i.e. no path found ist replaced by 0 to indicate that similarity is as low as possible
        sheet.replace(-1, 0, inplace=True)
        # print sheet

        return sheet

    def weightWithIDFscores(self, similarity_sheet, idf_scores_df, idf_weight):
        weighted_similarity_sheet = pd.DataFrame(similarity_sheet.values * idf_scores_df.values ** 1 / idf_weight,
                                                 columns=similarity_sheet.columns,
                                                 index=similarity_sheet.index)

        return weighted_similarity_sheet

    def main(self):
        # Find all filenames matching the pattern
        filenames = glob.glob('../../../data/*_WS4JResults_WoExample.xlsx')

        for filename in filenames:
            print filename
            xls = pd.ExcelFile(filename)
            WuPalmer = pd.read_excel(xls, 'WuPalmer')
            print 'WuPalmer'
            print WuPalmer
            Resnik = pd.read_excel(xls, 'Resnik')
            JiangConrath = pd.read_excel(xls, 'JiangConrath')
            Lin = pd.read_excel(xls, 'Lin')
            LeacockChodorow = pd.read_excel(xls, 'LeacockChodorow')
            Path = pd.read_excel(xls, 'Path')
            Lesk = pd.read_excel(xls, 'Lesk')

            ###Extract the id from the filename###

            # Necessary for the regex below.
            filenameReplaced = filename.replace('\\', '\\\\')
            # Extract the first number and use it as is
            id = int(re.search(r'\d+', filenameReplaced).group())

            Jena = self.findJenaSheetAndPreprocess(id)

            idf_scores_df = self.IDF_Constructor.construct_idf_df(id)
            # Necessary that the two dataframes to be multiplied have the same size. idf_scores_df has additional index.
            # try-catch is necessary for the empty tables. Later after aggregation these are deleted anyway
            # TBD for better performance: Check earlier if a table is empty.
            try:
                idf_scores_df.drop([0], inplace=True)
                idf_scores_df.drop([0], inplace=True, axis=1)
            except ValueError:
                continue

            print 'id + idf_scores_df'
            print id
            print idf_scores_df

            # Parametrize the weighting of the idf scores here.
            idf_weight = 1

            try:
                WuPalmer = self.weightWithIDFscores(WuPalmer, idf_scores_df, idf_weight)
                print 'WuPalmerWeighted'
                print WuPalmer
                Resnik = self.weightWithIDFscores(Resnik, idf_scores_df, idf_weight)
                JiangConrath = self.weightWithIDFscores(JiangConrath, idf_scores_df, idf_weight)
                Lin = self.weightWithIDFscores(Lin, idf_scores_df, idf_weight)
                LeacockChodorow = self.weightWithIDFscores( LeacockChodorow, idf_scores_df, idf_weight)
                Path = self.weightWithIDFscores(Path, idf_scores_df, idf_weight)
                Lesk = self.weightWithIDFscores(Lesk, idf_scores_df, idf_weight)
                Jena = self.weightWithIDFscores(Jena, idf_scores_df, idf_weight)
            except ValueError:
                continue
            # Is a similarity measure between 0.0 and 1.0
            WuPalmerAggregated = pd.Series.max((pd.DataFrame.max(WuPalmer)))
            print 'WuPalmerAggregated'
            print(WuPalmerAggregated)

            # Is a similarity measure between 0.0 and 1.0
            ResnikAggregated = pd.Series.max((pd.DataFrame.max(Resnik)))
            print(ResnikAggregated)

            # Similarity measure between 0.0 and infinity
            JiangConrathAggregated = pd.Series.max((pd.DataFrame.max(JiangConrath)))
            print(JiangConrathAggregated)

            # Similarity measure between 0.0 and infinity
            LinAggregated = pd.Series.max((pd.DataFrame.max(Lin)))
            print(LinAggregated)

            # Similarity measure between 0.0 and infinity
            LeacockChodorowAggregated = pd.Series.max((pd.DataFrame.max(LeacockChodorow)))
            print(LeacockChodorowAggregated)

            # Similarity measure between 0.0 and infinity
            PathAggregated = pd.Series.max((pd.DataFrame.max(Path)))
            print(PathAggregated)

            # Similarity measure between 0.0 and infinity
            LeskAggregated = pd.Series.max((pd.DataFrame.max(Lesk)))
            print(LeskAggregated)

            JenaAggregated = pd.Series.max(pd.DataFrame.max(Jena))

            self.df.at[self.row_index, 'id'] = id
            self.df.at[self.row_index, 'WuPalmer'] = WuPalmerAggregated
            self.df.at[self.row_index, 'Resnik'] = ResnikAggregated
            self.df.at[self.row_index, 'JiangConrath'] = JiangConrathAggregated
            self.df.at[self.row_index, 'Lin'] = LinAggregated
            self.df.at[self.row_index, 'LeacockChodorow'] = LeacockChodorowAggregated
            self.df.at[self.row_index, 'Path'] = PathAggregated
            self.df.at[self.row_index, 'Lesk'] = LeskAggregated
            self.df.at[self.row_index, 'Jena'] = JenaAggregated
            self.row_index = self.row_index + 1

        print self.df
        self.df.dropna(inplace=True)  # empty table so ignore report pair
        print self.df

        # As of now labels are added manually
        writer = pd.ExcelWriter('../../../result_sim/AggregatedResults_woLabels_IDFWeighted.xlsx')
        self.df.to_excel(writer, 'Sheet1')
        writer.save()



    # def run(self):
    #     converter_ = ExcelConverterTFIDF_()
    #     ExcelConverterTFIDF_.main(self)


#converter= ExcelConverterTFIDF_()
#converter.main()
