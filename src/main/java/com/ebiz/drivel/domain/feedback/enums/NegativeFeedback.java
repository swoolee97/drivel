package com.ebiz.drivel.domain.feedback.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NegativeFeedback implements BaseFeedback {
    RUDE(1, "무례하거나 배려심이 부족해요.", -0.5),
    OFFENSIVE_JOKES(2, "불쾌한 농담이나 인신공격적인 말을 해요.", -0.5),
    INAPPROPRIATE_LANGUAGE(3, "욕설•비방•혐오적인 표현이나 부적절한 언어를 사용해요.", -0.5),
    LATE(4, "약속시간에 늦었어요.", -0.5),
    SEXUAL_HARASSMENT(5, "성적 수치심을 유발하는 발언이나 행동을 해요.", -1),
    RECKLESS_DRIVING(6, "운전이 불안하고 난폭해요.", -1),
    TRAFFIC_VIOLATION(7, "신호위반, 과속 등 교통 법규를 위반해요.", -2),
    OTHER(8, "기타: 직접 작성", -0.5);

    private final int id;
    private final String description;
    private final double score;

    public static NegativeFeedback findById(Integer id) {
        return Arrays.stream(NegativeFeedback.values())
                .filter(positiveFeedback -> positiveFeedback.getId() == id)
                .findFirst().get();
    }

}
