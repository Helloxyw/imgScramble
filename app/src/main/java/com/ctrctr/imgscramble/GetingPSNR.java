package com.ctrctr.imgscramble;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class GetingPSNR {
    private Bitmap oriBitmap;
    private Bitmap endBitmap;

    public GetingPSNR(Bitmap oriBitmap, Bitmap endBitmap){

        this.oriBitmap = oriBitmap;
        this.endBitmap = endBitmap;
    }

    public double getPSNR(){

        double PSNR;

        int oWidth = oriBitmap.getWidth();
        int oHeight = oriBitmap.getHeight();
        int endWidth = endBitmap.getWidth();
        int endHeight = endBitmap.getHeight();

        int[][] ori_pixel_r = new int[oWidth][oHeight];
        int[][] ori_pixel_g = new int[oWidth][oHeight];
        int[][] ori_pixel_b = new int[oWidth][oHeight];

        int[][] end_pixel_r = new int[oWidth][oHeight];
        int[][] end_pixel_g = new int[oWidth][oHeight];
        int[][] end_pixel_b = new int[oWidth][oHeight];

        int ori_pixel;
        for (int i = 0; i < oWidth; i++) {
            for (int j = 0; j < oHeight; j++) {
                ori_pixel = oriBitmap.getPixel(i, j);
                ori_pixel_r[i][j] = Color.red(ori_pixel);
                ori_pixel_g[i][j] = Color.green(ori_pixel);
                ori_pixel_b[i][j] = Color.blue(ori_pixel);

            }
        }

        int end_pixel;
        for (int i = 0; i < endWidth; i++) {
            for (int j = 0; j < endHeight; j++) {
                end_pixel = endBitmap.getPixel(i, j);
                end_pixel_r[i][j] = Color.red(end_pixel);
                end_pixel_g[i][j] = Color.green(end_pixel);
                end_pixel_b[i][j] = Color.blue(end_pixel);

            }
        }


        double sum_red = 0.0;
        double sum_green = 0.0;
        double sum_blue = 0.0;
        for (int i = 0; i < oWidth; i++){
            for (int j = 0; j < oHeight; j++){
                sum_red += Math.pow((ori_pixel_r[i][j] - end_pixel_r[i][j]), 2);
                sum_green += Math.pow((ori_pixel_g[i][j] - end_pixel_g[i][j]), 2);
                sum_blue += Math.pow((ori_pixel_b[i][j] - end_pixel_b[i][j]), 2);

            }
        }
        Log.d("Debug", "sum: "+sum_red+","+sum_green+","+sum_blue);

        for (int i = 0;i < 8;i++){
            for (int j = 0; j < 8; j++){
                System.out.print(ori_pixel_b[i][j]+",");

            }
            System.out.println();
        }

        System.out.println("嵌入后");
        for (int i = 0;i < 8;i++){
            for (int j = 0; j < 8; j++){
                System.out.print(end_pixel_b[i][j]+",");
            }
            System.out.println();
        }
        double dishu = oWidth * oHeight;
        // 求每个通道的MSE值
        double MSE_red = sum_red / (dishu);
        Log.d("debug", "getRedMSE: " + MSE_red);
        double MSE_green = sum_green / (dishu);
        Log.d("debug", "getGreenMSE: " + MSE_green);
        double MSE_blue = sum_blue / (dishu);
        Log.d("debug", "getBlueMSE: " + MSE_blue);

        //求平均MSE
        double sum = MSE_red + MSE_green + MSE_blue;
        double average_MSE = sum / 3.0;
        Log.d("debug", "getPSNR: " + average_MSE);
        //得到指数
        double index = Math.pow(255, 2) / average_MSE;

        //计算PSNR
        PSNR = 10 * (Math.log(index) / Math.log(10));

        return PSNR;
    }
}
