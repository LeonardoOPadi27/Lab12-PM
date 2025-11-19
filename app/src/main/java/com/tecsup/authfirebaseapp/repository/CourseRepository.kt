package com.tecsup.authfirebaseapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.authfirebaseapp.model.Course
import kotlinx.coroutines.tasks.await

class CourseRepository {

    private val db = FirebaseFirestore.getInstance()
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid

    suspend fun addCourse(course: Course) {
        uid?.let {
            db.collection("users")
                .document(it)
                .collection("cursos")
                .add(course)
                .await()
        }
    }

    suspend fun getCoursesWithIds(): List<Pair<String, Course>> {
        uid?.let {
            val snapshot = db.collection("users")
                .document(it)
                .collection("cursos")
                .get()
                .await()

            return snapshot.documents.mapNotNull { doc ->
                val course = doc.toObject(Course::class.java)
                if (course != null) doc.id to course else null
            }
        }
        return emptyList()
    }

    suspend fun updateCourse(id: String, course: Course) {
        uid?.let {
            db.collection("users")
                .document(it)
                .collection("cursos")
                .document(id)
                .set(course)
                .await()
        }
    }

    suspend fun deleteCourse(id: String) {
        uid?.let {
            db.collection("users")
                .document(it)
                .collection("cursos")
                .document(id)
                .delete()
                .await()
        }
    }
}
