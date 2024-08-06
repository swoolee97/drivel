package com.ebiz.drivel.domain.member.api;

import com.ebiz.drivel.domain.member.entity.Member;
import com.ebiz.drivel.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id){
        Optional<Member> member = memberRepository.findById(id);
        return member.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member){
        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member) {
        if (!memberRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Member existingMember = memberRepository.findById(id).orElse(null);
        if (existingMember == null) {
            return ResponseEntity.notFound().build();
        }

        existingMember.update(member);
        Member updatedMember = memberRepository.save(existingMember);
        return ResponseEntity.ok(updatedMember);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        if(!memberRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        memberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
