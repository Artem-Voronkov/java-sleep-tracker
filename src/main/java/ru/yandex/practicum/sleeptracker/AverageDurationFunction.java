package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AverageDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        double avg = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .average()
                .orElse(0);
        return new SleepAnalysisResult("Средняя продолжительность сессии(в минутах)", avg);
    }
}
