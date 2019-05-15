package com.android.template.utils.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import template.android.com.domain.utils.exception.UnimplementedMethodException;

public final class ImageLoaderImpl implements ImageLoader {

    private final Context context;

    public ImageLoaderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(final String url, final ImageView target) {
        Glide.with(context)
             .load(url)
             .into(target);
    }

    @Override
    public void loadImageCenterCrop(final String url, final ImageView target) {
        throw new UnimplementedMethodException();
    }

    @Override
    public void loadImage(final String url, final ImageView target, @DrawableRes final int placeholderDrawable, @DrawableRes final int errorDrawable) {
        throw new UnimplementedMethodException();
    }

    @Override
    public void loadImage(final String url, final ImageView target, final Drawable placeholderDrawable, @DrawableRes final int errorDrawable) {
        throw new UnimplementedMethodException();
    }

    @Override
    public void loadImageAsBitmap(final String url, final ImageView target, final Drawable placeholderDrawable, @DrawableRes final int errorDrawable) {
        throw new UnimplementedMethodException();
    }

    @Override
    public void loadImageForCircularImageView(final String url, final ImageView target, final Drawable placeholderDrawable, @DrawableRes final int errorDrawable) {
        throw new UnimplementedMethodException();
    }
}
