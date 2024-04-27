package ru.nonoka.securityadminka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.nonoka.securityadminka.dto.JwtRequest;
import ru.nonoka.securityadminka.service.AuthService;
import ru.nonoka.securityadminka.utils.JwtTokenUtils;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    private ObjectMapper ob;

    @Test
    void authorizationSuccessfulIsOk() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("admin", "100");

        mockMvc.perform(post("/api/v1/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ob.writeValueAsString(jwtRequest)))
                .andExpect(status().isOk());
        verify(authService, times(1)).authorization(jwtRequest);
    }

    @Test
    void authorizationFailedAdminNoteFoundIsForbidden() throws Exception {
        JwtRequest jwtRequest = new JwtRequest("invalid_username", "invalid_password");

        mockMvc.perform(post("/api/v1/authorization")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ob.writeValueAsString(jwtRequest)))
                .andExpect(status().isForbidden());
        verify(authService, times(1)).authorization(jwtRequest);
    }
}