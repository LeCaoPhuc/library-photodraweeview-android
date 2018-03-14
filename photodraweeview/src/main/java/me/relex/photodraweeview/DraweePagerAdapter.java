package me.relex.photodraweeview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.ArrayList;

/**
 * Created by caophuc on 3/14/18.
 */

public class DraweePagerAdapter extends PagerAdapter {

    private ArrayList mDrawables;
    private String imageError;
    private Context mainContext;
    public DraweePagerAdapter(ArrayList mDrawables, String errorImage, Context context) {
        this.mDrawables = mDrawables;
        this.imageError = errorImage;
        this.mainContext = context;
    }
    @Override public int getCount() {
        return mDrawables.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override public Object instantiateItem(ViewGroup viewGroup, int position) {
        final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
        final ProgressBar progressBar = new ProgressBar(viewGroup.getContext());
        progressBar.setBackgroundColor(Color.argb(1,255,255,255));
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams layoutParams =  new FrameLayout.LayoutParams(200, 200);
        layoutParams.gravity = Gravity.CENTER;
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        int resourceId = 0;
        resourceId = this.mainContext.getResources().getIdentifier(this.imageError, "drawable", this.mainContext.getPackageName());
        if(resourceId != 0) {
            photoDraweeView.getHierarchy().setFailureImage(this.mainContext.getResources().getDrawable(resourceId),ScalingUtils.ScaleType.FIT_CENTER);
        }
        else {
            Log.e("resourceId :" , "not existed with name : " + this.imageError)  ;
        }
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse((String)mDrawables.get(position)));
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                progressBar.setVisibility(View.GONE);
                Log.i("onFailure", "onFailure load image");
            }

            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                progressBar.setVisibility(View.GONE);
                Log.i("onFinalImageSet", "onFinalImageSet load image");
                photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoDraweeView.setVisibility(View.VISIBLE);
        photoDraweeView.setController(controller.build());

        try {
            frameLayout.addView(progressBar,layoutParams);
            frameLayout.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            viewGroup.addView(frameLayout, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return frameLayout;
    }
}