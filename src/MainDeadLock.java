public class MainDeadLock {
    public static void main(String[] args) {
        final Integer[] a = {0};
        final Integer[] b = {100};

        Thread t1 = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 100; i++) {
                    synchronized (a[0]) {
                        synchronized (b[0]) {
                            a[0]++;
                            System.out.println(Thread.currentThread().getName() +  " a = " + a[0]);
                            b[0]--;
                            System.out.println(Thread.currentThread().getName() + " b = " + b[0]);
                            try {
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };
        Thread t2 = new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 100; i++) {
                    synchronized (b[0]) {
                        synchronized (a[0]) {
                            a[0]++;
                            System.out.println(Thread.currentThread().getName() +  " a = " + a[0]);
                            b[0]--;
                            System.out.println(Thread.currentThread().getName() + " b = " + b[0]);
                            try {
                                sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        t1.start();
        t2.start();
    }
}
