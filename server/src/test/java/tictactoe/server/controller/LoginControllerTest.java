package tictactoe.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ua.kpi.softeng_course.tictactoe.server.api.LoginRequest;
import ua.kpi.softeng_course.tictactoe.server.api.LoginResponse;
import ua.kpi.softeng_course.tictactoe.server.controller.LoginController;
import ua.kpi.softeng_course.tictactoe.server.model.User;
import ua.kpi.softeng_course.tictactoe.server.store.KnownUsers;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {LoginControllerTest.TestConfig.class})
@ComponentScan(basePackages = "ua.kpi.softeng_course.tictactoe.server.controller")
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
    );

    @BeforeEach
    void setUp() {
        KnownUsers.KNOWN_USERS.values();
    }

    @Test
    void login_WhenUserExists_ReturnsOkWithSessionId() throws Exception {
        // Given
        String username = "testUser1";
        LoginRequest request = new LoginRequest(username);
        User expectedUser = KnownUsers.KNOWN_USERS.get(username);

        // When/Then
        String responseContent = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        LoginResponse response = objectMapper.readValue(responseContent, LoginResponse.class);
        assertEquals(expectedUser, response.user());
        assertNotNull(response.sessionId());
        assertTrue(UUID_PATTERN.matcher(response.sessionId()).matches());
    }

    @Test
    void login_WhenUserDoesNotExist_ReturnsNotFound() throws Exception {
        // Given
        String nonExistentUsername = "nonExistentUser";
        LoginRequest request = new LoginRequest(nonExistentUsername);

        // When/Then
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void login_WhenSameUserLogsInTwice_ReturnsDifferentSessionIds() throws Exception {
        // Given
        String username = "testUser1";
        LoginRequest request = new LoginRequest(username);

        // When
        String responseContent1 = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String responseContent2 = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        LoginResponse response1 = objectMapper.readValue(responseContent1, LoginResponse.class);
        LoginResponse response2 = objectMapper.readValue(responseContent2, LoginResponse.class);
        
        assertNotEquals(response1.sessionId(), response2.sessionId());
        assertEquals(response1.user(), response2.user());
    }

    @Configuration
    @ComponentScan(basePackages = "ua.kpi.softeng_course.tictactoe.server")
    public static class TestConfig {
    }
}