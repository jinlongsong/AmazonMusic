package com.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaon.getset.Album;
import com.amazonmusic.R;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private Context mcontext;
    private List<Album> listAlbum;// 专辑列表

    public RecycleViewAdapter(Context context, List<Album> listAlbum) {
        this.mcontext = context;
        this.listAlbum = listAlbum;
        Log.d("TAG","size--"+listAlbum.size());
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // super(holder, int position);
        Log.d("TAG","111");
        Album album = listAlbum.get(position);
        Log.d("TAG","112");
        if (album.getAlbumArt()!=null && !album.getAlbumArt().equals("")){
            Drawable img = Drawable.createFromPath(album.getAlbumArt());
            holder.imageLab.setImageDrawable(img);
        }else {
            holder.imageLab.setImageResource(R.drawable.album1);
        }
        holder.albumLab.setText(album.getAlbumName());
        holder.artistLab.setText(album.getSingerName());
        holder.songLab.setText(album.getSongNumOfAlbum());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("TAG","113");
        LayoutInflater lif = LayoutInflater.from(mcontext);
        View view = lif.inflate(R.layout.gridspecial_item, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (listAlbum != null && listAlbum.size() > 0) {
            return listAlbum.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return listAlbum.get(position).hashCode();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageLab;// 图片标签
        private TextView albumLab;// 专辑标签
        private TextView songLab;// 歌曲数标签
        private TextView artistLab;// 歌手名

        public ViewHolder(View itemView) {
            super(itemView);
            imageLab = (ImageView) itemView.findViewById(R.id.gridspecial_view1);
            albumLab = (TextView) itemView.findViewById(R.id.album_num);//专辑名
            songLab = (TextView) itemView.findViewById(R.id.song_number);//歌曲数
            artistLab = (TextView) itemView.findViewById(R.id.artist_name);//歌手
        }
    }
}
