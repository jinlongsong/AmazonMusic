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

public class AlbumsAdapter extends BaseAdapter {
	
    private List<Music> listMusic;
    private Context context;
    public AlbumsAdapter(Context context,List<Music> listMusic){
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
			convertView=LayoutInflater.from(context).inflate(R.layout.music_item, null);
		}
		Music m=listMusic.get(position);
	//  获取歌曲名
		TextView textMusicName=(TextView) convertView.findViewById(R.id.music_item_name);
		textMusicName.setText(m.getSinger());
	//获取歌手
		TextView textMusicSinger=(TextView) convertView.findViewById(R.id.music_item_singer);
		textMusicSinger.setText(m.getName());
	   /*//
		TextView textMusicTime=(TextView) convertView.findViewById(R.id.music_item_time);
		textMusicTime.setText(toTime((int)m.getTime()));*/
		return convertView;
	}
	
			/**
			 * @param l
			 * @return
			 */
			public static String toTime(long l) {
		        
				l /= 1000;
				long minute = l / 60;
				long hour = minute / 60;
				long second = l % 60;
				minute %= 60;
				return String.format("%02d:%02d", minute, second);
			}
}
