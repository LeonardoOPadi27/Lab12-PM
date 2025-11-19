package com.tecsup.authfirebaseapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.authfirebaseapp.repository.CourseRepository
import com.tecsup.authfirebaseapp.model.Course
import kotlinx.coroutines.launch

@Composable
fun EditCourseScreen(
    id: String,
    course: Course,
    onCourseUpdated: () -> Unit,
    onCancel: () -> Unit
) {
    var titulo by remember { mutableStateOf(course.titulo) }
    var descripcion by remember { mutableStateOf(course.descripcion) }
    var creditos by remember { mutableStateOf(course.creditos.toString()) }

    val repo = CourseRepository()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Text("Editar curso ✏️", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = creditos,
            onValueChange = { creditos = it },
            label = { Text("Créditos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                scope.launch {
                    repo.updateCourse(
                        id,
                        Course(
                            titulo = titulo,
                            descripcion = descripcion,
                            creditos = creditos.toIntOrNull() ?: 0
                        )
                    )
                    onCourseUpdated()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
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
