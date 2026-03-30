package org.beginsecure.apitermi.controlllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String redirigirRaiz() {
        return "redirect:/tiendas";
    }
}