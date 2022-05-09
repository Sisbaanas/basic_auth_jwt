package basic_auth_jwt;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Accueil")
public class Controller {
    @PostMapping("/gimme")
    public String hello()
    {
        return " welcome ! ";
    }
}
