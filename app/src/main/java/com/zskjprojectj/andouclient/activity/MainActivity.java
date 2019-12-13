package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.next.easynavigation.view.EasyNavigationBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.HomePageFragment;
import com.zskjprojectj.andouclient.fragment.InfoPageFragment;
import com.zskjprojectj.andouclient.fragment.MePageFragment;
import com.zskjprojectj.andouclient.fragment.MerchantsPageFragment;
import com.zskjprojectj.andouclient.fragment.TieBaFragment;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 * public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener
 *
 * @author yizhubao
 */
public class MainActivity extends BaseActivity {
    private EasyNavigationBar navigationBar;
    //定义字体颜色
    private int normalTextColor = Color.parseColor("#646464");
    private int selectTextColor = Color.parseColor("#5ED3AE");
    private String[] tabText = {"首页", "商家", "信息", "贴吧", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.home_icon_uncheck, R.mipmap.merchants_icon_uncheck, R.mipmap.info_icon, R.mipmap.tieba_icon_uncheck, R.mipmap.me_icon_uncheck};
    //选中时icon
    private int[] selectIcon = {R.mipmap.home_icon_check, R.mipmap.merchants_icon_check, R.mipmap.info_icon, R.mipmap.tieba_icon_check, R.mipmap.me_icon_check};

    private List<Fragment> fragments = new ArrayList<>();
//    public static final String LOCK = "lock";
//    public static final String LOCK_KEY = "lock_key";
//
//    private static String homepage = "mHomePageFragment";
//    protected TextView tvProtruding;
//    protected ImageView imgProtruding;

    /**
     * 创建一个集合，存储碎片
     */
//    private List<Fragment> mFragments = new ArrayList<Fragment>();
//    private HomePageFragment mHomePageFragment;
//    private MerchantsPageFragment mMerchantsPageFragment;
//    private InfoPageFragment mInfoPageFragment;
//    private TieBaFragment mTieBaFragment;
//    private MePageFragment mMePageFragment;
//    private FragmentManager fm;
//    FragmentTransaction transaction;
//    private RadioGroup mRadioButtonRg;
//    private FragmentTransaction transaction1;

