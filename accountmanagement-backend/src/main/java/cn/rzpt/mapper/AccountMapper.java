package cn.rzpt.mapper;

import cn.rzpt.model.domain.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author waxja
* @description 针对表【account】的数据库操作Mapper
* @createDate 2023-08-02 17:21:46
* @Entity generator.domain.Account
*/

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}




