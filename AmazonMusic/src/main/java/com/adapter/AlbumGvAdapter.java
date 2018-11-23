package com.adapter;

import java.util.List;

import com.amaon.getset.Album;
import com.amaon.getset.Music;
import com.amazonmusic.AlbumActivity;
import com.amazonmusic.R;
import com.util.MusicList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumGvAdapter extends BaseAdapter {

	public Context context;
	GridView gv_1;
	public static AlbumGvAdapter msv;
	private List<Album> listAlbum;// 专辑列表
	private static Bitmap mDefaultAlbumIcon;
	public AlbumGvAdapter(Context context, List<Album> listAlbum) {
		this.context = context;
		this.listAlbum = listAlbum;
	}

	@Override
	public int getCount() { // 返回歌曲总数目
		// TODO Auto-generated method stub
		return listAlbum.size();
	}

	@Override
	public Object getItem(int arg0) { // 获取歌曲
		// TODO Auto-generated method stub
		return listAlbum.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		LayoutInflater lif = LayoutInflater.from(context);
		View v = lif.inflate(R.layout.gridspecial_item, null);

		ImageView iv;// 图片标签
		TextView tv1;// 专辑标签
		TextView tv2;// 歌曲数标签
		TextView tv3;// 歌手名

		iv = (ImageView) v.findViewById(R.id.gridspecial_view1);
		tv1 = (TextView) v.findViewById(R.id.album_num);//专辑名
		tv2 = (TextView) v.findViewById(R.id.song_number);//歌曲数
		tv3 = (TextView) v.findViewById(R.id.artist_name);//歌手
		mDefaultAlbumIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.album1);
		
		//listAlbum.get(position).getAlbumName();
	//	iv.setImageResource(R.drawable.icon_album_plus); // 设置专辑默认图片
		Bitmap bitmap =MusicList.getCachedArtwork(context, listAlbum.get(position).getAlbumId(), mDefaultAlbumIcon);
		iv.setBackgroundDrawable(new BitmapDrawable(context.getResources(), bitmap)); //根据专辑id 设置专辑图片
		iv.setAnimation(AnimationUtils.loadAnimation(context,
				R.anim.alpha_z));

		//专辑名
		tv1.setText(listAlbum.get(position).getAlbumName());
		tv1.setAnimation(AnimationUtils.loadAnimation(context,
				R.anim.alpha_y));

		//歌曲数
		tv2.setText("共"+listAlbum.get(position).getSongNumOfAlbum()+"首歌");  //text必须设置为String型  否则报错
		tv2.setAnimation(AnimationUtils.loadAnimation(context,
				R.anim.alpha_y));
		//歌手姓名
		tv3.setText(listAlbum.get(position).getSingerName());  
		tv3.setAnimation(AnimationUtils.loadAnimation(context,
				R.anim.alpha_y));
		return v;
	}

}
