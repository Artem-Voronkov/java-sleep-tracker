package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AverageDurationFunctionTest {

    @Test
    void testAverageWithValidSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 1, 23, 0)),
                new SleepingSession(SleepQuality.NORMAL,
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 1, 0)),
                new SleepingSession(SleepQuality.BAD,
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 17, 0))
        );
        AverageDurationFunction function = new AverageDurationFunction();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Средняя продолжительность сессии(в минутах): 120.0", result.toString());
    }

    @Test
    void testAverageEmptyList() {
        List<SleepingSession> emptySessions = List.of();
        AverageDurationFunction function = new AverageDurationFunction();

        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Средняя продолжительность сессии(в минутах): 0.0", result.toString());
    }

    @Test
    void testAverageSingleSession() {
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 0, 30))
        );
        AverageDurationFunction function = new AverageDurationFunction();

        SleepAnalysisResult result = function.apply(singleSession);

        assertEquals("Средняя продолжительность сессии(в минутах): 90.0", result.toString());
    }
}

