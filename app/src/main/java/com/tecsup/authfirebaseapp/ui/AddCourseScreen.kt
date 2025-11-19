package com.tecsup.authfirebaseapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.authfirebaseapp.model.Course
import com.tecsup.authfirebaseapp.repository.CourseRepository
import kotlinx.coroutines.launch

@Composable
fun AddCourseScreen(
    onCourseSaved: () -> Unit,
    onCancel: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var creditos by remember { mutableStateOf("") }

    val repo = CourseRepository()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Registrar Curso 📘",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(20.dp))

        // TÍTULO
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título del curso") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        // DESCRIPCIÓN
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        // CRÉDITOS
        OutlinedTextField(
            value = creditos,
            onValueChange = { creditos = it },
            label = { Text("Créditos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        // GUARDAR
        Button(
            onClick = {
                if (titulo.isNotBlank() && descripcion.isNotBlank()) {

                    scope.launch {
                        repo.addCourse(
                            Course(
                                titulo = titulo,
                                descripcion = descripcion,
                                creditos = creditos.toIntOrNull() ?: 0
                            )
                        )
                        onCourseSaved()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar curso")
        }

        Spacer(Modifier.height(10.dp))

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
