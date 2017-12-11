package com.receiptsproject.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.receiptsproject.R;

public class CodeInfoDialog extends android.support.v4.app.DialogFragment implements View.OnClickListener {

    TextView textView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Information");
        View view = inflater.inflate(R.layout.dialog_code_info, null);

        view.findViewById(R.id.button_dialog_ok).setOnClickListener(this);

        textView = view.findViewById(R.id.tv_code_info);
        String code = getArguments().getString("code");
        assert code != null;
        if (code.equals("Not Shared"))
            textView.setText(R.string.not_shared_info);
        else
            textView.setText(getResources().getString(R.string.code_info, code));
        return view;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
