package com.ebiz.drivel.domain.token.repository;

import com.ebiz.drivel.domain.token.entity.BlackList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends MongoRepository<BlackList, String> {
}
