package com.example.projectmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectmanagement.ui.ProjectListScreen
import com.example.projectmanagement.viewmodel.ProjectViewModel
import com.example.projectmanagement.viewmodel.ProjectViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (application as ProjectManagementApp).repository

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val viewModel: ProjectViewModel = viewModel(
                    factory = ProjectViewModelFactory(repository)
                )
                ProjectListScreen(viewModel = viewModel)
            }
        }
    }
}