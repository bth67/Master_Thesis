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
public class TestCurrency {

    private static final String PACKAGE_NAME = "org.billthefarmer.currency";
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
    public void addCurrency() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/action_add")).click();
        UiScrollable scroll = new UiScrollable(new UiSelector().resourceId("org.billthefarmer.currency:id/list"));
        scroll.scrollForward();
        mDevice.findObject(new UiSelector().text("SEK")).click();
    }

    //Test case 2
    @Test
    public void InputAmount() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/edit")).click();
        mDevice.pressKeyCode(KeyEvent.KEYCODE_1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_2);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_3);
    }

    //Test case 3
    @Test
    public void EditCurrency() throws UiObjectNotFoundException {
        UiObject obj = mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/list")).getChild(new UiSelector().className("android.widget.LinearLayout").index(2));
        longClickUiObject(obj);
        mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/list")).getChild(new UiSelector().className("android.widget.LinearLayout").index(3)).click();
        mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/action_remove")).click();
    }

    public void longClickUiObject(UiObject obj) throws UiObjectNotFoundException {
        int x = obj.getBounds().centerX();
        int y = obj.getBounds().centerY();
        mDevice.swipe(x, y, x, y, 200);
    }

    //Test case 4
    @Test
    public void SeeExchangeRateChart() throws UiObjectNotFoundException {
        UiObject obj = mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/list")).getChild(new UiSelector().className("android.widget.LinearLayout").index(2));
        longClickUiObject(obj);
        mDevice.findObject(new UiSelector().descriptionContains("More options")).click();
        mDevice.findObject(new UiSelector().text("Chart")).click();
        //convert
        mDevice.findObject(new UiSelector().resourceId("org.billthefarmer.currency:id/action_invert")).click();
        //back
        mDevice.findObject(new UiSelector().description("Navigate up")).click();
    }
}