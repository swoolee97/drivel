package com.ebiz.drivel.domain.feedback.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PositiveFeedback implements BaseFeedback {
    ON_TIME(1, "약속시간을 잘 지켜요.", 0.5),
    SAFE_DRIVER(2, "운전이 안전하고 신중해요.", 0.5),
    KNOWS_ROUTE(3, "경로를 잘 파악하고 있어요.", 0.5),
    KNOWS_SCENERY(4, "주변 경치나 명소에 대해 잘 알고 있어요.", 0.5),
    HELPFUL(5, "필요할 때 도움을 잘 줘요.", 0.5),
    KIND(6, "친절하고 배려심이 있어요.", 0.5),
    FUN_CONVERSATION(7, "대화가 즐겁고 유쾌해요.", 0.5),
    EASY_GOING(8, "새로운 사람들과 쉽게 어울려요.", 0.5);

    private final int id;
    private final String description;
    private final double score;

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public int getId() {
        return id;
    }


    public static PositiveFeedback findById(int id) {
        return Arrays.stream(PositiveFeedback.values())
                .filter(positiveFeedback -> positiveFeedback.getId() == id)
                .findFirst().get();
    }

}
