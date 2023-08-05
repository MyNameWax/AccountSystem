package cn.rzpt.service;

import cn.rzpt.model.domain.Managet;
import cn.rzpt.model.dto.ManagetDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
* @author waxja
* @description 针对表【managet】的数据库操作Service
* @createDate 2023-08-02 18:47:31
*/
public interface ManagetService extends IService<Managet> {

    boolean insertInfo(ManagetDTO managetDTO, HttpServletRequest request);

    boolean deleteInfo(Integer id, HttpServletRequest request);


    boolean updateInfo(ManagetDTO managetDTO, HttpServletRequest request);

    IPage<Managet> pageList(String platName, Integer page, Integer limit, HttpServletRequest request);


    Managet getManagetById(Integer id, HttpServletRequest request);
}
