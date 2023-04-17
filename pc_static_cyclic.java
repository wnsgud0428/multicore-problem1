public class pc_static_cyclic {
    private static int NUM_END = 200000;
    private static int NUM_THREADS = 32;
    private static int TASK_SIZE = 10;

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
            calThreads[i] = new CalThread(i, TASK_SIZE, NUM_THREADS);
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
            System.out.println(i + "번째 thread 실행 시간: " + calThreads[i].timeTakes + "ms");
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
        int threadNum; // 쓰레드 번호
        int taskSize;
        int n; // 쓰레드 개수

        long timeTakes;
        int primeNumCount;

        CalThread(int i, int taskSize, int threadCount) {
            this.threadNum = i;
            this.taskSize = taskSize;
            this.n = threadCount;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            for(int x=1 + threadNum*taskSize; x<= NUM_END; x += taskSize*n) {
//                System.out.println("x를 출력합니다 " + x);
                for (int j = x; j < x+TASK_SIZE; j++){
                    if(isPrime(j)) {
//                        System.out.println(threadNum+" "+j);
                        primeNumCount++;
                    }
                }
            }
            long endTime = System.currentTimeMillis();

//            counter += primeNumCount;
            timeTakes = endTime - startTime;
        }
    }
}
