package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.graction.developer.zoocaster.Adapter.FragmentAdapter;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Fragment.AlarmFragment;
import com.graction.developer.zoocaster.Fragment.FineDustFragment;
import com.graction.developer.zoocaster.Fragment.Forecast5DayFragment;
import com.graction.developer.zoocaster.Fragment.HomeFragment;
import com.graction.developer.zoocaster.Fragment.Test2Fragment;
import com.graction.developer.zoocaster.Fragment.TestFragment;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private boolean isFirst;
    private FragmentAdapter fragmentAdapter;
    private ArrayList<FragmentAdapter.TabItem> items;

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (!isFirst) {
            initViewPager();
            isFirst = !isFirst;
        }
        initLocation();
    }

    private void initViewPager() {
        items = new ArrayList<>();
        items.add(new FragmentAdapter.TabItem(HomeFragment.getInstance(), R.drawable.tab_home));
        items.add(new FragmentAdapter.TabItem(Test2Fragment.getInstance(), R.drawable.tab_week));
        items.add(new FragmentAdapter.TabItem(FineDustFragment.getInstance(), R.drawable.tab_dust));
        items.add(new FragmentAdapter.TabItem(AlarmFragment.getInstance(), R.drawable.tab_alarm));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), items, (index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
            logger.log(HLogger.LogType.INFO, "setTabItem", "index : " + index);

           /* ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
            View tabItem = itemTabBinding.getRoot();
            itemTabBinding.setItem(item);
            itemTabBinding.executePendingBindings();
            binding.activityMainTab.getTabAt(index).setCustomView(tabItem);*/
        });
       /* fragmentAdapter.setSetTabItem((index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
            logger.log(HLogger.LogType.INFO, "setTabItem", "index : "+index);

           *//* ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
            View tabItem = itemTabBinding.getRoot();
            itemTabBinding.setItem(item);
            itemTabBinding.executePendingBindings();
            binding.activityMainTab.getTabAt(index).setCustomView(tabItem);*//*
        });*/
//        fragmentAdapter.setItems(items);
        binding.activityMainTab.setupWithViewPager(binding.activityMainVP);
        binding.activityMainVP.setAdapter(fragmentAdapter);
        binding.activityMainVP.setOffscreenPageLimit(items.size() - 1);
    }

    private void initNavigation() {
        //        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //        binding.navigation.setSelectedItemId(R.id.navigation_current);
        /*ArrayList<CustomNavigationView.NavigationItem> items = new ArrayList<>();
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(HomeFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(Forecast5DayFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(AlarmFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(TestFragment.getInstance());
        }));
        binding.activityMainCNV.bindItemView(this, items, (imageView, resId) -> {
            Glide.with(MainActivity.this).load(resId).into(imageView);
        });
        binding.activityMainCNV.actionItem(0);*/
    }

    private void initLocation() {
        binding.activityMainTVLocation.setOnClickListener((v)->startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST));
        DataStorage.googleLocationManager = new GoogleLocationManager(address -> {
            logger.log(HLogger.LogType.INFO, "address : " + address);
            DataStorage.NowAddress = AddressParser.getInstance().parseAddress(address);
            binding.activityMainTVLocation.setText(DataStorage.NowAddress);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        logger.log(HLogger.LogType.INFO, "void onActivityResult(int requestCode, int resultCode, Intent data)","MainActivity");
        ((HomeFragment)items.get(0).getFragment()).reScan();
    }

}
