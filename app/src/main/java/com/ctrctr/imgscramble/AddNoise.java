package com.ctrctr.imgscramble;

import android.graphics.Bitmap;

import java.util.Random;

/**
 * Author:  xukai
 * Package: com.example.watermarker.ImageTool
 * FileName add_noise
 * Date on 2018/3/20 16:44
 * Explain:
 */

public class AddNoise {
    public final static double MEAN_FACTOR = 2.0;
    public final static int POISSON_NOISE_TYPE = 2;
    public final static int GAUSSION_NOISE_TYPE = 1;
    public final static  double _mNoiseFactor = 25;




    //  添加椒盐噪音
    static Bitmap addSaltAndPepperNoise(Bitmap bitmap, float SNR) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap spBitmap = bitmap.copy(bitmap.getConfig(), true);
        int size = (int) (width * height * (1 - SNR));

        for (int i = 0; i < size; ++i) {
            int row = (int)(Math.random() * height);
            int col = (int)(Math.random() * width);
            spBitmap.setPixel(col, row, (255 << 24) | (255 << 16) | (255 << 8) | 255);
        }

        return spBitmap;
    }
    //  添加泊松噪音
   /* static Bitmap addPoissonNoise(Bitmap bitmap) {
        return filter(bitmap, POISSON_NOISE_TYPE);
    }*/

    //  添加高斯噪音
    public Bitmap addGaussin(Bitmap bitmap, double ratio) {
        return filter(bitmap, ratio, GAUSSION_NOISE_TYPE);
    }

    static Bitmap filter(Bitmap src, double ratio, int noiseType) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap nBitmap = src.copy(src.getConfig(), true);
        Random random = new Random();
        int ta, tr, tg, tb;

        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++) {
                ta = (nBitmap.getPixel(col, row) >> 24) & 0xff;
                tr = (nBitmap.getPixel(col, row) >> 16) & 0xff;
                tg = (nBitmap.getPixel(col, row) >> 8) & 0xff;
                tb = nBitmap.getPixel(col, row) & 0xff;
                if(noiseType == POISSON_NOISE_TYPE) {
                    tr = clamp(addPNoise(tr, random));
                    tg = clamp(addPNoise(tg, random));
                    tb = clamp(addPNoise(tb, random));
                } else if(noiseType == GAUSSION_NOISE_TYPE) {
                    tr = clamp(addGNoise(tr, random, ratio));
                    tg = clamp(addGNoise(tg, random, ratio));
                    tb = clamp(addGNoise(tb, random, ratio));
                }
                nBitmap.setPixel(col, row, (ta << 24) | (tr << 16) | (tg << 8) | tb);
            }
        }

        return nBitmap;
    }



    static int addGNoise(int tr, Random random, double ratio) {
        int v, ran;
        boolean inRange = false;
        do {
            ran = (int)Math.round(random.nextGaussian()*ratio);
            v = tr + ran;
            // check whether it is valid single channel value
            inRange = (v>=0 && v<=255);
            if (inRange) tr = v;
        } while (!inRange);
        return tr;
    }

    private static int clamp(int p) {
        return p > 255 ? 255 : (p < 0 ? 0 : p);
    }

    static int addPNoise(int pixel, Random random) {
        // init:
        double L = Math.exp(-_mNoiseFactor * MEAN_FACTOR);
        int k = 0;
        double p = 1;
        do {
            k++;
            // Generate uniform random number u in [0,1] and let p ← p × u.
            p *= random.nextDouble();
        } while (p >= L);
        double retValue = Math.max((pixel + (k - 1) / MEAN_FACTOR - _mNoiseFactor), 0);
        return (int)retValue;
    }

}