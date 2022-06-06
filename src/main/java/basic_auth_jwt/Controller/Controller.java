package basic_auth_jwt.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/utils")
public class Controller {
    @GetMapping("/gimme")
    public List<String> hello()
    {
        List<String> a = new ArrayList<>();
        a.add("Holla !!!");
        a.add("welcome !!!");
        return a;
    }
}
