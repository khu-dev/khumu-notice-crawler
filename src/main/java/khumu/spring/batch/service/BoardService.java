package khumu.spring.batch.service;

import khumu.spring.batch.data.dto.AuthorDto;
import khumu.spring.batch.data.dto.BoardDto;
import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final AuthorRepository authorRepository;

    public BoardService(BoardRepository boardRepository,
                        AuthorRepository authorRepository) {
        this.boardRepository = boardRepository;
        this.authorRepository = authorRepository;
    }

    public List<BoardDto> getAllBoards() {
        List<BoardDto> boardDtos = new ArrayList<>();
        List<Author> authors = new ArrayList<>();
        List<AuthorDto> authorDtos = new ArrayList<>();

        List<Board> boards = new ArrayList<>(boardRepository.findAll());

        for (Board board : boards) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(board.getAuthor().getId())
                    .author_name(board.getAuthor().getAuthorName())
                    .followed(Boolean.FALSE)
                    .build();
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .whole_link(board.getFrontUrl() + "###indexpart###" + board.getBackUrl())
                    .last_id(board.getLastId())
                    .author(authorDto)  // 원래 BoardDto에서는 AuthorDto로 받았으나, 일단 한번 해봄. 문제 생기면 여기 수정해야됨
                    .build();
            boardDtos.add(boardDto);
        }
        return boardDtos;
    }

    public List<BoardDto> getBoardByAuthor(String authorname) {
        List<BoardDto> boardDtos = new ArrayList<>();
        Author authors = authorRepository.findByAuthorName(authorname);

        List<Board> boards = new ArrayList<>((boardRepository.findByAuthor(authors)));

//        for(Author author : authors) {
//            boards.addAll(boardRepository.findByAuthor(author.getId()));
//        }

        for (Board board : boards) {
            AuthorDto authorDto = AuthorDto.builder()
                    .id(board.getAuthor().getId())
                    .author_name(board.getAuthor().getAuthorName())
                    .followed(Boolean.FALSE)
                    .build();
            BoardDto boardDto = BoardDto.builder()
                    .id(board.getId())
                    .whole_link(board.getFrontUrl() + board.getBackUrl())
                    .last_id(board.getLastId())
                    .author(authorDto)
                    .build();
            boardDtos.add(boardDto);
        }

        return boardDtos;
    }

//    public BoardDto getBoardByLastid(Integer lastid) {
//        return boardRepository.findByLastid(lastid);
//    }
}
