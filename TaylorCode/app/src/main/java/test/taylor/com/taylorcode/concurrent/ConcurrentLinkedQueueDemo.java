package test.taylor.com.taylorcode.concurrent;


import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {
    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    public void ddd() throws InterruptedException {
        // 消费者add
        for (int i = 0; i < 100; i++) {
            new Thread(new Producer(i)).start();
        }

        // 消费者poll
        while (true) {
            int size = queue.size();
            if (size >= 5) {
                for (int i = 0; i < 5; i++) {
                    Log.i("ttaylor", "ConcurrentLinkedQueueDemo.() poll="+queue.poll());
                }
                Log.i("ttaylor", "ConcurrentLinkedQueueDemo--------------------------.()");
            }
        }
    }

    static class Producer implements Runnable {
        private int i;
        public Producer(int i){
           this.i = i;
        }
        public void run() {
                queue.offer(i);
        }
    }
}