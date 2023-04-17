import java.util.concurrent.atomic.AtomicInteger;

public class pc_dynamic {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 2;
    private static int TASK_SIZE = 10;
    static AtomicInteger queue = new AtomicInteger(0);
    static int PRIME_COUNT = 0;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        
        int counter = 0;
        CalThread[] calThreads = new CalThread[NUM_THREADS];

        // start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        long startTime = System.currentTimeMillis();


        for (int i=0; i<NUM_THREADS; i++) {
            calThreads[i] = new CalThread(10);
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            calThreads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            try {
                calThreads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long endTime = System.currentTimeMillis();
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~end

        long timeDiff = endTime - startTime;
        System.out.println("Program Execution Time: " + timeDiff + "ms");
        System.out.println("1..." + NUM_END + " prime# counter = " + counter);
    }

    private static boolean isPrime(int x) {
        if (x<=1) return false;
        for (int i=2; i<x; i++) {
            if (x%i == 0) return false;
        }
        return true;
    }

    static class CalThread extends Thread {
        int taskSize;

        long timeTakes;

        CalThread(int taskSize) {
            this.taskSize = taskSize;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while(queue.get() <= NUM_END){
                for(int j=0; j<taskSize; j++) {
                    if(isPrime(j+queue.getAndAdd(1))) {
                        System.out.println(j+queue.get()-1 + "는 소수 입니다");
                        PRIME_COUNT++;
                    }
                }
            }
            long endTime = System.currentTimeMillis();

            timeTakes = endTime - startTime;
        }

    }
}
