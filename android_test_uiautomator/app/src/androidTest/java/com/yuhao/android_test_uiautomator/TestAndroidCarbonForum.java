package com.yuhao.android_test_uiautomator;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestAndroidCarbonForum {
    private static final String PACKAGE_NAME = "com.lincanbin.carbonforum";
    private static final int LAUNCH_TIMEOUT = 2000;
    private static UiDevice mDevice;

    @BeforeClass
    public static void startApp() throws RemoteException, UiObjectNotFoundException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Clear background apps
        mDevice.pressRecentApps();
        UiObject recentApps = mDevice.findObject(new UiSelector().resourceId("com.android.systemui:id/dismiss_task"));

        do {
            recentApps.waitForExists(LAUNCH_TIMEOUT);
            if (recentApps.exists()) {
                recentApps.click();
            }
        } while (recentApps.exists());

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        // Clear out any previous instances
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)),
                LAUNCH_TIMEOUT);
    }

    //Test case 1
    @Test
    public void OpenTopic() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/topic_list")).getChild(new UiSelector().index(2)).click();
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("com.lincanbin.carbonforum:id/post_list"));
        scroll.scrollForward();
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/toolbar")).getChild(new UiSelector().index(0)).click();
    }

    //Test case 2
    @Test
    public void NewTopic() throws UiObjectNotFoundException{
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/fab")).click();
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/title")).click();
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_SPACE);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_H);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_R);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_A);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_D);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);

        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_ENTER);

        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_SPACE);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_H);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_R);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_A);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_D);

        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/new_button")).click();
    }

    //Test case 3
    @Test
    public void Reply() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/topic_list")).getChild(new UiSelector().index(2)).click();

        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/fab")).click();
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/content")).click();

        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_SPACE);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_R);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_P);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_L);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_Y);

        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/reply_button")).click();

        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/toolbar")).getChild(new UiSelector().index(0)).click();
    }

    //Test case 4
    @Test
    public void Refresh() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Refresh")).click();
    }

    //Test case 5
    @Test
    public void SeeNotification() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Notifications")).click();
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/notifications_list")).swipeLeft(10);
        mDevice.findObject(new UiSelector().resourceId("com.lincanbin.carbonforum:id/toolbar")).getChild(new UiSelector().index(0)).click();
    }
}
