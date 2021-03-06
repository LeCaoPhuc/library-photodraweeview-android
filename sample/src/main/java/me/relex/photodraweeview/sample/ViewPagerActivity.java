package me.relex.photodraweeview.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import me.relex.photodraweeview.DraweePagerAdapter;
import me.relex.photodraweeview.PhotoDraweeView;

public class ViewPagerActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ViewPagerActivity.class));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        ((Toolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        onBackPressed();
                    }
                });
        String[] mDrawables = new String[] {
                "https://www.alveo-slovakia.com/photo/147689/w3css-images-bordered-image.jpg",
                "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-400502.jpg",
                "https://images3.alphacoders.com/712/712915.jpg",
                "https://images8.alphacoders.com/869/869862.jpg",
                "https://images8.alphacoders121221.com/869/869862.jpg"
        };
        ArrayList arrayList = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            arrayList.add(mDrawables[i]);
        }
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        MultiTouchViewPager viewPager = (MultiTouchViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new DraweePagerAdapter(arrayList,"ic_loading",ViewPagerActivity.this));
        indicator.setViewPager(viewPager);

    }

}
