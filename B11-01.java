import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalTime;

public class TimeCheck {
    public static void main(String[] args) {
        try {
            String url = "https://www.timeanddate.com/worldclock/ukraine/kyiv";

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new URL(url).openStream(), "UTF-8")
            );

            StringBuilder html = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                html.append(line);
            }
            reader.close();

            String page = html.toString();

            String tagStart = "<span id=ct class=h1>";
            int start = page.indexOf(tagStart);

            if (start == -1) {
                System.out.println("Не вдалося знайти час на сторінці.");
                return;
            }

            start += tagStart.length();
            int end = page.indexOf("</span>", start);

            String onlineTimeStr = page.substring(start, end).trim();

            System.out.println("Час з сайту: " + onlineTimeStr);

            LocalTime localTime = LocalTime.now();
            LocalTime onlineTime = LocalTime.parse(onlineTimeStr);

            System.out.println("Локальний час: " + localTime.toString());

            if (localTime.getHour() == onlineTime.getHour() &&
                Math.abs(localTime.getMinute() - onlineTime.getMinute()) <= 1) {

                System.out.println("Локальний час приблизно відповідає точному.");
            } else {
                System.out.println("Час відрізняється від онлайн-часу.");
            }

        } catch (Exception e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
