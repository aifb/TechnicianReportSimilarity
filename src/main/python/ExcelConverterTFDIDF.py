import pandas as pd

columns = ['id','WuPalmer','Resnik','JiangConrath','Lin','LeacockChodorow','Path','Lesk']
df = pd.DataFrame(columns= columns)
a = 0

for i in range (10,28):
    if (i == 12 or i == 23 or i == 24):
        continue
    else:
        print i
        xls = pd.ExcelFile('../../../data/'+str(i)+'_WS4JResults_WoExample.xlsx')
        WuPalmer = pd.read_excel(xls, 'WuPalmer')
        Resnik = pd.read_excel(xls, 'Resnik')
        JiangConrath = pd.read_excel(xls, 'JiangConrath')
        Lin = pd.read_excel(xls, 'Lin')
        LeacockChodorow = pd.read_excel(xls, 'LeacockChodorow')
        Path = pd.read_excel(xls, 'Path')
        Lesk = pd.read_excel(xls, 'Lesk')

        # Is a relatedness measure between 0.0 and 1.0
        WuPalmerAggregated = pd.Series.max((pd.DataFrame.max(WuPalmer)))
        print(WuPalmerAggregated)

        # Is a similarity measure between 0.0 and 1.0
        ResnikAggregated = pd.Series.max((pd.DataFrame.max(Resnik)))
        print(ResnikAggregated)

        # Distance measure between 0.0 and infinity
        JiangConrathAggregated = pd.Series.min((pd.DataFrame.min(JiangConrath)))
        print(JiangConrathAggregated)

        # Distance measure between 0.0 and infinity
        LinAggregated = pd.Series.min((pd.DataFrame.min(Lin)))
        print(LinAggregated)

        # Distance measure between 0.0 and infinity - Not 100% sure
        LeacockChodorowAggregated = pd.Series.min((pd.DataFrame.min(LeacockChodorow)))
        print(LeacockChodorowAggregated)

        # Distance measure between 0.0 and infinity - Not 100% sure
        PathAggregated = pd.Series.min((pd.DataFrame.min(Path)))
        print(PathAggregated)

        # Similarity measure between 0.0 and infinity
        LeskAggregated = pd.Series.max((pd.DataFrame.max(Lesk)))
        print(LeskAggregated)

        df.at[a, 'id'] = i
        df.at[a, 'WuPalmer'] = WuPalmerAggregated
        df.at[a, 'Resnik'] = ResnikAggregated
        df.at[a, 'JiangConrath'] = JiangConrathAggregated
        df.at[a, 'Lin'] = LinAggregated
        df.at[a, 'LeacockChodorow'] = LeacockChodorowAggregated
        df.at[a, 'Path'] = PathAggregated
        df.at[a, 'Lesk'] = LeskAggregated
        a = a + 1
print df
df.dropna(inplace = True)
print df

writer = pd.ExcelWriter('../../../result_sim/AggregatedResults.xlsx')
df.to_excel(writer,'Sheet1')
writer.save()



