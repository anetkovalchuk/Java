import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherIUADay {

    private static String downloadPage(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        StringBuilder sb = new StringBuilder();

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }

    private static String extractDate(String html) {
        // спочатку шукаємо dd.mm.yyyy
        Pattern pFull = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4})");
        Matcher mFull = pFull.matcher(html);
        if (mFull.find()) {
            return mFull.group(1);
        }

        Pattern pShort = Pattern.compile("(\\d{2}\\.\\d{2})");
        Matcher mShort = pShort.matcher(html);
        if (mShort.find()) {
            String today = mShort.group(1);
            int year = LocalDate.now().getYear();
            return today + "." + year;
        }

        return "дата не знайдена";
    }

    private static List<Integer> extractTemperatures(String html, int limit) {
        List<Integer> temps = new ArrayList<>();

        Pattern p = Pattern.compile("(-?\\d+)\\s*(°|&#176;|&deg;)");
        Matcher m = p.matcher(html);

        while (m.find() && temps.size() < limit) {
            int t = Integer.parseInt(m.group(1));
            temps.add(t);
        }
        return temps;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Використання: java WeatherIUADay <англомовна-назва-міста>");
            System.out.println("Наприклад:   java WeatherIUADay Kyiv");
            return;
        }

        String city = args[0];           
        String url = "https://weather.i.ua/" + city + "/";

        try {
            String html = downloadPage(url);

            String date = extractDate(html);
            List<Integer> temps = extractTemperatures(html, 24);

            System.out.println("URL: " + url);
            System.out.println("Дата (знайдена на сторінці): " + date);
            System.out.println("Температури (перші 24 значення, °C):");

            if (temps.isEmpty()) {
                System.out.println("Не вдалося знайти значення температури у HTML.");
            } else {
                for (int i = 0; i < temps.size(); i++) {
                    System.out.printf("Година %2d: %d°C%n", i, temps.get(i));
                }
                if (temps.size() < 24) {
                    System.out.println("(На сторінці знайдено менше ніж 24 значення температури.)");
                }
            }

        } catch (IOException e) {
            System.out.println("Помилка доступу до сайту: " + e.getMessage());
        }
    }
}
