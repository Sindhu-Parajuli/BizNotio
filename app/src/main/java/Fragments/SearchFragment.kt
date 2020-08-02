package Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biznoti0.Adapter.ProfileAdapter
import com.example.biznoti0.Model.ProfileUser

import com.example.biznoti0.R
import kotlinx.android.synthetic.main.fragment_search.view.*

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    private var searchedView : RecyclerView? = null
    private var profileadapter:ProfileAdapter?= null
    private var userlist:MutableList<ProfileUser>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_search, container, false)
        searchedView = view.findViewById(R.id.recyclerviewid)
        searchedView?.setHasFixedSize(true)
        searchedView?.layoutManager= LinearLayoutManager(context)
        userlist = ArrayList()
        profileadapter = context?.let { ProfileAdapter(it,userlist as ArrayList<ProfileUser>,true)  }
        searchedView?.adapter = profileadapter
        view.searchView.addTextChangedListener(object :TextWatcher
        {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }


        
        })

        return view
    }



}
