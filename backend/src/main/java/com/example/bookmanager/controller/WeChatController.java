import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private final Map<String, String> stateTokenMap = new ConcurrentHashMap<>();
    private final Map<String, String> stateOpenidMap = new ConcurrentHashMap<>();

    @GetMapping("/login")
    public Map<String, String> getWeChatLoginUrl() {
        String state = UUID.randomUUID().toString();
        String url = UriComponentsBuilder.fromHttpUrl("https://open.weixin.qq.com/connect/qrconnect")
                .queryParam("appid", appId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "snsapi_login")
                .queryParam("state", state)
                .build().toUriString() + "#wechat_redirect";
        Map<String, String> result = new HashMap<>();
        result.put("url", url);
        result.put("state", state);
        return result;
    }

    @GetMapping("/poll")
    public Map<String, String> pollToken(@RequestParam String state) {
        Map<String, String> result = new HashMap<>();
        if (stateTokenMap.containsKey(state)) {
            result.put("token", stateTokenMap.get(state));
            stateTokenMap.remove(state);
        } else if (stateOpenidMap.containsKey(state)) {
            result.put("openid", stateOpenidMap.get(state));
            stateOpenidMap.remove(state);
        }
        return result;
    }

    @PostMapping("/bind")
    public Map<String, String> bindWeChat(@RequestBody Map<String, String> body) {
        String openid = body.get("openid");
        String username = body.get("username");
        String password = body.get("password");
        // 校验账号密码
        User user = userService.findByUsername(username)
            .filter(u -> passwordEncoder.matches(password, u.getPassword()))
            .orElseThrow(() -> new RuntimeException("账号或密码错误"));
        user.setWechatOpenid(openid);
        userService.save(user);
        Map<String, String> result = new HashMap<>();
        result.put("token", jwtUtil.generateToken(user.getUsername(), user.getRole()));
        return result;
    }

    @GetMapping("/callback")
    public void wechatCallback(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws IOException {
        // ...原有微信登录逻辑...
        // 4. 本地用户自动注册/登录或返回 openid
        String username = "wx_" + openid;
        Optional<User> userOpt = userService.findByWechatOpenid(openid);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String jwt = jwtUtil.generateToken(user.getUsername(), user.getRole());
            stateTokenMap.put(state, jwt);
        } else {
            // 未绑定，前端弹窗输入账号密码
            stateOpenidMap.put(state, openid);
        }
        response.getWriter().write("<script>window.close();</script>扫码成功，请在原页面继续操作");
    }
} 