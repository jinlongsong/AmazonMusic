package com.amazonmusic;

import com.service.PlayerService;

import android.Manifest;
import android.app.Activity;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, OnClickListener {
    //private String TAG = MainActivity.class.getSimpleName();
    private String TAG = "TAG";
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX__OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private RelativeLayout relativeLayout;
    private ImageButton playimage;
    private ImageButton shangimage;
    private ImageButton xiaimage;
    private ActionBar actionBar;
    private RadioGroup radioGroup;
    private String[] mPlanetTitles; // 侧边栏显示内容列表
    private DrawerLayout mDrawerLayout; // 侧边栏对象
    private ListView mDrawerList; // list列表

    private FragmentManager fragmentManager = null;
    private BaseFragment mFragment = null;

    private boolean mIsPlaying = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"MainActivity created");
        actionBar = getActionBar();// 获取actionBar
        actionBar.setIcon(R.drawable.action_bar_list);
        actionBar.setTitle("离线音乐库");
        actionBar.setHomeButtonEnabled(true); // 左上角可点击
        // actionBar.setDisplayHomeAsUpEnabled(true); //给左上角图标的左边加上一个返回的图标
        actionBar.setDisplayShowHomeEnabled(true); // 使左上角图标可点击
        setContentView(R.layout.activity_main_new);

        //当版本在android6.0以上时动态获取权限（就是弹出一个权限提示框要求获取权限，点确定就会获取）
        //PackageManager.PERMISSION_DENIED = -1;检查权限时，返回此值为无权限
        //PackageManager.PERMISSION_GRANTED = 0;检查权限时，返回此值为有权限
        if (Build.VERSION.SDK_INT > 23) {
            String permission = "android.permission.READ_EXTERNAL_STORAGE";
            boolean result = this.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED;
            if (!result) {
                Toast.makeText(MainActivity.this,"没有权限，正在获取权限",Toast.LENGTH_LONG).show();
                //弹出获取权限提示框
                requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE}, 1);
            }
            if (this.checkSelfPermission(permission)
                    == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"已获取权限",Toast.LENGTH_LONG).show();
            }
        }
        initView();
        //gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                if (gestureDetector.onTouchEvent(event)) {
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }

                return true;
            }
        };
    }
    /**
     * 初始化页面
     * */
    public void initView() {
        //侧栏选项
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mPlanetTitles = getResources().getStringArray(R.array.list_array);
        //底部播放控制栏
        relativeLayout = (RelativeLayout) findViewById(R.id.play_bottom_layout);
        playimage = (ImageButton) findViewById(R.id.startplay);
        playimage.setBackgroundResource(android.R.drawable.ic_media_play);
        shangimage = (ImageButton) findViewById(R.id.shang);
        xiaimage = (ImageButton) findViewById(R.id.xia1);
        //顶部切换按钮
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        //添加监听事件
        radioGroup.setOnCheckedChangeListener(this);
        playimage.setOnClickListener(this);
        shangimage.setOnClickListener(this);
        xiaimage.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        //首页加载
        fragmentManager = getSupportFragmentManager();
        switchFragment(PlayingListFragment.class);
        //侧滑打开的时候取消锁定模式，侧滑关闭的时候打开锁定模式
//         mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
//             @Override
//             public void onDrawerSlide(View drawerView, float slideOffset) {
//             }
        //
//             @Override
//             public void onDrawerOpened(View drawerView) {
//                 //打开手势滑动
//                 mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//             }
        //
//             @Override
//             public void onDrawerClosed(View drawerView) {
//                 //关闭手势滑动
//                 mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//             }
        //
//             @Override
//             public void onDrawerStateChanged(int newState) {
//             }
//         });

        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawlayout_list, mPlanetTitles)); // 自定义的布局只包含一个textview

        mDrawerList.setOnItemClickListener(new OnItemClickListener() {// 导航栏点击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                // arg1是当前item的view，通过它可以获得该项中的各个组件。
                // arg2是当前item的ID。这个id根据你在适配器中的写法可以自己定义。
                // arg3是当前的item在listView中的相对位置！
                switch (arg2) {
                    case 0:
                        Toast.makeText(MainActivity.this, "进入近期活动",
                                Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "进入音乐库",
                                Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "设置",
                                Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this, "帮助",
                                Toast.LENGTH_LONG).show();
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /**
     * 监听底部控制栏
     * */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.play_bottom_layout:

                Intent startPlayIntent = new Intent(MainActivity.this, // 点击开始播放
                        PlayActivity.class);
                startActivity(startPlayIntent);
                break;
            case R.id.shang:
                Intent play_left = new Intent(MainActivity.this,
                        PlayerService.class);
                play_left.putExtra("control", "front");
                startService(play_left);

                break;
            case R.id.startplay:
                boolean isplay = PlayerService.myMediaPlayer != null
                        && PlayerService.myMediaPlayer.isPlaying();
				if (isplay) {
					playimage.setBackgroundResource(android.R.drawable.ic_media_play);
                    mIsPlaying = true;
				}
				if (!isplay){
					playimage.setBackgroundResource(android.R.drawable.ic_media_pause);
                    mIsPlaying = false;
				}
                Intent play_center = new Intent(MainActivity.this,
                        PlayerService.class);
                play_center.putExtra("control", "play");
                startService(play_center);

                break;
            case R.id.xia1:
                Intent play_right = new Intent(MainActivity.this,
                        PlayerService.class);
                play_right.putExtra("control", "next");
                startService(play_right);
                break;
            default:
                break;
        }
    }

    /**
     * 监听选项切换不同的页面
     * */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        switch (checkedId) {
            case R.id.rBtnAll:
                switchFragment(PlayingListFragment.class);
                break;
            case R.id.rBtnAlbum:
                switchFragment(AlbumFragment.class);
                break;
            case R.id.rBtnSinger:
                switchFragment(ArtistFragment.class);
                break;
            case R.id.rBtnSong:
                switchFragment(SongFragment.class);
                break;
            default:
                break;
        }
    }

    /**
     * 切换页面，利用反射添加不同的fragment
     */
    private boolean switchFragment(Class<? extends BaseFragment> bf) {
        // TODO Auto-generated method stub
        if (mFragment != null && mFragment.getClass() == bf) {
            return false;
        }
        try {
            mFragment = bf.newInstance();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return false;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, mFragment).commit();
        return true;
    }

