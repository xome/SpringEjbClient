package de.mayer.ejb;

import de.mayer.ejbServer.Hello;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    private final Hello statelessHello;
    private final Hello statefulHello;

    public WebController(Hello statelessHello,
                         Hello statefulHello) {
        this.statelessHello = statelessHello;
        this.statefulHello = statefulHello;
    }

    @GetMapping("/stateful")
    public ResponseEntity<String> getMessageStateful() {
        String msg = statefulHello.message();
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/stateless")
    public ResponseEntity<String> getMessageStateless() {
        String msg = statelessHello.message();
        return ResponseEntity.ok(msg);
    }
}
