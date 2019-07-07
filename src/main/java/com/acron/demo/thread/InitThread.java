package com.acron.demo.thread;

import com.acron.demo.service.ICommonMQService;
import com.acron.demo.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author Acron
 * @ClassName InitThread
 * @Description TODO
 * @since 2019/07/07 15:25
 */
@Slf4j
@Service
public class InitThread {
    public static final int ThreadNum = 5000;

    private static int mobile=0;

    @Resource
    private ICommonMQService commonMQService;

    public void generateMultiThread(){
        log.info("开始初始化线程数----> ");
        try {
            CountDownLatch countDownLatch=new CountDownLatch(1);
            for (int i=0;i<ThreadNum;i++){
                new Thread(new RunThread(countDownLatch)).start();
            }
            //TODO：启动多个线程
            countDownLatch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class RunThread implements Runnable{
        private final CountDownLatch startLatch;

        public RunThread(CountDownLatch startLatch) {
            this.startLatch = startLatch;
        }

        public void run() {
            try {
                //TODO：线程等待
                startLatch.await();
                mobile += 1;
                //TODO：发送消息入抢单队列：env.getProperty("user.order.queue.name")
                commonMQService.sendRobbingMsg(String.valueOf(mobile));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
