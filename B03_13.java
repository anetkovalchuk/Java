import java.util.*;

class House {
    private final int apartmentNumber;
    private final double area;
    private final String address;
    private final int yearsInUse; 

    public House(int apartmentNumber, double area, String address, int yearsInUse) {
        this.apartmentNumber = apartmentNumber;
        this.area = area;
        this.address = address;
        this.yearsInUse = yearsInUse;
    }

    public int getApartmentNumber() { return apartmentNumber; }
    public double getArea() { return area; }
    public String getAddress() { return address; }
    public int getYearsInUse() { return yearsInUse; }

    @Override
    public String toString() {
        return String.format("House[№%d, площа=%.2f, адреса='%s', експлуатація=%d років]",
                apartmentNumber, area, address, yearsInUse);
    }
}

public class HouseExample {

    public static List<House> filterByYearsInUse(House[] houses, int minYears) {
        List<House> result = new ArrayList<>();
        for (House h : houses) {
            if (h.getYearsInUse() > minYears) {
                result.add(h);
            }
        }
        return result;
    }

    public static List<House> filterAndSortByArea(House[] houses, String targetAddress) {
        List<House> result = new ArrayList<>();
        for (House h : houses) {
            if (h.getAddress().equalsIgnoreCase(targetAddress)) {
                result.add(h);
            }
        }
        result.sort(Comparator.comparingDouble(House::getArea));
        return result;
    }

    public static void main(String[] args) {
        House[] houses = new House[] {
            new House(1, 45.5, "Київ, вул. Хрещатик 10", 12),
            new House(2, 60.0, "Київ, вул. Хрещатик 10", 20),
            new House(3, 38.7, "Львів, вул. Шевченка 5", 8),
            new House(4, 72.3, "Київ, вул. Хрещатик 10", 25),
            new House(5, 55.0, "Львів, вул. Шевченка 5", 15)
        };

        System.out.println("a) Квартири з терміном експлуатації > 10 років:");
        List<House> filtered = filterByYearsInUse(houses, 10);
        for (House h : filtered) System.out.println(h);

        System.out.println("\nb) Квартири на адресі 'Київ, вул. Хрещатик 10', відсортовані за площею:");
        List<House> sorted = filterAndSortByArea(houses, "Київ, вул. Хрещатик 10");
        for (House h : sorted) System.out.println(h);
    }
}
