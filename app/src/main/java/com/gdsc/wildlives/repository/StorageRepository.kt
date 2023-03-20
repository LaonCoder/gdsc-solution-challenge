package com.gdsc.wildlives.repository

import androidx.compose.animation.core.snap
import com.gdsc.wildlives.data.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class StorageRepository() {
    fun user() = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val userRef: CollectionReference =
        Firebase.firestore.collection("USER")

    fun getUserProfile(
        userId: String
    ): Flow<Resources<UserProfile>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = userRef
                .document(userId).addSnapshotListener { snapshot, e ->
                    var response = if (snapshot != null) {
                        val userProfile = snapshot.toObject(UserProfile::class.java)
                        Resources.Success(data = userProfile)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e?.cause))
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }
}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T>: Resources<T>()
    class Success<T>(data: T?): Resources<T>(data = data)
    class Error<T>(throwable: Throwable?): Resources<T>(throwable = throwable)
}