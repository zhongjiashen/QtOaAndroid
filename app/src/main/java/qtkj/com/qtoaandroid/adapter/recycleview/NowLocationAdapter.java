package qtkj.com.qtoaandroid.adapter.recycleview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.NowLocationActivity;
import qtkj.com.qtoaandroid.model.NowLocationF;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class NowLocationAdapter extends BaseRecycleViewAdapter<NowLocationF> {


    public NowLocationAdapter(Activity activity) {
        super(activity);

    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_now_location, parent, false);

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
        if(list.size()>0) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvDepartmentName.setText(list.get(position).getPostName());
            viewHolder.tvShouldNumber.setText(list.get(position).getSignCount()+"");
            viewHolder.tvTotalNumber.setText("/"+list.get(position).getUserCount()+"");
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.tv_should_number)
        TextView tvShouldNumber;
        @BindView(R.id.tv_total_number)
        TextView tvTotalNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
