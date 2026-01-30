package com.example.projectmanagement.mvi

import com.example.projectmanagement.data.Project

data class ProjectState(
    val projects: List<Project> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentProject: Project? = null,
    val isEditing: Boolean = false
)