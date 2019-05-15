package com.android.template.utils.ui;

import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImage(String url, ImageView target);

    void loadImageCenterCrop(String url, ImageView target);

    void loadImage(String url, ImageView target, @DrawableRes int placeholderDrawable, @DrawableRes int errorDrawable);

    void loadImage(String url, ImageView target, Drawable placeholderDrawable, @DrawableRes int errorDrawable);

    void loadImageAsBitmap(String url, ImageView target, Drawable placeholderDrawable, @DrawableRes int errorDrawable);

    void loadImageForCircularImageView(String url, ImageView target, Drawable placeholderDrawable, @DrawableRes int errorDrawable);
}
