package qtkj.com.qtoaandroid.activity;

import android.util.Log;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.viewbar.dragzoomimageview.DragImageView;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class DragImageActivity extends BaseActivity {
    @BindView(R.id.div_view)
    DragImageView divView;

    @Override
    protected int layout() {
        return R.layout.activity_drage_image;
    }

    @Override
    protected void Initialize() {
        String url = Contest.baseurl + getIntent().getStringExtra("url");
        Log.e("URL",url);
        Glide.with(this).load(url).error(R.mipmap.ic_launcher_round).error(R.mipmap.ic_photo).into(divView);

    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
