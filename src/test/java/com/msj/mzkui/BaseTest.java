package com.msj.mzkui;

import com.msj.mzkui.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //14.版本之前用的是SpringJUnit4ClassRunner.class
@SpringBootTest(classes = WebApplication.class)
//1.4版本之前用的是//@SpringApplicationConfiguration(classes = Application.class)
public class BaseTest {

    @Test
    public void test() {

    }
}