//    class MyGestureDetector extends SimpleOnGestureListener {
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                               float velocityY) {
//            // TODO Auto-generated method stub
//            TabHost tabHost = getTabHost();
//            try {
//                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX__OFF_PATH) {
//                    return false;
//                }
//
//                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
//                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Log.i("test", "向右滑动");
//                    int i = tabHost.getCurrentTab() + 1;
//                    Log.i("test1", "Tag页面" + i);
//                    if (currentView == maxTabIndex) {
//                        currentView = 0;
//                    } else {
//                        currentView++;
//                    }
//                    tabHost.setCurrentTab(currentView);
//                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
//                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//                    Log.i("test", "向左滑动");
//                    int i = tabHost.getCurrentTab() + 1;
//                    Log.i("test2", "Tag页面" + i);
//
//                    if (currentView == 0) {
//                        currentView = maxTabIndex;
//                    } else {
//                        currentView--;
//                    }
//
//                    tabHost.setCurrentTab(currentView);
//                }
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//            return false;
//        }
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) { // 用来分发触摸事件
//        // TODO Auto-generated method stub
//
//        if (gestureDetector.onTouchEvent(event)) {// 返回true,获取完整的触摸事件,事件由自己处理消耗
//            event.setAction(MotionEvent.ACTION_CANCEL);
//        }
//        return super.dispatchTouchEvent(event);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 用于监听DrawerLayout的打开与关闭
        // 其中isDrawOpen()函数默认返回值为false
        // OpenDrawer()打开，closeDrawer()关闭抽屉
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mDrawerLayout.isDrawerOpen(mDrawerList)) {// 判断是否打开抽屉
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.closeDrawers();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // if (PlayerService.myMediaPlayer != null) {
        // PlayerService.myMediaPlayer.stop();
        // PlayerService.myMediaPlayer.release();
        // PlayerService.myMediaPlayer = null;
        // }
        Log.d(TAG,"MainActivity destroyed");
    }
}
