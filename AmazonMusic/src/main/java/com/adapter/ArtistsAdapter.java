package com.adapter;

import java.util.List;





import com.amaon.getset.Artist;
import com.amaon.getset.Music;
import com.amazonmusic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ArtistsAdapter extends BaseAdapter {
	
    private List<Artist> artistList;  //每个list对象包含歌手id，名字，专辑数，歌曲数
    private Context context;
    public ArtistsAdapter(Context context,List<Artist> list){
    	this.context=context;
    	this.artistList=list;
    }
	public void setListItem(List<Artist> artistList){
		this.artistList=artistList;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return artistList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return artistList.get(arg0);
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
			convertView=LayoutInflater.from(context).inflate(R.layout.artist_item, null);
		}
		
		
		Artist artist=artistList.get(position);

		TextView textArtistName=(TextView) convertView.findViewById(R.id.artist_item_name);
		textArtistName.setText(artist.getSingerName());  //获取歌手名
		
		TextView textSongNum=(TextView) convertView.findViewById(R.id.artist_song_num);
		textSongNum.setText("共"+artist.getNumOfSinger()+"首歌曲");//获取歌手的歌曲数
	  
		return convertView;
	}
			
}
