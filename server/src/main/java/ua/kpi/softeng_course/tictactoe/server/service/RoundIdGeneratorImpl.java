package ua.kpi.softeng_course.tictactoe.server.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RoundIdGeneratorImpl implements RoundIdGenerator {

    private final Random random = new Random();

    @Override
    public int nextId() {
        return random.nextInt();
    }
}
