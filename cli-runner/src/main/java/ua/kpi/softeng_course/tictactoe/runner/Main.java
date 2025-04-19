//package ua.kpi.softeng_course.tictactoe.runner;
//
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        System.out.println("Welcome to Tic Tac Toe!");
//        System.out.println("Wanna start a new round? (yes/no)");
//        var scanner = new Scanner(System.in);
//        var answer = scanner.nextLine();
//
//        if (answer.equalsIgnoreCase("yes")) {
//            var idGenerator = new RoundIdGeneratorImpl();
//            var model = new TicTacToeRoundModelImpl(idGenerator);
//
//            while (model.nextAction().isPresent()) {
//                var nextAction = model.nextAction().get();
//                var action = nextAction.getAction();
//
//                switch (action) {
//                    case START -> {
//                        System.out.println("Starting new round...");
//                        model.start();
//                        System.out.println("New round: " + model.roundId().get());
//                        printBoard(model);
//                    }
//                    case MOVE_X, MOVE_O -> {
//                        System.out.println("Player " + (action == NextAction.Action.MOVE_X ? "X" : "O") + "'s turn");
//                        System.out.println("Enter row (0-2) and column (0-2) separated by space:");
//                        try {
//                            var input = scanner.nextLine().split(" ");
//                            int row = Integer.parseInt(input[0]);
//                            int col = Integer.parseInt(input[1]);
//
//                            if (!model.makeMove(row, col)) {
//                                System.out.println("Invalid move! Try again.");
//                                continue;
//                            }
//
//                            printBoard(model);
//                            var result = model.getGameResult();
//                            if (result.isGameOver()) {
//                                if (result.getWinner() != null) {
//                                    System.out.println("Player " + result.getWinner() + " wins!");
//                                } else {
//                                    System.out.println("It's a draw!");
//                                }
//                            }
//                        } catch (Exception e) {
//                            System.out.println("Invalid input! Please enter two numbers between 0 and 2 separated by space.");
//                        }
//                    }
//                    case FINISH -> {
//                        System.out.println("Finishing round...");
//                        model.finish();
//                    }
//                }
//            }
//        }
//    }
//
//    private static void printBoard(TicTacToeRoundModelImpl model) {
//        System.out.println("Current board:");
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                var cell = model.getCell(i, j);
//                System.out.print(cell == null ? "-" : cell);
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
//    }
//}