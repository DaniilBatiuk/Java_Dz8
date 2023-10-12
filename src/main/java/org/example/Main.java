package org.example;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {

    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    private static long calculateFactorial(int number) {
        if (number < 0) {
            return -1;
        }
        if (number == 0) {
            return 1;
        }
        long factorial = 1;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return factorial;
    }

    private static void copyDirectory(Path source, Path destination) throws IOException {
        Files.walk(source)
                .forEach(sourcePath -> {
                    Path destPath = destination.resolve(source.relativize(sourcePath));
                    try {
                        Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static List<Path> findFilesWithWord(Path directory, String searchWord) throws IOException {
        List<Path> foundFiles = new ArrayList<>();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.contains(searchWord)) {
                            foundFiles.add(file);
                            break;
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return foundFiles;
    }

    private static Path mergeFiles(List<Path> files) throws IOException {
        Path mergedFilePath = Files.createTempFile("merged_", ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(mergedFilePath)) {
            for (Path file : files) {
                Files.lines(file).forEach(line -> {
                    try {
                        writer.write(line);
                        writer.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        return mergedFilePath;
    }

    private static void removeForbiddenWords(Path filePath) throws IOException {
        List<String> forbiddenWords = Files.readAllLines(Paths.get("src/main/java/org/example/forbidden_words.txt"));

        String fileContent = new String(Files.readAllBytes(filePath));
        for (String word : forbiddenWords) {
            fileContent = fileContent.replaceAll("\\b" + word + "\\b", "");
        }
        Files.write(filePath, fileContent.getBytes());
    }

    public static void main(String[] args) {

        //Task1

//        System.out.println("\nTask1");
//
//
//        int[] array = new int[3];
//        Random random = new Random();
//
//        Thread fillArrayThread = new Thread(() -> {
//            for (int i = 0; i < array.length; i++) {
//                array[i] = random.nextInt(100);
//                System.out.println(array[i]);
//            }
//        });
//
//        Thread sumThread = new Thread(() -> {
//            try {
//                fillArrayThread.join();
//                int sum = 0;
//                for (int num : array) {
//                    sum += num;
//                }
//                System.out.println("Сума елементів масиву: " + sum);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread averageThread = new Thread(() -> {
//            try {
//                fillArrayThread.join();
//                int sum = 0;
//                for (int num : array) {
//                    sum += num;
//                }
//                double average = (double) sum / array.length;
//                System.out.println("Середнє арифметичне значення в масиві: " + average);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        fillArrayThread.start();
//        sumThread.start();
//        averageThread.start();


        //Task2

//        System.out.println("\nTask2");
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        System.out.print("Введіть шлях до файлу: ");
//        String filePath = null;
//        try {
//            filePath = reader.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        File inputFile = new File(filePath);
//        File primesFile = new File("src/main/java/org/example/primes.txt");
//        File factorialsFile = new File("src/main/java/org/example/factorials.txt");
//
//        Thread fillFileThread = new Thread(() -> {
//            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(inputFile))) {
//                Random random = new Random();
//                for (int i = 0; i < 100; i++) {
//                    int randomNumber = random.nextInt(1000);
//                    fileWriter.write(randomNumber + "\n");
//                }
//                fileWriter.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread primesThread = new Thread(() -> {
//            try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
//                 BufferedWriter primesWriter = new BufferedWriter(new FileWriter(primesFile))) {
//                String line;
//                while ((line = fileReader.readLine()) != null) {
//                    int number = Integer.parseInt(line);
//                    if (isPrime(number)) {
//                        primesWriter.write(number + "\n");
//                    }
//                }
//                primesWriter.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        Thread factorialsThread = new Thread(() -> {
//            try (BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));
//                 BufferedWriter factorialsWriter = new BufferedWriter(new FileWriter(factorialsFile))) {
//                String line;
//                while ((line = fileReader.readLine()) != null) {
//                    int number = Integer.parseInt(line);
//                    long factorial = calculateFactorial(number);
//                    factorialsWriter.write(factorial + "\n");
//                }
//                factorialsWriter.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        fillFileThread.start();
//
//        try {
//            fillFileThread.join();
//            primesThread.start();
//            factorialsThread.start();
//            primesThread.join();
//            factorialsThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Потік для заповнення файлу завершив роботу.");
//        System.out.println("Потік для знаходження простих чисел завершив роботу. Результати записані в primes.txt.");
//        System.out.println("Потік для обчислення факторіалів завершив роботу. Результати записані в factorials.txt.");


        //Task3
//
//        System.out.println("\nTask3");


//        String sourceDirectoryPath = "src/main/java/org/example/destination_directory";
//        String destinationDirectoryPath = "src/main/java/org/example/destination_directory";
//
//        File sourceDirectory = new File(sourceDirectoryPath);
//        File destinationDirectory = new File(destinationDirectoryPath);
//
//        try {
//            copyDirectory(sourceDirectory.toPath(), destinationDirectory.toPath());
//
//            System.out.println("Директорія скопійована успішно.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Помилка копіювання директорії.");
//        }


        //Task4

        System.out.println("\nTask4");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до директорії: ");
        String directoryPath = scanner.nextLine();
        System.out.print("Введіть слово для пошуку: ");
        String searchWord = scanner.nextLine();
        scanner.close();

        try {
            List<Path> foundFiles = findFilesWithWord(Paths.get(directoryPath), searchWord);
            if (foundFiles.isEmpty()) {
                System.out.println("Файли з вказаним словом не знайдені.");
                return;
            }

            Path mergedFilePath = mergeFiles(foundFiles);

            removeForbiddenWords(mergedFilePath);

            System.out.println("Операція завершена успішно.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Помилка виконання операції.");
        }
    }
}