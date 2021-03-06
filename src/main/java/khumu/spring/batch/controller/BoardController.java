package khumu.spring.batch.controller;

import khumu.spring.batch.data.dto.BoardDto;
import khumu.spring.batch.data.entity.Board;
import khumu.spring.batch.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/all")
    public List<BoardDto> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("author")
    public BoardDto getBoardByAuthor(@RequestParam String authorName) {
        return boardService.getBoardByAuthor(authorName);
    }
}
