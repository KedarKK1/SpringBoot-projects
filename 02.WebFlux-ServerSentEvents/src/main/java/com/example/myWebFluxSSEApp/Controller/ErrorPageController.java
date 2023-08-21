package com.example.myWebFluxSSEApp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController {

    @RequestMapping("/customErrorPage")
    public String getErrorsPage() {
        return "myErrorPage.html"; // Return the name of the view template
    }
}
