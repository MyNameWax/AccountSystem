package cn.rzpt.service.impl;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.rzpt.common.ErrorCode;
import cn.rzpt.exception.BusinessException;
import cn.rzpt.mapper.ManagetMapper;
import cn.rzpt.model.domain.Account;
import cn.rzpt.model.domain.Managet;
import cn.rzpt.model.dto.ManagetDTO;
import cn.rzpt.service.AccountService;
import cn.rzpt.service.ManagetService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static cn.rzpt.common.ErrorCode.*;

/**
 * @author waxja
 * @description 针对表【managet】的数据库操作Service实现
 * @createDate 2023-08-02 18:47:31
 */
@Service
public class ManagetServiceImpl extends ServiceImpl<ManagetMapper, Managet>
        implements ManagetService {

    @Resource
    private AccountService accountService;

    String key = "_=)&HyhWax521131"; // AES密钥，长度为16字节（128位）


    /**
     * 添加平台信息
     *
     * @param managetDTO 平台信息封装实体类
     * @param request    请求
     * @return 是否添加成功
     */
    @Override
    public boolean insertInfo(ManagetDTO managetDTO, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (ObjectUtils.isEmpty(managetDTO)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String name = managetDTO.getName();
        String account = managetDTO.getAccount();
        String password = managetDTO.getPassword();
        byte[] realNameByte = SecureUtil.aes(key.getBytes()).encrypt(name.getBytes());
        String realName = HexUtil.encodeHexStr(realNameByte);
        byte[] realAccountByte = SecureUtil.aes(key.getBytes()).encrypt(account.getBytes());
        String realAccount = HexUtil.encodeHexStr(realAccountByte);
        byte[] realPasswordByte = SecureUtil.aes(key.getBytes()).encrypt(password.getBytes());
        String realPassword = HexUtil.encodeHexStr(realPasswordByte);
        Integer id = loginUser.getId();
        if (StringUtils.isAnyBlank(name, account, password)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (id == null || id <= 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        LambdaQueryWrapper<Managet> wrapper = new LambdaQueryWrapper<>();
        byte[] nameByte = SecureUtil.aes(key.getBytes()).encrypt(name.getBytes());
        String nameReal = HexUtil.encodeHexStr(nameByte);
        wrapper.eq(Managet::getName, nameReal);
        wrapper.eq(Managet::getUid, id);
        Long count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException(PLATFORM_NAME_EXIST);
        }
        Managet managet = new Managet();
        BeanUtils.copyProperties(managetDTO, managet);
        managet.setUid(id);
        managet.setName(realName);
        managet.setAccount(realAccount);
        managet.setPassword(realPassword);
        int result = baseMapper.insert(managet);
        return result == 1;
    }

    /**
     * 删除平台信息
     *
     * @param id      删除平台信息的ID
     * @param request 请求
     * @return 是否删除成功
     */
    @Override
    public boolean deleteInfo(Integer id, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (id == null || id <= 0) {
            throw new BusinessException(NULL_ERROR);
        }
        Managet managet = baseMapper.selectById(id);
        if (managet == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Integer loginUserId = loginUser.getId();
        Integer insertUserId = managet.getUid();
        if (loginUserId == null || insertUserId == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        // 1. 判断登录的ID是否是属于本人的
        if (!loginUserId.equals(insertUserId)) {
            throw new BusinessException(NO_AUTH, "你无权删除别人的平台信息");
        }
        int result = baseMapper.deleteById(id);
        return result == 1;
    }

    /**
     * 更新平台信息
     *
     * @param managetDTO 用户信息封装类
     * @param request    请求
     * @return 是否更新成功
     */
    @Override
    public boolean updateInfo(ManagetDTO managetDTO, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (managetDTO == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Integer id = managetDTO.getId();
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        // 1. 先查询是否存在
        Managet managet = baseMapper.selectById(id);
        if (managet == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        // 用户是否是本人操作
        Integer insertUserId = managet.getUid();
        Integer loginUserId = loginUser.getId();

        if (insertUserId == null || loginUserId == null) {
            throw new BusinessException(PARAMS_ERROR);
        }
        if (!insertUserId.equals(loginUserId)) {
            throw new BusinessException(NO_AUTH, "您无权修改别人的信息");
        }
        if (!StringUtils.isEmpty(managetDTO.getAccount())) {
            byte[] realAccountByte = SecureUtil.aes(key.getBytes()).encrypt(managetDTO.getAccount().getBytes());
            String realAccount = HexUtil.encodeHexStr(realAccountByte);
            managet.setAccount(realAccount);
        }
        if (!StringUtils.isEmpty(managetDTO.getName())) {
            byte[] realNameByte = SecureUtil.aes(key.getBytes()).encrypt(managetDTO.getName().getBytes());
            String realName = HexUtil.encodeHexStr(realNameByte);
            managet.setName(realName);
        }
        if (!StringUtils.isEmpty(managetDTO.getPassword())) {
            byte[] realPasswordByte = SecureUtil.aes(key.getBytes()).encrypt(managetDTO.getPassword().getBytes());
            String realPassword = HexUtil.encodeHexStr(realPasswordByte);
            managet.setPassword(realPassword);
        }

        // 2. 修改信息
        int result = baseMapper.updateById(managet);
        // 3. 结果返回
        return result == 1;
    }

    @Override
    public IPage<Managet> pageList(String platName, Integer page, Integer limit, HttpServletRequest request) {
        // 用户是否登录  如果为空会抛异常
        Account loginUser = accountService.getLoginUser(request);
        // 判断是否存在搜索关键字 默认为空
        LambdaQueryWrapper<Managet> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(platName)) {
            wrapper.like(Managet::getName, platName);
        }
        Page<Managet> pageList = new Page<>(page, limit);
        IPage<Managet> managetIPage = baseMapper.selectPage(pageList, wrapper);
        List<Managet> records = managetIPage.getRecords();
        for (Managet record : records) {
            byte[] byteName = HexUtil.decodeHex(record.getName()); // 将加密后的十六进制字符串转换为字节数组
            byte[] RealByteName = SecureUtil.aes(key.getBytes()).decrypt(byteName);
            String RealName = new String(RealByteName);
            record.setName(RealName);

            byte[] byteAccount = HexUtil.decodeHex(record.getAccount()); // 将加密后的十六进制字符串转换为字节数组
            byte[] RealByteAccount = SecureUtil.aes(key.getBytes()).decrypt(byteAccount);
            String RealAccount = new String(RealByteAccount);
            record.setAccount(RealAccount);

            byte[] bytePassword = HexUtil.decodeHex(record.getPassword()); // 将加密后的十六进制字符串转换为字节数组
            byte[] RealBytePassword = SecureUtil.aes(key.getBytes()).decrypt(bytePassword);
            String RealPassword = new String(RealBytePassword);
            record.setPassword(RealPassword);
        }
        return managetIPage;
    }

    @Override
    public Managet getManagetById(Integer id, HttpServletRequest request) {
        Account loginUser = accountService.getLoginUser(request);
        if (id == null || id <= 0) {
            throw new BusinessException(PARAMS_ERROR);
        }
        Managet managet = baseMapper.selectById(id);
        byte[] byteName = HexUtil.decodeHex(managet.getName()); // 将加密后的十六进制字符串转换为字节数组
        byte[] RealByteName = SecureUtil.aes(key.getBytes()).decrypt(byteName);
        String RealName = new String(RealByteName);
        managet.setName(RealName);

        byte[] byteAccount = HexUtil.decodeHex(managet.getAccount()); // 将加密后的十六进制字符串转换为字节数组
        byte[] RealByteAccount = SecureUtil.aes(key.getBytes()).decrypt(byteAccount);
        String RealAccount = new String(RealByteAccount);
        managet.setAccount(RealAccount);

        byte[] bytePassword = HexUtil.decodeHex(managet.getPassword()); // 将加密后的十六进制字符串转换为字节数组
        byte[] RealBytePassword = SecureUtil.aes(key.getBytes()).decrypt(bytePassword);
        String RealPassword = new String(RealBytePassword);
        managet.setPassword(RealPassword);
        return managet;
    }
}




