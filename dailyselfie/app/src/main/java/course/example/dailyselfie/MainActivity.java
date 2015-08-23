package course.example.dailyselfie;

import android.app.ListActivity;
//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends ListActivity {
    private ListView mListView;
    private ImageListAdapter mAdapter;
    private File savedImagePath;
    private GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mListView = getListView();
        mAdapter = new ImageListAdapter(this);
        setListAdapter(mAdapter);
        SelfieAlarm alarm =new SelfieAlarm(this);
        alarm.setAlarm();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter.getCount() == 0) {
            for(File f:PreviewManager.loadSelfies()){
                mAdapter.add(new SelfiePlaceHolder(f.getAbsolutePath()));
           }
        }
        setupGestureDetector();
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }};
        mListView.setOnTouchListener(gestureListener);

   }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void setupGestureDetector(){
        mGestureDetector = new GestureDetector(this,
                                        new PreviewManager(this,mListView, mAdapter));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // TODO - Delegate the touch to the gestureDetector
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return takeASelfie();
        }

        return super.onOptionsItemSelected(item);
    }
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    public boolean takeASelfie(){
        if (!isCameraPresent()){
            Toast.makeText(this, "No camera. Please enable camera on your emulator",
                        Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fileUri = null;
            try {
                savedImagePath = SelfiePlaceHolder.getOutputMediaFile(SelfiePlaceHolder
                        .MEDIA_TYPE_IMAGE, SelfiePlaceHolder.SelfieFileType.Permanent);
            }catch (IOException e) {
               Toast.makeText(this, "No external storage. Add an external storage ( SD card) on " +
                                    "your emulator",
                            Toast.LENGTH_LONG).show();
            }
            fileUri = Uri.fromFile(savedImagePath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); //
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Log.d("DailySelfieApp", "Image saved to: " +
                        savedImagePath.getAbsolutePath());
                mAdapter.add(new SelfiePlaceHolder(savedImagePath.getAbsolutePath()));
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        mListView.setSelection(mAdapter.getCount() - 1);
                    }
                });

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                Toast.makeText(this, "Image capture failed ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isCameraPresent(){
        boolean isPresent = this.getPackageManager().hasSystemFeature(PackageManager
                .FEATURE_CAMERA_ANY);
        if (isPresent){
            Camera c = null;
            try {
                c = Camera.open(); // attempt to get a Camera instance
            }
            catch (Exception e){
                // Camera is not available (in use or does not exist)
                isPresent = false;
            }
        }
        return isPresent;
    }
}
