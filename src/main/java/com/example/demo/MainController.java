package com.example.demo;

import com.onelogin.saml2.Auth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
public class MainController {

    @RequestMapping("/")
    String welcome(Model model) {
        return "index";
    }

    @RequestMapping("/security/01")
    String security01() {
        System.out.println("/security/01");
        return "security";
    }

    @RequestMapping("/security/02")
    String security02(@RequestParam(value = "testValue", required = false) String testValue, HttpServletRequest request, HttpServletResponse response, Model model) {
        System.out.println("/security/02");
        model.addAttribute("redirectPath", "http://yahoo.co.jp");

        return "security2";
    }

    @RequestMapping("/acs")
    String attributeConsumerService(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {
        Auth auth = new Auth(request, response);
        // this logic supported only POST method.
        auth.processResponse();
        if (auth.isAuthenticated()) {
            OneLoginAttributeBean bean = new OneLoginAttributeBean();
            // 認証が行われた時間をセッションで記録
            bean.setLastAuthenticatedAt(LocalDateTime.now());
            // authに色々パラメータを持っているので他の認証情報(OneLoginのnameIdとか)を使いたい場合はここでbeanに突っ込むのがいいと思う
            request.getSession().setAttribute(OneLoginInterceptor.SESSION_KEY_ONELOGIN_AUTH, bean);

            return "redirect:" + request.getParameter("RelayState");
        } else {
            // OneLoginからリダイレクトされる時はisAuthenticated() => true なのでここにくる場合は不正リクエストとして良い
            return "redirect:/";
        }
    }

    @RequestMapping("/logout")
    void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            System.out.println("ログアウトしました");
        }
        new Auth(request, response).logout();
    }
}
