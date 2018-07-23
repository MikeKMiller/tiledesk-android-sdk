package org.chat21.android.tilewidget.core.departments;

import android.os.AsyncTask;
import android.util.Log;

import org.chat21.android.tilewidget.core.models.Department;
import org.chat21.android.tilewidget.core.utils.TimeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefanodp91 on 04/05/18.
 */

class DecodeJSONDepartmentsTask extends AsyncTask<String, List<Department>, List<Department>> {
    private static final String TAG = DecodeJSONDepartmentsTask.class.getName();

    private OnDepartmentsDecodedCallback<List<Department>> callback;

    private Exception exception = null;

    public DecodeJSONDepartmentsTask(OnDepartmentsDecodedCallback<List<Department>> callback) {
        this.callback = callback;
    }

    @Override
    protected List<Department> doInBackground(String... params) {

        String departments = params[0];

        try {
            JSONArray jsonArray = new JSONArray(departments);
            return decode(jsonArray);
        } catch (JSONException e) {
            exception = e;
            Log.e(TAG, "DecodeJSONDepartmentsTask.doInBackground: cannot parse jsonArray: " + e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Department> departments) {
//        super.onPostExecute(department);

        if (departments != null && exception == null) {
            callback.onJSONParsedSuccess(departments);
        } else {
            if (exception == null) {
                callback.onJSONParsedError(new Exception("generic error on parsing departments"));
            } else {
                callback.onJSONParsedError(exception);
            }
        }
    }

    private List<Department> decode(JSONArray jsonArray) {
        List<Department> departments = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonDepartment = null;
            try {
                jsonDepartment = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve jsonDepartment: " + e);
                exception = e;
            }

            if (jsonDepartment != null) {
//                "_id":"5ae1a39b86724100146e1e4e",
//                "updatedAt":"2018-04-26T10:07:01.215Z",
//                "createdAt":"2018-04-26T10:02:03.179Z",
//                "name":"Default Department",
//                "id_project":"5ae1a39b86724100146e1e4c",
//                "createdBy":"5ae1a38b86724100146e1e4b",
//                "__v":0,
//                "id_bot":null,
//                "default":true,
//                "routing":"assigned"

                Department department = new Department();

                // id
                try {
                    String id = jsonDepartment.getString("_id");
                    department.setId(id);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve id: " + e);
                }

                // updatedAt
                try {
                    String updatedAt = jsonDepartment.getString("updatedAt");

                    try {
                        long date = TimeUtils.timeToMillisTimestamp(updatedAt);
                        department.setUpdatedAt(date);
                    } catch (ParseException e) {
                        Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot parse updatedAt: " + e);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve updatedAt: " + e);
                }

                // createdAt
                try {
                    String createdAt = jsonDepartment.getString("createdAt");

                    try {
                        long date = TimeUtils.timeToMillisTimestamp(createdAt);
                        department.setCreatedAt(date);
                    } catch (ParseException e) {
                        Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot parse createdAt: " + e);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve createdAt: " + e);
                }

                // name
                try {
                    String name = jsonDepartment.getString("name");
                    department.setName(name);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve name: " + e);
                }

                // projectId
                try {
                    String projectId = jsonDepartment.getString("id_project");
                    department.setProjectId(projectId);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve projectId: " + e);
                }

                // createdBy
                try {
                    String createdBy = jsonDepartment.getString("createdBy");
                    department.setCreatedBy(createdBy);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve createdBy: " + e);
                }

                // botId
                try {
                    String botId = jsonDepartment.getString("id_bot");
                    department.setBotId(botId);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve botId: " + e);
                }

                // defaultDepartment
                try {
                    String defaultDepartment = jsonDepartment.getString("default");
                    department.setDefaultDepartment(defaultDepartment);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve defaultDepartment: " + e);
                }

                // routing
                try {
                    String routing = jsonDepartment.getString("routing");
                    department.setRouting(routing);
                } catch (JSONException e) {
                    Log.e(TAG, "DecodeJSONDepartmentsTask.decode: cannot retrieve routing: " + e);
                }

                // add the current department to the department list
                departments.add(department);
            } else {
                Log.e(TAG, "DecodeJSONDepartmentsTask.decode: jsonDepartment is null");
                exception = new Exception("jsonDepartment is null");
            }
        }

        return departments;
    }
}
