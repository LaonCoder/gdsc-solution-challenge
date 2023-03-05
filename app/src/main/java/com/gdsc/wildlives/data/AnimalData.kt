package com.gdsc.wildlives.data

import kotlinx.serialization.Serializable

@Serializable
data class AnimalData(
    val name: String = "",
    val animalClass: String = "",
    val iucn: String = "",
    val imageUrl: String = ""
)

enum class AnimalClass {
    Mammals, Birds, Fish, Reptiles, Amphibians, Invertebrates
}

enum class IucnRedList {
    EX, EW, CR, EN, VU, NT, LC, DD, NE
}

val animalData = ArrayList<AnimalData>()