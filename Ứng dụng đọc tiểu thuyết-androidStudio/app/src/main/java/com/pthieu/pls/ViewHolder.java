package com.pthieu.pls;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongclick(view, getAdapterPosition());
                return true;
            }
        });

    }
    public void setDetails(Context ctx, String titlle, String description, String image){
        TextView mTitleTv = mView.findViewById(R.id.rTitleTv);
        TextView mDetailTv = mView.findViewById(R.id.rDescriptionTv);
        ImageView mImageTv = mView.findViewById(R.id.rImageView);

        mTitleTv.setText(titlle);
        mDetailTv.setText(description);
        Picasso.get().load(image).into(mImageTv);
    }
    private ViewHolder.clickListener mClickListener;

    public interface clickListener{
        void onItemClick (View view, int position);
        void onItemLongclick (View view, int position);
    }
    public void setOnClickListener(ViewHolder.clickListener clickListener){
        mClickListener = clickListener;
    }

}

