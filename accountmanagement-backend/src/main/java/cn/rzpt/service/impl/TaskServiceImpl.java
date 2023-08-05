package cn.rzpt.service.impl;

import cn.rzpt.common.ErrorCode;
import cn.rzpt.exception.BusinessException;
import cn.rzpt.model.domain.Account;
import cn.rzpt.model.domain.Task;
import cn.rzpt.model.dto.TaskDTO;
import cn.rzpt.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.rzpt.service.TaskService;
import cn.rzpt.mapper.TaskMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static cn.rzpt.common.ErrorCode.*;

/**
* @author waxja
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-08-04 16:09:58
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

    @Resource
    private AccountService accountService;

    @Override
    public List<Task> listTask(HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUid,loginUser.getId());
        List<Task> taskList = baseMapper.selectList(wrapper);
        return taskList;
    }

    @Override
    public boolean insertTask(TaskDTO taskDTO, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (ObjectUtils.isEmpty(taskDTO)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String taskName = taskDTO.getTaskName();
        if (StringUtils.isEmpty(taskName)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"任务信息不能为空");
        }
        if (taskName.length() > 20) {
            throw new BusinessException(PARAMS_ERROR,"任务保持在20字之内");
        }
        //1. 查询用户现在有几条任务信息
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getUid,loginUser.getId());
        Long count = baseMapper.selectCount(wrapper);
        if (count >= 5) {
            throw new BusinessException(NOT_ALLOW_INSERT,"今天的任务量已经很大了,放在明天做吧~");
        }
        //2. 查看用户是否有相同的任务
        wrapper.eq(Task::getTaskName,taskName);
        Long countTask = baseMapper.selectCount(wrapper);
        if (countTask != 0) {
            throw new BusinessException(TASK_EXISTS,"任务已存在");
        }

        Task task = new Task();
        task.setUid(loginUser.getId());
        task.setTaskName(taskName);
        int insert = baseMapper.insert(task);
        return insert == 1 ;
    }

    @Override
    public boolean deleteTask(Integer id, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Task task = baseMapper.selectById(id);
        if (task == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Integer insertTaskUserId = task.getUid();
        Integer currentLoginUserId = loginUser.getId();
        if (!currentLoginUserId.equals(insertTaskUserId)) {
            throw new BusinessException(NO_AUTH,"您无权删除别人的任务");
        }
        int result = baseMapper.deleteById(id);
        return result == 1;
    }

    @Override
    public boolean deleteDayTask() {
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Task::getIsDelete,0);
        int delete = baseMapper.delete(wrapper);
        return delete == 1;
    }

    @Override
    public boolean changeTaskStatus(Integer id, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Task task = baseMapper.selectById(id);
        Integer status = task.getStatus();
        if (status == 0) {
            status = 1;
        } else {
            status = 0;
        }
        task.setStatus(status);
        int result = baseMapper.updateById(task);
        return result == 1;
    }
}




