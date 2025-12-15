public class B0603 {

    // Перевірка, чи токен є цілим числом (+5, -3, 10)
    private static boolean isInteger(String s) {
        return s.matches("[+-]?\\d+");
    }

    // Перевірка оператора
    private static boolean isOperator(String s) {
        return s.matches("[+\\-*/]");
    }

    public static boolean isValidExpression(String expr) {

        // Прибираємо зайві пробіли
        String cleaned = expr.trim().replaceAll("\\s+", " ");

        // Розбиваємо токени
        String[] tokens = cleaned.split(" ");

        // Перший токен обов'язково число
        if (!isInteger(tokens[0])) return false;

        // Далі повинні йти: оператор, число, оператор, число...
        boolean expectOperator = true;

        for (int i = 1; i < tokens.length; i++) {
            String t = tokens[i];

            if (expectOperator) {
                if (!isOperator(t)) return false;
            } else {
                if (!isInteger(t)) return false;
            }
            expectOperator = !expectOperator;
        }

        // Вираз не може закінчуватися оператором
        return !expectOperator;
    }

    public static void main(String[] args) {
        String expr = "+2   -  57*33 +   25 / -4";

        System.out.println("Вираз: " + expr);
        System.out.println(isValidExpression(expr) ? "Синтаксично правильний" : "Неправильний");
    }
}
