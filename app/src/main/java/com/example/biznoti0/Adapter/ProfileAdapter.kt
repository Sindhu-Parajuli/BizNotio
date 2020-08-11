package com.example.biznoti0.Adapter

import Fragments.ProfileFragment
import Fragments.SearchFragment
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.Model.ProfileUser
import com.example.biznoti0.R
import com.example.biznoti0.ViewModels.SearchViewModel
import com.squareup.picasso.Picasso

class ProfileAdapter (
                        private var model: SearchViewModel,
                        private var usercontext: Context,
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
     Picasso.get().load(userobtainer.getImage()).placeholder(R.drawable.profile).into(holder.ProfilePicture)



      holder.itemView.setOnClickListener(View.OnClickListener {
          Log.d("ProfileAdapter", "userobtainer: ${userobtainer.toString()}")
          Log.d("ProfileAdapter", "context: $usercontext")
          model.select(userobtainer)
          it.findNavController().navigate(R.id.navigation_profile, null)

          val imm = usercontext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
          imm.hideSoftInputFromWindow(it.windowToken, 0)




      })
    }

class ViewHolder (@NonNull itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val Name:TextView = itemView.findViewById(R.id.layoutuser)
        val AccountType:TextView = itemView.findViewById(R.id.accounttype)
        val ProfilePicture:ImageView = itemView.findViewById(R.id.picture)

    }

}