import java.io.*;
import java.util.*;

class Toy implements Serializable {
    private String name;
    private double price;
    private int minAge;
    private int maxAge;

    public Toy(String name, double price, int minAge, int maxAge) {
        this.name = name;
        this.price = price;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public boolean suitableFor(int age) {
        return age >= minAge && age <= maxAge;
    }

    @Override
    public String toString() {
        return name + " (" + price + " грн, " + minAge + "-" + maxAge + " років)";
    }
}

public class Task0702 {

    // ---------------------- Створення файлу з іграшками ----------------------
    public static void writeToys(String filename, List<Toy> toys) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(toys);
        out.close();
    }

    // ------------------------- Зчитування іграшок ----------------------------
    public static List<Toy> readToys(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
        List<Toy> toys = (List<Toy>) in.readObject();
        in.close();
        return toys;
    }

    // -------------- Вибір іграшок для конкретного віку -----------------------
    public static List<Toy> toysForAge(List<Toy> toys, int age) {
        List<Toy> result = new ArrayList<>();
        for (Toy t : toys) {
            if (t.suitableFor(age)) result.add(t);
        }
        return result;
    }

    // ------------------------------- MAIN ------------------------------------
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            // -------- 1. Створення файлу з іграшками --------
            List<Toy> toys = new ArrayList<>();
            toys.add(new Toy("М'яч", 150, 3, 12));
            toys.add(new Toy("Лялька", 300, 4, 10));
            toys.add(new Toy("Конструктор LEGO", 1200, 6, 16));
            toys.add(new Toy("Пазли", 250, 5, 99));
            toys.add(new Toy("Брязкальце", 80, 0, 2));

            writeToys("toys.dat", toys);
            System.out.println("Файл toys.dat створено.");

            // -------- 2. Зчитування файлу --------
            List<Toy> loadedToys = readToys("toys.dat");
            System.out.println("\nІграшки у файлі:");
            for (Toy t : loadedToys) System.out.println(" - " + t);

            // -------- 3. Введення віку та фільтрація --------
            System.out.print("\nВведіть вік дитини: ");
            int age = sc.nextInt();

            List<Toy> suitable = toysForAge(loadedToys, age);

            // -------- 4. Запис у новий файл --------
            writeToys("selected_toys.dat", suitable);

            System.out.println("\nІграшки, що підходять:");
            for (Toy t : suitable) System.out.println(" - " + t);

            System.out.println("\nФайл selected_toys.dat створено.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
