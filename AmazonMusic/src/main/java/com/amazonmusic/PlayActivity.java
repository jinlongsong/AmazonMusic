package com.amazonmusic;

import com.adapter.AlbumGvAdapter;
import com.adapter.AlbumsAdapter;
import com.amaon.getset.Music;
import com.service.PlayerService;
import com.util.MusicList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class PlayActivity extends Activity {

	public static SeekBar seekbar; // 进度条
	public static TextView time_left;// 当前时间
	public static TextView time_right;// 总时间
	public static TextView musicname;// 歌曲名字
	public static ImageButton left_ImageButton; // 上一首
	public static ImageButton play_ImageButton; // 播放/暂停
	public static ImageButton right_ImageButton;// 下一首
	public static ImageView album_pic; // 专辑图片
	private static Bitmap mDefaultAlbumIcon;
	public static int song_time = 0;
	public static int play_time = 0;
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.playing_view);

		left_ImageButton = (ImageButton) findViewById(R.id.left_bt);
		play_ImageButton = (ImageButton) findViewById(R.id.play_bt);
		right_ImageButton = (ImageButton) findViewById(R.id.right_bt);
		album_pic = (ImageView) findViewById(R.id.album_pic);//专辑图片
		time_left = (TextView) findViewById(R.id.time_left);
		time_right = (TextView) findViewById(R.id.time_right);
		musicname = (TextView) findViewById(R.id.music_name); // 在service中设置

		seekbar = (SeekBar) findViewById(R.id.player_seekbar);
		context = this;

		mDefaultAlbumIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.album1);
		
		initView();	
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// 通知用户触摸手势已经结束。户端可能需要使用这个来启用seekbar的滑动功能。
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// 通知用户已经开始一个触摸拖动手势。客户端可能需要使用这个来禁用seekbar的滑动功能。
			}

			/* 
			 *seekBar 当前被修改进度的SeekBar
			 *progress 当前的进度值。此值的取值范围为0到max之间。Max为用户通过setMax(int)设置的值，默认为100
			 *fromUser如果是用户触发的改变则返回True
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// seekTo是定位方法，可以让播放器从指定的位置开始播放，跳至滑动处播放
				if (fromUser && PlayerService.myMediaPlayer != null) {
					PlayerService.myMediaPlayer.seekTo(progress);
				}
				time_left.setText(AlbumsAdapter.toTime(progress)); // 已播放时间
			}
		});

		/**
		 * 上一首
		 */
		left_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PlayerService.myMediaPlayer != null
						&& PlayerService.myMediaPlayer.isPlaying()) {

				} else {
					play_ImageButton
							.setBackgroundResource(android.R.drawable.ic_media_pause);
				}
				Intent play_left = new Intent(PlayActivity.this,
						PlayerService.class);
				play_left.putExtra("control", "front");
				startService(play_left);
			}
		});

		/**
		 * 播放/暂停
		 */
		play_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 切换播放状态
				if (PlayerService.myMediaPlayer != null
						&& PlayerService.myMediaPlayer.isPlaying()) {
					play_ImageButton
							.setBackgroundResource(android.R.drawable.ic_media_play);

				} else {
					play_ImageButton
							.setBackgroundResource(android.R.drawable.ic_media_pause);
				}

				Intent play_center = new Intent(PlayActivity.this,
						PlayerService.class);
				play_center.putExtra("control", "play");
				startService(play_center);
			}

		});

		/**
		 * 下一首
		 */
		right_ImageButton.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (PlayerService.myMediaPlayer != null
						&& PlayerService.myMediaPlayer.isPlaying()) {

				} else {
					play_ImageButton
							.setBackgroundResource(android.R.drawable.ic_media_pause);
				}
				Intent play_right = new Intent(PlayActivity.this,
						PlayerService.class);
				play_right.putExtra("control", "next");
				startService(play_right);
			}
		});

	}

	private void initView() {  //初始化歌曲播放界面
		if (PlayerService.fromSongList) {
			time_right.setText(AlbumsAdapter.toTime(PlayerService.listMusic
					.get(PlayerService.playing_id).getTime()));// 歌曲总时间
			musicname.setText(PlayerService.listMusic
					.get(PlayerService.playing_id).getName());//歌曲名
			init(PlayerService.listMusic.get(PlayerService.playing_id)); ////******************
		} else if (PlayerService.fromSingerList) {
			time_right.setText(AlbumsAdapter.toTime(PlayerService.SongOfSingerList
					.get(PlayerService.artist_song_id).getTime()));// 歌曲总时间
			musicname.setText(PlayerService.SongOfSingerList
					.get(PlayerService.artist_song_id).getName());
			init(PlayerService.SongOfSingerList.get(PlayerService.artist_song_id));
		} else if (PlayerService.fromAlbumList) {
			time_right.setText(AlbumsAdapter.toTime(PlayerService.SongOfAlbumList
					.get(PlayerService.album_song_id).getTime()));// 歌曲总时间
			musicname.setText(PlayerService.SongOfAlbumList
					.get(PlayerService.album_song_id).getName());
			init(PlayerService.SongOfAlbumList.get(PlayerService.album_song_id));
		}		
	}

	public static void init(Music music) {
		Bitmap bitmap = MusicList.getCachedArtwork(context, music.getAlbumId(),mDefaultAlbumIcon);
		album_pic.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap));
		
	}

}
