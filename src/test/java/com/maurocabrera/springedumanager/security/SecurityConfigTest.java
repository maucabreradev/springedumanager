package com.maurocabrera.springedumanager.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenUnauthenticatedAccessAdmin_thenRedirectToLogin() throws Exception {
        mockMvc.perform(get("/admin/courses"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(roles = "ESTUDIANTE")
    void whenStudentAccessAdmin_thenForbidden() throws Exception {
        mockMvc.perform(get("/admin/courses"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "COORDINADOR")
    void whenCoordinadorAccessAdmin_thenOk() throws Exception {
        mockMvc.perform(get("/admin/courses"))
            .andExpect(status().isOk());
    }

    @Test
    void whenAccessLogin_thenOk() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("auth/login"));
    }

    @Test
    void whenAccessRegister_thenOk() throws Exception {
        mockMvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("auth/register"));
    }
}
