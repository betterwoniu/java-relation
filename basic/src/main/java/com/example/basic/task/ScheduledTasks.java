package com.example.basic.task;

import com.example.basic.entity.TaskIndexDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ScheduledTasks {
    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask(){
        try {
            String id = UUID.randomUUID().toString();
            TaskIndexDto build = TaskIndexDto.builder().id(id).message("taskindex 任务id:"+id).build();
            TaskIndex.TASK_INDEX_QUEUE.put(build);
        } catch (Exception ex) {
            log.error("TaskIndex 线程执行错误",ex);
        }
    }
}
