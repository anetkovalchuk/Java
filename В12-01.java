import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Main {

    // -------------------- MODEL --------------------

    enum Direction {
        HUMANITARIAN,
        NATURAL,
        NATURAL_HUMANITARIAN
    }

    static class Student {
        private final Direction direction;
        private final int requiredCredits;

        private int credits = 0;
        private int money;
        private boolean expelled = false;

        Student(Direction direction, int requiredCredits, int initialMoney) {
            this.direction = direction;
            this.requiredCredits = requiredCredits;
            this.money = initialMoney;
        }

        boolean canStudyHumanitarian() {
            return direction == Direction.HUMANITARIAN
                    || direction == Direction.NATURAL_HUMANITARIAN;
        }

        boolean canStudyNatural() {
            return direction == Direction.NATURAL
                    || direction == Direction.NATURAL_HUMANITARIAN;
        }

        void addCredits(int c) {
            credits += c;
        }

        void addMoney(int amount) {
            money += amount;
        }

        void pay(int amount) {
            if (money < amount) {
                expelled = true;               // немає грошей – відрахування
            } else {
                money -= amount;
            }
        }

        boolean isExpelled() {
            return expelled;
        }

        boolean hasDiploma() {
            return !expelled && credits >= requiredCredits;
        }

        void accept(StudentVisitor v) {
            if (!expelled) {
                v.visit(this);
            }
        }
    }

    // -------------------- VISITOR --------------------

    interface StudentVisitor {
        void visit(Student s);
    }

    // навчання гуманітарній дисципліні
    static class TeachHumanitarianVisitor implements StudentVisitor {
        private final int credits;

        TeachHumanitarianVisitor(int credits) {
            this.credits = credits;
        }

        @Override
        public void visit(Student s) {
            if (s.canStudyHumanitarian()) {
                s.addCredits(credits);
            }
        }
    }

    // навчання природничій дисципліні
    static class TeachNaturalVisitor implements StudentVisitor {
        private final int credits;

        TeachNaturalVisitor(int credits) {
            this.credits = credits;
        }

        @Override
        public void visit(Student s) {
            if (s.canStudyNatural()) {
                s.addCredits(credits);
            }
        }
    }

    // будь-яка оплата (гуртожиток, їдальня)
    static class PayVisitor implements StudentVisitor {
        private final int amount;

        PayVisitor(int amount) {
            this.amount = amount;
        }

        @Override
        public void visit(Student s) {
            s.pay(amount);
        }
    }

    // надходження грошей (стипендія, допомога)
    static class IncomeVisitor implements StudentVisitor {
        private final int amount;

        IncomeVisitor(int amount) {
            this.amount = amount;
        }

        @Override
        public void visit(Student s) {
            s.addMoney(amount);
        }
    }

    // -------------------- PARSING --------------------

    private static Direction parseDirection(String s) {
        String d = s.trim().toLowerCase(Locale.ROOT);
        switch (d) {
            case "humanitarian":
                return Direction.HUMANITARIAN;
            case "natural":
                return Direction.NATURAL;
            case "natural-humanitarian":
            case "natural_humanitarian":
            case "naturalhumanitarian":
                return Direction.NATURAL_HUMANITARIAN;
            default:
                // на всякий випадок
                return Direction.NATURAL_HUMANITARIAN;
        }
    }

    // -------------------- MAIN --------------------

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main input01.txt");
            return;
        }

        String fileName = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            // 1. напрям
            String dirLine = br.readLine();
            if (dirLine == null) {
                System.out.println("NO");
                return;
            }
            Direction direction = parseDirection(dirLine);

            // 2. потрібні кредити
            String creditsLine = br.readLine();
            if (creditsLine == null) {
                System.out.println("NO");
                return;
            }
            int requiredCredits = Integer.parseInt(creditsLine.trim());

            // 3. початкові гроші
            String moneyLine = br.readLine();
            if (moneyLine == null) {
                System.out.println("NO");
                return;
            }
            int initialMoney = Integer.parseInt(moneyLine.trim());

            Student student = new Student(direction, requiredCredits, initialMoney);

            // 4. команди
            String line;
            while ((line = br.readLine()) != null && !student.isExpelled()) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // розбиваємо по пробілах
                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue; // захист від кривого рядка

                String action = parts[0].toLowerCase(Locale.ROOT);
                String what   = parts[1].toLowerCase(Locale.ROOT);
                int value     = Integer.parseInt(parts[2]);

                StudentVisitor visitor = null;

                if (action.equals("teach")) {
                    if (what.startsWith("humanitarian")) {
                        visitor = new TeachHumanitarianVisitor(value);
                    } else if (what.startsWith("natural")) {
                        visitor = new TeachNaturalVisitor(value);
                    }
                } else if (action.equals("pay")) {
                    // hostel / canteen – однаково: списати гроші
                    visitor = new PayVisitor(value);
                } else if (action.equals("obtain")) {
                    // help / scholarship – надходження грошей
                    visitor = new IncomeVisitor(value);
                }

                if (visitor != null) {
                    student.accept(visitor);
                }
            }

            System.out.println(student.hasDiploma() ? "YES" : "NO");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
