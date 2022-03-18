import networkx
import re

double = []
one = []

class RawTaggerReader:
    def __init__(self, contents, tagger=None):
        if tagger:
            self.tagger = tagger
        else:
            from konlpy.tag import Komoran
            self.tagger = Komoran()
        self.contents = contents
        self.rgxSplitter = re.compile('([.!?:](?:["\']|(?![0-9])))')

    def __iter__(self):
        for i in self.contents:
            #print(i)
            ch = self.rgxSplitter.split(i)
            #print('-------')
            #print(ch)
            #print('-------')
            # 처음부터 끝까지 두 칸 간격으로
            # index 1 부터 끝까지 두 칸 간격으로
            for s in map(lambda a, b: a + b, ch[::2], ch[1::2]):
                #print(s)
                if not s: continue
                yield self.tagger.pos(s)

class TextRank:
    def __init__(self, **kargs):
        self.graph = None
        self.window = kargs.get('window', 5)
        self.coef = kargs.get('coef', 1.0)
        self.threshold = kargs.get('threshold', 0.005)
        self.dictCount = {}
        self.dictBiCount = {}
        self.dictNear = {}
        self.nTotal = 0

    def load(self, sentenceIter, wordFilter=None):
        def insertPair(a, b):
            if a > b:
                a, b = b, a
            elif a == b:
                return
            self.dictBiCount[a, b] = self.dictBiCount.get((a, b), 0) + 1

        def insertNearPair(a, b):
            self.dictNear[a, b] = self.dictNear.get((a, b), 0) + 1

        for sent in sentenceIter:
            for i, word in enumerate(sent):
                if wordFilter and not wordFilter(word): continue
                self.dictCount[word] = self.dictCount.get(word, 0) + 1
                self.nTotal += 1
                if i - 1 >= 0 and (not wordFilter or wordFilter(sent[i - 1])): insertNearPair(sent[i - 1], word)
                if i + 1 < len(sent) and (not wordFilter or wordFilter(sent[i + 1])): insertNearPair(word, sent[i + 1])
                for j in range(i + 1, min(i + self.window + 1, len(sent))):
                    if wordFilter and not wordFilter(sent[j]): continue
                    if sent[j] != word: insertPair(word, sent[j])

    def loadSents(self, sentenceIter, tokenizer=None):
        import math
        def similarity(a, b):
            n = len(a.intersection(b))
            return n / float(len(a) + len(b) - n) / (math.log(len(a) + 1) * math.log(len(b) + 1))

        if not tokenizer: rgxSplitter = re.compile('[\\s.,:;-?!()"\']+')
        sentSet = []
        for sent in filter(None, sentenceIter):
            if type(sent) == str:
                if tokenizer:
                    s = set(filter(None, tokenizer(sent)))
                else:
                    s = set(filter(None, rgxSplitter.split(sent)))
            else:
                s = set(sent)
            if len(s) < 2: continue
            self.dictCount[len(self.dictCount)] = sent
            sentSet.append(s)

        for i in range(len(self.dictCount)):
            for j in range(i + 1, len(self.dictCount)):
                s = similarity(sentSet[i], sentSet[j])
                if s < self.threshold: continue
                self.dictBiCount[i, j] = s

    def getPMI(self, a, b):
        import math
        co = self.dictNear.get((a, b), 0)
        if not co: return None
        return math.log(float(co) * self.nTotal / self.dictCount[a] / self.dictCount[b])

    def getI(self, a):
        import math
        if a not in self.dictCount: return None
        return math.log(self.nTotal / self.dictCount[a])

    def build(self):
        self.graph = networkx.Graph()
        self.graph.add_nodes_from(self.dictCount.keys())
        for (a, b), n in self.dictBiCount.items():
            self.graph.add_edge(a, b, weight=n * self.coef + (1 - self.coef))

    def rank(self):
        return networkx.pagerank(self.graph, weight='weight')

    def extract(self, ratio=0.1):
        ranks = self.rank()
        cand = sorted(ranks, key=ranks.get, reverse=True)[:int(len(ranks) * ratio)]
        pairness = {}
        startOf = {}
        tuples = {}
        for k in cand:
            tuples[(k,)] = self.getI(k) * ranks[k]
            for l in cand:
                if k == l: continue
                pmi = self.getPMI(k, l)
                if pmi: pairness[k, l] = pmi
        count =0
        adverb = ['후','전','수','때','것','만큼','데', '바', '뿐','점']
        for (k, l) in sorted(pairness, key=pairness.get, reverse=True):
            #print(k[0], l[0], pairness[k, l])
            if(len(k[0]) != 1 or len(l[0]) != 1):
                if(k[0] not in adverb and l[0] not in adverb):
                    if(count <2):
                        double.append([k[0], l[0]])
                        count += 1
        for (k, l), v in pairness.items():
            pmis = v
            rs = ranks[k] * ranks[l]
            path = (k, l)
            tuples[path] = pmis / (len(path) - 1) * rs ** (1 / len(path)) * len(path)
            last = l
            while last in startOf and len(path) < 7:
                if last in path: break
                pmis += pairness[startOf[last]]
                last = startOf[last][1]
                rs *= ranks[last]
                path += (last,)
                tuples[path] = pmis / (len(path) - 1) * rs ** (1 / len(path)) * len(path)

        used = set()
        both = {}
        for k in sorted(tuples, key=tuples.get, reverse=True):
            if used.intersection(set(k)): continue
            both[k] = tuples[k]
            for w in k: used.add(w)

        # for k in cand:
        #    if k not in used or True: both[k] = ranks[k] * self.getI(k)

        return both

    def summarize(self, ratio=0.333):
        r = self.rank()
        ks = sorted(r, key=r.get, reverse=True)[:int(len(r) * ratio)]
        return ' '.join(map(lambda k: self.dictCount[k], sorted(ks)))

