package ua.kpi.softeng_course.tictactoe.server.view;

import ua.kpi.softeng_course.tictactoe.model.*;

import java.util.List;
import java.util.Optional;

public record TicTacToeRoundState(
        int roundId,
        int ownerId,
        Status status,
        List<Cell> board,
        Optional<GameResult> gameResult,
        List<Seat> seats,
        Optional<NextAction> nextAction) {
}
