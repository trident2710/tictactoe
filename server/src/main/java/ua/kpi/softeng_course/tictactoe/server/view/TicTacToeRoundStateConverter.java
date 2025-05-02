package ua.kpi.softeng_course.tictactoe.server.view;

import ua.kpi.softeng_course.tictactoe.model.TicTacToeRound;

public class TicTacToeRoundStateConverter {

    public static TicTacToeRoundState convert(TicTacToeRound round) {
        return new TicTacToeRoundState(
                round.roundId(),
                round.ownerId(),
                round.getStatus(),
                round.getBoard(),
                round.getGameResult(),
                round.getSeats(),
                round.nextAction()
        );
    }
} 