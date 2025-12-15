import java.time.LocalDate;

public class B0601 {
    public static void main(String[] args) {

        String text = "Дата народження: 12.05.2001, дата подачі: __.__.____, інше: 07.11.1999";

        String today = LocalDate.now().toString();      // YYYY-MM-DD
        today = today.substring(8,10) + "." + today.substring(5,7) + "." + today.substring(0,4);

        // Регулярка: або цифри, або підкреслення у форматі дати
        String dateRegex = "\\d{2}\\.\\d{2}\\.\\d{4}|__\\.__\\.____";

        String replaced = text.replaceAll(dateRegex, today);

        System.out.println("Було:      " + text);
        System.out.println("Стало:     " + replaced);
    }
}
