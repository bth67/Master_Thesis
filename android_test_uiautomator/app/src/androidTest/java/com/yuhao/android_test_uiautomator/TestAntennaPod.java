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
public class TestAntennaPod {

    private static final String PACKAGE_NAME = "de.danoeh.antennapod.debug";
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
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Add Podcast")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butBrowseGpoddernet")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/gridView")).getChild(new UiSelector().className("android.widget.RelativeLayout").index(3)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butSubscribe")).click();

        mDevice.findObject(new UiSelector().text("OPEN PODCAST")).click();
    }

    //Test case 2
    @Test
    public void AddToQueue() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/nav_list")).getChild(new UiSelector().className("android.widget.RelativeLayout").index(7)).click();
        mDevice.findObject(new UiSelector().className("android.widget.ListView")).getChild(new UiSelector().className("android.widget.LinearLayout").index(1)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().className("android.support.v7.widget.LinearLayoutCompat")).getChild(new UiSelector().className("android.widget.ImageView")).click();
        mDevice.findObject(new UiSelector().text("Add to Queue")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Queue")).click();
    }

    //Test case 3
    @Test
    public void StreamEpisode() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/recyclerView")).getChild(new UiSelector().className("android.widget.FrameLayout").index(1)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butAction2")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
    }

    //Test case 4
    @Test
    public void DownloadEpisode() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/recyclerView")).getChild(new UiSelector().className("android.widget.FrameLayout").index(1)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butAction1")).click();
        mDevice.findObject(new UiSelector().textContains("Delete")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
    }

    //Test case 5
    @Test
    public void ClearQueue() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().className("android.support.v7.widget.LinearLayoutCompat")).getChild(new UiSelector().className("android.widget.ImageView")).click();
        mDevice.findObject(new UiSelector().text("Clear Queue")).click();
        mDevice.findObject(new UiSelector().text("CONFIRM")).click();
    }

    //Test case 6
    @Test
    public void ChangeTheme() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Settings")).click();
        mDevice.findObject(new UiSelector().text("User Interface")).click();
        mDevice.findObject(new UiSelector().text("Select Theme")).click();
        mDevice.findObject(new UiSelector().text("Dark")).click();
    }

    //Test case 7
    @Test
    public void CheckDownloads() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().index(0)).click();
        mDevice.findObject(new UiSelector().text("Downloads")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/viewpager")).swipeLeft(10);
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/viewpager")).swipeLeft(10);
    }

    //Test case 8
    @Test
    public void PlayNPause() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/recyclerView")).getChild(new UiSelector().className("android.widget.FrameLayout").index(0)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butAction2")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butFF")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butRev")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butPlay")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().className("android.widget.ImageButton")).click();
    }

    //Test case 9
    @Test
    public void AdjustSpeed() throws UiObjectNotFoundException {
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/recyclerView")).getChild(new UiSelector().className("android.widget.FrameLayout").index(0)).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butAction2")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butPlaybackSpeed")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/butPlay")).click();
        mDevice.findObject(new UiSelector().resourceId("de.danoeh.antennapod.debug:id/toolbar")).getChild(new UiSelector().className("android.widget.ImageButton")).click();
    }
}

