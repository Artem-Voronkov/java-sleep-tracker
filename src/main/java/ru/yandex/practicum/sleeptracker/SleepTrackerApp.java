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
        // Добавляем все функции анализа
        analysisFunctions.add(new TotalSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AverageDurationFunction());
        analysisFunctions.add(new BadQualityCountFunction());
        analysisFunctions.add(new SleeplessNightsFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public void run(String filePath) {
        System.out.println("Попытка загрузить файл: " + filePath);

        try {
            List<SleepingSession> sessions = loadSleepData(filePath);
            System.out.println("Анализ данных о сне...\n");

            analysisFunctions.stream()
                    .map(function -> function.apply(sessions))
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + filePath);
            System.err.println("Детальная ошибка: " + e.getMessage());
        }
    }

    public List<SleepingSession> loadSleepData(String filePath) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        System.out.println("Поиск файла: " + filePath);

        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            System.out.println("Файл найден в файловой системе: " + path.toAbsolutePath());
            return Files.lines(path)
                    .map(line -> processLine(line, formatter))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        } else {
            System.out.println("Файл не найден в файловой системе");
        }

        String resourcePath = filePath.startsWith("/") ? filePath : "/" + filePath;
        InputStream inputStream = SleepTrackerApp.class.getResourceAsStream(resourcePath);

        if (inputStream != null) {
            System.out.println("Файл найден в resources: " + resourcePath);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines()
                        .map(line -> processLine(line, formatter))
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            }
        } else {
            System.out.println("Файл не найден в resources");
        }

        throw new FileNotFoundException("Файл не найден: " + filePath +
                " (ни в файловой системе, ни в resources)");
    }

    public SleepingSession processLine(String line, DateTimeFormatter formatter) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Неверный формат строки: ожидается 3 поля, найдено " + parts.length + " в строке: " + line);
        }

        LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime end = LocalDateTime.parse(parts[1], formatter);

        SleepQuality quality;
        try {
            quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Неверное значение качества сна: '" + parts[2] +
                            "' (допустимые: GOOD, NORMAL, BAD) в строке: " + line);
        }

        return new SleepingSession(quality, start, end);
    }

    public static void main(String[] args) {

        String filePath = args.length > 0 ? args[0] : "sleep_log.txt";

        System.out.println("Используемый путь к файлу: " + filePath);

        SleepTrackerApp app = new SleepTrackerApp();
        app.run(filePath);
    }
}
