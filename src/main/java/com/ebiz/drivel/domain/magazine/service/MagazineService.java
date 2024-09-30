package com.ebiz.drivel.domain.magazine.service;

import com.ebiz.drivel.domain.magazine.entity.Magazine;
import com.ebiz.drivel.domain.magazine.repository.MagazineRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MagazineService {

    private final MagazineRepository magazineRepository;

    public List<Magazine> getRandomMagazine() {
        return magazineRepository.findAll();
    }

}
