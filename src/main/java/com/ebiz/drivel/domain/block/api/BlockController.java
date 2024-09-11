package com.ebiz.drivel.domain.block;

import static com.ebiz.drivel.domain.profile.api.ProfileController.BLOCK_MEMBER_SUCCESS;

import com.ebiz.drivel.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockController {

    private final BlockService blockService;

    @PostMapping("/member")
    public ResponseEntity<BaseResponse> blockMember(@RequestBody BlockMemberDTO blockMemberDTO) {
        blockService.blockMember(blockMemberDTO);
        BaseResponse response = BaseResponse.builder()
                .message(BLOCK_MEMBER_SUCCESS)
                .build();
        return ResponseEntity.ok(response);
    }

}
