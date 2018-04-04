import pandas as pd
import glob
import re
import numpy as np

columns = ['id','WuPalmer','Resnik','JiangConrath','Lin','LeacockChodorow','Path','Lesk', 'Jena']
df = pd.DataFrame(columns= columns)
a = 0

Jenaxls = pd.ExcelFile('../../../data/JenaResults.xlsx')

def findJenaSheetAndAggregate(i):
    #Conversion to string since sheet names are strings. Else numbering is used.
    sheet = pd.read_excel(Jenaxls, str(id))

    #Distance of zero is replaced by 1 to obtain similarity of 1 when inverting
    sheet.replace(0,1,inplace=True)



    sheet = sheet.transform(lambda x: 1/x)
    #print sheet

    #Distance/Similarity of -1, i.e. no path found ist replaced by 0 to indicate that similarity is as low as possible
    sheet.replace(-1,0,inplace=True)
    #print sheet

    JenaAggregated = pd.Series.max(pd.DataFrame.max(sheet))
    return JenaAggregated

#Find all filenames matching the pattern
filenames = glob.glob('../../../data/*_WS4JResults_WoExample.xlsx')

for filename in filenames:
    print filename
    xls = pd.ExcelFile(filename)
    WuPalmer = pd.read_excel(xls, 'WuPalmer')
    Resnik = pd.read_excel(xls, 'Resnik')
    JiangConrath = pd.read_excel(xls, 'JiangConrath')
    Lin = pd.read_excel(xls, 'Lin')
    LeacockChodorow = pd.read_excel(xls, 'LeacockChodorow')
    Path = pd.read_excel(xls, 'Path')
    Lesk = pd.read_excel(xls, 'Lesk')

    # Is a similarity measure between 0.0 and 1.0
    WuPalmerAggregated = pd.Series.max((pd.DataFrame.max(WuPalmer)))
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

    ###Extract the id from the filename###

    #Necessary for the regex below.
    filenameReplaced = filename.replace('\\','\\\\')
    #Extract the first number and use it as is
    id = int(re.search(r'\d+', filenameReplaced).group())

    JenaAggregated = findJenaSheetAndAggregate(id)




    df.at[a, 'id'] = id
    df.at[a, 'WuPalmer'] = WuPalmerAggregated
    df.at[a, 'Resnik'] = ResnikAggregated
    df.at[a, 'JiangConrath'] = JiangConrathAggregated
    df.at[a, 'Lin'] = LinAggregated
    df.at[a, 'LeacockChodorow'] = LeacockChodorowAggregated
    df.at[a, 'Path'] = PathAggregated
    df.at[a, 'Lesk'] = LeskAggregated
    df.at[a, 'Jena'] = JenaAggregated
    a = a + 1

print df
df.dropna(inplace = True) # empty table so ignore report pair
print df

#As of now labels are added manually
writer = pd.ExcelWriter('../../../result_sim/AggregatedResults_woLabels.xlsx')
df.to_excel(writer,'Sheet1')
writer.save()