package com.linksu.basespannabletextview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

import spannable.base.linksu.com.basespannablelibrary.BaseSpannableTextView;

public class MainActivity extends AppCompatActivity {

    private BaseSpannableTextView baseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseTextView = (BaseSpannableTextView) findViewById(R.id.baseTextView);
        baseTextView.setBaseText("最佳评论人Hello World!Yes 没错就是你最佳评论人 对你快过来最佳评论人");

        //单个关键字
        baseTextView.setKeys("最佳评论人");
        baseTextView.setisSerachAll(false);
        baseTextView.setUnderLine(true);
        baseTextView.setKeySize(80);
        baseTextView.setUnderLineColor(Color.RED);

        //多个关键字查找
//        baseTextView.setKeyList(new String[]{"最佳", "人", "论"});
//
//        baseTextView.setKeySizeList(new int[]{80, 40, 60});
//
//        baseTextView.setKeyColorList(new int[]{Color.RED, Color.GREEN, Color.BLUE});
//
//        baseTextView.setKeyLines(new boolean[]{true, false, true});
//
//        baseTextView.setisSerachAll(true);

        baseTextView.refreshView();
    }
}
