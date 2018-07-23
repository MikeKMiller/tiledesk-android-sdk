package org.chat21.android.tilewidget.ui.listeners;

import android.support.v4.app.Fragment;

/**
 * Created by stefanodp91 on 07/05/18.
 */

public interface ContactFormListener {

    void onPreloadContactForm();

    void onContactFormLoaded(Fragment fragment);
}
