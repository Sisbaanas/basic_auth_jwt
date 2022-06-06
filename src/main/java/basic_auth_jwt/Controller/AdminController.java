package basic_auth_jwt.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Administration")
public class AdminController {
    @GetMapping("/secrets")
    public List<String> secrets()
    {
        List<String> a = new ArrayList<>();
        a.add("secret 1 !!!");
        a.add("secret 2 !!!");
        a.add("secret 3 !!!");
        a.add("secret 4 !!!");
        return a;
    }
}
