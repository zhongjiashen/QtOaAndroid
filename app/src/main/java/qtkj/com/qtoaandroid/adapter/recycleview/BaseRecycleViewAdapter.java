package qtkj.com.qtoaandroid.adapter.recycleview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> list = new ArrayList<>();
    protected Activity activity;
    private final int NULLDATA = 0;
    protected final int WORD = 1;


    public BaseRecycleViewAdapter(Activity activity) {
        this.activity = activity;

    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0)
            return NULLDATA;
        else
            return getSonItemViewType(position);
    }

    /**
     * 子类扩展ItemView种类，如果子类不重写则默认使用一种布局
     *
     * @param position
     * @return
     */
    protected int getSonItemViewType(int position) {
        return WORD;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder holder;
        if (viewType == NULLDATA) {
            v = LayoutInflater.from(activity).inflate(R.layout.item_view_null_data, parent, false);
            holder = new NullDataViewHolder(v);
        } else {
            holder = getViewHolder(parent, viewType);
        }
        return holder;
    }

    /**
     * 子类添加自己的ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {

        if (list.size() == 0)
            return 1;
        else
            return list.size();
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    static class NullDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView textView;
        public NullDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
