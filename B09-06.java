import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Task0906 {

    static class Viewer implements Runnable {
        private final int id;
        private final long arrivalDelayMs;     // момент приходу від t=0
        private final long t1Ms;               // час проходу через турнікет
        private final long openTimeAbs;        // абсолютний час відкриття турнікетів
        private final long matchStartAbs;      // абсолютний час початку матчу
        private final Semaphore turnstiles;
        private final AtomicInteger notInTime;
        private final AtomicInteger inTime;

        Viewer(int id,
               long arrivalDelayMs,
               long t1Ms,
               long openTimeAbs,
               long matchStartAbs,
               Semaphore turnstiles,
               AtomicInteger notInTime,
               AtomicInteger inTime) {
            this.id = id;
            this.arrivalDelayMs = arrivalDelayMs;
            this.t1Ms = t1Ms;
            this.openTimeAbs = openTimeAbs;
            this.matchStartAbs = matchStartAbs;
            this.turnstiles = turnstiles;
            this.notInTime = notInTime;
            this.inTime = inTime;
        }

        @Override
        public void run() {
            try {
                // чекати свій момент приходу
                Thread.sleep(arrivalDelayMs);

                // якщо прийшов раніше відкриття — чекати відкриття
                long now = System.currentTimeMillis();
                if (now < openTimeAbs) {
                    Thread.sleep(openTimeAbs - now);
                }

                // якщо вже почався матч — запізнився
                now = System.currentTimeMillis();
                if (now >= matchStartAbs) {
                    notInTime.incrementAndGet();
                    return;
                }

                // пробуємо зайняти один із N турнікетів, але не довше ніж до старту матчу
                long timeLeftToStart = matchStartAbs - now;
                boolean acquired = turnstiles.tryAcquire(timeLeftToStart, java.util.concurrent.TimeUnit.MILLISECONDS);
                if (!acquired) {
                    notInTime.incrementAndGet();
                    return;
                }

                try {
                    // починаємо проходити негайно після отримання турнікета
                    long startPassAbs = System.currentTimeMillis();
                    long finishPassAbs = startPassAbs + t1Ms;

                    // якщо не встигає завершити до початку матчу — запізнився
                    if (finishPassAbs > matchStartAbs) {
                        notInTime.incrementAndGet();
                        return;
                    }

                    // "проходження" триває T1
                    Thread.sleep(t1Ms);
                    inTime.incrementAndGet();

                } finally {
                    turnstiles.release();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Кількість глядачів M: ");
        int M = sc.nextInt();

        System.out.print("Кількість турнікетів N: ");
        int N = sc.nextInt();

        System.out.print("Час проходу через турнікет T1 (мс): ");
        long T1 = sc.nextLong();

        System.out.print("Глядачі приходять у випадковий час від 0 до T2 (мс): ");
        long T2 = sc.nextLong();

        System.out.print("Турнікети відчиняють за T3 (мс) до початку матчу (T3 < T1+T2): ");
        long T3 = sc.nextLong();

        long matchStartRel = T1 + T2;               // матч починається через T1+T2 від t=0
        long openRel = matchStartRel - T3;          // відкриття за T3 до початку
        long startAbs = System.currentTimeMillis(); // t=0
        long matchStartAbs = startAbs + matchStartRel;
        long openTimeAbs = startAbs + openRel;

        Semaphore turnstiles = new Semaphore(N, true);
        AtomicInteger notInTime = new AtomicInteger(0);
        AtomicInteger inTime = new AtomicInteger(0);

        Random rnd = new Random();

        Thread[] viewers = new Thread[M];
        for (int i = 0; i < M; i++) {
            long arrival = (T2 == 0) ? 0 : (long) (rnd.nextDouble() * (T2 + 1)); // [0..T2]
            viewers[i] = new Thread(new Viewer(
                    i + 1, arrival, T1, openTimeAbs, matchStartAbs,
                    turnstiles, notInTime, inTime
            ));
            viewers[i].start();
        }

        for (Thread t : viewers) t.join();

        System.out.println("\nРезультат:");
        System.out.println("Встигли пройти: " + inTime.get());
        System.out.println("Не встигли пройти: " + notInTime.get());
    }
}
