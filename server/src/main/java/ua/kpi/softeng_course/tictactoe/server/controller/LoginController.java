package ua.kpi.softeng_course.tictactoe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.softeng_course.tictactoe.server.api.LoginRequest;
import ua.kpi.softeng_course.tictactoe.server.api.LoginResponse;
import ua.kpi.softeng_course.tictactoe.server.store.KnownUsers;
import ua.kpi.softeng_course.tictactoe.server.store.SessionStore;

@RestController
public class LoginController {
    private final SessionStore sessionStore;

    @Autowired
    public LoginController(SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);

        var user = KnownUsers.KNOWN_USERS.get(loginRequest.username());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        String sessionId = sessionStore.createSession(user.id());
        return ResponseEntity.ok(new LoginResponse(user, sessionId));
    }
}
