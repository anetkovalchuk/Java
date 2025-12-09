import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class B0507 {

    private static final String INPUT_FILE  = "phones.txt";
    private static final String OUTPUT_A    = "phones_a.txt";
    private static final String OUTPUT_B    = "phones_b.txt";

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Введіть прізвище та ініціали співробітника (наприклад, \"Ivanov I.I.\"): ");
        String targetEmployee = in.nextLine().trim();

        List<String> phonesOfTarget = new ArrayList<>();
        Map<String, Integer> countByEmployee = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;

                String surname  = parts[0];
                String initials = parts[1];
                String employee = surname + " " + initials;

                String phone = parts[parts.length - 1];

                if (employee.equalsIgnoreCase(targetEmployee)) {
                    phonesOfTarget.add(phone);
                }

                countByEmployee.put(employee,
                        countByEmployee.getOrDefault(employee, 0) + 1);
            }

        } catch (IOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return;
        }

        try (PrintWriter outA = new PrintWriter(OUTPUT_A)) {
            if (phonesOfTarget.isEmpty()) {
                outA.println("Телефони співробітника \"" + targetEmployee + "\" не знайдено.");
            } else {
                outA.println("Телефони співробітника " + targetEmployee + ":");
                for (String ph : phonesOfTarget) {
                    outA.println(ph);
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка запису у файл " + OUTPUT_A + ": " + e.getMessage());
        }

        try (PrintWriter outB = new PrintWriter(OUTPUT_B)) {
            for (Map.Entry<String, Integer> entry : countByEmployee.entrySet()) {
                outB.println(entry.getKey() + " " + entry.getValue());
            }
        } catch (IOException e) {
            System.out.println("Помилка запису у файл " + OUTPUT_B + ": " + e.getMessage());
        }

        System.out.println("Результат a) записано в " + OUTPUT_A);
        System.out.println("Результат b) записано в " + OUTPUT_B);
    }
}
