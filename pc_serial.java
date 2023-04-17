public class pc_serial {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 1;
    private static int counter = 0;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }
        // start~~~~~
        long startTime = System.currentTimeMillis();
        for (int i = 0; i <= NUM_END; i++){
            if (isPrime(i)) counter++;
        }
        long endTime = System.currentTimeMillis();
        // ~~~~~end
        long timeDiff = endTime - startTime;
        System.out.println("Program Execution Time: " + timeDiff + "ms");
        System.out.println("1..." + (NUM_END) + " prime# counter = " + counter);
    }

    private static boolean isPrime(int x) {
        if (x<=1) return false;
        for (int i=2; i<x; i++) {
            if (x%i == 0) return false;
        }
        return true;
    }

}
