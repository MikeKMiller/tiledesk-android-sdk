package org.chat21.android.tilewidget.core.departments;

import android.content.Context;
import android.util.Log;

import org.chat21.android.tilewidget.core.http_manager.HttpManager;
import org.chat21.android.tilewidget.core.http_manager.OnResponseRetrievedCallback;
import org.chat21.android.tilewidget.core.models.Department;

import java.util.List;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class DepartmentsManager {
    private static final String TAG = DepartmentsManager.class.getName();

    private static final String MONGODB_BASE_URL = "https://chat21-api-nodejs.herokuapp.com/";
    private static final String WIDGET_USERNAME = "andrea.leo@f21.it";
    private static final String WIDGET_PASSWORD = "123456";

    private Context context;
    private HttpManager httpManager;
    private String projectId;

    private static DepartmentsManager mInstance;

    private String herokuServiceURL;

    private DepartmentsManager() {
    }

    public static DepartmentsManager getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("instance cannot be null. call start first.");
        }
        return mInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    private void initHttpManager() {
        httpManager = new HttpManager(context);
    }

    public static void start(Context context, Configuration configuration) {

//        Log.d(TAG, "DepartmentsManager.herokuServiceURL: " + herokuServiceURL);

        DepartmentsManager manager = new DepartmentsManager();
        manager.setContext(context);

        mInstance = manager;

        manager.projectId = configuration.projectId;

        manager.initHttpManager();

    }

    public void getDepartments(final OnDepartmentsRetrievedCallback callback) {

        this.herokuServiceURL = MONGODB_BASE_URL + projectId + "/departments";

        httpManager.makeHttpGETCall(new OnResponseRetrievedCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "getDepartments.onSuccess:response: " + response);

                decodeJSONDepartments(response, callback);
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "getDepartments.onSuccess:onError: " + e);

            }
        }, herokuServiceURL, WIDGET_USERNAME, WIDGET_PASSWORD);
    }

    private void decodeJSONDepartments(String encodedDepartments, final OnDepartmentsRetrievedCallback callback) {
        new DecodeJSONDepartmentsTask(new OnDepartmentsDecodedCallback<List<Department>>() {
            @Override
            public void onJSONParsedSuccess(List<Department> decodedDepartments) {
                Log.d(TAG, "decodeJSONDepartments.onJSONParsedSuccess.decodedDepartments: " + decodedDepartments);
//                for (Department department : decodedDepartments) {
//                    Log.d(TAG, "decodeJSONDepartments.onJSONParsedSuccess.department: " + department);
//                }
                callback.onDepartmentsRetrievedSuccess(decodedDepartments);
            }

            @Override
            public void onJSONParsedError(Exception e) {
                Log.e(TAG, "decodeJSONDepartments.onJSONParsedError:" + e);
                callback.onDepartmentsRetrievedError(e);
            }
        }).execute(encodedDepartments);
    }

    public static final class Configuration {

        private static final String TAG = Configuration.class.getName();

        public static String projectId;

        public Configuration(Builder builder) {
            Log.v(TAG, "Configuration constructor called");

            this.projectId = builder.projectId;
        }

        /**
         * Creates a configuration object
         */
        public static final class Builder {
            private static final String TAG = Builder.class.getName();

            private String projectId;

            public Builder(String projectId) {
                Log.d(TAG, "Configuration.Builder: projectId = " + projectId);

                this.projectId = projectId;
            }


            public Configuration build() {
                Log.d(TAG, "Configuration.build");

                return new Configuration(this);
            }
        }
    }
}