package com.isme.shen.sdemo.event;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.isme.shen.sdemo.BaseButtonListActivity;
import com.isme.shen.slibrary.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by shen on 2016/8/30.
 */
public class RxJavaRxAndroidActivity extends BaseButtonListActivity {

    String[] name = {"normal_1", "normal_2", "normal_3", "optionalAction", "egPrintString",
            "egLoadImg", "scheduler_1", "map_1","flatMap"};

    @Override
    protected int getCount() {
        return name.length;
    }

    @Override
    protected String getBtnName(int position) {
        return name[position];
    }

    @Override
    protected void onClick(int position) {

        switch (position) {
            case 0:
                normal_1();
                break;
            case 1:
                normal_2();
                break;
            case 2:
                normal_3();
                break;
            case 3:
                optionalAction();
                break;
            case 4:
                egPrintString();
                break;
            case 5:
                egLoadImg();
                break;
            case 6:
                scheduler_1();
                break;
            case 7:
                map_1();
                break;
            case 8:
                flatMap();
                break;
        }

    }

    /*
    *  迭代变化
    * */
    private void flatMap() {
        List<Map<String,String>>  list = new ArrayList<>();
        for(int i =0;i<4;i++){
            Map map = new HashMap<String, String>();
            map.put("key:"+i,"values:"+i+"+"+i);
            list.add(map);
        }

        Observable.from(list)
                .flatMap(new Func1<Map<String, String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(Map<String, String> stringStringMap) {
                        return Observable.from(stringStringMap.values());
                    }
                }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this,"ss:"+s);
            }
        });

    }

    //类型转换
    private void map_1() {
        Observable.just("类型转换，字符串转drawable")
                .map(new Func1<String, Drawable>() {
                    @Override
                    public Drawable call(final String s) {
                        Drawable drawable = new Drawable() {
                            @Override
                            public void draw(Canvas canvas) {
                                canvas.drawColor(Color.RED);
                                Paint paint = new Paint();
                                paint.setTextSize(40);
                                paint.setColor(Color.BLUE);
                                canvas.drawText(s, 50, 400, paint);
                            }

                            @Override
                            public void setAlpha(int i) {

                            }

                            @Override
                            public void setColorFilter(ColorFilter colorFilter) {

                            }

                            @Override
                            public int getOpacity() {
                                return 0;
                            }
                        };
                        return drawable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Drawable>() {
                    @Override
                    public void call(Drawable drawable) {
                        rlContent.setBackground(drawable);
                    }
                });

    }

    /**
     * 基本线程控制
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     */
    private void scheduler_1() {

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                //会在io线程中执行
                subscriber.onNext(new Drawable() {
                    @Override
                    public void draw(Canvas canvas) {
                        canvas.drawColor(Color.GRAY);
                        Paint paint = new Paint();
                        paint.setTextSize(40);
                        paint.setColor(Color.BLUE);
                        canvas.drawText("我被IO线程画出来的", 50, 300, paint);
                    }

                    @Override
                    public void setAlpha(int i) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Drawable>() {//会在android的主线程中执行
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        rlContent.setBackground(drawable);
                    }
                });
    }

    private void egLoadImg() {

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                subscriber.onNext(new Drawable() {
                    @Override
                    public void draw(Canvas canvas) {
                        canvas.drawColor(Color.BLACK);
                        Paint paint = new Paint();
                        paint.setTextSize(40);
                        paint.setColor(Color.BLUE);
                        canvas.drawText("我被当前线程画出来的", 50, 100, paint);
                    }

                    @Override
                    public void setAlpha(int i) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                });
            }
        }).subscribe(new Subscriber<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(Drawable drawable) {
                rlContent.setBackground(drawable);
            }
        });

    }

    /*例子，打印字符串*/
    private void egPrintString() {
        String[] arr = {"one", "two", "three"};

        Observable.from(arr).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, s);
            }
        });
    }

    /**
     * 不完整的回调，即可选择性回调观察者的三个方法之一
     */
    private void optionalAction() {

        Action0 onCompleted = new Action0() {
            @Override
            public void call() {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onCompleted");
            }
        };

        Action1<String> onNext = new Action1<String>() {
            @Override
            public void call(String s) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onNext:" + s);
            }
        };

        Action1<Throwable> onErr = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onErr:" + throwable.getMessage());
            }
        };

        Observable<String> observable = Observable.just("one", "two", "three");

//        选择回调onnext方法
//        observable.subscribe(onNext);
        //
//        observable.subscribe(onNext,onErr);

        observable.subscribe(onNext, onErr, onCompleted);

    }

    private void normal_3() {

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onError");
            }

            @Override
            public void onNext(String o) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onNext:" + o);
            }
        };
        String[] arr = {"one", "two", "three"};
        //依次被调用观察者的onNext，最后调用onComplete方法
        Observable.from(arr).subscribe(observer);


    }

    private void normal_2() {
        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onError");
            }

            @Override
            public void onNext(String o) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onNext:" + o);
            }
        };
        //依次被调用观察者的onNext，最后调用onComplete方法
        Observable.just("one", "two", "three").subscribe(observer);

    }

    private void normal_1() {

        //观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onError");
            }

            @Override
            public void onNext(String o) {
                ToastUtils.showShort(RxJavaRxAndroidActivity.this, "onNext:" + o);
            }
        };
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("one");
                subscriber.onNext("two");
                subscriber.onNext("three");
                subscriber.onCompleted();
            }
        });
        //被观察者订阅观察者，此时观察者中的方法被调用
        observable.subscribe(observer);
    }


}
