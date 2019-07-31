import java.util.ArrayList;
import java.util.List;

public class MainCuncurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState());
            }
        }).start();

        System.out.println(thread0.getName() + ", " + thread0.getState());

        final MainCuncurrency mainCuncurrency = new MainCuncurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainCuncurrency.inc();
                }
            }));
            threads.get(i).start();
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(500);
        System.out.println(counter);
    }

    private synchronized void inc() {
//        synchronized (MainCuncurrency.class) {
//        synchronized (this) {
            counter++;
//       }
    }
}
