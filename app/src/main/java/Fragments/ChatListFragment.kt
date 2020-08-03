package Fragments

import android.os.Bundle
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

import kotlinx.android.synthetic.main.fragment_chat_list.*

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recycler view node initialized here

        initRecyclerView()
        addDataSet()

        // search button will trigger the new message fragment
        val searchButton = view.findViewById<RelativeLayout>(R.id.search_button)
        searchButton?.setOnClickListener {
            findNavController().navigate(R.id.chatNewMessageFragment, null)
        }

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
