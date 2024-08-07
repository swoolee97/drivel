package com.ebiz.drivel.domain.profile.application;
import com.ebiz.drivel.domain.auth.application.UserDetailsServiceImpl;
import com.ebiz.drivel.domain.member.entity.*;
import com.ebiz.drivel.domain.onboarding.StyleRepository;
import com.ebiz.drivel.domain.onboarding.entity.Style;
import com.ebiz.drivel.domain.profile.dto.UpdateStyleDTO;
import com.ebiz.drivel.domain.profile.service.ProfileService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;

public class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private StyleRepository styleRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateMemberStyle() {
        // Given
        UpdateStyleDTO updateStyleDTO = UpdateStyleDTO.builder()
                .styleIds(Arrays.asList(1L, 2L))
                .build();

        Style style1 = mock(Style.class);
        Style style2 = mock(Style.class);

        when(style1.getId()).thenReturn(1L);
        when(style1.getDisplayName()).thenReturn("스타일1");

        when(style2.getId()).thenReturn(2L);
        when(style2.getDisplayName()).thenReturn("스타일2");

        List<Style> styles = Arrays.asList(style1, style2);

        when(styleRepository.findAllById(anyList())).thenReturn(styles);

        doNothing().when(member).updateStyles(anyList());

        when(userDetailsService.getMemberByContextHolder()).thenReturn(member);

        profileService.updateMemberStyle(updateStyleDTO);

        verify(member).updateStyles(styles);
        verify(entityManager).flush();
    }
}
