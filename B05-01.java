public class B0501 {

    private static boolean noNestedBrackets(String s) {
        int openIndex = -1;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                if (openIndex != -1) return false; 
                openIndex = i;
            } else if (ch == ')') {
                if (openIndex == -1) return false; 
                openIndex = -1;
            }
        }
        return openIndex == -1; 
    }

    private static String removeBracketGroups(String s) {
        StringBuilder result = new StringBuilder();
        boolean inside = false;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                inside = true;
            } else if (ch == ')') {
                inside = false;
            } else if (!inside) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {

        String input = "ab(cd)ef(123)g";  

        // 1. Перевірка правильності дужок
        if (!noNestedBrackets(input)) {
            System.out.println("Дужки розставлені неправильно або містять вкладені дужки!");
            return;
        }

        String output = removeBracketGroups(input);

        System.out.println("Вхідний рядок:  " + input);
        System.out.println("Результат:      " + output);
    }
}
