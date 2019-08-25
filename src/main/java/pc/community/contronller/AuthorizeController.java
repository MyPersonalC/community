package pc.community.contronller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pc.community.dto.AccessTokenDTO;
import pc.community.dto.GithubUser;
import pc.community.mapper.UserMapper;
import pc.community.model.User;
import pc.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserMapper userMapper;

    //通过注解获取配置文件里的配置
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String access_token = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUer(access_token);
        if (githubUser != null) {
            //登录，获取用户信息，生成一个token存储在数据库中，并且手动将token添加到cookie中
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccount_id(String.valueOf(githubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
            //登录成功缓存cookie和session
            response.addCookie(new Cookie("token", token));
            //地址去掉，redirect重定向到首页
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }

}
