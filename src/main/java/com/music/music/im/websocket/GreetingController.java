package com.music.music.im.websocket;

import com.music.music.im.websocket.entity.Greeting;
import com.music.music.im.websocket.entity.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * @author yzx
 * @date 2019-8-20
 */
@Controller
@Slf4j
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) {
        log.info("--------------<<<<<<<"+message);
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
