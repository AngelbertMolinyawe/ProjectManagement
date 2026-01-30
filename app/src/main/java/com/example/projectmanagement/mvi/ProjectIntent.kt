package com.example.projectmanagement.mvi

import com.example.projectmanagement.data.Project

sealed class ProjectIntent {
    object LoadProjects : ProjectIntent()
    data class AddProject(val project: Project) : ProjectIntent()
    data class EditProject(val project: Project) : ProjectIntent()
    data class UpdateProject(val project: Project) : ProjectIntent()
    data class DeleteProject(val project: Project) : ProjectIntent()
    object ClearSelection : ProjectIntent()
}