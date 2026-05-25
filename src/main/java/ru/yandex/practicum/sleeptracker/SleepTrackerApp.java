package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;

public class SleepTrackerApp {
    private List<SleepAnalysisFunction> analysisFunctions = new ArrayList<>();

    public SleepTrackerApp() {
        analysisFunctions.add(new TotalSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AverageDurationFunction());
        analysisFunctions.add(new BadQualityCountFunction());
        analysisFunctions.add(new SleeplessNightsFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public void run(String filePath) {
        System.out.printf("Попытка загрузить файл: %s%n", filePath);

        try {
            List<SleepingSession> sessions = loadSleepData(filePath);
            System.out.println("Анализ данных о сне...\n");

            analysisFunctions.stream()
                    .map(function -> function.apply(sessions))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.err.printf("Ошибка при чтении файла: %s%n", filePath);
            System.err.printf("Детальная ошибка: %s%n", e.getMessage());
        }
    }

    public List<SleepingSession> loadSleepData(String filePath) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        System.out.printf("Поиск файла: %s%n", filePath);

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            System.out.printf("Файл найден в файловой системе: %s%n",path.toAbsolutePath());
            return Files.lines(path)
                    .map(line -> processLine(line, formatter))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } else {
            System.out.println("Файл не найден в файловой системе");
        }

        String resourcePath = filePath.startsWith("/") ? filePath : "/" + filePath;
        InputStream inputStream = SleepTrackerApp.class.getResourceAsStream(resourcePath);

        if (inputStream != null) {
            System.out.printf("Файл найден в resources: %s%n", resourcePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines()
                        .map(line -> processLine(line, formatter))
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            }
        } else {
            System.out.println("Файл не найден в resources");
        }

        throw new FileNotFoundException(String.format(
                "Файл не найден: %s (ни в файловой системе, ни в resources)",filePath));
    }

    public SleepingSession processLine(String line, DateTimeFormatter formatter) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException(String.format(
                    "Неверный формат строки: ожидается 3 поля, найдено %d в строке: %s",
                    parts.length, line));
        }

        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);

        SleepQuality quality;
        try {
            quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format(
                    "Неверное значение качества сна: '%s' (допустимые: GOOD, NORMAL, BAD) в строке: %s",
                    parts[2], line));
        }

        return new SleepingSession(quality, start, end);
    }

    public static void main(String[] args) {

        String filePath = args.length > 0 ? args[0] : "sleep_log.txt";

        System.out.printf("Используемый путь к файлу: %s%n", filePath);

        SleepTrackerApp app = new SleepTrackerApp();
        app.run(filePath);
    }
}
