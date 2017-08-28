package qtkj.com.qtoaandroid.adapter.expandablelistview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.MyApplication;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.activity.AddressBookDetailActivity;
import qtkj.com.qtoaandroid.model.AddressBook;

/**
 * Created by wt-pc on 2017/6/21.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    private Context context;
    private List<AddressBook> list = new ArrayList<>();

    public ExpandableListViewAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<AddressBook> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getDept().size();
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
        }
        groupViewHolder = (GroupViewHolder) convertView.getTag();
        if (!isExpanded)
            groupViewHolder.ivArrow.setBackground(context.getResources().getDrawable(R.mipmap.ic_group_close));
        else
            groupViewHolder.ivArrow.setBackground(context.getResources().getDrawable(R.mipmap.ic_group_open));
        groupViewHolder.tvDepartmentName.setText(list.get(groupPosition).getPostName() + "(" + list.get(groupPosition).getNumber() + ")");

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubitemViewHolder subitemViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address_book_subitem, parent, false);
            subitemViewHolder = new SubitemViewHolder(convertView);
            convertView.setTag(subitemViewHolder);
        }
        subitemViewHolder = (SubitemViewHolder) convertView.getTag();
        if(MyApplication.mApplication.getLogin().getType()==0)
            subitemViewHolder.ivArrow.setVisibility(View.GONE);
        else {
            subitemViewHolder.ivArrow.setVisibility(View.VISIBLE);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AddressBookDetailActivity.class).putExtra("userid",list.get(groupPosition).getDept().get(childPosition).getUserId()+""));
                }
            });
        }
        subitemViewHolder.tvName.setText(list.get(groupPosition).getDept().get(childPosition).getUserName());
        subitemViewHolder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + list.get(groupPosition).getDept().get(childPosition).getUserPhone());
                intent.setData(data);
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(Contest.baseurl+list.get(groupPosition).getDept().get(childPosition).getImg()).error(R.mipmap.ic_photo).into(subitemViewHolder.ivHeadPortrait);
        subitemViewHolder.tvKind.setText(list.get(groupPosition).getPostName()+list.get(groupPosition).getDept().get(childPosition).getUserId());
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
        @BindView(R.id.iv_head_portrait)
        ImageView ivHeadPortrait;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_kind)
        TextView tvKind;
        @BindView(R.id.iv_call_phone)
        ImageView ivCallPhone;
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;
        public SubitemViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

    }
}
