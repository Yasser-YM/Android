package com.example.veloanantes.model
import android.location.Location
import kotlinx.serialization.*

var currentLocation:Location?=null
var stationSelected:Station? = null
var allStation:List<Station>? =null


@Serializable
data class Station (
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val status: String,
    val bikeStands: Long,
    val availabeBikes: Long,
    val availabeBikesStands: Long,


    val recordId: String,

    val address: String,
){
    fun toLocation(): Location {
        val location =Location("")

        location.latitude = latitude

        location.longitude = longitude
        return location
    }
    fun showDetails():CharSequence{
        return "\uD83D\uDEB2${availabeBikes} \uD83D\uDCE3${availabeBikesStands} ✅${bikeStands}"
    }
}

var parkingSelected:Parking? = null
var allparking:List<Parking>? =null
@Serializable
data class Parking(
    val id :Long,
    val grpDisponible:Long,
    val grpNom:String,
    val  grpStatut:Long,
    val  grpIdentifiant:String,
    val  disponibilite:Long,
    val  idobj:String,
    val  grpComplet:Long,
    val  grpExploitation:Long,
    val  latitude:Double,
    val longitude:Double,
    val  recordId:String,
){
    fun toLocation(): Location {
        val location =Location("")

        location.latitude = latitude

        location.longitude = longitude
        return location
    }
    fun showDetails():CharSequence{
        return "${disponibilite} "
    }
}
