import java.util.Scanner;

public class BitPositions {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть ціле двобайтове число (short): ");
        short inputShort = scanner.nextShort();

        int value = inputShort & 0xFFFF; // Маска, щоб обробити як беззнакове 16-бітне число

        int firstBit = -1;  // Номер найстаршого значущого біта
        int lastBit = -1;   // Номер наймолодшого значущого біта

        for (int i = 15; i >= 0; i--) {
            if (((value >> i) & 1) == 1) {
                firstBit = i;
                break;
            }
        }

        for (int i = 0; i <= 15; i++) {
            if (((value >> i) & 1) == 1) {
                lastBit = i;
                break;
            }
        }

        if (firstBit == -1) {
            System.out.println("У числі немає значущих бітів (усі біти нулі).");
        } else {
            System.out.println("Перший значущий біт: " + firstBit);
            System.out.println("Останній значущий біт: " + lastBit);
        }
    }
}
