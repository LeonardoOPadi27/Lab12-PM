package com.tecsup.authfirebaseapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.authfirebaseapp.model.Course
import com.tecsup.authfirebaseapp.ui.*

object Destinations {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val LISTA = "lista"
    const val ADD = "add"
    const val EDIT = "edit"
}


object TempCourseHolder {
    var course: Course? = null
}

@Composable
fun AuthApp() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}

@Composable
fun AuthNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {

        // ---------------- LOGIN ----------------
        composable(Destinations.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.LISTA) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- REGISTRO ----------------
        composable(Destinations.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.REGISTER) { inclusive = true }
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- LISTA DE CURSOS ----------------
        composable(Destinations.LISTA) {
            CourseListScreen(
                onAddCourse = {
                    navController.navigate(Destinations.ADD)
                },
                onEditCourse = { course, id ->
                    TempCourseHolder.course = course
                    navController.navigate("${Destinations.EDIT}/$id")
                },
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(Destinations.LISTA) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- AGREGAR CURSO ----------------
        composable(Destinations.ADD) {
            AddCourseScreen(
                onCourseSaved = {
                    navController.navigate(Destinations.LISTA) {
                        popUpTo(Destinations.ADD) { inclusive = true }
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- EDITAR CURSO ----------------
        composable("${Destinations.EDIT}/{id}") { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            val course = TempCourseHolder.course!!

            EditCourseScreen(
                id = id,
                course = course,
                onCourseUpdated = {
                    navController.navigate(Destinations.LISTA) {
                        popUpTo("${Destinations.EDIT}/$id") { inclusive = true }
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}
