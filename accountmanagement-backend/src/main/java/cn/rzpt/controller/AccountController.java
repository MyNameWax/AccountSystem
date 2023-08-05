package cn.rzpt.controller;

import cn.rzpt.common.BaseResponse;
import cn.rzpt.common.ResultUtils;
import cn.rzpt.model.domain.Account;
import cn.rzpt.model.dto.AccountDTO;
import cn.rzpt.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(tags = "用户模块")
@RestController
@RequestMapping("/account")
@Slf4j
public class AccountController {
    @Resource
    private AccountService accountService;


    @PostMapping("/login")
    @ApiOperation("用户登录")
    public BaseResponse<Account> userLogin(@RequestBody AccountDTO accountDTO, HttpServletRequest request) {
        log.info("用户登录:{}",accountDTO);
        Account account = accountService.userLogin(accountDTO,request);
        return ResultUtils.success(account);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public BaseResponse<Boolean> userRegister(@RequestBody AccountDTO accountDTO) {
        log.info("用户注册:{}",accountDTO);
        boolean isSuccess = accountService.userRegister(accountDTO);
        return ResultUtils.success(isSuccess);
    }
    @ApiOperation("注销登录")
    @PostMapping("/logout")
    public BaseResponse userLogout(HttpServletRequest request) {
        log.info("用户注销当次登录");
        accountService.userLogout(request);
        return ResultUtils.success(null);
    }

    @GetMapping("/getCaption")
    @ApiOperation("获取验证码")
    public BaseResponse<String> getCaption(HttpServletRequest request, HttpServletResponse response) {
        log.info("获取验证码");
        String caption = accountService.getCaption(request, response);
        return ResultUtils.success(caption);
    }
    @GetMapping("/info")
    @ApiOperation("获取个人信息")
    public BaseResponse<Account> getInfo(HttpServletRequest request) {
        log.info("获取个人信息");
        Account loginUser = accountService.getLoginUser(request);
        return ResultUtils.success(loginUser);
    }

}
