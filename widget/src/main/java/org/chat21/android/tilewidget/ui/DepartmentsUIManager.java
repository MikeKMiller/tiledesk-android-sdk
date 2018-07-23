package org.chat21.android.tilewidget.ui;

import android.content.Context;
import android.util.Log;

import org.chat21.android.tilewidget.core.departments.DepartmentsManager;
import org.chat21.android.tilewidget.ui.departments.listeners.OnDepartmentClickListener;
import org.chat21.android.tilewidget.ui.listeners.ContactFormListener;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class DepartmentsUIManager {
    private static final String TAG = DepartmentsUIManager.class.getName();

    private Context context;
    private OnDepartmentClickListener onDepartmentClickListener;
    private ContactFormListener contactFormListener;

    private static volatile DepartmentsUIManager instance;

    private DepartmentsUIManager() {

        context = DepartmentsManager.getInstance().getContext();

        // Prevent form the reflection api.
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static DepartmentsUIManager getInstance() {
        if (instance == null) { //if there is no instance available... create new one
            synchronized (DepartmentsUIManager.class) {
                if (instance == null) instance = new DepartmentsUIManager();
            }
        }

        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public OnDepartmentClickListener getOnDepartmentClickListener() {
        Log.d(TAG, "DepartmentsUIManager.getOnDepartmentClickListener");
        return onDepartmentClickListener;
    }

    public void setOnDepartmentClickListener(OnDepartmentClickListener onDepartmentClickListener) {
        Log.d(TAG, "DepartmentsUIManager.setOnMessageClickListener");
        this.onDepartmentClickListener = onDepartmentClickListener;
    }

    public ContactFormListener getContactFormListener() {
        Log.d(TAG, "DepartmentsUIManager.getContactFormListener");
        return contactFormListener;
    }

    public void setContactFormListener(ContactFormListener contactFormListener) {
        Log.d(TAG, "DepartmentsUIManager.setContactFormListener");
        this.contactFormListener = contactFormListener;
    }
}
