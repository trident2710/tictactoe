package ua.kpi.softeng_course.tictactoe.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicTacToeRoundImpl implements TicTacToeRound {

    private final int roundId;
    private final int ownerId;
    private final Seat[] seats = new Seat[2]; // Array to store two players

    public TicTacToeRoundImpl(int roundId, int ownerId) {
        this.roundId = roundId;
        this.ownerId = ownerId;
    }

    @Override
    public int roundId() {
        return roundId;
    }

    @Override
    public int ownerId() {
        return ownerId;
    }

    private Status status = Status.NOT_STARTED;
    private PlayerType currentPlayerType = PlayerType.X;
    private final CellState[][] board = new CellState[3][3];
    private Optional<GameResult> gameResult = Optional.empty();

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public CellState[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public List<Seat> getSeats() {
        return Arrays.asList(seats);
    }

    @Override
    public void start() {
        if (status != Status.NOT_STARTED) {
            throw new IllegalStateException("The round has already been started");
        }
        if (seats[0] == null || seats[1] == null) {
            throw new IllegalStateException("Cannot start round: not all players have joined");
        }
        status = Status.ONGOING;
        currentPlayerType = PlayerType.X;
        gameResult = Optional.empty();
        // Clear the board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = CellState.EMPTY;
            }
        }
    }

    @Override
    public void finish() {
        if (status != Status.ONGOING) {
            throw new IllegalStateException("The round is not active");
        } else {
            status = Status.FINISHED;
        }
    }

    @Override
    public Optional<NextAction> nextAction() {
        if (status == Status.NOT_STARTED) {
            if (seats[0] == null || seats[1] == null) {
                return Optional.of(new NextAction(NextAction.Action.OCCUPY_SEATS));
            }
            return Optional.of(new NextAction(NextAction.Action.START));
        }
        if (status == Status.ONGOING) {
            var maybeGameResult = calculateGameResult();
            if (maybeGameResult.isPresent()) {
                gameResult = maybeGameResult;
                return Optional.of(new NextAction(NextAction.Action.FINISH));
            }
            if (currentPlayerType == PlayerType.X) {
                return Optional.of(new NextAction(NextAction.Action.MOVE_X));
            } else {
                return Optional.of(new NextAction(NextAction.Action.MOVE_O));
            }
        }
        return Optional.empty();
    }

    @Override
    public void makeMove(int playerId, int row, int col) {
        if (status != Status.ONGOING) {
            throw new IllegalStateException("The round is not active");
        }
        if (row < 0 || row >= 3 || col < 0 || col >= 3) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (board[row][col] != CellState.EMPTY) {
            throw new IllegalStateException("Position already taken");
        }

        if (calculateGameResult().isPresent()) {
            throw new IllegalStateException("Game already concluded");
        }

        // Check if it's the player's turn
        var playerSeat = getPlayerSeat(playerId)
            .orElseThrow(() -> new IllegalStateException("Player not in this round"));
        
        if (playerSeat.playerType() != currentPlayerType) {
            throw new IllegalStateException("Not your turn");
        }

        board[row][col] = currentPlayerType == PlayerType.X ? CellState.X : CellState.O;
        currentPlayerType = (currentPlayerType == PlayerType.X) ? PlayerType.O : PlayerType.X;

        gameResult = calculateGameResult();
    }

    private Optional<GameResult> calculateGameResult() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != CellState.EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return Optional.of(board[i][0] == CellState.X ? GameResult.X_WINS : GameResult.O_WINS);
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != CellState.EMPTY && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return Optional.of(board[0][j] == CellState.X ? GameResult.X_WINS : GameResult.O_WINS);
            }
        }

        // Check diagonals
        if (board[0][0] != CellState.EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return Optional.of(board[0][0] == CellState.X ? GameResult.X_WINS : GameResult.O_WINS);
        }
        if (board[0][2] != CellState.EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return Optional.of(board[0][2] == CellState.X ? GameResult.X_WINS : GameResult.O_WINS);
        }

        // Check for draw
        boolean isDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == CellState.EMPTY) {
                    isDraw = false;
                    break;
                }
            }
            if (!isDraw) break;
        }
        if (isDraw) {
            return Optional.of(GameResult.DRAW);
        }

        return Optional.empty();
    }

    @Override
    public Optional<GameResult> getGameResult() {
        return gameResult;
    }

    public void join(int playerId) {
        if (status != Status.NOT_STARTED) {
            throw new IllegalStateException("Cannot join a round that has already started");
        }
        if (seats[0] != null && seats[1] != null) {
            throw new IllegalStateException("Round is full");
        }
        if (seats[0] != null && seats[0].playerId() == playerId) {
            throw new IllegalStateException("Player already joined");
        }
        if (seats[1] != null && seats[1].playerId() == playerId) {
            throw new IllegalStateException("Player already joined");
        }

        // Assign player to first available seat
        if (seats[0] == null) {
            seats[0] = new Seat(playerId, PlayerType.X);
        } else {
            seats[1] = new Seat(playerId, PlayerType.O);
        }
    }

    public Optional<Seat> getPlayerSeat(int playerId) {
        if (seats[0] != null && seats[0].playerId() == playerId) {
            return Optional.of(seats[0]);
        }
        if (seats[1] != null && seats[1].playerId() == playerId) {
            return Optional.of(seats[1]);
        }
        return Optional.empty();
    }

}
