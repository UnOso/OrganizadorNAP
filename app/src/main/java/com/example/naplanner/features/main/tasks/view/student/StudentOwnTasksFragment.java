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
import com.example.naplanner.adapters.TaskRecycleAdapter;
import com.example.naplanner.databinding.FragmentOwnTasksStudentBinding;
import com.example.naplanner.databinding.FragmentOwnTasksTeacherBinding;
import com.example.naplanner.features.main.tasks.view.teacher.TeacherOwnTasksFragmentDirections;
import com.example.naplanner.features.main.tasks.viewmodel.TasksViewModel;
import com.example.naplanner.interfaces.TaskItemListener;

public class StudentOwnTasksFragment extends Fragment implements TaskItemListener {

    private FragmentOwnTasksStudentBinding binding;
    private TasksViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOwnTasksStudentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(TasksViewModel.class);
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
        viewModel.getTasks().observe(getViewLifecycleOwner(), taskModels -> {
            TaskRecycleAdapter adapter = new TaskRecycleAdapter(taskModels, this, requireContext());
            binding.ownTasksFragmentTasksListRecycleview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            binding.ownTasksFragmentTasksListRecycleview.setAdapter(adapter);
        });
        viewModel.getNotifyTaskViewModelException().observe(getViewLifecycleOwner(), exception -> printMsg(exception.getMessage()));
    }

    private void setupUI() {
        viewModel.loadOwnTasks(false);
        binding.ownTasksFragmentTasksListRecycleview.setHasFixedSize(true);
    }

    @Override
    public void onCheckboxTap(int taskID) {
        viewModel.setTasksComplete(taskID);
    }

    @Override
    public void onEditTap(int taskID) {
        TeacherOwnTasksFragmentDirections.ActionTeacherOwnTaeesksFragmentToTaskForm action = TeacherOwnTasksFragmentDirections.actionTeacherOwnTaeesksFragmentToTaskForm();
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