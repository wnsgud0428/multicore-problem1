public class pc_static_block {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 1;
//    private static int COUNTER = 0;

    public static void main (String[] args) {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        int counter = 0;
        CalThread[] calThreads = new CalThread[NUM_THREADS];
        int blockSize = NUM_END / NUM_THREADS;

        // start~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        long startTime = System.currentTimeMillis();

        // 쓰레드 개수 만큼 block decomposition 함(마지막 번호의 쓰레드는 남은 작업을 다 들고감)
        for (int i=0; i<NUM_THREADS; i++) {
            if (i == NUM_THREADS-1) {
                calThreads[i] = new CalThread(1+ blockSize*i, NUM_END);
            } else {
                calThreads[i] = new CalThread(1 + blockSize*i, blockSize*(i+1));
            }
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

        for (int i = 0; i < NUM_THREADS; i++) {
            counter += calThreads[i].primeNumCount;
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
        int startNum;
        int endNum;
        long timeTakes;
        int primeNumCount;

        CalThread(int s, int e) {
            this.startNum = s;
            this.endNum = e;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            for(int i = startNum; i <= endNum; i++) {
                if (isPrime(i)) {
                    primeNumCount++;
                }
            }
            long endTime = System.currentTimeMillis();
//            counter += primeNumCount;

            timeTakes = endTime - startTime;
        }
    }
}
