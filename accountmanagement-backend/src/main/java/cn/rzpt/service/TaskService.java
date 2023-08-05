package cn.rzpt.service;

import cn.rzpt.model.domain.Task;
import cn.rzpt.model.dto.TaskDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author waxja
* @description 针对表【task】的数据库操作Service
* @createDate 2023-08-04 16:09:58
*/
public interface TaskService extends IService<Task> {

    List<Task> listTask(HttpServletRequest request);

    boolean insertTask(TaskDTO taskDTO, HttpServletRequest request);

    boolean deleteTask(Integer id, HttpServletRequest request);

    boolean deleteDayTask();

    boolean changeTaskStatus(Integer id, HttpServletRequest request);
}
