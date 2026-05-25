package ru.yandex.practicum.sleeptracker;


import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;


class SleepTrackerAppTest {

    @Test
    void testProcessLineWithValidData() {
        SleepTrackerApp app = new SleepTrackerApp();
        String validLine = "01.10.25 23:00;02.10.25 07:00;GOOD";

        SleepingSession session = app.processLine(validLine,
                DateTimeFormatter.ofPattern("dd.MM.yy HH:mm"));

        assertNotNull(session, "Сессия не должна быть null");
        assertEquals(SleepQuality.GOOD, session.getQuality(),
                "Качество сна должно быть GOOD");
        assertEquals(LocalDateTime.of(2025, 10, 1, 23, 0),
                session.getStartTime(), "Неверное время начала");
        assertEquals(LocalDateTime.of(2025, 10, 2, 7, 0),
                session.getEndTime(), "Неверное время окончания");
        assertEquals(480, session.getDurationInMinutes(),
                "Длительность должна быть 480 минут (8 часов)");
    }

    @Test
    void testProcessLineWithInvalidFormat() {
        SleepTrackerApp app = new SleepTrackerApp();
        String invalidLine = "01.10.25 23:00";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> app.processLine(invalidLine,
                        DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")),
                "Должен выбрасывать исключение при неверном формате строки"
        );

        assertTrue(exception.getMessage().contains("Неверный формат строки"),
                "Сообщение об ошибке должно указывать на неверный формат");
    }

    @Test
    void testProcessLineWithInvalidQuality() {
        SleepTrackerApp app = new SleepTrackerApp();
        String invalidQualityLine = "01.10.25 23:00;02.10.25 07:00;EXCELLENT";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> app.processLine(invalidQualityLine,
                        DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")),
                "Должен выбрасывать исключение при недопустимом качестве сна"
        );

        assertTrue(exception.getMessage().contains("Неверное значение качества сна"),
                "Сообщение об ошибке должно указывать на недопустимое качество сна");
        assertTrue(exception.getMessage().contains("EXCELLENT"),
                "В сообщении должна быть указана некорректная строка качества");
    }
}
