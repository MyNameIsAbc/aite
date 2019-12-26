package com.example.jianancangku.o;

public class J {
    //                        List< MsgCenterbean.ListBean > t=msgCenterbean.getList();
////                        JsonArray jsonArray=t;
//                        Gson gson = new Gson();
//                        ArrayList<MsgCenterbean.ListBean> listBeans = new ArrayList<>();
//
//                        //循环遍历
//                        for (JsonElement user : jsonArray) {
//                            //通过反射 得到UserBean.class
//                            MsgCenterbean.ListBean lis = gson.fromJson(user, new TypeToken<MsgCenterbean.ListBean>() {}.getType());
//                            listBeans.add(lis);
//                        }
//                        final MsgCenterbean.ListBean listBean = BeanConvertor.convertBean(msgCenterbean.getList(), MsgCenterbean.ListBean.class);
//                        if (baseData.getCode() == 200) {
//                            LogUtils.d(response);
//                            LogUtils.d(msgCenterbean.getList());
//                            Gson gson;
//                            for (int i = 0; i < msgCenterbean.getList().size(); i++)
//                                ToastUtils.showToast(context, !TextUtils.isEmpty(listBean.getTitle()) ? String.valueOf(listBean.getTitle().charAt(i)) : "NULL");
//
//
//                        }
    //            Observable.create(new ObservableOnSubscribe<Object>() {
//                @Override
//                public void subscribe(ObservableEmitter<Object> e) throws Exception {
//    HttpParams params = new HttpParams();
//                    params.put("KEY", Constant.KEY);
//                    params.put("p", 1);
//                    params.put("size", 50);
//    OkGo .<BaseData<MsgCenterbean.ListBean>>loginpost(msgcenterAddress)
//                            .tag(context)
//                            .params(params)
//                            .execute(new AbsCallback<BaseData<MsgCenterbean.ListBean>>() {
//        @Override
//        public BaseData<MsgCenterbean.ListBean> convertResponse(okhttp3.Response response) throws Throwable {
//            LogUtils.d(response);
//            final BaseData baseData = BeanConvertor.convertBean(response.body().string(), BaseData.class);
//            final MsgCenterbean msgCenterbean = BeanConvertor.convertBean(baseData.getDatas(), MsgCenterbean.class);
//            recydatalist = msgCenterbean.getList();
//            LogUtils.d(msgCenterbean.getList().get(1).getAdd_time());
//            return baseData;
//        }
//
//        @Override
//        public void onStart(Request<BaseData<MsgCenterbean.ListBean>, ? extends Request > request) {
//
//        }
//
//        @Override
//        public void onSuccess(Response<BaseData<MsgCenterbean.ListBean>> response) {
//            LogUtils.d(recydatalist);
//            if (recydatalist == null) {
//                ToastUtils.showToast(context, "数据错误");
//                return;
//            }
//            recyAdapter = new MsgCenterRecyAdapter(MsgCenterActivity.this, recydatalist);
//            msgcenterrecy.setAdapter(recyAdapter);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//            msgcenterrecy.setLayoutManager(linearLayoutManager);
//            msgcenterrecy.setItemAnimator(new DefaultItemAnimator());
//
//        }
//    });
//                    e.onNext(recydatalist != null);
//
//
//
//
//                }
//            }).subscribeOn(Schedulers.from((Executor) AndroidSchedulers.mainThread()))
//                    .subscribe(new Observer<Object>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(Object o) {
//                            if (!(boolean)o)
//                                return;
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            LogUtils.e(e.getCause() + e.getMessage());
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });

//}
//LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) topTxt.getLayoutParams();
//    linearParams.height=50;
//    linearParams.weight=10;
//        topTxt.setLayoutParams(linearParams);
//        android.os.Process.killProcess(android.os.Process.myPid());

}
