package com.example.xiangha.imageutil;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState
     */
    //    TopicInfoActivity.this.runOnUiThread(new Runnable() {
//        @Override
//        public void run() {
//            if (bitmaps != null && mDatas != null) {
//                for (int i = size - 1; i >= 0; i--) {
//                    mDatas.remove(i);
//                }
//                tabPosition = bitmaps.size();
//                mTopicInfoStaggeredAdapter.setTabIndex(tabPosition);
//                for (int i = 0; i < bitmaps.size(); i++) {
//                    Bitmap bitmap = bitmaps.get(i);
//                    TopicItemModel model = new TopicItemModel();
//                    model.setBitmap(bitmap);
//                    model.setItemType(TopicInfoStaggeredAdapter.ITEM_ACTIVITY_IMG);
//                    mDatas.add(i, model);
//                }
//                if (mTopicInfoStaggeredAdapter != null) {
//                    mTopicInfoStaggeredAdapter.notifyDataSetChanged();
//                }
//            }
//        }
//    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageUtil.tailorImageByUrl(MainActivity.this, "图片链接", 100, 100, 400, new ImageUtil.OnResourceCallback() {
            @Override
            public void onResource(ArrayList<Bitmap> bitmaps) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }
}
