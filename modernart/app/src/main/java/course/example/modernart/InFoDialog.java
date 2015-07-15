package course.example.modernart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * Created by javikkal on 7/15/2015.
 */

public class InFoDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Resources res = getResources();
        String msg = res.getString(R.string.dialog_caption) + "\n\n" + res.getString(R.string.click_here_msg);
        builder.setMessage(msg)
                .setPositiveButton(R.string.visit_moma, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }

}