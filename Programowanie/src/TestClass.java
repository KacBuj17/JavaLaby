public class TestClass {
    private static int objectCounter = 0;
    private final int objectID;
    private final long creationTimestamp;

    public TestClass() {
        this.objectID = ++objectCounter;
        this.creationTimestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return objectID + ". [" + creationTimestamp + "]";
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
            return;
        }

        int numberOfObjects = Integer.parseInt(args[0]);

        for (int i = 0; i < numberOfObjects; i++) {
            TestClass obj = new TestClass();
            System.out.println(obj);
        }

        System.out.println("Liczba obiektów: " + objectCounter);
    }
}
