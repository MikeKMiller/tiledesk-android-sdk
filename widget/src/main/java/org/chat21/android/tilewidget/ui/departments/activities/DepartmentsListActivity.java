package org.chat21.android.tilewidget.ui.departments.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.chat21.android.tilewidget.R;
import org.chat21.android.tilewidget.ui.DepartmentsUIManager;
import org.chat21.android.tilewidget.ui.departments.fragments.DepartmentsListFragment;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class DepartmentsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments_list);

        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (DepartmentsUIManager.getInstance().getContactFormListener() != null) {
            DepartmentsUIManager.getInstance().getContactFormListener().onPreloadContactForm();
        }

        Fragment fragment = new DepartmentsListFragment();

        // container
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        if (DepartmentsUIManager.getInstance().getContactFormListener() != null) {
            DepartmentsUIManager.getInstance().getContactFormListener()
                    .onContactFormLoaded(fragment);
        }
    }
}