package cn.rzpt.controller;


import cn.rzpt.common.BaseResponse;
import cn.rzpt.common.ResultUtils;
import cn.rzpt.model.domain.Managet;
import cn.rzpt.model.dto.ManagetDTO;
import cn.rzpt.service.ManagetService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/manage")
@Api(tags = "平台账号管理模块")
@RestController
public class ManagetController {
    @Resource
    private ManagetService managetService;


    @ApiOperation("添加平台信息")
    @PostMapping("/insertInfo")
    public BaseResponse<Boolean> insertInfo(@RequestBody ManagetDTO managetDTO, HttpServletRequest request) {
        log.info("用户添加平台信息:{}",managetDTO);
        boolean isSuccess = managetService.insertInfo(managetDTO,request);
        return ResultUtils.success(isSuccess);
    }

    @ApiOperation("删除平台信息")
    @PostMapping("/deleteInfo/{id}")
    public BaseResponse<Boolean> deleteInfo(@PathVariable Integer id, HttpServletRequest request) {
        log.info("删除平台信息:{}",id);
        boolean isDelete = managetService.deleteInfo(id,request);
        return ResultUtils.success(isDelete);
    }

    @ApiOperation("通过ID查询平台信息")
    @GetMapping("/get/{id}")
    public BaseResponse<Managet> getById(@PathVariable Integer id,HttpServletRequest request) {
        log.info("通过id获取平台信息:{}",id);
        Managet managet = managetService.getManagetById(id,request);
        return ResultUtils.success(managet);
    }

    @ApiOperation("修改平台信息")
    @PostMapping("/updateInfo")
    public BaseResponse<Boolean> updateInfo(@RequestBody ManagetDTO managetDTO,HttpServletRequest request) {
        log.info("更新平台信息:{}",managetDTO);
        boolean isUpdate =  managetService.updateInfo(managetDTO,request);
        return ResultUtils.success(isUpdate);
    }
    @GetMapping("list/{page}/{limit}")
    @ApiOperation("查询自己所有平台信息")
    public BaseResponse<IPage<Managet>> list(@RequestParam(required = false) String platName,
                                            @PathVariable Integer page,
                                            @PathVariable Integer limit,
                                            HttpServletRequest request
                                            ) {
        log.info("查询自己所有平台信息模糊查询关键字:{},页码:{}页数:{}",platName,page,limit);
        IPage<Managet> list = managetService.pageList(platName,page,limit,request);
        return ResultUtils.success(list);
    }

}
