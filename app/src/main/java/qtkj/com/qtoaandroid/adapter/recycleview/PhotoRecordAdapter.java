package qtkj.com.qtoaandroid.adapter.recycleview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.DragImageActivity;
import qtkj.com.qtoaandroid.model.PhotoRecord;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class PhotoRecordAdapter extends BaseRecycleViewAdapter<PhotoRecord> {


    public PhotoRecordAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_photo_record, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if(list.size()>0) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvAddress.setText(list.get(position).getPosition());
            Glide.with(activity).load(Contest.baseurl+list.get(position).getPhotoUrl()).error(R.mipmap.ic_photo).into(viewHolder.ivPhoto);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            viewHolder.tvDate.setText(sdf.format(new Date(list.get(position).getCreateTime())));
            viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(activity, DragImageActivity.class).putExtra("url",list.get(position).getPhotoUrl()));
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
