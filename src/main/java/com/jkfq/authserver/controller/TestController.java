package com.jkfq.authserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-02-01
 **/
@Slf4j
@RestController
@EnableResourceServer
public class TestController {
    @RequestMapping("/sayHi")
    public String sayHi(@RequestParam String name) {
        log.info("访问测试。。。");
        return name + " hello!";
    }


    @RequestMapping("/sayHi2")
    public String sayHi2() {
        log.info("访问测试。。。");
        return " hello11111111!";
    }

    // todo 刷新token访问方式
    // http://10.0.8.52:60002/uaa/oauth/token?grant_type=refresh_token&client_id=my_admin&client_secret=123456&scope=*&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsiaWQiOjEsInVzZXJOYW1lIjoiYWRtaW4iLCJ1c2VyUGFzc3dvcmQiOm51bGwsImRpc3BsYXlOYW1lIjoi57O757uf566h55CG5ZGYIiwidGVsZXBob25lIjpudWxsLCJtb2JpbGUiOm51bGwsImxvZ2luX3RpbWUiOm51bGwsImxhc3RfbG9naW5fdGltZSI6bnVsbCwiY3JlYXRlX3VzZXIiOm51bGwsImNyZWF0ZV90aW1lIjoxNTQ4NzkyMjM2MDAwLCJ1cGRhdGVfdXNlciI6bnVsbCwidXBkYXRlX3RpbWUiOjE1NDkwNTU5MzkwMDAsImRlbF9mbGFnIjoiMCIsImlzX2FjdGl2ZSI6IjEifSwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyIqIl0sImF0aSI6ImRiMDNjY2NhLTMwNjctNDk1MS04ZTk4LTk4NjJhMDFjZTM4ZSIsImV4cCI6MTU1MTA4Njc4NiwiYXV0aG9yaXRpZXMiOlsiQWRtaW4iXSwianRpIjoiZmE5ZTE5NDQtMWU1OC00MGQzLTkzZjYtMzE3ZGFkNTFjODJhIiwiY2xpZW50X2lkIjoibXlfYWRtaW4ifQ.VW7iNv0SUaC8A6tlNqsTHAqRBfJfQrxTtBm1d6yG4V7y3wygfp2iH7MfalO2c83AfEtCyfkr_3YY8hxx5MSOHQGgcE62k-41IA6N3p8gaVDnCaFueIBpUEpqxkr3IVAUBmt8tjbQfsrgYfbkS31FiVDEjyJ06JRei4ZpsqkDJ0_qTCu8_kQ_LTtddKvD0g9-QT9ULbEqr3L-jxOEpc3pRLlk8CJrgHRNVH0fKFMouBsArwJwfckWzcj0YhDChq1TaQrV3jJlZ2Xz0Tgjukc5ydIANvGU7fYGAueCAhNGIklNR0Wi4bH7Fht3W8hZNry-AGbagZo13cvqTtuY7eigdQ


}
