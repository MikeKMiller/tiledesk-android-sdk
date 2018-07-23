package org.chat21.android.tilewidget.ui.departments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.chat21.android.tilewidget.R;
import org.chat21.android.tilewidget.ui.departments.activities.DepartmentsListActivity;

/**
 * Created by stefanodp91 on 07/05/18.
 */

public class ContactFormFragment extends Fragment implements TextWatcher {
    private static final String TAG = ContactFormFragment.class.getName();

    private TextInputLayout tilFullname, tilEmail;
    private Button openConversationButton;

    public static Fragment newInstance() {
        Fragment mFragment = new ContactFormFragment();
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "DepartmentsListFragment.onCreateView");
        View view = inflater.inflate(R.layout.fragment_contact_form, container, false);

        // fullname
        tilFullname = view.findViewById(R.id.til_fullname);
        tilFullname.getEditText().addTextChangedListener(this);

        // email
        tilEmail = view.findViewById(R.id.til_email);
        tilEmail.getEditText().addTextChangedListener(this);


        // open conversation button
        openConversationButton = view.findViewById(R.id.open_covnersation_button);
        openConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DepartmentsListActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String fullName, email;

        // check text fields validity
            fullName = tilFullname.getEditText().getText().toString();
            // TODO: 07/05/18 aggiungere lunghezza minima nome
            if (fullName == null || fullName.isEmpty()) {
                tilFullname.setError("Inserisci un nome");
            } else {
                tilFullname.setError(null); // clear the error
            }

            email = tilEmail.getEditText().getText().toString();
            // TODO: 07/05/18 verifica validità email
            if (email == null || email.isEmpty()) {
                tilEmail.setError("Inserisci una email");
            } else {
                tilEmail.setError(null); // clear the error
            }

        // enable/disable the new conversation button
        if (tilFullname.getError() == null && tilEmail.getError() == null &&
                fullName != null && !fullName.isEmpty() && email != null && !email.isEmpty()) {
            openConversationButton.setEnabled(true);
        } else {
            openConversationButton.setEnabled(false);
        }
    }
}
