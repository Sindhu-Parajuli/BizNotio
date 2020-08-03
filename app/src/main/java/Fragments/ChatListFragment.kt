package Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController

import com.example.biznoti0.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biznoti0.ChatListRecyclerAdapter
import com.example.biznoti0.ChatListSource
import com.example.biznoti0.ChatListDecoration
import com.example.biznoti0.Model.ChatMessage
import com.example.biznoti0.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

import kotlinx.android.synthetic.main.fragment_chat_list.*
import kotlinx.android.synthetic.main.layout_chat_list_element.view.*

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

    val adapter = GroupAdapter<GroupieViewHolder>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chat_list_recycler_view.adapter = adapter
//        setupDummyRows()
        listenForLatestMessages()
        fetchCurrentUser()
        // Recycler view node initialized here

//        initRecyclerView()
//        addDataSet()

        // search button will trigger the new message fragment
        val searchButton = view.findViewById<RelativeLayout>(R.id.search_button)
        searchButton?.setOnClickListener {
            findNavController().navigate(R.id.chatNewMessageFragment, null)
        }

    }

    class LatestMessageRow(val chatMessage: ChatMessage): Item<GroupieViewHolder>() {
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.chat_list_element_message_preview.text = chatMessage.text

            val chatPartnerId: String
            if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                chatPartnerId = chatMessage.toId
            } else {
                chatPartnerId = chatMessage.fromId
            }

            val ref = FirebaseDatabase.getInstance().getReference("/usersID/$chatPartnerId")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    viewHolder.itemView.chat_list_element_user_name.text = "${user?.FName} ${user?.LName} "
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        }

        override fun getLayout(): Int {
            return R.layout.layout_chat_list_element
        }

    }

    val latestMessagesMap = HashMap<String, ChatMessage>()

    fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?: return

                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {


            }
        })
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
