package khumu.spring.batch.tasklet;

import khumu.spring.batch.data.dto.AnnouncementDto;
import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.publish.EventPublish;
import khumu.spring.batch.repository.AnnouncementRepository;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static khumu.spring.batch.configuration.HttpClientConfig.setSSL;

@Component
@StepScope
@RequiredArgsConstructor
public class ArtDesignCrawling implements Tasklet, StepExecutionListener {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;
    private final AnnouncementRepository announcementRepository;
    private final EventPublish eventPublish;

    // 멱등성을 위한 board 데이터 주입
    @Override
    public void beforeStep(StepExecution stepExecution) {

        Author author = Author.builder()
                .id(1L)
                .authorName("예술디자인대학").build();
        authorRepository.save(author);

        Board board = Board.builder()
                .id(1L)
                .frontUrl("http://and.khu.ac.kr/board/bbs/board.php?bo_table=05_01&page=1")
                .backUrl(null)
                .lastId(null)
                .author(author).build();
        boardRepository.save(board);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Author target = authorRepository.findByAuthorName("예술디자인대학").get();
        Board board = boardRepository.findByAuthor(target).get();

        String frontUrl = board.getFrontUrl();
        String backUrl = board.getBackUrl();
        Integer lastId = board.getLastId();
        String authorName = target.getAuthorName();

        String page = frontUrl + backUrl;

        String title = null;
        String date = null;
        String subLink = null;
        ArrayList<String> subLinkList = new ArrayList<>();

        String lastAnnouncement = null;

        // ssl 우회 설정
        setSSL();
        // URL 연결
        Document document = null;
        try {
            document = Jsoup.connect(page).get();
            System.out.println("연결 성공");
        } catch (IOException e) {
            System.out.println("연결 실패");
            e.printStackTrace();
        }

        // css selector
        // 제목과 date 긁기
        Elements elements = document.select("tr.bo_notice");
        Iterator<Element> titleIterator = elements.select(".td_subject").iterator();
        Iterator<Element> dateIterator = elements.select(".td_date").iterator();

        // css selector
        // href 추출
        Elements subLinkElements = document.select("td.td_subject a");
        for (Element element : subLinkElements) {subLinkList.add(element.attr("href"));}
        Iterator<String> subLinkIterator = subLinkList.iterator();

        while(titleIterator.hasNext()) {
            title = titleIterator.next().text();
            date = "2022-" + dateIterator.next().text();
            subLink = subLinkIterator.next();

            System.out.println("=====긁어온 데이터=====" + "\n제목 : " + title + "\n날짜 : " + date + "\n링크 : " + subLink);

            try {
                lastAnnouncement = announcementRepository.findByTitle(title).getTitle();
                System.out.println(lastAnnouncement);
            } catch(NullPointerException e) {
//                e.printStackTrace();
                System.out.println("동일한 공지 사항 없음\nDB기록 실시");
            }

            if (title.equals(lastAnnouncement)) {
                System.out.println("이미 존재하는 공지사항 발견\n새로운 공지사항 없음\n크롤링 조기종료");
                break;
            }

            // 공지사항 DTO 객체 생성
            AnnouncementDto announcementDto = AnnouncementDto.builder()
                    .title(title)
                    .author(AuthorDto.builder()
                            .id(target.getId())
                            .authorName(authorName)
                            .build())
                    .date(date)
                    .subLink(subLink)
                    .build();

            // 푸시 알림 보내기
//            eventPublish.pubTopic(announcementDto);

            // DB Data write
            announcementRepository.save(announcementDto.toEntity());
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("예술 디자인 대학 크롤링 종료");
        return ExitStatus.COMPLETED;
    }
}
