package com.ebiz.drivel.domain.meeting.util;

import java.util.Calendar;
import java.util.Date;

public class AgeCalculator {

    public static String getAgeGroup(Date birthDate) {
        // 현재 날짜와 출생 날짜를 사용하여 나이 계산
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birthDate);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        // 생일이 지나지 않았다면 나이에서 1을 뺀다
        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        // 연령대 계산
        int ageGroup = (age / 10) * 10;

        return ageGroup + "대";
    }
}
