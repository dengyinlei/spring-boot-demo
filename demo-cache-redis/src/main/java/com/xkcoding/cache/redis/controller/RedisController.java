package com.xkcoding.cache.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/admin/v1")
public class RedisController {

    /**
     * 登录 Redis 输入 keys '*sessions*'
     *
     * spring:session:sessions:db031986-8ecc-48d6-b471-b137a3ed6bc4
     * spring:session:expirations:1472976480000
     * 其中 1472976480000 为失效时间，意思是这个时间后 Session 失效，
     * db031986-8ecc-48d6-b471-b137a3ed6bc4 为 sessionId,
     * 登录 http://localhost:8080/uid 发现会一致，就说明 Session 已经在 Redis 里面进行有效的管理了。
     *
     * 如何在两台或者多台中共享 Session
     * 其实就是按照上面的步骤在另一个项目中再次配置一次，启动后自动就进行了 Session 共享。
     * @param session
     * @return
     */

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("map"));
        return map;
    }
    /**
     * 启动之后进行访问测试，首先访问8080端口的tomcat，返回
     * {"request Url":"http://localhost:8080/admin/v1/first"}
     * 接着，我们访问8080端口的sessions，返回：
     * {"sessionId":"efcc85c0-9ad2-49a6-a38f-9004403776b5","message":"http://localhost:8080/admin/v1/first"}
     * 最后，再访问9090端口的sessions，返回：
     * {"sessionId":"efcc85c0-9ad2-49a6-a38f-9004403776b5","message":"http://localhost:8080/admin/v1/first"}
     * 可见，8080与9090两个服务器返回结果一样，实现了session的共享
     * 如果此时再访问9090端口的first的话，首先返回：
     * {"request Url":"http://localhost:9090/admin/v1/first"}
     * 而两个服务器的sessions都是返回：
     * {"sessionId":"efcc85c0-9ad2-49a6-a38f-9004403776b5","message":"http://localhost:9090/admin/v1/first"}
     * 通过spring boot + redis来实现session的共享非常简单，而且用处也极大，配合nginx进行负载均衡，便能实现分布式的应用了。
     */


}
