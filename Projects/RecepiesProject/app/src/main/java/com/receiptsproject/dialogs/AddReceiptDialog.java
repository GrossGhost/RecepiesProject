package com.receiptsproject.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.receiptsproject.R;

public class AddReceiptDialog extends DialogFragment{
    private Spinner spinner;
    private EditText name;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getDialog().setTitle("Add new bill!");
        View v = getActivity().getLayoutInflater().inflate(R.layout.add_receipt_dialog, null);
        name = v.findViewById(R.id.title_dialog);
        spinner = v.findViewById(R.id.categories);
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = name.getText().toString();
                        String category = spinner.getSelectedItem().toString();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CAMERA_BUTTON);
                        intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_CAMERA));
                        startActivity(intent, null);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        return adb.create();
    }
}
