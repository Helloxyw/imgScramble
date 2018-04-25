package com.ctrctr.imgscramble;

import android.graphics.Bitmap;
import android.util.Log;

public class GetingBER {

    private Bitmap resultBitmap;
    private Bitmap watermarkBitmap;

    public GetingBER(Bitmap resultBitmap, Bitmap watermarkBitmap) {
        this.resultBitmap = resultBitmap;
        this.watermarkBitmap = watermarkBitmap;
    }

    public double getBER() {

        int oWidth = watermarkBitmap.getWidth();
        int oHeight = watermarkBitmap.getHeight();
        int endWidth = resultBitmap.getWidth();
        int endHeight = resultBitmap.getHeight();


        int ori_pixel;
        int end_pixel;
        int stamp = 0;
        for (int i = 0; i < oWidth; i++) {
            for (int j = 0; j < oHeight; j++) {
                ori_pixel = watermarkBitmap.getPixel(i, j);
                end_pixel = resultBitmap.getPixel(i, j);
                if (ori_pixel != end_pixel)
                    stamp++;

            }
        }
        Log.d("debug", "stamp: " + stamp);


        //计算错位率,返回为百分之
        double dishu = oWidth * oHeight;
        Log.d("debug", "dishu: " + dishu);
        double BER = stamp / dishu;
        Log.d("debug:", "getBER:" + BER);
        return BER * 100;
    }
}
