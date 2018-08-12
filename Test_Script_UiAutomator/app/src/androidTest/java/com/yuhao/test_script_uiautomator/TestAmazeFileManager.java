package com.yuhao.test_script_uiautomator;

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
import android.support.test.uiautomator.UiScrollable;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestAmazeFileManager {

    private static final String PACKAGE_NAME = "com.amaze.filemanager";
    private static final int LAUNCH_TIMEOUT = 2000;
    private UiDevice mDevice;

    @Before
    public void startApp() throws RemoteException, UiObjectNotFoundException {
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
    public void NewFolder() throws UiObjectNotFoundException {
        //Press new button
        UiObject NewBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/fab_expand_menu_button"));
        NewBtn.click();
        UiObject NewFolderBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/menu_new_folder"));
        NewFolderBtn.click();

        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);

        UiObject CreateBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/md_buttonDefaultPositive"));
        CreateBtn.click();
    }

    //Test case 2
    @Test
    public void NewFile() throws UiObjectNotFoundException {
        //Press new button
        UiObject NewBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/fab_expand_menu_button"));
        NewBtn.click();
        UiObject NewFileBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/menu_new_file"));
        NewFileBtn.click();

        //Input file name
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T, 1);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);

        //Click create
        UiObject CreateBtn=mDevice.findObject(new UiSelector().resourceId(PACKAGE_NAME + ":id/md_buttonDefaultPositive"));
        CreateBtn.click();
    }

    //Test case 3
    @Test
    public void 
}

