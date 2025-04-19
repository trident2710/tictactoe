package tictactoe.server.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.kpi.softeng_course.tictactoe.model.TicTacToeRound;
import ua.kpi.softeng_course.tictactoe.model.TicTacToeRoundImpl;
import ua.kpi.softeng_course.tictactoe.server.store.RoundStore;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundStoreTest {

    private static final int ROUND_ID = 42;
    private static final int OWNER_ID = 100;

    @Test
    void testGetReturnsEmptyForNonExistentRound() {
        var store = new RoundStore();
        assertEquals(Optional.empty(), store.get(ROUND_ID));
    }

    @Test
    void testGetReturnsRoundWhenExists() {
        var store = new RoundStore();
        var round = new TicTacToeRoundImpl(ROUND_ID, OWNER_ID);
        store.put(round);
        
        Optional<TicTacToeRound> retrievedRound = store.get(ROUND_ID);
        Assertions.assertTrue(retrievedRound.isPresent());
        Assertions.assertEquals(ROUND_ID, retrievedRound.get().roundId());
        Assertions.assertEquals(OWNER_ID, retrievedRound.get().ownerId());
    }

    @Test
    void testGetAllReturnsEmptyListWhenStoreIsEmpty() {
        var store = new RoundStore();
        List<TicTacToeRound> rounds = store.getAll();
        Assertions.assertTrue(rounds.isEmpty());
    }

    @Test
    void testGetAllReturnsAllRounds() {
        var store = new RoundStore();
        var round1 = new TicTacToeRoundImpl(ROUND_ID, OWNER_ID);
        var round2 = new TicTacToeRoundImpl(ROUND_ID + 1, OWNER_ID + 1);
        
        store.put(round1);
        store.put(round2);
        
        List<TicTacToeRound> rounds = store.getAll();
        Assertions.assertEquals(2, rounds.size());
        Assertions.assertTrue(rounds.contains(round1));
        Assertions.assertTrue(rounds.contains(round2));
    }

    @Test
    void testPutStoresRound() {
        var store = new RoundStore();
        var round = new TicTacToeRoundImpl(ROUND_ID, OWNER_ID);
        
        store.put(round);
        
        Optional<TicTacToeRound> retrievedRound = store.get(ROUND_ID);
        Assertions.assertTrue(retrievedRound.isPresent());
        Assertions.assertEquals(round, retrievedRound.get());
    }

    @Test
    void testPutOverwritesExistingRound() {
        var store = new RoundStore();
        var round1 = new TicTacToeRoundImpl(ROUND_ID, OWNER_ID);
        var round2 = new TicTacToeRoundImpl(ROUND_ID, OWNER_ID + 1);
        
        store.put(round1);
        store.put(round2);
        
        Optional<TicTacToeRound> retrievedRound = store.get(ROUND_ID);
        Assertions.assertTrue(retrievedRound.isPresent());
        Assertions.assertEquals(round2, retrievedRound.get());
        Assertions.assertEquals(OWNER_ID + 1, retrievedRound.get().ownerId());
    }
} 