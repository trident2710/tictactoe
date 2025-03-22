package ua.kpi.softeng_course.tictactoe.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeRoundModelImplTest {

    @Test
    void testRoundStarts() {
        // arrange
        var model = new TicTacToeRoundModelImpl();

        // act

        // assert
        assertEquals(NextAction.Action.START, model.nextAction().get().getAction());
    }

    @Test
    void testRoundFinishes() {

        // arrange
        var model = new TicTacToeRoundModelImpl();

        // act
        model.start();

        // assert
        assertEquals(NextAction.Action.FINISH, model.nextAction().get().getAction());
    }


    @Test
    void testSecondStartFails() {

        // arrange
        var model = new TicTacToeRoundModelImpl();

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