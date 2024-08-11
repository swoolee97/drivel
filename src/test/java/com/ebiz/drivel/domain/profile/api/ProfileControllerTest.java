package com.ebiz.drivel.domain.profile.api;

import com.ebiz.drivel.domain.member.application.MemberService;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import com.ebiz.drivel.domain.profile.dto.ReportProfileDTO;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import com.ebiz.drivel.domain.profile.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private ReportService reportService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void checkingProfile() throws Exception {
        Long memberId = 1L;

        ProfileDTO mockProfileDTO = ProfileDTO.builder()
                .nickname("testUser")
                .imagePath("/images/test.jpg")
                .carModel("과학 5호기")
                .carCareer(5)
                .gender("남")
                .description("테스트입니닷.")
                .birth(new Date())
                .regions(Collections.emptyList())
                .styles(Collections.emptyList())
                .themes(Collections.emptyList())
                .togethers(Collections.emptyList())
                .build();

        when(memberService.getProfileById(memberId)).thenReturn(ResponseEntity.ok(mockProfileDTO));

        mockMvc.perform(get("/profile/" + memberId)
                        .with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value(mockProfileDTO.getNickname()))
                .andExpect(jsonPath("$.imagePath").value(mockProfileDTO.getImagePath()))
                .andExpect(jsonPath("$.carModel").value(mockProfileDTO.getCarModel()))
                .andExpect(jsonPath("$.carCareer").value(mockProfileDTO.getCarCareer()))
                .andExpect(jsonPath("$.gender").value(mockProfileDTO.getGender()))
                .andExpect(jsonPath("$.description").value(mockProfileDTO.getDescription()))
                .andExpect(jsonPath("$.birth").exists())
                .andExpect(jsonPath("$.regions").isEmpty())
                .andExpect(jsonPath("$.styles").isEmpty())
                .andExpect(jsonPath("$.themes").isEmpty())
                .andExpect(jsonPath("$.togethers").isEmpty())
                .andReturn();
    }
    @Test
    void reportProfileSuccess() throws Exception {
        ReportProfileDTO dto = new ReportProfileDTO(1L, "부적절한 콘텐츠", "음란물 게시");

        // Mock the service call to succeed
        doNothing().when(reportService).reportProfile(dto);

        mockMvc.perform(post("/profile/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)) // Serialize DTO to JSON
                        .with(user("user").password("password").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("유저가 신고되었습니다."));
    }
}


