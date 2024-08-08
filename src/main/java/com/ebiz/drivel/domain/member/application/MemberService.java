package com.ebiz.drivel.domain.member.application;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import com.ebiz.drivel.domain.profile.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean isExistsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public ResponseEntity<String> checkNickname(String nickname) {
        // 유효성 검사 : 닉네임이 null이거나 빈 문자열일 때
        if (nickname == null || nickname.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("닉네임이 빈 문자열이거나 유효하지 않습니다.");
        }

        // 닉네임 중복 확인
        boolean isNicknameUsed = memberRepository.existsByNickname(nickname);

        if (isNicknameUsed) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임이 이미 사용 중입니다.");
        }

        return ResponseEntity.ok("사용 가능한 닉네임입니다.");
    }


    public ResponseEntity<ProfileDTO> getProfileById(Long id){
        Optional<Member> optionalMember = memberRepository.findById(id);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            ProfileDTO profileDTO = ProfileDTO.from(member);
            return ResponseEntity.ok(profileDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

