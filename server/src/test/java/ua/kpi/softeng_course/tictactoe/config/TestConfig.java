package ua.kpi.softeng_course.tictactoe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.kpi.softeng_course.tictactoe.server.store.SessionStore;

@Configuration
@ComponentScan(basePackages = "ua.kpi.softeng_course.tictactoe.server")
public class TestConfig {
}
