import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task0701 {

    // ------------------------- Створення файлу F -------------------------
    public static void createBinaryFileF(String filename, double[] numbers) throws IOException {
        DataOutputStream out = new DataOutputStream(new FileOutputStream(filename));
        for (double num : numbers) {
            out.writeDouble(num);
        }
        out.close();
    }

    // -------------------- Зчитування дійсних чисел з файлу F --------------------
    public static List<Double> readBinaryFile(String filename) throws IOException {
        List<Double> list = new ArrayList<>();
        DataInputStream in = new DataInputStream(new FileInputStream(filename));

        try {
            while (true) {
                list.add(in.readDouble());
            }
        } catch (EOFException e) {
            // кінець файлу — нормально
        }
        in.close();
        return list;
    }

    // ---------------------- Створення файлу G (> a) ----------------------
    public static void createFileG(String fileF, String fileG, double a) throws IOException {
        List<Double> nums = readBinaryFile(fileF);

        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileG));
        for (double x : nums) {
            if (x > a) {
                out.writeDouble(x);   // записуємо у файл G
            }
        }
        out.close();
    }

    // ------------------------------ MAIN ------------------------------
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        // 1. Введення чисел
        System.out.println("Скільки чисел записати у файл F?");
        int n = sc.nextInt();

        double[] arr = new double[n];
        System.out.println("Введіть числа:");
        for (int i = 0; i < n; i++) arr[i] = sc.nextDouble();

        // 2. Створюємо файл F
        createBinaryFileF("F.bin", arr);

        // 3. Вводимо число a
        System.out.println("Введіть число a:");
        double a = sc.nextDouble();

        // 4. Створюємо файл G
        createFileG("F.bin", "G.bin", a);

        // 5. Виводимо результат
        System.out.println("\nЧисла з файлу F:");
        System.out.println(readBinaryFile("F.bi
