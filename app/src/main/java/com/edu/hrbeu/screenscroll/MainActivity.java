package com.edu.hrbeu.screenscroll;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.OnCarrouselItemSelectedListener;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private Context mContext;
    private TextView tvTime;
    //    private ViewPager viewPager;
    private ArrayList<View> viewList;
    private boolean isLooper;
    private Uri uri;
    private VideoView videoView;
    private final int totalPage = 4;
    private View view1, view2, view3;
    private int index;
    private LinearLayout container;
    private TextView tvDay;
    private TextView tvWeek;
    private LinearLayout tvGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContext = this;
        findIds();
        initView();
//        initViewPager();

        startCarousel();


        new TimeThread().start();


    }

    private void startCarousel() {
//        //开启一个线程，用于循环
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                isLooper = true;
//                while (isLooper) {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            //这里是设置当前页的下一页
////                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                            mHandler.sendEmptyMessage(2);
//                        }
//                    });
//                }
//            }
//        }).start();

        timer.start();
    }

//    private void initViewPager() {
//        viewPager = (ViewPager) findViewById(R.id.view_pager);
//        viewList = new ArrayList<>();
//        for (int i = 0; i < totalPage; i++) {
//            if (i == 1) {
//                View view = View.inflate(mContext, R.layout.item_video, null);
//                videoView = (VideoView) view.findViewById(R.id.video_view);
//                videoView.setVideoURI(uri);
//                viewList.add(view);
//            } else {
//                View view = View.inflate(mContext, R.layout.item_img, null);
//                ImageView imageView = (ImageView) view.findViewById(R.id.img_pic);
//                displayImage(mContext, R.drawable.jaychou, imageView);
//                viewList.add(view);
//            }
//        }
//
//        MyAdapter adapter = new MyAdapter(viewList);
//        viewPager.setAdapter(adapter);
//        viewPager.setOnPageChangeListener(this);
//        viewPager.setCurrentItem(0);
//
//    }

    private void initView() {
        index = 0;
        viewList = new ArrayList<>();
        view1 = LayoutInflater.from(mContext).inflate(R.layout.item_video, null);
        view2 = LayoutInflater.from(mContext).inflate(R.layout.item_img, null);
        view3 = LayoutInflater.from(mContext).inflate(R.layout.item_img, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        refreshDay();

        ImageView img1 = (ImageView) view2.findViewById(R.id.img_pic);
        ImageView img2 = (ImageView) view3.findViewById(R.id.img_pic);

        displayImage(mContext, R.drawable.img1, img1);
        displayImage(mContext, R.drawable.img2, img2);


        ImageView img3 = (ImageView) findViewById(R.id.img1);
        ImageView img4 = (ImageView) findViewById(R.id.img2);

        displayImage(mContext, R.drawable.img3, img3);
        displayImage(mContext, R.drawable.img4, img4);




        String uriStr = "android.resource://" + mContext.getPackageName() + "/" + R.raw.loser;
        uri = Uri.parse(uriStr);
        videoView = (VideoView) view1.findViewById(R.id.video_view);
        videoView.setVideoURI(uri);

        container = (LinearLayout) findViewById(R.id.container);

        container.addView(viewList.get(0));

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh :  mm", sysTime);
                    tvTime.setText(sysTimeStr);
                    break;
                case 2:
                    container.removeAllViews();
                    container.addView(viewList.get(index % 3));
                    if (index % 3 == 0) {
                        videoView.start();
                    }
                    index++;
                    startCarousel();
                    break;

                default:
                    break;
            }
        }
    };

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private void findIds() {
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvWeek = (TextView) findViewById(R.id.tv_week);

//        tvGlobal = (LinearLayout) findViewById(R.id.global);

    }


    public void displayImage(Context context, Integer resId, ImageView imageView) {
        Glide.with(context).load(resId)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .crossFade(1000)
                .into(imageView);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.e("position", String.valueOf(position));
        if (position % totalPage == 1) {
            videoView.start();
        } else {
            if (videoView != null) {
                videoView.pause();
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private CountDownTimerUtil timer = new CountDownTimerUtil(20000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
//            second.setText(String.valueOf(millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
//            second.setText("载入");
//            timer.start();
            container.removeAllViews();
            container.addView(viewList.get(index % 3));
            if (index % 3 == 0) {
                videoView.start();
            }
            index++;


            refreshDay();
            timer.start();
        }
    };

    private void refreshDay() {
        Calendar calendar = Calendar.getInstance();
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String week = getWeek(calendar.get(Calendar.DAY_OF_WEEK));
        String res = year + "年" + month + "月" + day + "日";
        tvDay.setText(res);
        tvWeek.setText(week);


    }

    public static String getWeek(int i) {
        switch (i) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 7:
                return "星期天";
            default:
                return "";
        }
    }
}
