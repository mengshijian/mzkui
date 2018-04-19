package com.ctf.log.server.component;

import com.ctf.log.server.pojo.OnLineUser;
import com.ctf.log.server.pojo.User;
import com.ctf.log.server.utils.LogWrapper;
import com.ctf.oss.CCM;
import org.springframework.stereotype.Component;

import java.util.HashMap;


/**
 * @author Charles
 * @create 2017/7/21 15:06
 */
@Component
public class MongoDbUtil {
    private LogWrapper logger = LogWrapper.getLogger(MongoDbUtil.class);
    private static HashMap<String,User> userMap;

    static {
        userMap =  new HashMap<>();
        userMap.put("111",new User("111",null) );
        userMap.put("222",new User("222",null) );
        userMap.put("333",new User("333",null) );
        userMap.put("444",new User("444",null) );
        userMap.put("555",new User("555",null) );
    }


    public User getUser(String username) {
        //调用web端接口
        return userMap.get("111");
    }

    public void insertOnlineUser(OnLineUser onLineUser) {
        //调用web端接口
        CCM.onLineUserHashMap.put(onLineUser.getSessionId(),onLineUser);
    }

    public OnLineUser getOnlineUserBySid(byte[] sessionId){
        //调用web接口
        return CCM.onLineUserHashMap.get(sessionId);
    }
}