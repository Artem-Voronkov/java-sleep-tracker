package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MaxDurationFunctionTest {

    @Test
    void testMaxWithMultipleSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 1, 23, 0)),  // 60 мин
                new SleepingSession(SleepQuality.NORMAL,
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0)),    // 480 мин (8 часов)
                new SleepingSession(SleepQuality.BAD,
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 16, 0))   // 120 мин
        );
        MaxDurationFunction function = new MaxDurationFunction();

        SleepAnalysisResult result = function.apply(sessions);

        assertEquals("Максимальная продолжительность сессии сна(в минутах): 480", result.toString());
    }

    @Test
    void testMaxEmptyList() {
        List<SleepingSession> emptySessions = List.of();
        MaxDurationFunction function = new MaxDurationFunction();

        SleepAnalysisResult result = function.apply(emptySessions);

        assertEquals("Максимальная продолжительность сессии сна(в минутах): 0", result.toString());
    }

    @Test
    void testMaxSingleSession() {
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 1, 0),
                        LocalDateTime.of(2025, 10, 1, 3, 30))
        );
        MaxDurationFunction function = new MaxDurationFunction();

        SleepAnalysisResult result = function.apply(singleSession);

        assertEquals("Максимальная продолжительность сессии сна(в минутах): 150", result.toString());
    }
}

