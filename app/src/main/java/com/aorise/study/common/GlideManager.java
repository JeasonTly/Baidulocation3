package com.aorise.study.common;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideManager {

    private static GlideManager ourInstance = new GlideManager();

    public static GlideManager getInstance() {
        return ourInstance;
    }

    private GlideManager() {
    }

    public <T> void load(Context context, ImageView view, T uri, @DrawableRes int placeholder, @DrawableRes int error) {
        Glide.with(context).load(uri).apply(new RequestOptions().error(error).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(view);
    }
    public <T> void loadImage(Context context, ImageView view, T uri, @DrawableRes int placeholder, @DrawableRes int error) {
        Glide.with(context).load(uri).apply(new RequestOptions().error(error).override(Target.SIZE_ORIGINAL).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)).into(view);
    }
}