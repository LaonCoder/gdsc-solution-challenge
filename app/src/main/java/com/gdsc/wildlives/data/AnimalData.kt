package com.gdsc.wildlives.data

import kotlinx.serialization.Serializable

@Serializable
data class AnimalData(
    val name: String = "",
    val scientificName: String = "",
    val description: String = "",
    val animalClass: String = "",
    val endangeredClass: String = "",
    val length: String = "",
    val weight: String = "",
    val habitats: String = "",
    val imageUrl: String = "",
    val photoTakenTime: String? = "",
)

enum class AnimalClass {
    Mammals, Birds, Fish, Reptiles, Amphibians, Invertebrates
}

enum class EndangeredClassList {
    ONE, TWO, NONE
}

val animalData = ArrayList<AnimalData>()

// Sample
val animalClassificationList = arrayOf(
    "tiger",
    "amur leopard",
    "ussuri tube-nosed bat",
    "korean red fox",
    "eurasian lynx",
    "eurasian otter",
    "long-tailed goral",
    "siberian musk deer",
    "ussuri black bear",
    "sika deer",
)