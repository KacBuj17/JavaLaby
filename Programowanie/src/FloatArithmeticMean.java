public class FloatArithmeticMean {
    static void floatArithmeticMean(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
        } else if (args.length == 1) {
            System.out.println("Przekazano tylko jedną wartość: " + args[0]);
        } else {
            float sum = 0;
            for (String arg : args) {
                sum += Float.parseFloat(arg);
            }
            float mean = sum / args.length;
            System.out.println("Średnia arytmetyczna: " + mean);
        }
    }

    public static void main(String[] args) {
        floatArithmeticMean(args);
    }
}