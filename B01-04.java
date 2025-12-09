import java.util.Scanner;

public class B0104 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Введіть перше число: ");
        int a = in.nextInt();

        System.out.print("Введіть друге число: ");
        int b = in.nextInt();

        System.out.print("Введіть третє число: ");
        int c = in.nextInt();

        double geometricMean = Math.cbrt(a * b * c);

        System.out.printf("Середнє геометричне: %.4f%n", geometricMean);
    }
}
