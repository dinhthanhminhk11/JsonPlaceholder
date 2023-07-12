package com.example.jsonplaceholder.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonplaceholder.R;
import com.example.jsonplaceholder.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> data;

    public void setData(List<Photo> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        Photo photo = data.get(position);
        if (photo != null) {
            holder.id.setText(photo.getId() + "");
            holder.title.setText(photo.getTitle());
            Picasso.get().load(photo.getThumbnailUrl()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView id;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            id = (TextView) itemView.findViewById(R.id.id);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
