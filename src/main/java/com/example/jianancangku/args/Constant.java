package com.example.jianancangku.args;

public class Constant {
    public static final String device = "android";
    public static final String MOBILE = "isMobile";
    public static final String EMAIL = "isEmail";
    public static String usernumber = "";
    public static final String userkey = "666666";
    public static final String device_id = "fs8sdf56s";//极光推送
    //    public static String KEY = "vv983e34c5ea716cb6a564c9250c4c9275";
    //"18270281021"
    public static String KEY = null;
    public static String peopeliconUrl = null;
    public static String peopelname = null;
    public static String peopelwoker = null;
    public static String usernumb = null;
    private static final String baseUrl = "https://piikmall.aitecc.com/mobile/";
    public static boolean isLogin = false;
    public static String type = null;
    //interface
    public static final String mainuisAddress = baseUrl + "index.php?act=warehouse_clerk&op=me";
    public static final String mainuiallAddress = baseUrl + "index.php?act=warehouse_clerk&op=home";

    public static final String LogInAddress = baseUrl + "index.php?act=login&op=loginThreeWay";
    public static final String phonecodefindkeyAddress = baseUrl + "index.php?act=login&op=retrievePasswordSendCode";

    public static final String findkeyAddress = baseUrl + "index.php?act=login&op=find_password";
    public static final String msgcenterAddress = baseUrl + "index.php?act=warehouse_clerk&op=messageCenter";
    public static final String thingsFixAddress = baseUrl + "index.php?act=warehouse_clerk&op=orderManager";
    public static final String workermainAddress = baseUrl + "index.php?act=warehouse_clerk&op=staffManagerInfo";

    public static final String workerFixAddress = baseUrl + "index.php?act=warehouse_clerk&op=staffManager";
    public static final String thingbookAddress = baseUrl + "index.php?act=warehouse_clerk&op=baleList";
    public static final String qrgohomeAddress = baseUrl + "index.php?act=warehouse_clerk&op=scanCodeEnterWarehouse";
    public static final String qrouthomeAddress = baseUrl + "index.php?act=warehouse_clerk&op=scanCodeOutWarehouse";
    public static final String sendlistAddress = baseUrl + "index.php?act=warehouse_clerk&op=deliveryList";
    public static final String changekeyAdrress = baseUrl + "index.php?act=warehouse_clerk&op=updatePassword";
    public static final String sendallmsgAdrress = baseUrl + "index.php?act=warehouse_clerk&op=deliveryDetail";
    public static final String choiceareaAdrress = baseUrl + "index.php?act=delivery_staff&op=getAreaList";
    public static final String createAccountAdrress = baseUrl + "index.php?act=warehouse_clerk&op=addStaffManager";
    public static final String workerthingAdrress = baseUrl + "index.php?act=warehouse_clerk&op=staffOrderManager";
    public static final String workerfixkeyAdrress = baseUrl + "index.php?act=warehouse_clerk&op=editStaffManager";
    public static final String killworkerAdrress = baseUrl + "index.php?act=warehouse_clerk&op=disableStaffManager";

    public static final String qroutThingHouseAdrress = baseUrl + "index.php?act=warehouse_clerk&op=scanCodeBale";

    public static final String deletethingbooksAdrress = baseUrl + "index.php?act=warehouse_clerk&op=deleteBale";
    public static final String callfindsendworkerAddress = baseUrl + "index.php?act=warehouse_clerk&op=callDistribution";

    public static final String postPeopleIconAdrress = baseUrl + "index.php?act=warehouse_clerk&op=updateAvatar";
    public static final String fixouthouseAdrress = baseUrl + "index.php?act=warehouse_clerk&op=baleCodeOutWarehouse";

    public static final String postproblemAdrress = baseUrl + "index.php?act=warehouse_clerk&op=addAbnormal";
    public static final String getAreaCodeAdrress = baseUrl + "index.php?act=index&op=getAreaCode";

}
