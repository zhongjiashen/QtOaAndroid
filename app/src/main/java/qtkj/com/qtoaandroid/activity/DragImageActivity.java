package qtkj.com.qtoaandroid.activity;

import android.graphics.Rect;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import qtkj.com.qtoaandroid.Contest;
import qtkj.com.qtoaandroid.R;
import qtkj.com.qtoaandroid.viewbar.dragzoomimageview.DragImageView;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public class DragImageActivity extends BaseActivity {
    @BindView(R.id.div_view)
    DragImageView divView;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    private int window_width, window_height;// 控件宽度

    @Override
    protected int layout() {
        return R.layout.activity_drage_image;
    }

    @Override
    protected void Initialize() {
        String url = Contest.baseurl + getIntent().getStringExtra("url");
        /** 获取可見区域高度 **/
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();

        Log.e("URL", url);
        Glide.with(this).load(url).error(R.mipmap.ic_launcher_round).error(R.mipmap.ic_photo).into(divView);
        /** 测量状态栏高度 **/
        ViewTreeObserver viewTreeObserver = divView.getViewTreeObserver();

        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        // 获取状况栏高度
                        Rect frame = new Rect();
                        getWindow().getDecorView()
                                .getWindowVisibleDisplayFrame(frame);

                        divView.setScreen_H(window_height - frame.top);
                        divView.setScreen_W(window_width);


                    }
                });
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}
