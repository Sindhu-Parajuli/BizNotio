package Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.biznoti0.Model.ChatMessage
import com.example.biznoti0.Model.User
import com.example.biznoti0.R
import com.example.biznoti0.ViewModels.ChatViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.fragment_chat_log.*
import kotlinx.android.synthetic.main.layout_chat_log_from_row.view.*
import kotlinx.android.synthetic.main.layout_chat_log_to_row.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ChatLogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_chat_log, container, false)
    }

    companion object {
    }

    private val model: ChatViewModel by activityViewModels()
    private var toId: String? = null
    private var userObject: User = User()
    private var adapter = GroupAdapter<GroupieViewHolder>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // back button
        val backButton =  view.findViewById<ConstraintLayout>(R.id.chat_log_back_button)
        backButton.setOnClickListener {
            Log.d("ChatLogFragment", "Back button pressed")
            findNavController().navigate(R.id.chatListFragment, null)
        }

        text_field_send_button.visibility = View.GONE
        chat_log_recycler_view.adapter = adapter
        model.selectedUser.observe(viewLifecycleOwner, Observer<User> { item ->
            chat_header_user_text.text = item.FName
            toId = item.usersID
            userObject = item
        })

//        setupDummyData()
        listenForMessages()
        text_field_text_view.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text_field_microphone_button.visibility = View.GONE

                text_field_send_button.alpha = 1f
                text_field_send_button.visibility = View.VISIBLE

                if (count == 0) {
                    text_field_microphone_button.visibility = View.VISIBLE
                    text_field_send_button.visibility = View.GONE
                }
            }
        })

        text_field_send_button.setOnClickListener {
            Log.d("ChatLogFragment", "Attempt to send message...")
            performSendMessage()
        }

    }

    private fun listenForMessages() {

        model.selectedUser.observe(viewLifecycleOwner, Observer<User> { item ->
            chat_header_user_text.text = item.FName
            toId = item.usersID
            userObject = item
            val fromId = FirebaseAuth.getInstance().uid
            val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

            ref.addChildEventListener(object: ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatMessage = snapshot.getValue(ChatMessage::class.java)
                    if (chatMessage != null) {
                        Log.d("ChatLogFragment", chatMessage.text)

                        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                            val currentUser = ChatListFragment.currentUser ?: return
                            adapter.add(ChatFromItem(chatMessage.text, currentUser))
                        } else {
                            adapter.add(ChatToItem(chatMessage.text, userObject))
                        }
                    }
//                    val currentCount = adapter.itemCount
                    try {
                        chat_log_recycler_view.scrollToPosition(adapter.itemCount - 1)
                    } catch (e: Exception) {
                        Log.d("ChatLogFragment", "Avoided fatal crash")
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
            })
        })


    }



    private fun performSendMessage() {
        model.selectedUser.observe(viewLifecycleOwner, Observer<User> { item ->
            chat_header_user_text.text = item.FName
            toId = item.usersID
            userObject = item
            val text = text_field_text_view.text.toString()
            val fromId = FirebaseAuth.getInstance().uid

            val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

            val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

            val chatMessage = ChatMessage(reference.key!!, text, fromId!!, toId!!, System.currentTimeMillis())
            reference.setValue(chatMessage)
                    .addOnSuccessListener {
                        Log.d("ChatLogFragment", "Message has been saved to firebase: ${reference.key}")
                        text_field_text_view.text.clear()
                        chat_log_recycler_view.scrollToPosition(adapter.itemCount - 1)
                    }
            toReference.setValue(chatMessage)

            val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
            latestMessageRef.setValue(chatMessage)

            val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
            latestMessageToRef.setValue(chatMessage)
        })

    }
}


class ChatFromItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
        val profileImageUrl = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageview_from_row

        Glide.with(viewHolder.itemView).load(profileImageUrl).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.layout_chat_log_from_row
    }
}

class ChatToItem(val text: String, val user: User): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
        val profileImageUrl = user.profileImageUrl
        val targetImageView = viewHolder.itemView.imageview_to_row

        Glide.with(viewHolder.itemView).load(profileImageUrl).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.layout_chat_log_to_row
    }
}



