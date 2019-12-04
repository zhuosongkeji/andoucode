package com.zskjprojectj.andouclient.interfacehelp;


import com.zskjprojectj.andouclient.fragment.HomePageFragment;
import com.zskjprojectj.andouclient.fragment.InfoPageFragment;
import com.zskjprojectj.andouclient.fragment.MePageFragment;
import com.zskjprojectj.andouclient.fragment.MerchantsPageFragment;
import com.zskjprojectj.andouclient.fragment.TieBaFragment;

/**
 * 全局变量
 */
public class FragmentHelp {
    private static HomePageFragment homePageFragment;//首页
    private static MerchantsPageFragment merchantsPageFragment;//商家
    private static InfoPageFragment infoPageFragment;//信息
    private static TieBaFragment tieBaFragment;//贴吧
    private static MePageFragment mePageFragment;//我的
    public  static ChangeFragment schangeFragment;//改变选中的fragment接口
    /**
     * 依次初始化每个碎片对象
     * 首页
     */
    public static HomePageFragment getHomePageFragment()
    {
        if (homePageFragment == null) {
            homePageFragment = new HomePageFragment();
        }
        return homePageFragment;
    }
    /**
     * 商家
     */
    public static MerchantsPageFragment getMerchantsPageFragment()
    {
        if (merchantsPageFragment == null) {
            merchantsPageFragment = new MerchantsPageFragment();
        }
        return merchantsPageFragment;
    }
    /**
     * 信息
     */
    public static  InfoPageFragment getInfoPageFragment()
    {
        if (infoPageFragment==null)
        {
            infoPageFragment=new InfoPageFragment();
        }
        return infoPageFragment;
    }
    /**
     * 贴吧
     */
    public static TieBaFragment getTieBaFragment()
    {
        if (tieBaFragment==null){
            tieBaFragment=new TieBaFragment();
        }
        return tieBaFragment;
    }
    /**
     * 我的
     */
    public static MePageFragment getMePageFragment()
    {
        if (mePageFragment==null)
        {
            mePageFragment=new MePageFragment();
        }
        return  mePageFragment;
    }
    /**
     * 设置被选中的Fragment
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        schangeFragment = changeFragment;

    }
}
