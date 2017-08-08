package qtkj.com.qtoaandroid.adapter.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.NowLocationActivity;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class NowLocationAdapter<String> extends BaseRecycleViewAdapter{
    public NowLocationAdapter(Activity activity) {
        super(activity);
        list.add("");
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(activity).inflate(R.layout.item_now_location, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, NowLocationActivity.class));
            }
        });

    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
