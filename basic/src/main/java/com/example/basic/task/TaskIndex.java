package com.example.basic.task;

import com.example.basic.entity.TaskIndexDto;
import com.example.basic.service.SensitivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskIndex implements Runnable{

    private final SensitivityService sensitivityService;
    public static LinkedBlockingQueue<TaskIndexDto> TASK_INDEX_QUEUE = new LinkedBlockingQueue<>();


    @PostConstruct
    public void init(){
        new Thread(this).start();
    }
    @Override
    public void run() {
      log.info("TaskIndex 线程开始启动...");
      while (true) {
          try {
              TaskIndexDto taskIndexDto = TASK_INDEX_QUEUE.take();
              ArrayList<String> list = new ArrayList<>();
              sensitivityService.listDetail(list);
          } catch (Exception e) {
              log.error("TaskIndex 线程执行异常！",e);
          }

      }
    }
}
