package ua.kpi.softeng_course.tictactoe.model;

import java.util.Optional;

public class TicTacToeRoundModelImpl implements TicTacToeRoundModel {

    private final RoundIdGenerator roundIdGenerator;

    public TicTacToeRoundModelImpl(RoundIdGenerator roundIdGenerator) {
        this.roundIdGenerator = roundIdGenerator;
    }

    private Optional<Integer> roundId = Optional.empty();

    private Status status = Status.NOT_STARTED;

    @Override
    public Optional<Integer> roundId() {
        return roundId;
    }

    @Override
    public void start() {
        if (status != Status.NOT_STARTED) {
            throw new IllegalStateException("The round has already been started");
        } else {
            status = Status.ONGOING;
            roundId = Optional.of(roundIdGenerator.nextId());
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
            return Optional.of(new NextAction(NextAction.Action.START));
        }

        if (status == Status.ONGOING) {
            return Optional.of(new NextAction(NextAction.Action.FINISH));
        }

        return Optional.empty();
    }

    enum Status {
        NOT_STARTED,
        ONGOING,
        FINISHED
    }
}
