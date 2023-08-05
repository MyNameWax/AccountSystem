package cn.rzpt.controller;


import cn.rzpt.common.BaseResponse;
import cn.rzpt.common.ResultUtils;
import cn.rzpt.model.domain.Task;
import cn.rzpt.model.dto.TaskDTO;
import cn.rzpt.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/task")
@Slf4j
@Api(tags = "任务管理")
public class TaskController {

    @Resource
    private TaskService taskService;

    @GetMapping("list")
    @ApiOperation("查询所有任务")
    public BaseResponse<List<Task>> list(HttpServletRequest request) {
        log.info("查询所有任务");
        List<Task> taskList= taskService.listTask(request);
        return ResultUtils.success(taskList);
    }

    @PostMapping("insert")
    @ApiOperation("添加任务")
    public BaseResponse<Boolean> insertTask(@RequestBody TaskDTO taskDTO, HttpServletRequest request) {
        log.info("添加任务:{}",taskDTO);
        boolean Result = taskService.insertTask(taskDTO,request);
        return ResultUtils.success(Result);
    }

    @PostMapping("/delete/{id}")
    @ApiOperation("删除任务")
    public BaseResponse<Boolean> deleteTask(@PathVariable Integer id,HttpServletRequest request) {
        log.info("删除任务:{}",id);
        boolean result = taskService.deleteTask(id,request);
        return ResultUtils.success(result);
    }
    @PostMapping("/change/{id}")
    @ApiOperation("更改任务状态")
    public BaseResponse<Boolean> changeTaskStatus(@PathVariable Integer id,HttpServletRequest request) {
        log.info("更改任务状态:{}",id);
        boolean result = taskService.changeTaskStatus(id,request);
        return ResultUtils.success(result);
    }


}
