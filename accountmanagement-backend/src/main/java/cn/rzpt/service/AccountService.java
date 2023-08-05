package cn.rzpt.service;

import cn.rzpt.model.domain.Account;
import cn.rzpt.model.dto.AccountDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author waxja
* @description 针对表【account】的数据库操作Service
* @createDate 2023-08-02 17:21:46
*/
public interface AccountService extends IService<Account> {

    Account userLogin(AccountDTO accountDTO, HttpServletRequest request);

    boolean userRegister(AccountDTO accountDTO);

    void userLogout(HttpServletRequest request);

    Account getLoginUser(HttpServletRequest request);

    String getCaption(HttpServletRequest request, HttpServletResponse response);
}
