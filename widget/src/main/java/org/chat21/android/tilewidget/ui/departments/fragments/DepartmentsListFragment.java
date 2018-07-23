package org.chat21.android.tilewidget.ui.departments.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.chat21.android.tilewidget.R;
import org.chat21.android.tilewidget.core.departments.DepartmentsManager;
import org.chat21.android.tilewidget.core.departments.OnDepartmentsRetrievedCallback;
import org.chat21.android.tilewidget.core.models.Department;
import org.chat21.android.tilewidget.ui.DepartmentsUIManager;
import org.chat21.android.tilewidget.ui.departments.adapters.DepartmentsListAdapter;
import org.chat21.android.tilewidget.ui.departments.listeners.OnDepartmentClickListener;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stefanodp91 on 04/05/18.
 */

public class DepartmentsListFragment extends Fragment implements OnDepartmentClickListener {

    private static final String TAG = DepartmentsListFragment.class.getName();

    private static final String BUNDLE_DEPARTMENTS = DepartmentsListFragment.class.getName() + "_KEY_DEPARTMENTS";

    private RecyclerView rvDepartments;
    private LinearLayoutManager llmDepartments;
    private DepartmentsListAdapter departmentsListAdapter;
    private List<Department> mDepartments;

    public static Fragment newInstance(List<Department> departmentList) {
        Fragment mFragment = new DepartmentsListFragment();

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_DEPARTMENTS, (Serializable) departmentList);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDepartments();

    }

    private void getDepartments() {
        if (getArguments() != null && getArguments().getSerializable(BUNDLE_DEPARTMENTS) != null) {
            mDepartments = (List<Department>) getArguments().getSerializable(BUNDLE_DEPARTMENTS);
            Log.d(TAG, "DepartmentsListFragment.getDepartments.departments: " + mDepartments);
            updateUI(mDepartments);
        } else {
            // retrieve the departments list
            DepartmentsManager.getInstance().getDepartments(new OnDepartmentsRetrievedCallback() {
                @Override
                public void onDepartmentsRetrievedSuccess(List<Department> departments) {
                    Log.d(TAG, "DepartmentsListFragment.getDepartments.onDepartmentsRetrievedSuccess.departments: " + departments);

                    mDepartments = departments;
                    updateUI(mDepartments);
                }

                @Override
                public void onDepartmentsRetrievedError(Exception e) {
                    Log.d(TAG, "DepartmentsListFragment.getDepartments.onDepartmentsRetrievedError: " + e);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "DepartmentsListFragment.onCreateView");
        View view = inflater.inflate(R.layout.fragment_departments_list, container, false);

        // init RecyclerView
        rvDepartments = view.findViewById(R.id.departments_list);
        rvDepartments.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        llmDepartments = new LinearLayoutManager(getActivity());
        rvDepartments.setLayoutManager(llmDepartments);

        // init RecyclerView adapter
        updateUI(mDepartments);

        return view;
    }

    private void updateUI(List<Department> departments) {
        if (departmentsListAdapter == null) {
            departmentsListAdapter = new DepartmentsListAdapter(getActivity(), departments);
            departmentsListAdapter.setOnDepartmentClickListener(this);
            rvDepartments.setAdapter(departmentsListAdapter);
        } else {
            departmentsListAdapter.setList(departments);
            departmentsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDepartmentsClicked(Department department, int position) {
        Log.d(TAG, "DepartmentsListFragment.onDepartmentsClicked.position: " + position +
                ", department: " + department.toString());

//        Toast.makeText(getActivity(),
//                position + ". " + department.getName(),
//                Toast.LENGTH_SHORT)
//                .show();

        if (DepartmentsUIManager.getInstance().getOnDepartmentClickListener() != null) {
            DepartmentsUIManager.getInstance().getOnDepartmentClickListener()
                    .onDepartmentsClicked(department, position);
        }
    }
}