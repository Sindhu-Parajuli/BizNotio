package com.example.biznoti0.Adapter

import Fragments.ProfileFragment
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.MainActivity
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.R
import com.squareup.picasso.Picasso
import org.w3c.dom.DocumentFragment

class ProfileAdapter (private var usercontext: Context,
                       private var userlist:List<ProfileUser>,
                      private var Fragment: Boolean = false) : RecyclerView.Adapter<ProfileAdapter.ViewHolder>()

{
    //View holder to return views on the layout created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(usercontext).inflate(R.layout.result_layout,parent,false)
        return ProfileAdapter.ViewHolder(view)
    }

    //gets the total item of searches obtained from the database
    override fun getItemCount(): Int {
        return userlist.size

    }
 //Displays the data obtained from search
    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
      val userobtainer = userlist[position]
     holder.Name.text = userobtainer.getFNAME() + "  "+ userobtainer.getLName()
     holder.AccountType.text = userobtainer.getACType()
     //Picasso.get().load(userobtainer.getImage()).placeholder(R.drawable.profile).into(holder.ProfilePicture)
      holder.itemView.setOnClickListener(View.OnClickListener {
          if (Fragment) {
              val preference = usercontext.getSharedPreferences("Preferences", Context.MODE_PRIVATE).edit()
              preference.putString("IDforprofile", userobtainer.getusersID())
              preference.apply()
              (usercontext as FragmentActivity).supportFragmentManager.beginTransaction()
                  .replace(R.id.nav_host_fragment_container, ProfileFragment()).commit()
          }
      })
    }

class ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val Name:TextView = itemView.findViewById(R.id.layoutuser)
        val AccountType:TextView = itemView.findViewById(R.id.accounttype)
        val ProfilePicture:ImageView = itemView.findViewById(R.id.picture)

    }

}