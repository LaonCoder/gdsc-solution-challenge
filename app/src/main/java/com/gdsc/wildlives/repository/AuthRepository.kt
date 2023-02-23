package com.gdsc.wildlives.repository

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class AuthRepository {
    val currentUser: FirebaseUser? = Firebase.auth.currentUser

    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    fun signOut() {
        Firebase.auth.signOut()
        FirebaseAuth.getInstance().signOut()
        Log.d("signOut", FirebaseAuth.getInstance().currentUser?.email ?: "Unnamed User")
    }

    private suspend fun createUserProfile(
        userName: String,
        email: String,
        onComplete: (Boolean) -> Unit
    ) {
        val fireStoreRef = Firebase.firestore
            .collection("USER")
            .document(getUserId())

        try {
            val initialProfile = hashMapOf(
                "userId" to getUserId(),
                "name" to userName,
                "email" to email,
                "description" to "",
                "badges" to mutableListOf<String>(),
                "animals" to mutableListOf<String>()
            )
            fireStoreRef.set( initialProfile )
                .addOnSuccessListener { onComplete.invoke(true) }
        } catch (e: Exception) {
            e.printStackTrace()
            onComplete.invoke(false)
        }
    }


    private suspend fun createGoogleUserProfile(
        fireStoreRef: DocumentReference,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        val user = FirebaseAuth.getInstance().currentUser
        val userName = user?.displayName ?: user?.email?.substringBefore('@')
        val email = user?.email

        try {
            val initialProfile = hashMapOf(
                "userId" to getUserId(),
                "name" to userName,
                "email" to email,
                "description" to "",
                "badges" to mutableListOf<String>(),
                "animals" to mutableListOf<String>()
            )
            fireStoreRef.set( initialProfile )
                .addOnSuccessListener {
                    onComplete.invoke(true)
                    Log.d("createGoogleUserProfile", "Google User Created")
                }
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseAuth.getInstance().currentUser?.delete()
            onComplete.invoke(false)
            Log.d("createGoogleUserProfile", "Google User cannot be Created")
        }
    }


    suspend fun createUser(
        userName: String,
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ): AuthResult = withContext(Dispatchers.IO) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    CoroutineScope(Dispatchers.IO).launch {
                        createUserProfile(userName, email, onComplete = onComplete)
                    }
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }


    suspend fun login(
        email: String,
        password: String,
        onComplete: (Boolean) -> Unit
    ) = withContext(Dispatchers.IO) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onComplete.invoke(true)
                } else {
                    onComplete.invoke(false)
                }
            }.await()
    }


    suspend fun googleLogin(
        credential: AuthCredential,
        onComplete: (Boolean) -> Unit
    ): AuthResult = withContext(Dispatchers.IO) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener {

                if (it.isSuccessful) {
                    // check if google user exists
                    val fireStoreRef = Firebase.firestore
                        .collection("USER")
                        .document(getUserId())

                    fireStoreRef
                        .get()
                        .addOnSuccessListener { document ->
                            // if google user exists
                            if (!document.exists()) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    createGoogleUserProfile(fireStoreRef, onComplete = onComplete)
                                }
                            } else {
                                onComplete.invoke(true)
                                Log.d("checkGoogleAccount", "User exists")
                            }
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            onComplete.invoke(false)
                        }
                } else {
                    Log.d("googleLogin", "Login Failed")
                    onComplete.invoke(false)
                }
            }.await()
    }
}