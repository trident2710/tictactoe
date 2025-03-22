package ua.kpi.softeng_course.tictactoe.runner;

import ua.kpi.softeng_course.tictactoe.model.RoundIdGeneratorImpl;
import ua.kpi.softeng_course.tictactoe.model.TicTacToeRoundModelImpl;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello tictactoe!");
        System.out.println("Wanna start round?");
        var answer = System.console().readLine();

        if (answer.equalsIgnoreCase("yes")) {
            var idGenerator = new RoundIdGeneratorImpl();
            var model = new TicTacToeRoundModelImpl(idGenerator);

            while (model.nextAction().isPresent()) {
                var nextAction = model.nextAction().get();
                var action = nextAction.getAction();
                switch (action) {
                    case START -> {
                        System.out.println("Starting?");
                        if (System.console().readLine().equals("yes")) {
                            model.start();
                            System.out.println("New round: " + model.roundId().get());
                        }
                    }
                    case FINISH -> {
                        System.out.println("Finishing?");
                        if (System.console().readLine().equals("yes")) {
                            model.finish();
                        }
                    }
                }
            }
        }

    }
}