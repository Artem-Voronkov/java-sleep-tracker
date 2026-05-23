package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;

public class SleeplessNightsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) return new SleepAnalysisResult("Бессонных ночей", 0);

        LocalDate startDate = sessions.get(0).getStartTime().toLocalDate();
        LocalDate endDate = sessions.get(sessions.size() - 1).getEndTime().toLocalDate();

        if (sessions.get(0).getStartTime().toLocalTime().isAfter(LocalTime.NOON)) {
            startDate = startDate.plusDays(1);
        }

        int totalNights = (int) Period.between(startDate, endDate).getDays();
        int nightsWithSleep = 0;

        for (SleepingSession session : sessions) {
            LocalDateTime start = session.getStartTime();
            LocalDateTime end = session.getEndTime();

            boolean sleepsAtNight =
                    (start.toLocalTime().isBefore(LocalTime.of(6, 0)) &&
                            end.toLocalTime().isAfter(LocalTime.MIDNIGHT)) ||
                            (start.toLocalDate().isBefore(end.toLocalDate()));

            if (sleepsAtNight) {
                nightsWithSleep++;
            }
        }

        int sleeplessNights = totalNights - nightsWithSleep;
        return new SleepAnalysisResult("Бессонных ночей", Math.max(0, sleeplessNights));
    }
}

