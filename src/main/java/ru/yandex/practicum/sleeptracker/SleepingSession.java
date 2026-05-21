package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private SleepQuality quality;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public SleepingSession(SleepQuality quality, LocalDateTime startTime,
                           LocalDateTime endTime) {
        this.quality = quality;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SleepQuality getQuality() {
        return quality;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public long getDurationInMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }

}

