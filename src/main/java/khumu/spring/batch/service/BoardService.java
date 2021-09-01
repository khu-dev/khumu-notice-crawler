package khumu.spring.batch.service;

import khumu.spring.batch.data.entity.Author;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.repository.AuthorRepository;
import khumu.spring.batch.repository.BoardRepository;
import org.springframework.stereotype.Service;

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

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board getBoardlByAuthor(String authorname) {
        List<Author> authors = authorRepository.findByAuthorname(authorname);
        Long authorid = authors.getId();

        return boardRepository.findByAuthor(authorid);
    }

    public Board getBoardByLastid(Integer lastid) {
        return boardRepository.findByLastid(lastid);
    }
}
