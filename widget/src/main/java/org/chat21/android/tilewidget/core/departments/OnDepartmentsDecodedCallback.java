package org.chat21.android.tilewidget.core.departments;

/**
 * Created by stefanodp91 on 04/05/18.
 */

interface OnDepartmentsDecodedCallback<T> {

    void onJSONParsedSuccess(T data);

    void onJSONParsedError(Exception e);
}
