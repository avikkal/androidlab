package course.example.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javikkal on 8/20/2015.
 */
public class SelfiePlaceHolder {
    private String mImageName;
    private String mImageFolder;
    private String mFullImagePath;
    private Bitmap mPreviewBitmap;


    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    public SelfiePlaceHolder(String fullPath) {
        if (!fullPath.isEmpty()) {
            if (fullPath.lastIndexOf(File.separatorChar) > 0 ) {
                mImageName = fullPath.substring(fullPath.lastIndexOf(File.separatorChar) + 1);

                mFullImagePath = fullPath;
                if (fullPath.lastIndexOf(mImageName) > 0 ) {
                    mImageFolder = fullPath.substring(0,fullPath.lastIndexOf(mImageName));
                }
            }
            else
            {
                mImageName = fullPath;
            }
        }
        //mPreviewBitmap = previewBitmap;

    }

    public String getImageName() {
        return mImageName;
    }

    public String getImageFolder() {
        return mImageFolder;
    }

    public String getFullImagePath() {
        return mFullImagePath;
    }

    public Bitmap getBitmapPreview(int height, int width) {
        if (height == 0 ){

        }
        if (mPreviewBitmap == null){
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mFullImagePath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            int scaleFactor = 1;
            // Determine how much to scale down the image
            if (height < photoH && width < photoW  ) {
                scaleFactor = Math.min(photoW / width, photoH / height);
            }


            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            mPreviewBitmap = BitmapFactory.decodeFile(mFullImagePath, bmOptions);
        }
        return mPreviewBitmap;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    public static Uri getOutputMediaFileUri(int type) throws  IOException{
        Uri uri = null;
        File fileUri = getOutputMediaFile(type, SelfieFileType.Permanent);
        if (fileUri != null) {
            uri = Uri.fromFile(fileUri);
        }
        return uri;
    }

    public static Uri getTempOutputMediaFileUri(int type) throws IOException{
        Uri uri = null;
        File fileUri = getOutputMediaFile(type, SelfieFileType.Temp);
        if (fileUri != null) {
            uri = Uri.fromFile(fileUri);
        }
        return uri;
    }

    public static File getRootMediaDir() throws IOException{
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = null;
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {

            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "DailySelfieApp");
            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("DailySelfieApp", "failed to create directory");
                    return null;
                }
            }
        }
        else {
                throw new IOException("SD Card not mounted yet");
        }
        return mediaStorageDir;
    }

    public static String getTempImageFullPath() throws IOException{
        File mediaStorageDir = SelfiePlaceHolder.getRootMediaDir();
        String tmpPath = null;
        if (mediaStorageDir != null) {
            File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                       "IMG_TMP.jpg");
            tmpPath = mediaFile.getPath();
        }
        return tmpPath;

    }

    /** Create a File for saving an image or video */
    public static File getOutputMediaFile(int type, SelfieFileType selfieFileType ) throws IOException{

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            if (selfieFileType == SelfieFileType.Permanent) {
                File mediaStorageDir = SelfiePlaceHolder.getRootMediaDir();
                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                        "IMG_" + timeStamp + ".jpg");
            }else{
                mediaFile = new File(SelfiePlaceHolder.getTempImageFullPath());
            }
        } else {
            return null;
        }

        return mediaFile;
    }

    public enum SelfieFileType {Temp, Permanent};
}


