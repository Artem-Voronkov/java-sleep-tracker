package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class MinDurationFunctionTest {

    @Test
    void testMinWithMultipleSessions() {
        // Given: сессии с длительностями 60, 120 и 30 минут → минимум = 30
        List<SleepingSession> sessions = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 1, 23, 0)),  // 60 мин
                new SleepingSession(SleepQuality.NORMAL,
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 1, 0)),   // 120 мин
                new SleepingSession(SleepQuality.BAD,
                        LocalDateTime.of(2025, 10, 3, 14, 0),
                        LocalDateTime.of(2025, 10, 3, 14, 30))  // 30 мин
        );
        MinDurationFunction function = new MinDurationFunction();

        // When
        SleepAnalysisResult result = function.apply(sessions);

        // Then
        assertEquals("Минимальная продолжительность сессии сна(в минутах): 30", result.toString());
    }

    @Test
    void testMinEmptyList() {
        // Given: пустой список сессий
        List<SleepingSession> emptySessions = List.of();
        MinDurationFunction function = new MinDurationFunction();

        // When
        SleepAnalysisResult result = function.apply(emptySessions);

        // Then: для пустого списка возвращаем 0 или специальное сообщение
        assertEquals("Минимальная продолжительность сессии сна(в минутах): 0", result.toString());
    }

    @Test
    void testMinSingleSession() {
        // Given: одна сессия длительностью 90 минут
        List<SleepingSession> singleSession = List.of(
                new SleepingSession(SleepQuality.GOOD,
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 0, 30))
        );
        MinDurationFunction function = new MinDurationFunction();

        // When
        SleepAnalysisResult result = function.apply(singleSession);

        // Then
        assertEquals("Минимальная продолжительность сессии сна(в минутах): 90", result.toString());
    }
}

