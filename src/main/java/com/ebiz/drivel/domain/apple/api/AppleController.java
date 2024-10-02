package com.ebiz.drivel.domain.apple.api;

import com.ebiz.drivel.domain.apple.dto.AppleSignInDTO;
import com.ebiz.drivel.domain.apple.service.AppleService;
import com.ebiz.drivel.domain.auth.dto.SignInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apple")
public class AppleController {

    private final AppleService appleService;

    @PostMapping("/signIn")
    public ResponseEntity<SignInDTO> signInWithApple(@RequestBody AppleSignInDTO appleSignInDTO) {
        SignInDTO signInDTO = appleService.signInWithApple(appleSignInDTO.getEmail(), appleSignInDTO.getFcmToken());
        return ResponseEntity.ok(signInDTO);
    }

}
