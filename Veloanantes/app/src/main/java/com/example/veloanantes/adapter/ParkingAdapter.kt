package com.example.veloanantes.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.veloanantes.R
import com.example.veloanantes.model.Parking
import com.example.veloanantes.model.currentLocation
import com.example.veloanantes.model.parkingSelected
import com.example.veloanantes.ui.parking.ParkingMapsActivity

class ParkingAdapter(private val parkings:List<Parking>, private val context: Context):
    RecyclerView.Adapter<ParkingAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val cardView : CardView = itemView.findViewById(R.id.cardView_parkings)
        val name : TextView = itemView.findViewById(R.id.grpNom)
        val availability : TextView = itemView.findViewById(R.id.availabilty)
        val status : ImageView = itemView.findViewById(R.id.grpstatus)
        val distance : TextView = itemView.findViewById((R.id.distance_p))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_parkings, parent, false)
        return ViewHolder(view)
    }

    //Pour chaque view_id on met à jour les composants de la view (cardView: CardView,  name:TextView)
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n", "ResourceAsColor")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parking = parkings[position]
        holder.name.text = parking.grpNom

        if (currentLocation != null){
            holder.distance.text = "${String.format("%.2f", currentLocation!!.distanceTo(parking.toLocation()) /1000)} km"
        }else{
            holder.distance.text = "- km"
        }

        if (parking.disponibilite.toInt() in 0..parking.grpComplet.toInt() ){
            holder.name.text = "${parking.grpNom} (COMPLET)"
            holder.name.setTextColor(context.getColor(R.color.red))
        }else if(parking.disponibilite.toInt() <= -1 ) {
            holder.name.text = "${parking.grpNom} (PAS DISPONIBLE)"
            holder.name.setTextColor(context.getColor(R.color.red))
        } else {
            holder.name.setTextColor(context.getColor(R.color.teal_200))
        }

        holder.availability.text = "Dispo : "+parking.showDetails()
        if (parking.grpStatut.toInt() == 5){
            holder.status.setImageResource(R.drawable.ic_baseline_circle_green)
        }else if (parking.grpStatut.toInt() == 2) {
            holder.name.text = "${parking.grpNom}\n(Ouvert que pour les abonnés)"
            holder.name.setTextColor(context.getColor(R.color.purple_500))
            holder.status.setImageResource(R.drawable.ic_baseline_subscriptions_24)
        } else if (parking.grpStatut.toInt() == 1) {
            holder.status.setImageResource(R.drawable.ic_baseline_circle_red)
        } else {
            holder.status.setImageResource(R.drawable.ic_baseline_do_not_disturb_24)
        }

        //Quand on click sur la card view -> on ouvre une nouvelle fenetre
        holder.cardView.setOnClickListener {
            val intent = Intent(context, ParkingMapsActivity::class.java)
           parkingSelected = parking
            context.startActivity(intent)
        }
    }

    /*On retourne le nombre d'éléments de la liste parkings*/
    override fun getItemCount(): Int {
        return parkings.size
    }
}