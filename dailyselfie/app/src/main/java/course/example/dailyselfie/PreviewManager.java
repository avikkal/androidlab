package course.example.dailyselfie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PreviewManager extends GestureDetector.SimpleOnGestureListener {
    private ListView mPreviewList;
    private ImageListAdapter mListAdapter;
    private Context mContext;
    private static final int MIN_SWIPE_DISTANCE_FOR_REMOVE = 150;
    private static final int MIN_SWIPE_VELOCITY_FOR_REMOVE = 200;

    public PreviewManager(Context context, ListView list, ImageListAdapter listAdapter) {
        mPreviewList = list;
        mListAdapter = listAdapter;
        mContext = context;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        super.onSingleTapConfirmed(e);
        int pos = mPreviewList.pointToPosition((int)e.getX(), (int)e.getY());
        SelfiePlaceHolder s = (SelfiePlaceHolder)mListAdapter.getItem(pos);
        if (s != null) {
            Intent imageIntent = new Intent(mContext, ImageActivity.class);
            imageIntent.putExtra("selfie_path", s.getFullImagePath());
            mContext.startActivity(imageIntent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        super.onDoubleTap(e);
        int pos = mPreviewList.pointToPosition((int)e.getX(), (int)e.getY());
        SelfiePlaceHolder s = (SelfiePlaceHolder)mListAdapter.getItem(pos);
        if (s != null) {
            Intent imageIntent = new Intent(mContext, ImageActivity.class);
            imageIntent.putExtra("selfie_path", s.getFullImagePath());
            mContext.startActivity(imageIntent);
            return true;
        }
        return false;
    }

    //CONDITIONS ARE TYPICALLY VELOCITY OR DISTANCE
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //if (INSERT_CONDITIONS_HERE)
        if(e2.getX() - e1.getX() > MIN_SWIPE_DISTANCE_FOR_REMOVE && Math.abs(velocityX) >
                MIN_SWIPE_VELOCITY_FOR_REMOVE) {
            if (deleteImage(e1)) {
                return true;
            }
        }
        else
        {
            Log.d("DailySlfieApp", "Not a flig from Left to Right");
        }
        return false;

        //return super.onFling(e1, e2, velocityX, velocityY);
    }

    private boolean deleteImage(MotionEvent e1) {
        int pos = mPreviewList.pointToPosition((int)e1.getX(), (int)e1.getY());
        mListAdapter.remove(pos);
        return false;
    }

    private void  showImageInfullScreen(int itemPosition){
        SelfiePlaceHolder item = (SelfiePlaceHolder)    mListAdapter.getItem(itemPosition);
        if (item != null){
            String imagePath = item.getFullImagePath();
        }
    }

    public static ArrayList<File>loadSelfies(){

        ArrayList<File> selfies = new ArrayList<>();
        try {
            File selfesRoot = SelfiePlaceHolder.getRootMediaDir();
            for (File f : selfesRoot.listFiles()){
                if (f.length() > 0 ){
                    selfies.add(f);
                }
            }

        }catch (IOException e){

        }
        return selfies;
    }

}