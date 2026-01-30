package com.example.projectmanagement.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectmanagement.data.Project
import com.example.projectmanagement.data.ProjectRepository
import com.example.projectmanagement.mvi.ProjectIntent
import com.example.projectmanagement.mvi.ProjectState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(private val repository: ProjectRepository) : ViewModel() {

    private val _state = MutableStateFlow(ProjectState())
    val state: StateFlow<ProjectState> = _state.asStateFlow()

    init {
        processIntent(ProjectIntent.LoadProjects)
    }

    fun processIntent(intent: ProjectIntent) {
        when (intent) {
            is ProjectIntent.LoadProjects -> loadProjects()
            is ProjectIntent.AddProject -> addProject(intent.project)
            is ProjectIntent.EditProject -> editProject(intent.project)
            is ProjectIntent.UpdateProject -> updateProject(intent.project)
            is ProjectIntent.DeleteProject -> deleteProject(intent.project)
            is ProjectIntent.ClearSelection -> clearSelection()
        }
    }

    private fun loadProjects() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                repository.getAllProjects().collect { projects ->
                    _state.value = _state.value.copy(
                        projects = projects,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Failed to load projects: ${e.message}"
                )
            }
        }
    }

    private fun addProject(project: Project) {
        viewModelScope.launch {
            try {
                repository.insertProject(project)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to add project: ${e.message}"
                )
            }
        }
    }

    private fun updateProject(project: Project) {
        viewModelScope.launch {
            try {
                repository.updateProject(project)
                _state.value = _state.value.copy(
                    currentProject = null,
                    isEditing = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to update project: ${e.message}"
                )
            }
        }
    }

    private fun deleteProject(project: Project) {
        viewModelScope.launch {
            try {
                repository.deleteProject(project)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Failed to delete project: ${e.message}"
                )
            }
        }
    }

    private fun editProject(project: Project) {
        _state.value = _state.value.copy(
            currentProject = project,
            isEditing = true
        )
    }

    private fun clearSelection() {
        _state.value = _state.value.copy(
            currentProject = null,
            isEditing = false
        )
    }
}