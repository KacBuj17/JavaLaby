public class ArithmeticMean {
    static void arithmeticMean(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
        } else if (args.length == 1) {
            System.out.println("Przekazano tylko jedną wartość: " + args[0]);
        } else {
            int sum = 0;
            for (String arg : args) {
                sum += Integer.parseInt(arg);
            }
            int mean = sum / args.length;
            int remainder = sum % args.length;
            System.out.print("Średnia arytmetyczna liczb: ");
            System.out.print(String.join(", ", args));
            System.out.print(" wynosi " + mean);
            if (remainder != 0) {
                System.out.println(", reszta: " + remainder);
            } else {
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        arithmeticMean(args);
    }
}