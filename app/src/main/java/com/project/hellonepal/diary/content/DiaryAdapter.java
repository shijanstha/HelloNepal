package com.project.hellonepal.diary.content;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.hellonepal.R;
import com.project.hellonepal.diary.model.Diary;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by rojo on 2/25/17.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    final SimpleDateFormat format = new SimpleDateFormat("MMM d, yyyy h:mm a");
    private List<Diary> diaryItems;


    public DiaryAdapter(List<Diary> diaryItems) {
        this.diaryItems = diaryItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Diary diaryItem = diaryItems.get(position);

        holder.diaryTitle.setText(diaryItem.getTitle());
        holder.diaryContent.setText(diaryItem.getContent());
        holder.diaryDate.setText(format.format(diaryItem.getDate()));

        holder.imageView.setImageDrawable(null);

        boolean photoExists = diaryItem.getPhoto() != null;

        if (photoExists) {
            Bitmap imageBitmap = convertByteArrayToBitMap(diaryItem.getPhoto());
            holder.imageView.setImageBitmap(imageBitmap);
        }

    }

    private Bitmap convertByteArrayToBitMap(byte[] photo) {
        return BitmapFactory.decodeByteArray(photo,
                0, photo.length);
    }

    @Override
    public int getItemCount() {
        return diaryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView diaryTitle, diaryContent, diaryDate;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            diaryTitle = (TextView) itemView.findViewById(R.id.diary_title);
            diaryContent = (TextView) itemView.findViewById(R.id.diary_content);
            diaryDate = (TextView) itemView.findViewById(R.id.diary_date);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
