import java.util.Date;

public class Weekend {
    public static void main(String[] args) {
        Date now = new Date();
        int day = now.getDay();

        if(day == 6){
            System.out.println("Dzisiaj jest sobota!");
        } else{
            System.out.println("Liczba dni do weekendu: " + (6 - day));
        }
    }
}
