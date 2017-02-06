package com.yeahmobi.ssdbcluster.client.pressure;

import com.alibaba.fastjson.JSONObject;
import com.yeahmobi.ssdb.client.JSSDB;
import com.yeahmobi.ssdb.client.JSSDBPool;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author penuel (penuel.leo@gmail.com)
 * @date 2016/12/27 下午4:52
 * @desc
 */
public class PressureApp {

    private static AtomicLong counter = new AtomicLong(0);

    private final static String KEY_SUFFIX = "_ABCDEF";

    private static volatile boolean stop = false;

    public static void main(String[] args) throws Exception {
        System.out.println("startPressureApp " + new Date().getTime() + " - args:" + JSONObject.toJSONString(args));
        int threadNum = Integer.valueOf(args[0]);
        String cmd = args[1];
        String ssdbIp = args[2];
        long speed = Long.valueOf(args[3]);
        int size = Integer.valueOf(args[4]);

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum + 5);
        for ( int i = 0; i < threadNum; i++ ) {
            executorService.execute(new SSDBTask(ssdbIp, 9801, cmd, speed, size));
        }

        System.out.println("endPressureApp " + new Date().getTime() + ", execute counter=" + counter.get());

    }

    static class SSDBTask implements Runnable {

        private String cmd;

        private long speed;

        private int size;

        private SSDBClient ssdbClient;

        public SSDBTask(String ip, int port, String cmd, long speed, int size) throws Exception {
            this.cmd = cmd;
            this.speed = speed;
            this.size = size;
            ssdbClient = SSDBClient.getInstance();
        }

        @Override
        public void run() {
            while ( !stop ) {
                switch ( cmd ) {
                case "set":
                    try {
                        char[] values = new char[size];
                        Arrays.fill(values, 'x');
                        this.ssdbClient.set(counter.get() + KEY_SUFFIX, new String(values));
                    } catch ( Exception e ) {
                        stop = true;
                        e.printStackTrace();
                    }
                    break;
                case "get":
                    try {
                        this.ssdbClient.get(counter.get() + KEY_SUFFIX);
                    } catch ( Exception e ) {
                        stop = true;
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
                }
//                if ( counter.incrementAndGet() % 100 == 0 ) {
                counter.incrementAndGet();
                    System.out.println(
                        "execute " + cmd + " " + counter.get() + " times at " + new Date().getTime() + " on thread-" + Thread.currentThread().getId());
//                }
                if ( speed > 0 ) {
                    try {
                        System.out.println("sleep " + speed + " ms");
                        Thread.sleep(speed);
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
