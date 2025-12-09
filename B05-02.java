public class B0502 {

    private static boolean checkA(String s) {
        if (s.isEmpty()) return false;

        char first = s.charAt(0);
        if (!Character.isDigit(first) || first == '0') return false;

        int k = first - '0';

        if (s.length() < 1 + k) return false;

        for (int i = 1; i <= k; i++) {
            if (!Character.isLetter(s.charAt(i))) return false;
        }

        return true;
    }

    private static boolean checkB(String s) {
        int digitCount = 0;
        int digitValue = -1;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                digitCount++;
                digitValue = ch - '0';
            }
        }

        return digitCount == 1 && digitValue == s.length();
    }

    private static boolean checkC(String s) {
        int sum = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                sum += (ch - '0');
            }
        }

        return sum == s.length();
    }

    public static void main(String[] args) {
        String input = "3abc";  

        System.out.println("Рядок: " + input);
        System.out.println("a) " + (checkA(input) ? "Так" : "Ні"));
        System.out.println("b) " + (checkB(input) ? "Так" : "Ні"));
        System.out.println("c) " + (checkC(input) ? "Так" : "Ні"));
    }
}
