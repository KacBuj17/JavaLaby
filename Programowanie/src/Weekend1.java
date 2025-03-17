import java.util.Date;

public class Weekend1 {
    public static void main(String[] args) {
        Date now = new Date();
        int day = now.getDay();

        if(day == 0 || day == 6){
            System.out.println("Jest weekend!");
        } else{
            switch(day){
                case 1:
                    System.out.println("Dzisiaj jest poniedziałek");
                    break;
                case 2:
                    System.out.println("Dzisiaj jest wtorek");
                    break;
                case 3:
                    System.out.println("Dzisiaj jest środa");
                    break;
                case 4:
                    System.out.println("Dzisiaj jest czwartek");
                    break;
                case 5:
                    System.out.println("Dzisiaj jest piątek");
                    break;
            }
        }
    }
}
