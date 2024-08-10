package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.profile.dto.UpdateProfileDTO;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileService profileService;

    @Test
    public void testUpdateProfile() throws Exception {
        // Arrange
        UpdateProfileDTO updateProfileDTO = UpdateProfileDTO.builder()
                .styleIds(List.of(1L, 2L))
                .themeIds(List.of(3L, 4L))
                .togetherIds(List.of(5L, 6L))
                .build();

        mockMvc.perform(patch("/profile/profileUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProfileDTO))
                        .with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk());

        verify(profileService, times(1)).updateMemberProfile(any(UpdateProfileDTO.class));
    }
}
