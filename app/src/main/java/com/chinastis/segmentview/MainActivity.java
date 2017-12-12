package com.chinastis.segmentview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chinastis.segmentview.widget.SegmentView;

public class MainActivity extends AppCompatActivity {

    private SegmentView segmentView;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        segmentView = (SegmentView) findViewById(R.id.segment_main);

        segmentView.setOnSegmentChangeListener(new SegmentView.OnSegmentChangeListener() {
            @Override
            public void segmentChanged(View view, int index) {
                Toast.makeText(MainActivity.this,"点击了："+index,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
