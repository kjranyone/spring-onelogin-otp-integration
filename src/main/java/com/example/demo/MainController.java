package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private OneloginAuth auth;

    @RequestMapping("/")
    String welcome(Model model) {
        model.addAttribute("user", "hoge");
        return "index";
    }

    @RequestMapping("/security")
    String security(Model model, HttpServletRequest request) {
        // 認証が必要なページに相当
        String oneloginSid = request.getSession().getAttribute("oneloginSid").toString();
        if (oneloginSid == null) {
            auth.login();
        } else {
            auth.login("/java-saml-tookit-jspsample/attrs.jsp");
        }
        return "security";
    }

    private class OneloginAuth{
        public OneloginAuth() {
            //initialize
        }

        private void login(){
            //check onelogin session
        }

        private void login(String path) {
            System.out.println(path);
        }
    }
}
