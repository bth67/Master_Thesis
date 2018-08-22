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
public class TestCalendarII {

    private static final String PACKAGE_NAME = "top.soyask.calendarii";
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
    public void addEvents() throws UiObjectNotFoundException {
        for (int i = 0; i < 2; i++) {
            addOneEvent();
        }
    }

    public void addOneEvent() throws UiObjectNotFoundException {
        UiObject addButton = mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/add_event"));
        if (addButton.exists() && addButton.isEnabled()) {
            addButton.click();
            mDevice.waitForWindowUpdate(PACKAGE_NAME, LAUNCH_TIMEOUT);
        }
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_PERIOD);
        UiObject doneButton = mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/ib_done"));
        if (doneButton.exists() && doneButton.isEnabled()) {
            doneButton.click();
            mDevice.waitForWindowUpdate(PACKAGE_NAME, LAUNCH_TIMEOUT);
        }
    }

    //Test case 2
    @Test
    public void deleteCrossed() throws UiObjectNotFoundException {
        addOneEvent();
        viewEvents();
        crossedOutOneEvent(50, 365);
        clickDeleteButton();

        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/rb_comp")).click();
        longClickUiObjectByResourceId(PACKAGE_NAME + ":id/cpb");
    }

    public void crossedOutOneEvent(int x, int y) throws UiObjectNotFoundException {
        int endX = x + 800;
        mDevice.swipe(x, y, endX, y, 10);
    }

    //Test case 3
    @Test
    public void deleteAll() throws UiObjectNotFoundException {
        addOneEvent();
        viewEvents();
        clickDeleteButton();

        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/rb_all")).click();
        longClickUiObjectByResourceId(PACKAGE_NAME + ":id/cpb");
    }

    public void viewEvents() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/menu_all_event")).click();
    }

    public void clickDeleteButton() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/ib_delete_all")).click();
    }

    public void longClickUiObjectByResourceId(String id) throws UiObjectNotFoundException {
        int x = mDevice.findObject(new UiSelector().resourceId(id)).getBounds().centerX();
        int y = mDevice.findObject(new UiSelector().resourceId(id)).getBounds().centerY();
        mDevice.swipe(x, y, x, y, 300);
    }

    //Test case 4
    @Test
    public void editEvent() throws UiObjectNotFoundException {
        addOneEvent();
        viewEvents();
        viewDetail();

        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/ib_edit")).click();

        mDevice.pressKeyCode(KeyEvent.KEYCODE_SPACE);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_D);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_I);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_D);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_PERIOD);

        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/ib_done")).click();
    }

    public void viewDetail() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/ib_down")).click();
    }

    //Test case 5
    @Test
    public void backToToday() throws UiObjectNotFoundException {
        for (int i = 0; i < 5; i++) {
            switchToNextMonth();
        }

        mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/menu_today")).click();
    }

    public void switchToNextMonth() throws UiObjectNotFoundException {
        String id = PACKAGE_NAME + ":id/rv_date";
        mDevice.findObject(new UiSelector().resourceId(id)).swipeLeft(10);
    }

    //Test case 6
    @Test
    public void jumpTo() throws UiObjectNotFoundException {
        mDevice.pressMenu();
        mDevice.findObject(new UiSelector().textContains("跳转到指定日期")).click();
        UiObject detePicker = mDevice.findObject(new UiSelector().resourceId("top.soyask.calendarii:id/ll"));
        detePicker.getChild(new UiSelector().index(0)).swipeDown(5);
        detePicker.getChild(new UiSelector().index(1)).swipeUp(10);
        detePicker.getChild(new UiSelector().index(2)).swipeDown(15);
        mDevice.findObject(new UiSelector().resourceId("top.soyask.calendarii:id/btn_confirm")).click();
    }

    //Test case 7
    @Test
    public void ChangeTheme() throws UiObjectNotFoundException {
        mDevice.pressMenu();
        mDevice.findObject(new UiSelector().textContains("设置")).click();
        mDevice.findObject(new UiSelector().textContains("主题风格")).click();
        mDevice.findObject(new UiSelector().textContains("哔哩粉")).click();
    }

    //Test case 8
    @Test
    public void SyncHoliday() throws UiObjectNotFoundException {
        mDevice.pressMenu();
        mDevice.findObject(new UiSelector().textContains("设置")).click();
        mDevice.findObject(new UiSelector().textContains("节假日同步")).click();
    }
}

