package com.hodumaru.newsmaru.testdata;

import com.hodumaru.newsmaru.model.*;
import com.hodumaru.newsmaru.repository.ArticleRepository;
import com.hodumaru.newsmaru.repository.ClipRepository;
import com.hodumaru.newsmaru.model.Article;
import com.hodumaru.newsmaru.model.CategoryEnum;
import com.hodumaru.newsmaru.model.User;
import com.hodumaru.newsmaru.repository.UserRepository;
import com.hodumaru.newsmaru.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class TestDataRunner implements ApplicationRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    ClipRepository clipRepository;

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

        // 테스트 기사
        Article article3 = new Article("NH농협은행, ‘NH모바일전세대출’ 판매재개", "NH농협은행(은행장 권준학)은 ‘보증서 통합 심사 프로세스’를 새로 도입해 고객 편의성 및 접근성을 강화한 비대면 ‘NH모바일전세대출’를 판매재개 했다. 농협은행은 11일 “‘보증서 통합 심사 프로세스’는 한 번의 전세대출 신청정보 입력으로 3개 보증기관 4종 보증서의 대출 가능금액, 금리, 보증수수료 등을 한눈에 비교하고 최적의 조건을 선택할 수 있도록 하는 서비스”라며 이같이 밝혔다. ‘NH모바일전세대출+’의 대출 대상은 본인과 배우자 모두 무주택 이거나 9억원 이하 1주택을 소유한 만 19세 이상의 개인 또는 개인사업자로, DTI(총부채상환비율) 40% 이내 및 소득 요건 부합 시 임대차 계약 관련서류를 촬영하여 NH스마트뱅킹 앱에서 신청할 수 있다. 대출금리는 카드이용(0.10%포인트), 급여이체(0.10%포인트), 자동이체(0.10%포인트), 농업인우대(0.20%포인트), 비대면 신규(0.10%포인트), 보증서발급(0.10%포인트) 등 우대조건 충족 시 최대 0.70%포인트의 금리우대 혜택을 제공한다. 청년전세대출의 경우 최저 연2.61%까지 가능하다. 최순체 마케팅지원부장은 “고객의 다양한 니즈를 적극 반영해 상품 가입 여정을 획기적으로 단축했다”며 “앞으로도 디지털 혁신을 통해 고객에게 더욱 편리한 최상의 금융우대 서비스를 제공하겠다”고 말했다.", CategoryEnum.ECONOMY);
        Article article4 = new Article("삼성전자, 갤럭시S22 ‘GOS 의무화’ 해제… 가라앉지 않는 불만", "삼성전자가 지난 10일 오후 스마트폰 갤럭시S22 시리즈 3종의 ‘게임 옵티마이징 서비스(GOS)’ 관련 소프트웨어(SW) 업데이트를진행했다. 게임 애플리케이션(앱) 실행 시 스마트폰의 발열로 인한 안전 문제와 배터리 수명 단축을 막기 위해 기기 성능을 낮추는 GOS 기능을 사용자가 켜고 끌 수 있게 한 것이다. 온라인 커뮤니티를 중심으로 삼성전자가 소비자에게 기기 성능을 속였다는 이른바 GOS 논란이 터지자, 삼성전자는 이를 진화하기 위해 SW 업데이트를 결정했다. 삼성전자 커뮤니티 ‘삼성멤버스’를 포함한 온라인 커뮤니티에선 SW 업데이트 후 게임 성능이 향상됐다는 반응이 있는 반면, 불만 역시 적지 않다. GOS는 갤럭시S7 이후 모든 갤럭시 스마트폰 기종에 적용된 기능인데, 소비자 불만이 가장 많았던 갤럭시S22만 이 기능 의무화를 해제한 건 반쪽짜리 해결책이란 것이다. 갤럭시S22 GOS 의무화의 근본적인 원인으로 퀄컴 스냅드래곤8 1세대의 발열과 삼성전자 파운드리의 반도체칩 수율 문제까지 이용자들의 입에 오르고 있다. 삼성전자 MX(모바일경험)사업부장인 노태문 사장은 전날 사내 타운미팅홀을 통해 임직원에게 관련 논란을 설명하고 임직원과의 소통이 부족했다며 사과했다. 이를 두고는, 노 사장이 소비자 불만도 아직 제대로 해결하지 못하면서 임직원에게 사과하는 건 대응의 순서가 잘못됐다는 취지의 반응도 온라인 커뮤니티에서 나오고 있다. 일부 이용자는 환불을 요구하고 있다. 삼성전자에 대한 집단소송을 준비하는 네이버 카페는 지난 2일 개설돼 9일 만인 이날 오전 기준 6800명 이상의 회원을 모집했다. 최근 공정거래위원회는 삼성전자가 표시광고법을 위반했다는 신고를 받아 조사 여부를 검토 중이다.", CategoryEnum.TECH);

        article3 = articleRepository.save(article3);
        article4 = articleRepository.save(article4);

        // 테스트 View, Clip 생성
        View view1 = new View(testUser1, article3);
        View view2 = new View(testUser1,article4);
        Clip clip1 = new Clip(testUser1,article3);
        Clip clip2 = new Clip(testUser1,article4);
        view1 = viewRepository.save(view1);
        view2 = viewRepository.save(view2);
        clip1 = clipRepository.save(clip1);
        clip2 = clipRepository.save(clip2);
    }
}
