package ua.kpi.softeng_course.tictactoe.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.softeng_course.tictactoe.model.TicTacToeRoundImpl;
import ua.kpi.softeng_course.tictactoe.server.api.CreateRoundRequest;
import ua.kpi.softeng_course.tictactoe.server.api.CreateRoundResponse;
import ua.kpi.softeng_course.tictactoe.server.service.RoundIdGenerator;
import ua.kpi.softeng_course.tictactoe.server.store.RoundStore;
import ua.kpi.softeng_course.tictactoe.server.store.SessionStore;
import ua.kpi.softeng_course.tictactoe.server.view.TicTacToeRoundView;

@RestController
public class RoundController {

    private final RoundStore roundStore;
    private final RoundIdGenerator idGenerator;
    private final SessionStore sessionStore;

    @Autowired
    public RoundController(RoundStore roundStore,
                           RoundIdGenerator idGenerator,
                           SessionStore sessionStore) {
        this.roundStore = roundStore;
        this.idGenerator = idGenerator;
        this.sessionStore = sessionStore;
    }

    @PostMapping("/rounds")
    public ResponseEntity<CreateRoundResponse> createRound(@RequestBody CreateRoundRequest request) {
        System.out.println(request);
        var userId = sessionStore.getUserId(request.sessionId());

        return userId.map(id -> {
            var roundId = idGenerator.nextId();
            var round = new TicTacToeRoundImpl(roundId, id);
            round.join(id);
            roundStore.put(round);
            return ResponseEntity.ok(new CreateRoundResponse(new TicTacToeRoundView(round)));
        }).orElseGet(
                () -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        );
    }
} 