package chap09.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(Model model, @RequestParam(value = "name", required = false) String name){

        model.addAttribute("greeting", "안녕하세요, " + name);
        log.info("왜안됨 {}", name);
        return "hello";
    }
}