    // @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        initView();
////        if (savedInstanceState == null) {
////            FragmentManager fragmentManager = getSupportFragmentManager();
////            mHomePageFragment = new HomePageFragment();
////            fragmentManager.beginTransaction().replace(R.id.fragment_all, mHomePageFragment, homepage).commit();
////        }
//    }
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @Override
    protected void initViews() {
        navigationBar = findViewById(R.id.navigationBar);
        fragments.add(new HomePageFragment());
        fragments.add(new MerchantsPageFragment());
        fragments.add(new InfoPageFragment());
        fragments.add(new TieBaFragment());
        fragments.add(new MePageFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .anim(null)
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(0)
                .addAlignBottom(true)
                .addAsFragment(true)
                .normalTextColor(normalTextColor)
                .selectTextColor(selectTextColor)
                .fragmentManager(getSupportFragmentManager())
//                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
//                    @Override
//                    public boolean onTabClickEvent(View view, int position) {
//                        Log.e("onTabClickEvent", position + "");
//                        if (position == 2) {
//                            mHandler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //＋ 旋转动画
//                                    if (flag) {
//                                        navigationBar.getAddImage().animate().rotation(45).setDuration(400);
//                                    } else {
//                                        navigationBar.getAddImage().animate().rotation(0).setDuration(400);
//                                    }
//                                    flag = !flag;
//                                }
//                            });
//                        }
//                        return false;
//                    }
//                })
                .canScroll(true)
                .mode(EasyNavigationBar.MODE_ADD)
                .build();
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



    //    void initView()
//    {
////        mRadioButtonRg = (RadioGroup) findViewById(R.id.rg_oper);
////        mRadioButtonRg.setOnCheckedChangeListener(MainActivity.this);
////        fm = getSupportFragmentManager();
////        transaction = fm.beginTransaction();
////        //这里就每个界面的碎片对象第一个是首页简单举例，怎么去修改首页的样式
////        //ctrl+鼠标左键找到这个碎片
////        mHomePageFragment = (HomePageFragment) fm.findFragmentByTag(homepage);
////        mMerchantsPageFragment = (MerchantsPageFragment) fm.findFragmentByTag("mMerchantsPageFragment");
////        mInfoPageFragment = (InfoPageFragment) fm.findFragmentByTag("mInfoPageFragment");
////        mTieBaFragment = (TieBaFragment) fm.findFragmentByTag("mTieBaFragment");
////        mMePageFragment = (MePageFragment) fm.findFragmentByTag("mMePageFragment");
////        imgProtruding = (ImageView) findViewById(R.id.img_protruding);
//
////        rg_oper=findViewById(R.id.rg_oper);
////        home_page=findViewById(R.id.rd_home_page);
////        info_page=findViewById(R.id.rd_info_page);
////        merchants_page=findViewById(R.id.rd_merchants_page);
////        tieba_page=findViewById(R.id.rd_tieba_page);
////        me_page=findViewById(R.id.rd_me_page);
////        mViewPager = findViewById(R.id.viewPager);
////        mViewPager.setOffscreenPageLimit(4);
////        //初始化碎片对象
////        List<Fragment> fragments = new ArrayList<>();
////        fragments.add(new HomePageFragment());
////        fragments.add(new InfoPageFragment());
////        fragments.add(new MePageFragment());
////        fragments.add(new MerchantsPageFragment());
////        fragments.add(new TieBaFragment());
////        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
////        //设置点击事件切换
////        rg_oper.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(RadioGroup group, int checkedId) {
////
////                switch (checkedId)
////                {
////                    case  R.id.rd_home_page:
////                        mViewPager.setCurrentItem(0);
////                        break;
////                    case R.id.rd_merchants_page:
////                        mViewPager.setCurrentItem(1);
////                        break;
////                    case R.id.rd_info_page:
////                        mViewPager.setCurrentItem(2);
////                        break;
////                    case R.id.rd_tieba_page:
////                        mViewPager.setCurrentItem(3);
////                        break;
////                    case R.id.rd_me_page:
////                        mViewPager.setCurrentItem(4);
////                        break;
////                }
////            }
////        });
////        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int i, float v, int i1) {
////
////            }
////
////            @Override
////            public void onPageSelected(int i) {
////                switch (1)
////                {
////                    case 0:
////                        home_page.setChecked(true);
////                        break;
////                    case 1:
////                        merchants_page.setChecked(true);
////                        break;
////                    case 2:
////                        info_page.setChecked(true);
////                        break;
////                    case 3:
////                        tieba_page.setChecked(true);
////                        break;
////                    case 4:
////                        me_page.setChecked(true);
////                        break;
////                }
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int i) {
////
////            }
////        });
//    }


//    /**
//     *  tab切换事件处理
//     * @param group
//     * @param checkedId
//     */
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        transaction1 = fm.beginTransaction();
//        if (mHomePageFragment != null) {
//            transaction1.hide(mHomePageFragment);
//        }
//        if (mMerchantsPageFragment != null) {
//            transaction1.hide(mMerchantsPageFragment);
//        }
//        if (mInfoPageFragment != null) {
//            transaction1.hide(mInfoPageFragment);
//        }
//        if (mTieBaFragment != null) {
//            transaction1.hide(mTieBaFragment);
//        }
//        if (mMePageFragment != null) {
//            transaction1.hide(mMePageFragment);
//        }
//        //判断当前选中的页面改变底部菜单
//        if (checkedId==R.id.rd_home_page)
//        {
//            if (mHomePageFragment == null) {
//                mHomePageFragment = new HomePageFragment();
//                transaction1.add(R.id.fragment_all, mHomePageFragment, homepage);
//            } else {
//                transaction1.show(mHomePageFragment);
//            }
//            imgProtruding.setImageResource(R.mipmap.info_icon);
//        }else if (checkedId == R.id.rd_merchants_page) {
//            if (mMerchantsPageFragment == null) {
//                mMerchantsPageFragment = new MerchantsPageFragment();
//                transaction1.add(R.id.fragment_all, mMerchantsPageFragment, "mMerchantsPageFragment");
//            } else {
//                transaction1.show(mMerchantsPageFragment);
//            }
//            imgProtruding.setImageResource(R.mipmap.info_icon);
//        }else if (checkedId==R.id.rd_info_page)
//        {
//            if (mInfoPageFragment == null) {
//                mInfoPageFragment = new InfoPageFragment();
//                transaction1.add(R.id.fragment_all, mInfoPageFragment, "mInfoPageFragment");
//            } else {
//                transaction1.show(mInfoPageFragment);
//            }
//            imgProtruding.setImageResource(R.mipmap.info_icon);
//        }else  if (checkedId==R.id.rd_tieba_page)
//        {
//            if (mTieBaFragment == null) {
//                mTieBaFragment = new TieBaFragment();
//                transaction1.add(R.id.fragment_all, mTieBaFragment, "mTieBaFragment");
//            } else {
//                transaction1.show(mTieBaFragment);
//            }
//            imgProtruding.setImageResource(R.mipmap.info_icon);
//        }else  if (checkedId==R.id.rd_me_page)
//        {
//            if (mMePageFragment == null) {
//                mMePageFragment = new MePageFragment();
//                transaction1.add(R.id.fragment_all, mMePageFragment, "mMePageFragment");
//            } else {
//                transaction1.show(mMePageFragment);
//            }
//            imgProtruding.setImageResource(R.mipmap.info_icon);
//        }
//        transaction1.commit();
//
//    }
    /**
     * 按键执行操作，连点两次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }
}
