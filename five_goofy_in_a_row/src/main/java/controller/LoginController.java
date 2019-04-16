package controller;

@RestController
@CrossOrigin("*")
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public boolean login(@RequestParam String username) {

        return true;
    }
}
