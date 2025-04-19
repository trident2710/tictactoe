package ua.kpi.softeng_course.tictactoe.server.view;

import ua.kpi.softeng_course.tictactoe.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicTacToeRoundView {
    private final int roundId;
    private final int ownerId;
    private final Status status;
    private final CellState[][] board;
    private final Optional<GameResult> gameResult;
    private final List<Seat> seats;

    public TicTacToeRoundView(TicTacToeRound round) {
        this.roundId = round.roundId();
        this.ownerId = round.ownerId();
        this.status = round.getStatus();
        this.board = round.getBoard();
        this.gameResult = round.getGameResult();
        this.seats = round.getSeats();
    }

    public int getRoundId() {
        return roundId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Status getStatus() {
        return status;
    }

    public CellState[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public Optional<GameResult> getGameResult() {
        return gameResult;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
