package ua.kpi.softeng_course.tictactoe.model;

import java.util.Random;

public class RoundIdGeneratorImpl implements RoundIdGenerator {

    private final Random random = new Random();

    @Override
    public int nextId() {
        return random.nextInt();
    }
}
