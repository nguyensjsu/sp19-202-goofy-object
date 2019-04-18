package five_in_a_row.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping()
public class PlayController {

    @GetMapping("/single")
    public void singleMode() {
        // start socket
    }

    @GetMapping("/battle")
    public void battleMode() {
        // start socket
    }
}
