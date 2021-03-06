package com.example.naplanner.features.main.tasks.view.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.naplanner.MainActivity;
import com.example.naplanner.adapters.StudentTaskByTeacherRecycleAdapter;
import com.example.naplanner.databinding.FragmentTaskAssignedByTeacherBinding;
import com.example.naplanner.features.main.tasks.viewmodel.TasksViewModel;
import com.example.naplanner.interfaces.TaskItemListener;

public class StudentTaskAssignedByTeacherFragment extends Fragment implements TaskItemListener {

    private FragmentTaskAssignedByTeacherBinding binding;
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTaskAssignedByTeacherBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(TasksViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) requireActivity()).showInteractionBars();
        setObservables();
        setupUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setObservables() {
        viewModel.tasks.observe(getViewLifecycleOwner(), taskModels -> {
            StudentTaskByTeacherRecycleAdapter adapter = new StudentTaskByTeacherRecycleAdapter(taskModels, this, requireContext());
            binding.taskByTeacherFragmentTasksListRecycleview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            binding.taskByTeacherFragmentTasksListRecycleview.setAdapter(adapter);
        });
        viewModel.notifyTaskViewModelException.observe(getViewLifecycleOwner(), exception -> printMsg(exception.getMessage()));
    }

    private void setupUI() {
        viewModel.loadAllTeacherTasks();
        binding.taskByTeacherFragmentTasksListRecycleview.setHasFixedSize(true);
    }

    @Override
    public void onCheckboxTap(int taskID) {
        viewModel.setTasksComplete(taskID);
    }

    @Override
    public void onEditTap(int taskID) {
        StudentTaskAssignedByTeacherFragmentDirections.ActionTaskAssignedByTeacherFragmentToStudentCreateTaskFragment action = StudentTaskAssignedByTeacherFragmentDirections.actionTaskAssignedByTeacherFragmentToStudentCreateTaskFragment();
        action.setIsEdit(true);
        action.setTaskID(taskID);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void printMsg(String msg) {
        Toast.makeText(requireActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}