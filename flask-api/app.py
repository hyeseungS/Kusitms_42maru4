import os
from flask import Flask,  jsonify, request
from flask_restx import Resource, Api
from main import Keyword

from flask_cors import CORS
from flask_mysqldb import MySQL
from dotenv import load_dotenv

from wordCloud import TagCloud

load_dotenv()
app = Flask(__name__)
api = Api(app)
CORS(app)
mysql = MySQL(app)

app.config['MYSQL_USER'] = os.getenv("MYSQL_USER")
app.config['MYSQL_PASSWORD'] = os.getenv("MYSQL_PASSWORD")
app.config['MYSQL_HOST'] = os.getenv("MYSQL_HOST")
app.config['MYSQL_DB'] = os.getenv("MYSQL_DB")

@api.route('/extract/<int:article_id>')
class Extract(Resource):
    def get(self, article_id):

        # MySQL 서버에 접속하기
        cur = mysql.connection.cursor()
        # MySQL 명령어 실행하기
        cur.execute("SELECT content FROM article WHERE article_id = %s", [article_id])
        # article.content 읽어오기
        rows = cur.fetchall()

        content = rows[0]
        keyword = Keyword()
        keyword_list = keyword.get_keyword(content)
        print(keyword_list)

        for tag in keyword_list:
            cur.execute("SELECT tag_id FROM tag WHERE name = %s", [tag])
            tagRows = cur.fetchall()
            if len(tagRows) > 0:
                tag_id = tagRows[0]
            else:
                cur.execute("INSERT INTO tag (name) VALUES (%s)", [tag])
                tag_id = cur.lastrowid

            cur.execute("SELECT tag_id FROM article_tag WHERE article_id = %s AND tag_id = %s", [article_id, tag_id])
            articleTagRows = cur.fetchall()

            if len(articleTagRows) == 0:
                cur.execute("INSERT INTO article_tag (article_id, tag_id) VALUES (%s, %s)", [article_id, tag_id])

        mysql.connection.commit()
        cur.close()

        # Flask에서 제공하는 json변환 함수
        return jsonify(rows[0])


@api.route('/wordcloud/<int:article_id>')
class MakeWordCloud(Resource):
    def get(self, article_id):

        # MySQL 서버에 접속하기
        cur = mysql.connection.cursor()
        # MySQL 명령어 실행하기
        cur.execute("SELECT content FROM article WHERE article_id = %s", [article_id])
        # article.content 읽어오기
        rows = cur.fetchone()

        data = rows[0]
        print(data)
        wordcloud = TagCloud()
        wordcloud.make_wordcloud(data, article_id)

        binary_image = convertToBinaryData(str(article_id)+'.jpg')

        cur.execute("UPDATE article SET cloud_image = %s WHERE article_id = %s", [binary_image, article_id])
        mysql.connection.commit()
        cur.close()

        return jsonify({"success": True})


def convertToBinaryData(filename):
    # Convert digital data to binary format
    with open(filename, 'rb') as file:
        binaryData = file.read()
    return binaryData



if __name__ == '__main__':
    app.secret_key = "123"
    app.run(host="0.0.0.0", port="4000", debug=True)

