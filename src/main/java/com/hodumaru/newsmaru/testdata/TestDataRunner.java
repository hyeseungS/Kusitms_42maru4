package com.hodumaru.newsmaru.testdata;

import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;


@Component
public class TestDataRunner implements ApplicationRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User testUser1 = new User("호두마루", "hodu@maru.com", passwordEncoder.encode("123"), 1, LocalDate.now());
        testUser1 = userRepository.save(testUser1);

        // 테스트 Article 생성
        for(int i=1; i<=10; i++) {
            Article article = Article.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .category(CategoryEnum.ECONOMY)
                    .build();
            articleRepository.save(article);
        }

        Article article2 = Article.builder()
                .title("이준석, 당직 전면개편 착수…초기인물 재기용으로 기강잡기 나설듯")
                .content("국민의힘 이준석 대표가 당직 전면 개편에 착수하며 6월 지방선거를 채비에 나서고 있다. 이 대표는 초박빙 대선 승리에 대한 일각의 책임론에는 즉각 반박했다.\n" +
                        "복수의 국민의힘 관계자에 따르면 이 대표는 대선 과정에서 윤석열 대통령 당선인 측과의 협의로 임명했던 당직 인사들을 교체하고, 자신이 취임 초기 기용했던 인물들을 재차 기용할 방침이다. 대표로서 주도권을 되찾고 내부 기강을 잡으려는 취지로 보인다.\n" +
                        "이에 따라 권영세 의원이 물러나며 공석이 된 사무총장 자리에는 한기호 의원이, 조직부총장에는 김석기 의원이 다시 임명될 예정이다. 전략기획부총장으로는 새 인물을 찾고 있다. 울산시장에 도전하는 서범수 의원이 사임하면서 공석이 된 대표 비서실장에는 대구·경북 지역 초선의원 중 적임자를 찾고 있다.\n" +
                        "이 대표는 6월 지방선거 채비에 총력을 기울이는 모양새다. 당의 전략과 교육을 담당하는 여의도연구원장과 중앙연수원장을 교체한 뒤 자신이 공언했던 지방선거 공직후보자 시험을 예정대로 진행할 계획이다. 이달 말에는 토론 배틀을 통해 당 대변인단을 새로 꾸릴 방침이다.\n" +
                        "국민의힘 한 재선 의원은 “물밑에서 윤핵관(윤석열 핵심관계자)과 이 대표 간 지방선거 공천권을 두고 갈등이 치열하게 벌어지고 있다”며 “이 대표가 빠르게 당직 인선을 단행하면서 밀리지 않겠다는 뜻을 분명히 한 셈”이라고 했다.\n" +
                        "윤 당선인의 대선 경선 경쟁자였던 홍준표 의원은 대구시장 출마를 공식화했다. 홍 의원은 이날 자신이 만든 온라인 커뮤니티 ‘청년의꿈’에 “중앙정치는 윤 당선인에게 맡기고 저는 하방하고자 한다”며 “대한민국 리모델링 꿈이 좌절된 지금 제가 할 일은 나를 키워준 대구부터 리모델링하는 것”이라고 했다.")
                .category(CategoryEnum.POLITICS)
                .build();
        articleRepository.save(article2);

    }
}
