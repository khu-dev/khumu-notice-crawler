package khumu.spring.batch.service;

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

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getBoardlByAuthor(String authorname) {
        List<Board> board = new ArrayList<>();
        List<Author> authors = authorRepository.findByAuthorname(authorname);

        for(Author author : authors) {
            board.addAll(boardRepository.findByAuthor(author.getId()));
        }

        return board;
    }

    public Board getBoardByLastid(Integer lastid) {
        return boardRepository.findByLastid(lastid);
    }
}
