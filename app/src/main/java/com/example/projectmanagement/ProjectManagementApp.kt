package com.example.projectmanagement

import android.app.Application
import com.example.projectmanagement.data.AppDatabase
import com.example.projectmanagement.data.ProjectRepository

class ProjectManagementApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ProjectRepository(database.projectDao()) }
}