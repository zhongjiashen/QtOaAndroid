package qtkj.com.qtoaandroid.adapter.expandablelistview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.model.Daily;
import qtkj.com.qtoaandroid.utils.DateUtil;

/**
 * Created by wt-pc on 2017/6/21.
 */

public class DailyExpandableListViewAdapter extends BaseExpandableListAdapter {


    private Activity context;
    private List<Daily.BottomBean> list=new ArrayList<>();

    public DailyExpandableListViewAdapter(Activity context) {
        this.context = context;
    }

    public void setList(List<Daily.BottomBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChild().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_book_group, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
            if (!isExpanded)
                groupViewHolder.ivArrow.setBackground(context.getResources().getDrawable(R.mipmap.ic_group_close));
            else
                groupViewHolder.ivArrow.setBackground(context.getResources().getDrawable(R.mipmap.ic_group_open));
        }
        groupViewHolder.tvDepartmentName.setText(list.get(groupPosition).getGroup() + "(" + list.get(groupPosition).getCount() + ")");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubitemViewHolder subitemViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_daily_subitem, parent, false);
            subitemViewHolder = new SubitemViewHolder(convertView);
            convertView.setTag(subitemViewHolder);
        } else {
            subitemViewHolder = (SubitemViewHolder) convertView.getTag();
        }
        subitemViewHolder.tvName.setText(list.get(groupPosition).getChild().get(childPosition).getUserName());
        subitemViewHolder.tvKind.setText(list.get(groupPosition).getChild().get(childPosition).getPostName());
        subitemViewHolder.tvKindNumber.setText(list.get(groupPosition).getChild().get(childPosition).getPostName()+list.get(groupPosition).getChild().get(childPosition).getUserId());
        subitemViewHolder.tvTime.setText("打卡时间："+ DateUtil.longDateToString(list.get(groupPosition).getChild().get(childPosition).getSignTime(),"HH:mm"));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.tv_department_name)
        TextView tvDepartmentName;
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;

        public GroupViewHolder(View itemView) {

            ButterKnife.bind(this, itemView);
        }

    }

    static class SubitemViewHolder {
        @BindView(R.id.iv_photo)
        ImageView ivPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_kind)
        TextView tvKind;
        @BindView(R.id.tv_kind_number)
        TextView tvKindNumber;
        @BindView(R.id.tv_time)
        TextView tvTime;
        public SubitemViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }
}
