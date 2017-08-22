package qtkj.com.qtoaandroid.adapter.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.DragImageActivity;
import qtkj.com.qtoaandroid.model.PhotoRecord;
import qtkj.com.qtoaandroid.utils.LogUtils;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class PhotoRecordAdapter extends BaseRecycleViewAdapter<PhotoRecord> {
    protected final int BOOTOM = 2;
    private boolean loading;
    private boolean more;

    public boolean isLoading() {
        return loading;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public boolean isMore() {
        return more;
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0)
            return 1;
        else if (list.size() < 4)
            return list.size();
        else {
            loading=true;
            return list.size() + 1;

        }
    }

    /**
     * 子类扩展ItemView种类，如果子类不重写则默认使用一种布局
     *
     * @param position
     * @return
     */
    @Override
    protected int getSonItemViewType(int position) {
        if (list.size()!=0&&list.size()> 4&&position  == list.size() ) {
            LogUtils.d("bOTT");
            return BOOTOM;
        } else
            return WORD;
    }

    public PhotoRecordAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case WORD:
                v = LayoutInflater.from(activity).inflate(R.layout.item_photo_record, parent, false);
                holder = new ViewHolder(v);
                break;
            case BOOTOM:
                LogUtils.d("TT");
                v = LayoutInflater.from(activity).inflate(R.layout.item_foot, parent, false);
                holder = new BottomViewHolder(v);
                break;
        }
        return holder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case WORD:
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.tvAddress.setText(list.get(position).getPosition());
                Glide.with(activity).load(Contest.baseurl + list.get(position).getPhotoUrl()).error(R.mipmap.ic_photo).into(viewHolder.ivPhoto);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                viewHolder.tvDate.setText(sdf.format(new Date(list.get(position).getCreateTime())));
                viewHolder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.startActivity(new Intent(activity, DragImageActivity.class).putExtra("url", list.get(position).getPhotoUrl()));
                    }
                });
                break;
            case BOOTOM:
                LogUtils.d("TTT");
                BottomViewHolder hold= (BottomViewHolder) holder;
                if(more){
                    hold.progressBar.setVisibility(View.GONE);
                    hold.tvLoding.setText("没有更多的数据了！");
                }
                break;
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

    class BottomViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.progressBar)
        ProgressBar progressBar;
        @BindView(R.id.tv_loding)
        TextView tvLoding;

        BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
