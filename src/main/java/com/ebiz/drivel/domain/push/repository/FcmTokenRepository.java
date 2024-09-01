package com.ebiz.drivel.domain.push.repository;

import com.ebiz.drivel.domain.push.entity.FcmToken;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmTokenRepository extends MongoRepository<FcmToken, String> {
    Optional<FcmToken> findByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
