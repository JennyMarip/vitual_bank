package com.example.vbank_cryptology.mapper;
import com.example.vbank_cryptology.entity.user;
import com.example.vbank_cryptology.entity.user_account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MainMapper {
    /***  创建账户 ***/
    @Insert("insert into user_account (username,balance,cardNum,hashcode) values(#{username},#{balance},#{cardNum},#{hashcode})")
    int creatAccount(String username,String balance,String cardNum,String hashcode);
    /******/

    /*** 获取账户详细信息 ***/
    @Select("select * from user_account where username=#{username}")
    user_account getAccountByName(String username);
    @Select("select * from user_account where username=#{username} and hashcode=#{hashcode}")
    user_account getAccount_name_hash(String username,String hashcode);
    @Select("select * from user_account where username=#{username} and cardNum=#{cardNum}")
    user_account getAccount_name_card(String username,String cardNum);
    @Select("select * from user_account where cardNum=#{cardNum}")
    user_account getAccount_card(String cardNum);
    /******/

    /*** 获取余额 ***/
    @Select("select balance from user_account where username=#{username}")
    String getBalance(String username);
    /******/

    /*** 存/取钱业务 ***/
    @Update("update user_account set balance=#{balance} where username=#{username}")
    int set(String username,String balance);
    /******/

    /*** 查询用户口令散列值 ***/
    @Select("select hashcode from user_account where username=#{username}")
    String getHashCodeByName(String username);

    /*** 获取用户密码 ***/
    @Select("select password from user_account where username=#{username}")
    String getPassByName(String username);




    /******************* 普通用户操作 *******************/

    /*** 创建普通用户 ***/
    @Insert("insert into client (username,hashcode) values(#{username},#{hashcode})")
    int creatUser(String username,String hashcode);
    /*** 获取普通用户信息 ***/
    @Select("select * from client where hashcode=#{hashcode}")
    user getUserByHash(String hashcode);
    @Select("select * from client where username=#{username}")
    user getUserByName(String username);

    /*** 通过银行卡号获取余额 ***/
    @Select("select balance from user_account where cardNum=#{cardNum}")
    String getBalance_cardNum(String cardNum);
}
