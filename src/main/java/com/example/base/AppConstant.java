package com.example.base;

import com.example.jianancangku.R;

/**
 * @Auther: valy
 * @datetime: 2019-12-19
 * @desc:
 */
public class AppConstant {
    public static class MAINUI {
        public static int[] settingImg = {
                R.mipmap.main_icon,
                R.mipmap.gohouse_ago,
                R.mipmap.gohouse,
                R.mipmap.out_house_ago,
                R.mipmap.out_house_ago,
        };

        public static String[] settingTv = {
                "首页", "未入库", "已入库", "已出库", "已打包"
        };
    }
    public static class HOUSEFIXER {
        public static int[] settingImg = {
                R.mipmap.main_icon,
                R.mipmap.housego,
                R.mipmap.thingfix,
                R.mipmap.problems_icon,
                R.mipmap.out_house_ago,
        };

        public static String[] settingTv ={ "扫描入库", "订单打包", "包裹异常", "员工管理"};
    }
}
