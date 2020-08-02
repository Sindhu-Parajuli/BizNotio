package com.example.biznoti0.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.R
import com.squareup.picasso.Picasso
import org.w3c.dom.DocumentFragment

class ProfileAdapter (private var usercontext: Context,
                       private var userlist:List<ProfileUser>,
                      private var isFragment: Boolean = false) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>()

{
    //View holder to return views on the layout created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(usercontext).inflate(R.layout.result_layout,parent,false)
        return ProfileAdapter.ViewHolder(view)

        TODO("Not yet implemented")
    }

    //gets the total item of searches obtained from the database
    override fun getItemCount(): Int {
        return userlist.size
        TODO("Not yet implemented")
    }
 //Displays the data obtained from search
    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
      val userobtainer = userlist[position]
     holder.Name.text = userobtainer.getFNAME() //+ userobtainer.getLName()
     Picasso.get().load(userobtainer.getImage()).placeholder(R.drawable.profile).into(holder.ProfilePicture)


        TODO("Not yet implemented")
    }

class ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView)
{
val Name:TextView = itemView.findViewById(R.id.layoutuser)
    val ProfilePicture:ImageView = itemView.findViewById(R.id.picture)


}

}