class Keyword:

    def make_keyword(self, contents):
        tr = TextRank(window=5, coef=1)
        print('Load...')
        stopword = set([('있', 'VV'), ('하', 'VV'), ('되', 'VV'), ('없', 'VV') ])
        tr.load(RawTaggerReader(contents), lambda w: w not in stopword and (w[1] in ('NNG', 'NNP', 'VV', 'VA')))
        print('Build...')
        tr.build()
        kw = tr.extract(0.1)
        count = 0
        StopWord = ['최초', '일시', '사람', '3월', '자신','이다','시간', '이용', '증가', '감소', '현재', '지금', '과거', '이번해', '지난해', '지난달','이번달', '하루전', '이틀전','오늘', '어제', '내일', '모레', '올해', '내년', '작년', '하루',
'이용', '주요', '최고', '가장', '어느덧', '인정', '한낮', '환영', '사건', '사고', '이유', '필요', '자리', '일시', '이용', '경험', '조사', '일부',
'문제', '서비스', '기반', '자신', '관계', '최근', '인정', '삭제', '실시간', '일대', '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월',
'도대체', '지난', '바로', '하나', '한층', '대두', '당연', '상황', '적용', '사람', '인간', '이미', '항상', '자주', '전부', '보통', '공통', '자꾸', '곳곳', '한번', '열흘',
'그대로', '그대', '결국', '금방', '전혀', '마치', '대체로', '매달', '매주', '매일', '조금', '올해']
        for k in sorted(kw, key=kw.get, reverse=True):
            #print("%s\t%g" % (k, kw[k]))
            for i in k:
                if(i[1] != 'VV' and i[1] != 'VA' and count <3):
                    if(len(i[0]) != 1):
                        if all(i[0] not in l for l in double):
                            if(i[0] not in StopWord):
                                try:
                                    one.append(i[0])
                                    count += 1
                                except IndexError:
                                    one[0] = i[0]
                                    count += 1

    def get_keyword(self, content):
        result = []
        article = content[0]
        contents = article.splitlines()
        self.make_keyword(contents)

        for i in double:
            result.append(str(i[0] + i[1]))
        for i in one:
            result.append(str(i))

        one.clear()
        double.clear()
        #print(result)
        return result

if __name__ == '__main__':
    keyword = Keyword()
    keyword.make_keyword()