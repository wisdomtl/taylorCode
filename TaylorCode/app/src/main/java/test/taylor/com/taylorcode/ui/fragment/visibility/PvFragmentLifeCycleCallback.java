/*
 * Copyright (c) 2015-2018 BiliBili Inc.
 */

package test.taylor.com.taylorcode.ui.fragment.visibility;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;


/**
 * @author : windfall
 * @date : 2018/11/8
 * @email : liuchangjiang@bilibili.com
 */
class PvFragmentLifeCycleCallback extends FragmentManager.FragmentLifecycleCallbacks {
    private Map<String, Integer> mFragmentLoadType = new HashMap<>();

    private String mLastVisibleUniqueId = "";
    private boolean mIsIntercept = false;
    private boolean mHasListenViewPager = false;

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        if (f instanceof IPvTracker) {
            String eventId = getEventId(f);
            if (TextUtils.isEmpty(eventId) || mFragmentLoadType == null) {
                return;
            }
            mFragmentLoadType.put(PageViewTracker.getUniqueId(f, eventId), PvStateManager.LOAD_TYPE_ENTER);
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        if (mIsIntercept) {
            return;
        }
        if (f.getUserVisibleHint() && !isFragmentHidden(f)) {
            String eventId = getEventId(f);
            if (!TextUtils.isEmpty(eventId)) {
                onFragmentVisibleChanged(f, true, false);
            }
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        if (mIsIntercept) {
            return;
        }
        if (f.getUserVisibleHint() && !isFragmentHidden(f)) {
            String eventId = getEventId(f);
            if (!TextUtils.isEmpty(eventId)) {
                onFragmentVisibleChanged(f, false, false);
            }
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        if (f instanceof IPvTracker && mFragmentLoadType != null) {
            String eventId = getEventId(f);
            if (TextUtils.isEmpty(eventId)) {
                return;
            }
            mFragmentLoadType.remove(PageViewTracker.getUniqueId(f, eventId));
        }
    }

    public void onFragmentVisibleChanged(@Nullable Fragment fragment, boolean isVisible, boolean slideEnter) {
        if (fragment == null) {
            return;
        }

        if (fragment instanceof IPvTracker) {
            Log.d("ttaylor", "onFragmentVisibleChanged: fragment("+ ((IPvTracker) fragment).getPvEventId()+") is "+isVisible);
            IPvTracker tracker = (IPvTracker) fragment;
            if (!tracker.shouldReport()) {
                return;
            }
            String eventId = tracker.getPvEventId();
            Bundle bundle = tracker.getPvExtra();
            if (TextUtils.isEmpty(eventId) || mFragmentLoadType == null) {
                return;
            }
            String uniqueEventId = PageViewTracker.getUniqueId(fragment, eventId);
            int loadType = mFragmentLoadType.get(uniqueEventId) == null ? 0 : mFragmentLoadType.get(uniqueEventId);
            if (isVisible) {
                mLastVisibleUniqueId = uniqueEventId;
                if (slideEnter) {
                    PvStateManager.getInstance().triggerVisible(uniqueEventId, eventId, bundle, PvStateManager.LOAD_TYPE_ENTER, mHasListenViewPager);
                } else {
                    PvStateManager.getInstance().triggerVisible(uniqueEventId, eventId, bundle, loadType, mHasListenViewPager);
                }
                if (loadType != PvStateManager.LOAD_TYPE_BACK) {
                    mFragmentLoadType.put(uniqueEventId, PvStateManager.LOAD_TYPE_BACK);
                }
            } else {
                PvStateManager.getInstance().triggerInvisible(uniqueEventId);
                mLastVisibleUniqueId = "";
            }
        }
    }

    public void observePageChange(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }

        mHasListenViewPager = true;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int lastPosition = viewPager.getCurrentItem();

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = getFragmentFromAdapter(viewPager.getAdapter(), position);
                Fragment lastVisibleFragment = getFragmentFromAdapter(viewPager.getAdapter(), lastPosition);
                if (lastVisibleFragment != null) {
                    onFragmentVisibleChanged(lastVisibleFragment, false, true);
                    String eventId = getEventId(lastVisibleFragment);
                    if (!TextUtils.isEmpty(eventId)) {
                        String uniqueEventId = PageViewTracker.getUniqueId(lastVisibleFragment, eventId);
                        mFragmentLoadType.put(uniqueEventId, PvStateManager.LOAD_TYPE_ENTER);
                    }
                }
                if (fragment != null && !isFragmentHidden(fragment)) {
                    onFragmentVisibleChanged(fragment, true, true);
                }
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void observeCurPageChange(ViewPager viewPager, boolean isVisible) {
        if (viewPager == null) {
            return;
        }
        Fragment fragment = getFragmentFromAdapter(viewPager.getAdapter(), viewPager.getCurrentItem());
        onFragmentVisibleChanged(fragment, isVisible, true);
    }

    public void switchToBackground() {
        String curVisibleUniqueId = PvStateManager.getInstance().getCurVisibleUniqueId();
        if (TextUtils.isEmpty(curVisibleUniqueId)) {
            return;
        }
        if (mFragmentLoadType.containsKey(curVisibleUniqueId)) {
            mFragmentLoadType.put(curVisibleUniqueId, PvStateManager.LOAD_TYPE_ENTER);
        }
    }

    public String getLastVisibleUniqueId() {
        return mLastVisibleUniqueId;
    }

    public void setLastVisibleUniqueId(String uniqueId) {
        mLastVisibleUniqueId = uniqueId;
    }

    public void onInterceptFragmentReport(boolean intercept) {
        mIsIntercept = intercept;
    }

    public boolean isInterceptFragmentReport() {
        return mIsIntercept;
    }

    @Nullable
    private Fragment getFragmentFromAdapter(PagerAdapter adapter, int position) {
        if (position < 0 || position >= adapter.getCount()) {
            return null;
        }
        Fragment fragment = null;
        if (adapter instanceof FragmentPagerAdapter) {
            fragment = ((FragmentPagerAdapter) adapter).getItem(position);
        } else if (adapter instanceof FragmentStatePagerAdapter) {
            fragment = ((FragmentStatePagerAdapter) adapter).getItem(position);
        }
        return fragment;
    }

    private boolean isFragmentHidden(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        if (fragment.getParentFragment() == null) {
            return fragment.isHidden();
        }

        return fragment.isHidden() || isFragmentHidden(fragment.getParentFragment());
    }

    private String getEventId(Fragment fragment) {
        if (fragment instanceof IPvTracker) {
            return ((IPvTracker) fragment).getPvEventId();
        }
        return "";
    }

    public void observePageChange(ViewPager2 viewpager, FragmentManager fragmentManager) {
        if (viewpager == null || fragmentManager == null) {
            return;
        }

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                Fragment current = fragmentManager.findFragmentByTag("f" + position);
                if (current instanceof IPvTracker) {
                    String eventId = ((IPvTracker) current).getPvEventId();
                    if (TextUtils.isEmpty(eventId)) {
                        return;
                    }
                    String uniqueId = PageViewTracker.getUniqueId(current, eventId);
                    mFragmentLoadType.put(uniqueId, PvStateManager.LOAD_TYPE_ENTER);
                }
            }
        });
    }
}
