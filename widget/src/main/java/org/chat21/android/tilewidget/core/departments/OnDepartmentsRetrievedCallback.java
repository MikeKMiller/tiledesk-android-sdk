package org.chat21.android.tilewidget.core.departments;

import org.chat21.android.tilewidget.core.models.Department;

import java.util.List;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public interface OnDepartmentsRetrievedCallback {

    void onDepartmentsRetrievedSuccess(List<Department> departments);

    void onDepartmentsRetrievedError(Exception e);
}
