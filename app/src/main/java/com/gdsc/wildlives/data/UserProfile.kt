package com.gdsc.wildlives.data

data class UserProfile(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val description: String = "",
    val points: Int = 0,
    val animals: List<AnimalData> = mutableListOf()
)
