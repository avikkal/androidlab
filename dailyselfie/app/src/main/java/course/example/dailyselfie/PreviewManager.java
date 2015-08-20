package course.example.dailyselfie;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PreviewManager extends GestureDetector.SimpleOnGestureListener {
    private ListView mPreviewList;

    public PreviewManager(ListView list) {
        mPreviewList = list;
    }

    //CONDITIONS ARE TYPICALLY VELOCITY OR DISTANCE
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //if (INSERT_CONDITIONS_HERE)
            if (showDeleteButton(e1))
                return true;
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private boolean showDeleteButton(MotionEvent e1) {
        int pos = mPreviewList.pointToPosition((int)e1.getX(), (int)e1.getY());
        return showDeleteButton(pos);
    }

    private boolean showDeleteButton(int pos) {
        View child = mPreviewList.getChildAt(pos);
        if (child != null){
            //Button delete = (Button) child.findViewById(R.id.delete_button_id);
            // if (delete != null)
            //    if (delete.getVisibility() == View.INVISIBLE)
            //        delete.setVisibility(View.VISIBLE);
            //   else
            //        delete.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }
}