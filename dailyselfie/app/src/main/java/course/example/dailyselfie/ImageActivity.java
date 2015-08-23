package course.example.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by javikkal on 8/22/2015.
 */
public class ImageActivity extends Activity {
    private String mSelfieFile;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);
        mImageView = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        mSelfieFile = intent.getStringExtra("selfie_path");
    }

    @Override
    protected void onResume() {
        super.onResume();
        File f = new File(mSelfieFile);
        if (f != null && f.exists()){
            SelfiePlaceHolder selfie = new SelfiePlaceHolder(mSelfieFile);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            mImageView.setImageBitmap(selfie.getBitmapPreview(height, width));
        }

    }
}
