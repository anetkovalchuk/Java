import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class B0602 {
    public static void main(String[] args) {

        String text = "Телефони: +380671234567, 067-123-45-67, (093)1234567, 0501234567";
        String phoneRegex =
                "\\+?\\d{1,3}?[- ]?\\(?\\d{2,3}\\)?[- ]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}";

        Pattern p = Pattern.compile(phoneRegex);
        Matcher m = p.matcher(text);

        System.out.println("Знайдені телефони:");
        while (m.find()) {
            System.out.println(m.group());
        }
    }
}
