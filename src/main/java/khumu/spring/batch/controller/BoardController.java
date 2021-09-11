package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.BoardDto;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.service.BoardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/boardTest")
    public String boardTest() {
        return "it's working";
    }

    @GetMapping("/all")
    public List<BoardDto> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{author}")
    public List<BoardDto> getBoardByAuthor(@RequestParam String authorname) {
        return boardService.getBoardlByAuthor(authorname);
    }

//    @GetMapping("/{lastid}")
//    public BoardDto getBoardByLastid(@RequestParam Integer lastid) {
//        return boardService.getBoardByLastid(lastid);
//    }
}
