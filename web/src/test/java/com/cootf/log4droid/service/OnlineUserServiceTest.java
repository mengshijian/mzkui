package com.cootf.log4droid.service;

import static org.junit.Assert.*;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.common.uenum.ClientType;
import com.cootf.log4droid.entity.OnLineUser;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OnlineUserServiceTest extends BaseTest {

    @Autowired
    private OnlineUserService service;

    private OnLineUser onLineUser;

    @Before
    public void initEntity() {
        onLineUser = new OnLineUser();
        onLineUser.setClientType(ClientType.PC.getCode());
        onLineUser.setModel("NO1.1.0");
        onLineUser.setVersion("V1.1.0");
        onLineUser.setImei("4679871354321");
        onLineUser.setSessionId("45645464656");
        onLineUser.setMacAddress("AC:UR:WA:BC");
        onLineUser.setStatus(0);
        onLineUser.setLastLoginTime(new Date());
    }

    @Test
    public void saveOneTest() {
        service.saveOne(onLineUser);
    }
}