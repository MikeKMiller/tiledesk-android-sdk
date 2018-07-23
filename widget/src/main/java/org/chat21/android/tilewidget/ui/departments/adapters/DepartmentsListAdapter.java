package org.chat21.android.tilewidget.ui.departments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.chat21.android.tilewidget.R;
import org.chat21.android.tilewidget.core.models.Department;
import org.chat21.android.tilewidget.ui.adapters.AbstractRecyclerAdapter;
import org.chat21.android.tilewidget.ui.departments.listeners.OnDepartmentClickListener;

import java.util.List;

/**
 * Created by stefanodp91 on 04/05/18.
 */
public class DepartmentsListAdapter extends AbstractRecyclerAdapter<Department,
        DepartmentsListAdapter.ViewHolder> {
    private static final String TAG = DepartmentsListAdapter.class.getName();

    private OnDepartmentClickListener onDepartmentClickListener;

    public OnDepartmentClickListener getOnDepartmentClickListener() {
        return onDepartmentClickListener;
    }

    public void setOnDepartmentClickListener(OnDepartmentClickListener onDepartmentClickListener) {
        this.onDepartmentClickListener = onDepartmentClickListener;
    }

    public DepartmentsListAdapter(Context context, List<Department> departments) {
        super(context, departments);
    }

    @Override
    public void setList(List<Department> mList) {
        super.setList(mList);
    }

    @Override
    public DepartmentsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_department, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DepartmentsListAdapter.ViewHolder holder, final int position) {
        final Department department = getItem(position);

        holder.name.setText(department.getName() != null && !department.getName().equals("") ? department.getName() : department.getId());

        holder.id.setText(department.getId());

        setDepartmentCLickAction(holder, department, position);
    }


    // set on row click listener
    private void setDepartmentCLickAction(ViewHolder holder,
                                          final Department department, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnDepartmentClickListener() != null) {
                    getOnDepartmentClickListener().onDepartmentsClicked(department, position);
                } else {
                    Log.w(TAG, "DepartmentsListAdapter.setOnDepartmentClickListener:" +
                            " getOnDepartmentClickListener() is null. " +
                            "set it with setOnDepartmentClickListener method. ");
                }
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView id;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
        }
    }
}