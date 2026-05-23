package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class BadQualityCountFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult("Сессия с плохим качеством сна(в минутах)", count);
    }
}
