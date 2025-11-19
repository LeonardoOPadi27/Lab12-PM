package com.tecsup.authfirebaseapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.authfirebaseapp.repository.CourseRepository
import com.tecsup.authfirebaseapp.model.Course
import kotlinx.coroutines.launch

@Composable
fun CourseListScreen(
    onAddCourse: () -> Unit,
    onEditCourse: (Course, String) -> Unit,   // ← Ahora enviamos curso + ID
    onLogout: () -> Unit
) {
    val repo = CourseRepository()
    var cursos by remember { mutableStateOf(listOf<Pair<String, Course>>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        cursos = repo.getCoursesWithIds()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onAddCourse) {
                Text("Agregar curso")
            }
            Button(onClick = onLogout) {
                Text("Cerrar sesión")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(cursos) { (id, curso) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(curso.titulo, style = MaterialTheme.typography.headlineSmall)
                        Text(curso.descripcion)
                        Text("Créditos: ${curso.creditos}")

                        Spacer(Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedButton(onClick = { onEditCourse(curso, id) }) {
                                Text("✏ Editar")
                            }
                            OutlinedButton(onClick = {
                                scope.launch {
                                    repo.deleteCourse(id)
                                    cursos = repo.getCoursesWithIds()
                                }
                            }) {
                                Text("🗑 Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
