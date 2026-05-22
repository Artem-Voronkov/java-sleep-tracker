package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class SleeplessNightsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Бессонных ночей", 0);
        }
        return null;
    }
}
