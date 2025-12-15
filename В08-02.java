import java.util.Stack;

public class BracketsChecker {

    public static boolean isCorrect(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // відкриваючі дужки
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            // закриваючі дужки
            else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if (!matches(open, c)) {
                    return false;
                }
            }
        }
        // стек має бути порожнім
        return stack.isEmpty();
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '[' && close == ']') ||
               (open == '{' && close == '}');
    }

    public static void main(String[] args) {
        String s1 = "({[]})";
        String s2 = "([)]";
        String s3 = "{[()()]}";

        System.out.println(s1 + " -> " + isCorrect(s1)); // true
        System.out.println(s2 + " -> " + isCorrect(s2)); // false
        System.out.println(s3 + " -> " + isCorrect(s3)); // true
    }
}
