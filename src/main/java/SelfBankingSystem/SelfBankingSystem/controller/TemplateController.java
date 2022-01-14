package SelfBankingSystem.SelfBankingSystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {
    @GetMapping
    public String getHomePageView(){
        return "register";
    }

    @GetMapping("login")
    public String getLoginView(){
        return "login";
    }

    @GetMapping("customer")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String getCustomerView(){
        return "customer";
    }
}
