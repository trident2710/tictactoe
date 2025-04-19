package ua.kpi.softeng_course.tictactoe.server.store;

import ua.kpi.softeng_course.tictactoe.server.model.User;

import java.util.HashMap;
import java.util.Map;

public class KnownUsers {

    public static Map<String, User> KNOWN_USERS = new HashMap<>();

    static {
        KNOWN_USERS.put("testUser1", new User("testUser1", 1));
        KNOWN_USERS.put("testUser2", new User("testUser2", 2));
    }
}
