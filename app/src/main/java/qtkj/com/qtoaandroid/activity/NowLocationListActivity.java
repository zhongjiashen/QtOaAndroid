package qtkj.com.qtoaandroid.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.adapter.recycleview.NowLocationListAdapter;
import qtkj.com.qtoaandroid.model.NowLocationList;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class NowLocationListActivity extends BaseActivity {
    @BindView(R.id.rl_view)
    RecyclerView rlView;
    Map<String, NowLocationList> map = new HashMap();
    List<NowLocationList> list = new ArrayList<>();
    NowLocationListAdapter adapter;

    /**
     * 指定加载布局
     *
     * @return 返回布局
     */
    @Override
    protected int layout() {
        return R.layout.activity_now_location_list;
    }

    /**
     * 初始化方法
     */
    @Override
    protected void Initialize() {
        Gson gson = new Gson();
        Type jsonType = new TypeToken<List<NowLocationList>>() {
        }.getType();
        list = gson.fromJson(getIntent().getStringExtra("list"), jsonType);
        String postname = getIntent().getStringExtra("name");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rlView.setLayoutManager(layoutManager);
        rlView.setAdapter(adapter = new NowLocationListAdapter(this));
        adapter.setPostname(postname);
        adapter.setList(list);
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
