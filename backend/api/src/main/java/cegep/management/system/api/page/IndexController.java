package cegep.management.system.api.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {

    @GetMapping("/{path:[^\\.]*}")
    public String forwardToIndex() {
        return "forward:/index.html";
    }
}