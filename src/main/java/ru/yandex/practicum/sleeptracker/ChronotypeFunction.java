package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;

public class ChronotypeFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long owlCount = 0;
        long larkCount = 0;
        long doveCount = 0;

        for (SleepingSession session: sessions) {
            LocalTime sleepTime = session.getStartTime().toLocalTime();
            LocalTime wakeTime = session.getEndTime().toLocalTime();

            if (session.getStartTime().toLocalDate()
                    .equals(session.getEndTime().toLocalDate()) &&
                    sleepTime.isAfter(LocalTime.of(12, 0))) {
                continue;
            }

            if (sleepTime.isAfter(LocalTime.of(23, 0))) {
                if (wakeTime.isAfter(LocalTime.of(9, 0))) {
                    owlCount++;
                } else {
                    doveCount++;
                }
            } else if (sleepTime.isBefore(LocalTime.of(22, 0))) {
                if (wakeTime.isBefore(LocalTime.of(7, 0))) {
                    larkCount++;
                } else {
                    doveCount++;
                }
            } else {
                doveCount++;
            }
    }
        Chronotype result;
        if (owlCount > larkCount && owlCount > doveCount) {
            result = Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > doveCount) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.DOVE;
        }

        return new SleepAnalysisResult("Хронотип пользователя", result);
    }
}
