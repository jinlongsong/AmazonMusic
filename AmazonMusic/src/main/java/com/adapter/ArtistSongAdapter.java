package com.adapter;

import java.util.List;




import com.amaon.getset.Music;
import com.amazonmusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArtistSongAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    /**
     * @param context
     * @param listMusic  属于该歌手的歌曲列表
     */
    public ArtistSongAdapter(Context context,List<Music> listMusic){
    	this.context=context;
    	this.listMusic=listMusic;
    }
	public void setListItem(List<Music> listMusic){
		this.listMusic=listMusic;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listMusic.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listMusic.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.artist_song, null);
		}
		Music m=listMusic.get(position);
	//  歌曲名
		TextView textMusicName=(TextView) convertView.findViewById(R.id.artist_song_name);
		textMusicName.setText(m.getTitle());
	//  歌手
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.artist_song_singer);
		textMusicSinger.setText(m.getSinger());
	//  歌曲时间
		TextView textMusicTime=(TextView) convertView.findViewById(R.id.artist_song_time);
		textMusicTime.setText(toTime((int)m.getTime()));
		return convertView;
	}
	  
			/**
			 * @param time   
			 * @return
			 */
			public String toTime(int time) {  //转换成分秒类型
		        
				time /= 1000;
				int minute = time / 60;
				int hour = minute / 60;
				int second = time % 60;
				minute %= 60;
				return String.format("%02d:%02d", minute, second);
			}
}
