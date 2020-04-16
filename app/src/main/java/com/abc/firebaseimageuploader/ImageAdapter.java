package com.abc.firebaseimageuploader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.firebaseimageuploader.R;
import com.abc.firebaseimageuploader.Upload;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener monItemClickListener;

    public ImageAdapter(Context context,List<Upload> uploads){
        mContext=context;
        mUploads=uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadcurrent=mUploads.get(position);
        holder.textViewName.setText(uploadcurrent.getName());
        Picasso.with(mContext)
                .load(uploadcurrent.getmImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    , View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.text_view_name);
            imageView=itemView.findViewById(R.id.image_view_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if(monItemClickListener !=null){
                int position=getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    monItemClickListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever=menu.add(Menu.NONE,1,1,"Do Wish You");
            MenuItem delete=menu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(monItemClickListener !=null){
                int position=getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
             switch (item.getItemId()){
                 case 1:
                     monItemClickListener.onWhatEverClick(position);
                     return true;
                 case 2:
                     monItemClickListener.onDeleteClick(position);
                     return true;

             }
                }
            }


            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
monItemClickListener =listener;
    }

}
