package cn.rzpt.mapper;

import cn.rzpt.model.domain.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author waxja
* @description 针对表【task】的数据库操作Mapper
* @createDate 2023-08-04 16:09:58
* @Entity generator.domain.Task
*/
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




