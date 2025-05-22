package ua.kpi.softeng_course.tictactoe.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.softeng_course.tictactoe.server.api.LoginRequest;
import ua.kpi.softeng_course.tictactoe.server.api.LoginResponse;
import ua.kpi.softeng_course.tictactoe.server.service.UserService;
import ua.kpi.softeng_course.tictactoe.server.store.SessionStore;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final SessionStore sessionStore;
    private final UserService userService;

    @Autowired
    public LoginController(SessionStore sessionStore, UserService userService) {
        this.sessionStore = sessionStore;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt",
                keyValue("username", loginRequest.username()),
                keyValue("timestamp", System.currentTimeMillis()));

        var user = userService.findByUsername(loginRequest.username());
        if (user.isEmpty()) {
            logger.warn("Login failed - user not found",
                    keyValue("username", loginRequest.username()),
                    keyValue("timestamp", System.currentTimeMillis()));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String sessionId = sessionStore.createSession(user.get().id());

        logger.info("User successfully logged in",
                keyValue("username", user.get().username()),
                keyValue("userId", user.get().id()),
                keyValue("sessionId", sessionId),
                keyValue("timestamp", System.currentTimeMillis()));

        return ResponseEntity.ok(new LoginResponse(user.get(), sessionId));
    }
}
