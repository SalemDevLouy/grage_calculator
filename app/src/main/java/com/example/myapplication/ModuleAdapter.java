package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private List<Module> moduleList;
    private OnItemClickListener listener;

    public ModuleAdapter(List<Module> moduleList, OnItemClickListener listener) {
        this.moduleList = moduleList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.tvModuleName.setText(module.getName());
        holder.tvTest.setText("Exm:" + module.getTest());
        holder.tvPractical.setText("Tp:" + module.getPractical());
        holder.tvProject.setText("Td:" + module.getProject());
        holder.tvModuleAverage.setText("Module Avg " + String.format("%.2f", module.getAverage()));
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView tvModuleName, tvTest, tvPractical, tvProject, tvModuleAverage;
        Button btnDelete, btnUpdate;

        public ModuleViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvModuleName = itemView.findViewById(R.id.tvModuleName);
            tvTest = itemView.findViewById(R.id.tvTest);
            tvPractical = itemView.findViewById(R.id.tvPractical);
            tvProject = itemView.findViewById(R.id.tvProject);
            tvModuleAverage = itemView.findViewById(R.id.tvModuleAverage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);


            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });


            btnUpdate.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onUpdateClick(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onUpdateClick(int position);
    }
}