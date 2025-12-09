import java.util.Scanner;

public class B0105 {
    public static void main(String[] args) {

        int N, M;

        // Якщо передані аргументи
        if (args.length == 2) {
            try {
                N = Integer.parseInt(args[0]);
                M = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Аргументи повинні бути додатними цілими числами.");
                return;
            }
        } else {
            // Інакше читаємо через консоль
            Scanner in = new Scanner(System.in);

            System.out.print("Введіть N: ");
            N = in.nextInt();

            System.out.print("Введіть M: ");
            M = in.nextInt();
        }

        if (N <= 0 || M <= 0) {
            System.out.println("N і M повинні бути додатними.");
            return;
        }

        System.out.println("Випадкові числа:");

        for (int i = 0; i < N; i++) {
            int random = (int) (Math.random() * M); // від 0 до M-1
            System.out.println(random);
        }
    }
}
