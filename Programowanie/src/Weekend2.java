import java.util.Calendar;

public class Weekend2 {
    private final static String[] days = { "niedziela", "poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota" };

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String dayName = days[dayOfWeek];

        if(dayOfWeek == 0 || dayOfWeek == 6) {
            System.out.println("Mamy weekend!");
        }else{
            int daysToWeekend = 6 - dayOfWeek;
            String dayText = (daysToWeekend == 1) ? "dzień" : "dni";
            System.out.println("Dziś " + dayName + ", do weekendu pozostał" + (daysToWeekend == 1 ? " " : "o ") + daysToWeekend + " " + dayText + ".");
        }
    }
}
