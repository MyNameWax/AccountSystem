package cn.rzpt.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.rzpt.common.ErrorCode;
import cn.rzpt.exception.BusinessException;
import cn.rzpt.mapper.AccountMapper;
import cn.rzpt.model.domain.Account;
import cn.rzpt.model.dto.AccountDTO;
import cn.rzpt.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.rzpt.common.ErrorCode.*;
import static cn.rzpt.constanct.CaptchaImg.CAPTCHA_FINAL_STATUS;
import static cn.rzpt.constanct.UserLoginStatus.USER_LOGIN_STATUS;

/**
 * @author waxja
 * @description 针对表【account】的数据库操作Service实现
 * @createDate 2023-08-02 17:21:46
 */
@Service
@Slf4j
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
        implements AccountService {


    /**
     * 用户登录
     *
     * @param accountDTO 用户登录信息封装实体
     * @param request    请求
     * @return 登录成功用户具体信息
     */
    @Override
    public Account userLogin(AccountDTO accountDTO, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(accountDTO)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数传递错误");
        }
        String username = accountDTO.getUsername();
        String userPassword = accountDTO.getUserPassword();
        String code = accountDTO.getCode();
        if (StringUtils.isAnyBlank(username, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "账号密码不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(NULL_ERROR,"验证码不能为空");
        }
        // 判断验证码是否正确
        String realCode = (String) request.getSession().getAttribute(CAPTCHA_FINAL_STATUS);
        if (!code.equals(realCode)) {
            throw new BusinessException(VERITY_CODE_ERROR, "验证码错误");
        }
        String safetyPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("userPassword", safetyPassword);
        Account account = baseMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(account)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "账号或密码错误");
        }
        Integer status = account.getStatus();
        if (status != 0) {
            throw new BusinessException(ACCOUNT_NOT_ALLOW_LOGIN, "账号被禁用");
        }
        // 返回安全用户 （用户脱敏）
        Account safetyUser = safetyUser(account);
        // 设置用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATUS, safetyUser);
        return safetyUser;
    }

    /**
     * 用户注册
     *
     * @param accountDTO 用户注册信息封装实体
     * @return 是否登录成功
     */
    @Override
    public boolean userRegister(AccountDTO accountDTO) {
        //判断实体类是否为空
        if (ObjectUtils.isEmpty(accountDTO)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 获取用户名和密码
        String username = accountDTO.getUsername();
        String userPassword = accountDTO.getUserPassword();
        //判断用户名密码是否为空
        if (StringUtils.isAnyBlank(username, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 判断用户名是否存在
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getUsername, username);
        Long count = baseMapper.selectCount(wrapper);
        if (count != 0) {
            throw new BusinessException(ACCOUNT_EXIST_ERROR);
        }
        //把用户输入的密码进行加密
        String registerPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());
        Account account = new Account();
        accountDTO.setUserPassword(registerPassword);
        BeanUtils.copyProperties(accountDTO, account);
        int result = baseMapper.insert(account);
        return result == 1;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATUS) != null) {
            request.getSession().removeAttribute(USER_LOGIN_STATUS);
            return;
        }
        throw new BusinessException(NOT_LOGIN);

    }

    private static Account safetyUser(Account account) {
        Account safetyUser = new Account();
        account.setUserPassword(null);
        BeanUtils.copyProperties(account, safetyUser);
        return safetyUser;
    }

    @Override
    public Account getLoginUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(NOT_LOGIN);
        }
        Account account = (Account) request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (account == null) {
            throw new BusinessException(NOT_LOGIN);
        }
        return account;
    }


    @Override
    public String getCaption(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        // 1. 获取验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100, 50);
        // 2. 获取验证码到流
        String imageBase64 = lineCaptcha.getImageBase64Data();
        log.info("验证码为:{}", lineCaptcha.getCode());
        // 3. 将验证码放入session
        request.getSession().setAttribute(CAPTCHA_FINAL_STATUS, lineCaptcha.getCode());
        return imageBase64;

    }
}




