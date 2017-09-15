package com.example.demo;

import com.onelogin.saml2.Auth;
import com.onelogin.saml2.exception.Error;
import com.onelogin.saml2.exception.SettingsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OneLoginによる認証が必要となるページはここにリダイレクトします
 */
@Controller
public class OneLoginInterceptController {

    @RequestMapping("/security")
    String security(Model model, HttpServletRequest request, HttpServletResponse response) {
        // 認証が必要なページに相当
        System.out.println("security");
        Auth auth = null;
        try {
            auth = new Auth(request, response);
            auth.login();
        } catch (IOException | SettingsException | Error e) {
            e.printStackTrace();
        }
        model.addAttribute("auth", auth);
        // OneLoginがコケた場合はここに来る
        return "security";
    }
}