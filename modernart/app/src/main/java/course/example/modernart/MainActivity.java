package course.example.modernart;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity {

    SeekBar mSeekBar;
    TextView mGreenTextView3;
    TextView mBlueTextView4;
    TextView mYellowTextView;
    TextView mPinkTextView5;

    private  static String PROGRESS = "PROGRESS";
    public static String TAG = "MODERNART";
    int mGreenTextView3Color, mGreenTextView3ColorBase ;
    int mBlueTextView4Color, mBlueTextView4ColorBase;
    int mYellowTextViewColor, mYellowTextViewColorBase;
    int mPinkTextView5Color, mPinkTextView5ColorBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mSeekBar.setProgress(0);
        mSeekBar.setMax(256);

        mGreenTextView3 = (TextView) findViewById(R.id.textView3);
        mBlueTextView4 = (TextView) findViewById(R.id.textView4);
        mYellowTextView = (TextView) findViewById(R.id.textView);
        mPinkTextView5 = (TextView) findViewById(R.id.textView5);

        mGreenTextView3ColorBase = mGreenTextView3Color = ((ColorDrawable)mGreenTextView3.getBackground()).getColor();
        mBlueTextView4ColorBase = mBlueTextView4Color = ((ColorDrawable)mBlueTextView4.getBackground()).getColor();
        mYellowTextViewColorBase = mYellowTextViewColor = ((ColorDrawable)mYellowTextView.getBackground()).getColor();
        mPinkTextView5ColorBase = mPinkTextView5Color = ((ColorDrawable)mPinkTextView5.getBackground()).getColor();

        mSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mGreenTextView3.setBackgroundColor(getViewColor(mGreenTextView3ColorBase, progress));
                        mBlueTextView4.setBackgroundColor(getViewColor(mBlueTextView4ColorBase, progress));
                        mYellowTextView.setBackgroundColor(getViewColor(mYellowTextViewColorBase, progress));
                        mPinkTextView5.setBackgroundColor(getViewColor(mPinkTextView5ColorBase, progress));

                        if (progress == 0)
                        {
                            mGreenTextView3.setBackgroundColor(mGreenTextView3ColorBase);
                            mBlueTextView4.setBackgroundColor(mBlueTextView4ColorBase);
                            mYellowTextView.setBackgroundColor(mYellowTextViewColorBase);
                            mPinkTextView5.setBackgroundColor(mPinkTextView5ColorBase);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );

        if (savedInstanceState != null) {
            mSeekBar.setProgress(savedInstanceState.getInt("PROGRESS"));
            Log.d(MainActivity.TAG, "Restore in onCreate") ;
        }

    }

    public void onResume(){
     super.onResume();
    }

    public  void  onPause(){
        super.onPause();
    }

    public void onSavedInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        if (bundle != null)
        {
            bundle.putInt(MainActivity.PROGRESS, mSeekBar.getProgress());
        }
        Log.d(MainActivity.TAG, "onSavedInstanceState");
    }

    public  void onRestoreState(Bundle bundle){
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            mSeekBar.setProgress(bundle.getInt("PROGRESS"));
        }
        Log.d(MainActivity.TAG, "onRestoreState");

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

            InFoDialog inFoDialog = new InFoDialog();
            inFoDialog.show(getFragmentManager(), "Show");
        }

        return super.onOptionsItemSelected(item);
    }

    private int getViewColor(int curColor, int slider){
        int red = Color.red(curColor);
        int blue = Color.blue(curColor);
        int green = Color.green(curColor);
        int alpha = Color.alpha(curColor);

        return Color.argb(alpha, getTint(red, slider),
                               getTint(blue, slider),
                               getTint(green, slider) );
    }

    private  int getTint(int primary, int slider)
    {
         //return  Math.abs((255 - primary))*slider/10;
        return  Math.abs(primary - slider);
    }
}
