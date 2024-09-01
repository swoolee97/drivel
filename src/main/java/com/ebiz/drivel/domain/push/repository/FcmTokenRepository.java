package com.ebiz.drivel.domain.push.repository;

import com.ebiz.drivel.domain.push.entity.FcmToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FcmTokenRepository extends MongoRepository<FcmToken, String> {
    FcmToken findByMemberId(Long memberId);

    void deleteAllByMemberId(Long memberId);
}
