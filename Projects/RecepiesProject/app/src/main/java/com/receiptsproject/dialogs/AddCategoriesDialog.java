package com.receiptsproject.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.receiptsproject.R;
import com.receiptsproject.objects.CategoriesData;

import io.realm.Realm;


public class AddCategoriesDialog extends android.app.DialogFragment implements View.OnClickListener {

    private EditText editCategoryText;
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle("Enter name of new category");
        View view = inflater.inflate(R.layout.categories_dialog, null);
        view.findViewById(R.id.button_cancel_category).setOnClickListener(this);
        view.findViewById(R.id.button_add_category).setOnClickListener(this);
        editCategoryText = view.findViewById(R.id.edit_text_new_category);
        editCategoryText.setText("");
        realm = Realm.getDefaultInstance();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_cancel_category :
                dismiss();
                break;
            case R.id.button_add_category :
                String newCategoryName = editCategoryText.getText().toString();

                if (newCategoryName.equals(""))
                    Toast.makeText(getActivity(),"Enter Category name!", Toast.LENGTH_SHORT).show();
                else if(realm.where(CategoriesData.class).contains("name", newCategoryName).findAll().size() != 0) {
                    Toast.makeText(getActivity(),"Category name already exist!", Toast.LENGTH_SHORT).show();
                }
                else{
                    CategoriesData newCategory = new CategoriesData();
                    newCategory.setName(newCategoryName);

                    realm.beginTransaction();
                    realm.insert(newCategory);
                    realm.commitTransaction();

                    dismiss();
                }
                break;

        }
    }
}
