import java.io.*;
import java.nio.file.*;
import java.util.concurrent.*;

public class Task0901 {

    // спеціальний маркер завершення
    private static final String POISON = "__POISON__";

    static class Producer implements Runnable {
        private final Path inputFile;
        private final BlockingQueue<String> queue;
        private final long t1Millis;

        Producer(Path inputFile, BlockingQueue<String> queue, long t1Millis) {
            this.inputFile = inputFile;
            this.queue = queue;
            this.t1Millis = t1Millis;
        }

        @Override
        public void run() {
            try (BufferedReader br = Files.newBufferedReader(inputFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
                    Thread.sleep(t1Millis);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // треба 2 "poison pill" — бо 2 споживачі
                try {
                    queue.put(POISON);
                    queue.put(POISON);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    static class Consumer implements Runnable {
        private final BlockingQueue<String> queue;
        private final long processMillis;
        private final Path outFile;

        Consumer(BlockingQueue<String> queue, long processMillis, Path outFile) {
            this.queue = queue;
            this.processMillis = processMillis;
            this.outFile = outFile;
        }

        @Override
        public void run() {
            try (BufferedWriter bw = Files.newBufferedWriter(
                    outFile,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            )) {
                while (true) {
                    String line = queue.take();
                    if (POISON.equals(line)) break;

                    Thread.sleep(processMillis); // "обробка" за T2 або T3
                    bw.write(line);
                    bw.newLine();
                    bw.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // ---- Налаштування ----
        Path fileF = Paths.get("F.txt");          // вхідний файл
        Path fileOut2 = Paths.get("out_T2.txt");  // вихід для потоку з T2
        Path fileOut3 = Paths.get("out_T3.txt");  // вихід для потоку з T3

        long T1 = 200; // мс між читанням рядків (T1)
        long T2 = 500; // мс "обробка" споживач 1 (T2)
        long T3 = 800; // мс "обробка" споживач 2 (T3)

        BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);

        Thread producer = new Thread(new Producer(fileF, queue, T1), "Producer");
        Thread consumerT2 = new Thread(new Consumer(queue, T2, fileOut2), "Consumer-T2");
        Thread consumerT3 = new Thread(new Consumer(queue, T3, fileOut3), "Consumer-T3");

        producer.start();
        consumerT2.start();
        consumerT3.start();

        producer.join();
        consumerT2.join();
        consumerT3.join();

        System.out.println("Готово. Результати у файлах: " + fileOut2 + " і " + fileOut3);
    }
}
