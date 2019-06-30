package jhchv.searchplace.controller;

import jhchv.searchplace.kakao.KakaoConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jihun Cha
 */
@Controller
@RequestMapping("/")
@EnableConfigurationProperties(KakaoConfigurationProperties.class)
@RequiredArgsConstructor
public class IndexController {

    private final KakaoConfigurationProperties properties;

    @GetMapping
    public String doGet(Model model) {
        model.addAttribute("kakaoJavaScriptKey", properties.getJavaScriptKey());
        return "index";
    }

}
