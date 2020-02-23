package com.zskjprojectj.andouclient.interfacehelp;


import com.zskjprojectj.andouclient.fragment.AppHomeFragment;
import com.zskjprojectj.andouclient.fragment.InfoPageFragment;
import com.zskjprojectj.andouclient.fragment.MeFragment;
import com.zskjprojectj.andouclient.fragment.MerchantListFragment;
import com.zskjprojectj.andouclient.fragment.TieBaFragment;

/**
 * 全局变量
 */
public class FragmentHelp {
    private static AppHomeFragment appHomeFragment;//首页
    private static MerchantListFragment merchantListFragment;//商家
    private static InfoPageFragment infoPageFragment;//信息
    private static TieBaFragment tieBaFragment;//贴吧
    private static MeFragment meFragment;//我的
    public  static ChangeFragment schangeFragment;//改变选中的fragment接口
    /**
     * 依次初始化每个碎片对象
     * 首页
     */
    public static AppHomeFragment getHomePageFragment()
    {
        if (appHomeFragment == null) {
            appHomeFragment = new AppHomeFragment();
        }
        return appHomeFragment;
    }
    /**
     * 商家
     */
    public static MerchantListFragment getMerchantListFragment()
    {
        if (merchantListFragment == null) {
            merchantListFragment = new MerchantListFragment();
        }
        return merchantListFragment;
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
    public static MeFragment getMePageFragment()
    {
        if (meFragment ==null)
        {
            meFragment =new MeFragment();
        }
        return meFragment;
    }
    /**
     * 设置被选中的Fragment
     * @param changeFragment
     */
    public static void setFragmentSelected(ChangeFragment changeFragment) {
        schangeFragment = changeFragment;

    }
}
