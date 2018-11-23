package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adapter.AlbumsAdapter;
import com.adapter.ArtistsAdapter;
import com.amaon.getset.Music;
import com.amazonmusic.MyApp;
import com.amazonmusic.PlayActivity;
import com.amazonmusic.R;
import com.util.AlbumList;
import com.util.ArtistList;
import com.util.MusicList;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class PlayerService extends Service {

	public static MediaPlayer myMediaPlayer;
	private MyApp myApp;
		
	public static boolean fromSongList=false;  //点击事件来自歌曲列表
	public static boolean fromSingerList=false; //点击事件来自歌手歌曲列表	
	public static boolean fromAlbumList=false;  //点击事件来自专辑列表
	
	public static List<Music> listMusic;        //歌曲列表
	public static List<Music> SongOfSingerList; //歌手歌曲列表
	public static List<Music> SongOfAlbumList;  //专辑歌曲列表
	
	public static int playing_id = 0;      //所点击歌曲在歌曲list中的位置（arg2）
	public static int artist_song_id = 0;  //所点击歌曲在歌手歌曲list中的位置（arg2）
	public static int album_song_id = 0;   //所点击歌曲在专辑歌曲list中的位置（arg2）
	
	private int singerid;// 由前面传过来的歌手id  用来取出属于该歌手的歌曲
	private int albumid; // 由前面传过来的专辑id  用来取出属于该专辑里的歌曲
	
	public static String  currentsongname; //当前播放歌曲名字
	
	private int index = 0;
	private int CurrentTime = 0;// 当前播放时间
	private int CountTime = 0;// 总时间

	public Thread thread;
	Handler handler = new Handler();
  
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myApp=(MyApp)getApplication(); //全局变量 设置当前歌曲名
		
	//	initMediaSource(initMusicUri(0)); // 初始化播放
	//	listMusic = MusicList.getMusicData(getApplicationContext());  //移动之后专辑不能播放，需找出原因 
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (myMediaPlayer != null) {
			myMediaPlayer.stop();
			myMediaPlayer.release();
			myMediaPlayer = null;
		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);

		String playFlag = intent.getExtras().getString("control"); // 获取intent
			//play、next、front事件来自于playctivity和常驻栏按钮
		if ("play".equals(playFlag)) { // 播放 
			if (myMediaPlayer != null) {
				playMusic();
			}else {
				Toast.makeText(this, "当前无歌曲", Toast.LENGTH_LONG).show();
			}
		} else if ("next".equals(playFlag)) { // 下一首
			if (myMediaPlayer != null) {
				playNext();
			}else {
				Toast.makeText(this, "当前无歌曲", Toast.LENGTH_LONG).show();
			}
		} else if ("front".equals(playFlag)) { // 上一首
			if (myMediaPlayer != null) {
				playFront();
			}else {
				Toast.makeText(this, "当前无歌曲", Toast.LENGTH_LONG).show();
			}
		} else if ("song_listClick".equals(playFlag)) { // 歌曲列表
			//同时将另外两个置为false，防止串台
			fromSongList=true;
			fromSingerList=false;//来自歌手列表
			fromAlbumList=false;				
				
			listMusic = MusicList.getMusicData(getApplicationContext());  //移动之后专辑不能播放，需找出原因
			playing_id = intent.getExtras().getInt("musicId_1");// 获取点击的歌曲 在listview中的位置 据此获取歌曲			
			currentsongname=MusicList.getMusicData(getApplicationContext()).get(playing_id).getName(); //当前播放歌曲名字
			initMediaSource(initMusicUri(playing_id));
			playMusic();
		}
		else if ("artistsong_listClick".equals(playFlag)) {//单击事件来自于歌手歌曲列表
			//同时将另外两个置为false，防止串台
			fromSingerList=true;//来自歌手列表
			fromAlbumList=false;
			fromSongList=false;
			artist_song_id = intent.getExtras().getInt("musicId_2");// 获取点击的歌曲 在listview中的位置 据此获取歌曲
			
			singerid=intent.getExtras().getInt("singerid_1"); //当前歌手id，区分是哪个歌手的歌曲
			SongOfSingerList=ArtistList.getArtistSongData(this, singerid); 	
			currentsongname= SongOfSingerList.get(artist_song_id).getName(); //当前播放歌曲名字			
			initMediaSource(initMusicUri(artist_song_id));  //创建音乐对象成功			
			playMusic();//播放
		}else if ("albumsong_listClick".equals(playFlag)) {//单击事件来自于专辑歌曲列表
			//同时将另外两个置为false，防止串台
			fromAlbumList=true;//来自专辑列表
			fromSongList=false;
			fromSingerList=false;//来自歌手列表
			album_song_id = intent.getExtras().getInt("musicId_3");// 所点击专辑 在listview中的位置 据此获取歌曲
			
			albumid=intent.getExtras().getInt("albumid_1"); //当前专辑id，区分是哪个专辑的歌曲
			SongOfAlbumList=AlbumList.getAlbumSongData(this, albumid); 	//根据专辑id 获取专辑里面的歌曲
			currentsongname=SongOfAlbumList.get(album_song_id).getName(); //当前播放歌曲名字			
			initMediaSource(initMusicUri(album_song_id));  //创建音乐对象成功			
			playMusic();//播放
		}
	}

	/**
	 * @param mp3Path
	 */
	public void initMediaSource(String mp3Path) {// 初始化媒体库
		Uri mp3Uri = Uri.parse(mp3Path);
		if (myMediaPlayer != null) {
			myMediaPlayer.stop();
			myMediaPlayer.reset();
			myMediaPlayer = null;
			
		}
		myMediaPlayer = MediaPlayer.create(this, mp3Uri); // 创建歌曲对象
	}

	/**
	 * @param _id
	 * @return uri
	 */
	public String initMusicUri(int _id) { // 根据传过来的参数 获取所点击歌曲uri
		String s; // 获取所点击 歌曲路径
		
		if (fromSongList && listMusic != null && listMusic.size() != 0) {//事件来自歌曲列表		
			s = listMusic.get(_id).getUrl();
			return s;		
		}else if (fromSingerList && SongOfSingerList != null && SongOfSingerList.size() != 0) {//事件来自于歌手歌曲列表 	
			s = SongOfSingerList.get(_id).getUrl(); //获取歌曲的 url路径
			return s;		
		}else if (fromAlbumList && SongOfAlbumList != null && SongOfAlbumList.size() != 0) { //专辑
			s = SongOfAlbumList.get(_id).getUrl(); //获取歌曲的 url路径		
			return s;
		}else {
			return "";
		}
	}
	/**
	 * 播放/暂停
	 */
	public void playMusic() { // 播放音乐


	//	PlayActivity.time_right.setText(AlbumsAdapter.toTime(listMusic.get(playing_id).getTime()));
		
		if (fromSongList && myMediaPlayer != null && listMusic.size() != 0) {

			if (myMediaPlayer.isPlaying()) {
				// 若正在播放
				myMediaPlayer.pause();
			} else {
				myMediaPlayer.start();
				mHandler.post(mRunnable);
				startSeekBarUpdate();
			}
			myMediaPlayer.setOnCompletionListener(new OnCompletionListener() { // setcompletionlistener实现自动播放下一曲

						@Override
						public void onCompletion(MediaPlayer mp) {
							// 上一首播放完后自动播放下一收
							playNext();
						}
					});
		} else if (fromSingerList && myMediaPlayer != null && SongOfSingerList.size() != 0) {
			
			if (myMediaPlayer.isPlaying()) {
				// 若正在播放
				myMediaPlayer.pause();
			} else {
				myMediaPlayer.start();
				mHandler.post(mRunnable);
				startSeekBarUpdate();
			}
			myMediaPlayer.setOnCompletionListener(new OnCompletionListener() { // setcompletionlistener实现自动播放下一曲

						@Override
						public void onCompletion(MediaPlayer mp) {
							// 上一首播放完后自动播放下一收
							playNext();
						}
					});			
			
		}else if (fromAlbumList && myMediaPlayer != null && SongOfAlbumList.size() != 0) {
			
			if (myMediaPlayer.isPlaying()) {
				// 若正在播放
				myMediaPlayer.pause();
			} else {
				myMediaPlayer.start();
				mHandler.post(mRunnable);
				startSeekBarUpdate();
			}
			myMediaPlayer.setOnCompletionListener(new OnCompletionListener() { // setcompletionlistener实现自动播放下一曲

						@Override
						public void onCompletion(MediaPlayer mp) {
							// 上一首播放完后自动播放下一收
							playNext();
						}
					});
			
		}else {  //**
			Toast.makeText(PlayerService.this, "暂无歌曲播放，请选择歌曲",Toast.LENGTH_SHORT).show();			
		}
	}
	  
	/**
	 *  下一首
	 */  
	public void playNext() {

		if (fromSongList && listMusic.size() != 0) {

			if (playing_id == listMusic.size() - 1) {
				playing_id = listMusic.size() - 1;
				Toast.makeText(PlayerService.this, "骚年，已经是最后一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(++playing_id));
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(listMusic.get(playing_id).getTime()));
				PlayActivity.musicname.setText(listMusic.get(playing_id).getName());
				PlayActivity.init(listMusic.get(playing_id));
				playMusic();
			}
		}else if (fromSingerList && SongOfSingerList.size()!=0) {
			if (artist_song_id == SongOfSingerList.size() - 1) {
				artist_song_id = SongOfSingerList.size() - 1;
				Toast.makeText(PlayerService.this, "骚年，已经是最后一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(++artist_song_id));				
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(SongOfSingerList.get(artist_song_id).getTime()));
				PlayActivity.musicname.setText(SongOfSingerList.get(artist_song_id).getName());
				PlayActivity.init(PlayerService.listMusic.get(PlayerService.playing_id));
				playMusic();
			}
		}else if (fromAlbumList && SongOfAlbumList.size()!=0) {
			if (album_song_id == SongOfAlbumList.size() - 1) {
				album_song_id = SongOfAlbumList.size() - 1;
				Toast.makeText(PlayerService.this, "骚年，已经是最后一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(++album_song_id));				
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(SongOfAlbumList.get(album_song_id).getTime()));
				PlayActivity.musicname.setText(SongOfAlbumList.get(album_song_id).getName());
				PlayActivity.init(SongOfAlbumList.get(album_song_id));
				playMusic();
			}
		}else {
			Toast.makeText(PlayerService.this, "wrong...", Toast.LENGTH_SHORT).show();
		}		
	}

	/**
	 * 上一首
	 */
	public void playFront() {
		
		if (fromSongList && listMusic.size() != 0) {
			//歌曲列表
			if (playing_id == 0) {
				playing_id = 0;
				Toast.makeText(PlayerService.this, "骚年，已经是第一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(--playing_id));
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(listMusic.get(playing_id).getTime()));
				PlayActivity.musicname.setText(listMusic.get(playing_id).getName());
				PlayActivity.init(listMusic.get(playing_id));
				playMusic();
			}
		} else if (fromSingerList && SongOfSingerList.size()!=0) {
			//按歌手分类歌曲列表
			if (artist_song_id == 0) {
				artist_song_id = 0;
				Toast.makeText(PlayerService.this, "骚年，已经是第一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(--artist_song_id));
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(SongOfSingerList.get(artist_song_id).getTime()));
				PlayActivity.musicname.setText(SongOfSingerList.get(artist_song_id).getName());
				PlayActivity.init(SongOfSingerList.get(artist_song_id));
				playMusic();
			}			
		} else if (fromAlbumList && SongOfAlbumList.size()!=0) {
			//按专辑分类歌曲列表
			if (album_song_id == 0) {
				album_song_id = 0;
				Toast.makeText(PlayerService.this, "骚年，已经是第一首歌了",
						Toast.LENGTH_SHORT).show();
			} else {
				initMediaSource(initMusicUri(--album_song_id));
				PlayActivity.time_right.setText(AlbumsAdapter.toTime(SongOfAlbumList.get(album_song_id).getTime()));
				PlayActivity.musicname.setText(SongOfAlbumList.get(album_song_id).getName());
				PlayActivity.init(SongOfAlbumList.get(album_song_id));
				playMusic();
			}			
		} else {
			Toast.makeText(PlayerService.this, "wrong...", Toast.LENGTH_SHORT).show();
		}
	}

	public void startSeekBarUpdate() {
		// TODO Auto-generated method stub
		handler.post(start);
	}

	Runnable start = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// post 用它可以更新一个组件的内容
			handler.post(updatesb);
		}

	};

	Runnable updatesb = new Runnable() {              //此线程用于动态更改进度条信息

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			// 获取歌曲播放位置
				PlayActivity.play_time = myMediaPlayer.getCurrentPosition();

				// 设置进度条
				PlayActivity.seekbar.setProgress(PlayActivity.play_time);
				// 设置进度条最大值//修改显示时间  这里不是很懂，三个一起设定才行？？？
			
				if (fromSongList) {
					PlayActivity.seekbar.setMax((int) listMusic.get(playing_id).getTime()); // 转换成整数
				}else if (fromSingerList) {
					PlayActivity.seekbar.setMax((int) SongOfSingerList.get(artist_song_id).getTime()); // 转换成整数
				}else if (fromAlbumList) {
					PlayActivity.seekbar.setMax((int) SongOfAlbumList.get(album_song_id).getTime()); // 转换成整数
				}else {
					return;
				}			

			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//
			handler.postDelayed(updatesb, 1000);
		}
	};

	Handler mHandler = new Handler();

	Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.postDelayed(mRunnable, 100);
		}
	};
}
