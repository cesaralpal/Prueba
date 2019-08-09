package com.polimentes.utilitieskotlin.images;

import android.graphics.Bitmap;


public interface IImageManager {
    void onSuccesfulConvertion(Bitmap image);

    void onSuccesfulConvertion(byte[] image);

    void onSuccesfulConvertion(byte[] image, String imageEncoded);
}
