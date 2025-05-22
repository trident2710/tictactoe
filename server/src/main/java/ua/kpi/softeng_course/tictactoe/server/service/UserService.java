package ua.kpi.softeng_course.tictactoe.server.service;

import org.springframework.stereotype.Service;
import ua.kpi.softeng_course.tictactoe.server.model.User;
import ua.kpi.softeng_course.tictactoe.server.persistence.UserDao;
import ua.kpi.softeng_course.tictactoe.server.persistence.UserPO;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username)
                .map(this::convertToUser);
    }

    public Optional<User> findById(int id) {
        return userDao.findById(id)
                .map(this::convertToUser);
    }

    private User convertToUser(UserPO userPO) {
        return new User(userPO.username(), userPO.id());
    }

    // This method is only used for testing purposes
    public User createTestUser(String username) {
        UserPO userPO = new UserPO(1, username); // Using 1 as a test ID
        return convertToUser(userPO);
    }
} 