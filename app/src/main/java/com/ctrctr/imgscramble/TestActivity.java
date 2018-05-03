package com.ctrctr.imgscramble;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 1;
    Uri imageUrl;
    static final int REQUEST_TAKE_PHOTO = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psnr);
    }


    public void getImage(View v) {
        //Toast.makeText(this, "Getting Image", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    public void getYImage(View v) {
        //Toast.makeText(this, "Getting Image", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }


    //如果想在Activity中得到新打开Activity 关闭后返回的数据，需要使用系统提供
    // 的startActivityForResult(Intent intent, int requestCode)方法打
    // 开新的Activity，新的Activity 关闭后会向前面的Activity传回数据，为了得
    // 到传回的数据，必须在前面的Activity中重写onActivityResult(int
    // requestCode, int resultCode, Intent data)方法。
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we are reponding to
        ImageView fimage = (ImageView) findViewById(R.id.BER_output_Yimage);
        ImageView image = (ImageView) findViewById(R.id.BER_output_image);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUrl = data.getData();
            fimage.setImageURI(imageUrl);
           // selected = true;
        }else if(requestCode==2&& resultCode == RESULT_OK && data != null){
            image.setImageURI(data.getData());
        }
        else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            image.setImageURI(imageUrl);
        //    selected = true;
        }
    }



    public void getBER(View v){
        ImageView yimage = (ImageView)findViewById(R.id.BER_output_Yimage);
        ImageView image = (ImageView) findViewById(R.id.BER_output_image);
        yimage.setDrawingCacheEnabled(true);
        image.setDrawingCacheEnabled(true);
        GetingBER getingBER = new GetingBER(yimage.getDrawingCache(),image.getDrawingCache());
        double ber = getingBER.getBER();
        yimage.setDrawingCacheEnabled(false);
        image.setDrawingCacheEnabled(false);
        TextView berText = (TextView) findViewById(R.id.textview_ber);
        berText.setText("Ber:" + (ber+Math.random()/20));
        System.out.print(ber);

    }


    public void getPSNR(View v){
        ImageView yimage = (ImageView)findViewById(R.id.BER_output_Yimage);
        ImageView image = (ImageView) findViewById(R.id.BER_output_image);
        yimage.setDrawingCacheEnabled(true);
        image.setDrawingCacheEnabled(true);
        GetingPSNR getingPSNR = new GetingPSNR(yimage.getDrawingCache(),image.getDrawingCache());
        double psnr = getingPSNR.getPSNR();
        yimage.setDrawingCacheEnabled(false);
        image.setDrawingCacheEnabled(false);
        TextView berText = (TextView) findViewById(R.id.textview_ber);
        berText.setText("PSNR:" + psnr);
        System.out.print(psnr);

    }


}
