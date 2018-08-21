package com.yuhao.android_test_appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.KeyEventFlag;
import io.appium.java_client.android.nativekey.KeyEventMetaModifier;

import java.net.URL;

public class TestCalendarII {

    private static AppiumDriver driver;


    @BeforeClass
    public static void setup() throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.BROWSER_NAME, "");
        cap.setCapability("platformName", "Android"); //指定测试平台
        cap.setCapability("deviceName", "Android Emulator"); //指定测试机的ID,通过adb命令`adb devices`获取
        cap.setCapability("platformVersion", "7.1.1");

        //将上面获取到的包名和Activity名设置为值
        cap.setCapability("appPackage", "top.soyask.calendarii");
        cap.setCapability("appActivity", ".MainActivity");

        //A new session could not be created的解决方法
        cap.setCapability("appWaitActivity",".MainActivity");
        //每次启动时覆盖session，否则第二次后运行会报错不能新建session
        cap.setCapability("sessionOverride", true);


        cap.setCapability("unicodeKeyboard", "True");
        cap.setCapability("resetKeyboard", "True");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
    }

    @Test
    public void addEvents() {
        for (int i = 0; i < 2; i++) {
            addOneEvent();
        }
    }

    public void addOneEvent() {
        driver.findElementById("top.soyask.calendarii:id/add_event").click();
超         MobileElement event = (MobileElement) driver.findElementById("top.soyask.calendarii:id/et");
        event.sendKeys("Hello world!");
        driver.findElementById("top.soyask.calendarii:id/ib_done").click();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}