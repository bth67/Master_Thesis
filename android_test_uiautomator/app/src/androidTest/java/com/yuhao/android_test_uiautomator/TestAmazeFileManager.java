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
public class TestAmazeFileManager {
    private static final String PACKAGE_NAME = "com.amaze.filemanager";
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

    //NewFolder
    @Test
    public void Test1() throws UiObjectNotFoundException {
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

    //NewFile
    @Test
    public void Test2() throws UiObjectNotFoundException {
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

    //ChangeTheme
    @Test
    public void Test3() throws UiObjectNotFoundException {
        UiObject act=mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/action_bar"));
        act.getChild(new UiSelector().className("android.widget.ImageButton")).click();
        UiScrollable sc=new UiScrollable(new UiSelector().resourceId("com.amaze.filemanager:id/design_navigation_view"));
        sc.scrollForward();
        mDevice.findObject(new UiSelector().text("Settings")).click();
        mDevice.findObject(new UiSelector().text("Theme")).click();
        mDevice.findObject(new UiSelector().text("Material Dark")).click();
        UiObject tool=mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/toolbar"));
        tool.getChild(new UiSelector().index(0)).click();
    }

    //ChangColor
    @Test
    public void Test4() throws UiObjectNotFoundException {
        UiObject act=mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/action_bar"));
        act.getChild(new UiSelector().className("android.widget.ImageButton")).click();
        UiScrollable sc=new UiScrollable(new UiSelector().resourceId("com.amaze.filemanager:id/design_navigation_view"));
        sc.scrollForward();
        mDevice.findObject(new UiSelector().text("Settings")).click();
        mDevice.findObject(new UiSelector().text("Color")).click();
        mDevice.findObject(new UiSelector().text("Select color config")).click();
        mDevice.findObject(new UiSelector().text("Preselected configs")).click();
        mDevice.findObject(new UiSelector().text("Orange")).click();
        mDevice.findObject(new UiSelector().text("OK")).click();
        UiObject tool=mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/toolbar"));
        tool.getChild(new UiSelector().index(0)).click();
        tool.getChild(new UiSelector().index(0)).click();
        tool.getChild(new UiSelector().index(0)).click();
    }

    //Search
    @Test
    public void Test5() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/search")).click();
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_E);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_S);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_T);
        mDevice.pressKeyCode(KeyEvent.KEYCODE_SEARCH);
        mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/img_view_back")).click();
    }

    //OpenFile
    @Test
    public void Test6() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/home")).click();
        mDevice.findObject(new UiSelector().text("Download")).click();
        mDevice.findObject(new UiSelector().textContains("IMG")).click();
        mDevice.pressBack();
    }

    //AddToBookmark
    @Test
    public void Test7() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/home")).click();

        mDevice.findObject(new UiSelector().resourceId("com.amaze.filemanager:id/properties")).click();
        mDevice.findObject(new UiSelector().text("Add to Bookmark")).click();
    }
}
