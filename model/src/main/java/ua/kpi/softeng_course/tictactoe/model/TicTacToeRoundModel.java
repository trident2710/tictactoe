package ua.kpi.softeng_course.tictactoe.model;

import java.util.Optional;

public interface TicTacToeRoundModel {

    Optional<Integer> roundId();

    void start();

    void finish();

    Optional<NextAction> nextAction();
}
