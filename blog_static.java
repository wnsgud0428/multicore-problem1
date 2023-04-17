public class blog_static {
    // 20만까지 테스트하기 위함
    private static final int NUM_END = 200000;
    private static final int NUM_THREAD = 8;
    private static int counter = 0;

    // 메인함수
    public static void main(String[] args) throws InterruptedException {
        // 소수의 수를 저장하기 위한 counter. 짝수 2를 카운트하기 위해 미리 1 추가
        ThreadCal.counter = 1;
        // for 문에서 소수를 테스트하기 위한 변수
        int i;

        // 쓰레드 생성 및 변수 할당
        ThreadCal[] thread = new ThreadCal[NUM_THREAD];
        // 쓰레드에 시작값 할당
        for (int t = 0; t < NUM_THREAD; t++) {
            // 배열은 0부터 시작이라 0에는 1을, 1에는 3을 할당하기 위해 1 추가.
            thread[t] = new ThreadCal(1 + t * 2);
        }

        //시작 시간을 측정하기 위한 변수
        long startTime = System.currentTimeMillis();
        // 소수인지 테스트
        // 쓰레드 시작
        for (int t = 0; t < NUM_THREAD; t++) {
            thread[t].start();
        }
        // 시간 측정을 위해 join 을 이용해 쓰레드가 끝날 때까지 대기
        for (int t = 0; t < NUM_THREAD; t++) {
            thread[t].join();
        }

        // 끝나는 시간을 측정하기 위한 변수
        long endTime = System.currentTimeMillis();
        // 실제 실행 시간이 얼마인지를 측정하기 위한 변수
        long timeDiff = endTime - startTime;

        // (1) execution time of each thread
        // 쓰레드별 실행 시간 출력문
        for (int t = 0; t < NUM_THREAD; t++) {
            System.out.println("Thread-" + t + "'s Execution Time : " + thread[t].timeDiff + "ms");
        }

        // (2) execution time when using all threads
        // 전체 실행 시간 출력문
        System.out.println("Execution Time : " + timeDiff + "ms");

        // 1부터 NUM_END-1까지의 소수의 갯수 출력문
        System.out.println("1..." + (NUM_END - 1) + " prime# counter=" + ThreadCal.counter + "\n");

    }

    // 소수 판별 함수. 판별하기 위한 숫자를 x로 받는다.
    private static boolean isPrime(int x) {
        // 임시 변수 i 설정
        int i;
        // 받은 숫자가 1이라면 false 반환
        if (x <= 1) return false;
        // i를 2부터 x-1까지 실행한다.
        for (i = 2; i < x; i++) {
            // 만약 x가 i로 나누어지고(% 연산 시 0 출력) i가 x가 아니라면 소수가 아니니 false 반환
            // 사실 여기는 코드를 단순화 가능
            if (((x % i) == 0) && (i != x)) return false;
        }
        return true;
    }

    static class ThreadCal extends Thread {
        // 쓰레드에서 처음 판단할 숫자를 변수 x로 주었다.
        int x;
        // 소수 숫자를 세기 위해 counter 변수를 클래스 내에 선언해줬다.
        static int counter;
        // 쓰레드 끝난 후 더하기 위해 임시 변수 선언
        int temp = 0;
        // 쓰레드 시작 시간
        long startTime;
        // 쓰레드 종료 시간
        long endTime;
        // 쓰레드 실행 시간
        long timeDiff;

        // constructor, 생성자로 변수 전달
        public ThreadCal(int x) {
            this.x = x;
        }

        public void run() {
            // 쓰레드 시작 시간
            startTime = System.currentTimeMillis();

            // 소수 판별용 변수 i
            int i;
            // x :: 처음 판단할 숫자
            // i<NUM_END :: i부터 NUM_END-1 까지
            // i = i+NUM_THREAD :: i부터 판단할 숫자는 NUM_THREAD 씩 더한다.
            for (i = x; i < NUM_END; i = i + NUM_THREAD * 2) {
                // 소수이면 temp 에 1을 더한다.
                if (isPrime(i)) temp++;
            }

            // 클래스 내 counter 변수에 소수를 더한다.
            counter += temp;
            // 쓰레드 종료 시간
            endTime = System.currentTimeMillis();
            // 쓰레드 실행 시간
            timeDiff = endTime - startTime;
        }
    }
}