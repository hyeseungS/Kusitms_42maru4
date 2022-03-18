from wordcloud import WordCloud
from PIL import Image
import numpy as np
from konlpy.tag import Twitter
from collections import Counter


class TagCloud:

    def make_wordcloud(self, data, article_id):
        twitter = Twitter()

        # twitter함수를 통해 읽어들인 내용의 형태소를 분석한다.
        sentences_tag = []
        sentences_tag = twitter.pos(data)

        noun_adj_list = []

        # tag가 명사이거나 형용사인 단어들만 noun_adj_list에 넣어준다.
        for word, tag in sentences_tag:
            if tag in ['Noun', 'Adjective']:
                noun_adj_list.append(word)

        # 가장 많이 나온 단어부터 100개를 저장한다.
        counts = Counter(noun_adj_list)
        tags = counts.most_common(100)

        # 한글을 분석하기 위해 font를 한글로 지정해주어야 된다. macOS는 .otf , window는 .ttf 파일의 위치를 지정해준다.
        font_path = 'NotoSansKR-Medium.otf'

        # 마스크 이미지
        mask = np.array(Image.open('cloud.png'))

        # WordCloud를 생성한다.
        wc = WordCloud(font_path=font_path, background_color="white", mask=mask)
        cloud = wc.generate_from_frequencies(dict(tags))

        # 생성된 WordCloud를 test.jpg로 보낸다.
        cloud.to_file(str(article_id) + '.jpg')


