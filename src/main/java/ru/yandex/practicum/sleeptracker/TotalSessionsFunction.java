package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long count = sessions.stream().count();
        return new SleepAnalysisResult("Всего сессий сна", count);
    }
}
