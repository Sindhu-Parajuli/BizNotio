package Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biznoti0.*
import com.example.biznoti0.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_chat_list.*
import android.content.Intent

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var linearLayoutManager: LinearLayoutManager

class ChatListFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var chatListAdapter: ChatListRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        var currentUser: User? = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchCurrentUser()
        // Recycler view node initialized here

        initRecyclerView()
        addDataSet()

        // search button will trigger the new message fragment
        val searchButton = view.findViewById<RelativeLayout>(R.id.search_button)
        searchButton?.setOnClickListener {
            findNavController().navigate(R.id.chatNewMessageFragment, null)
        }

        val appointmentButton = view.findViewById<RelativeLayout>(R.id.appointment_button)
        appointmentButton?.setOnClickListener {
            findNavController().navigate(R.id.appointmentSelectUser, null)
        }

    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/usersID/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                currentUser = snapshot.getValue<User>()
                currentUser = snapshot.getValue(User::class.java)
                Log.d("ChatListFragment", "Current user: ${currentUser?.usersID}")
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun addDataSet(){
        val data = ChatListSource.createDataSet()
        chatListAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        chat_list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
//            val chatListDecoration = ChatListDecoration(30)
//            addItemDecoration(chatListDecoration)
            chatListAdapter = ChatListRecyclerAdapter()
            adapter = chatListAdapter
        }
    }
}
