package ua.kpi.softeng_course.tictactoe.server.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<UserPO> userRowMapper = (rs, rowNum) -> 
        new UserPO(rs.getInt("id"), rs.getString("username"));

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserPO> findByUsername(String username) {
        String sql = "SELECT id, username FROM Users WHERE username = ?";
        List<UserPO> users = jdbcTemplate.query(sql, userRowMapper, username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public Optional<UserPO> findById(int id) {
        String sql = "SELECT id, username FROM Users WHERE id = ?";
        List<UserPO> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public UserPO save(String username) {
        String sql = "INSERT INTO Users (username) VALUES (?)";
        jdbcTemplate.update(sql, username);
        
        // Get the generated ID
        String selectSql = "SELECT id, username FROM Users WHERE username = ?";
        List<UserPO> users = jdbcTemplate.query(selectSql, userRowMapper, username);
        return users.get(0);
    }

    public List<UserPO> findAll() {
        String sql = "SELECT id, username FROM Users";
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
