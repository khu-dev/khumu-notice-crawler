package khumu.spring.batch.controller;

import khumu.spring.batch.data.entity.Announcement;
import khumu.spring.batch.data.entity.WebUrl;

@RestController
public class BatchDataController {

    @GetMapping("/csboard")
    public Announcement csboard() {

    }

    @GetMapping("/scholarship")
    public Announcement scholar() {

    }

    @GetMapping("/swboard")
    public Announcement swboard() {

    }
}
