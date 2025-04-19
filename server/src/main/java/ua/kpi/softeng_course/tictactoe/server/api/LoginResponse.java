package ua.kpi.softeng_course.tictactoe.server.api;

import ua.kpi.softeng_course.tictactoe.server.model.User;

public record LoginResponse(User user, String sessionId) {
}
