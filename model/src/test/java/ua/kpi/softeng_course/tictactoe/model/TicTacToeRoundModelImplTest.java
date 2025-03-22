package ua.kpi.softeng_course.tictactoe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeRoundModelImplTest {


    @Test
    void tesRoundIdAssigned() {

        // arrange
        var generator = Mockito.mock(RoundIdGenerator.class);
        var model = new TicTacToeRoundModelImpl(generator);

        Mockito.when(generator.nextId()).thenReturn(1);

        // act
        model.start();

        assertEquals(1, model.roundId().get());

    }

    @Test
    void testRoundStarts() {
        // arrange
        var generator = new RoundIdGeneratorImpl();
        var model = new TicTacToeRoundModelImpl(generator);

        // act

        // assert
        assertEquals(NextAction.Action.START, model.nextAction().get().getAction());
    }

    @Test
    void testRoundFinishes() {

        // arrange
        var generator = new RoundIdGeneratorImpl();
        var model = new TicTacToeRoundModelImpl(generator);


        // act
        model.start();

        // assert
        assertEquals(NextAction.Action.FINISH, model.nextAction().get().getAction());
    }


    @Test
    void testSecondStartFails() {

        // arrange
        var generator = new RoundIdGeneratorImpl();
        var model = new TicTacToeRoundModelImpl(generator);


        // act
        model.start();

        // assert
        assertThrows(IllegalStateException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                model.start();
            }
        });

    }

}