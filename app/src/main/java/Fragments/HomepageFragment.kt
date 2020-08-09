package Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.biznoti0.Adapter.ProposalsAdapter
import com.example.biznoti0.Model.Proposal
import com.example.biznoti0.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * A simple [Fragment] subclass.
 */
class HomepageFragment : Fragment() {

    private lateinit var firesDb: FirebaseFirestore
    private lateinit var proposals: MutableList<Proposal>
    private lateinit var adapterP: ProposalsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageButton = view.findViewById<ImageButton>(R.id.messenger)
        imageButton?.setOnClickListener{
            findNavController().navigate(R.id.chatListFragment, null)
        }

        proposals = mutableListOf()
        adapterP = ProposalsAdapter(view.context, proposals)
        recyclerViewFeed.adapter = adapterP
        recyclerViewFeed.layoutManager = LinearLayoutManager(view.context)

        firesDb = FirebaseFirestore.getInstance()
        val proposalsRef = firesDb
            .collection("proposals")
            .limit(20)
            .orderBy("timeCreated", Query.Direction.DESCENDING)
        proposalsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                Log.e(TAG, "Exception thrown when querrying proposals", exception)
                return@addSnapshotListener
            }
            val proposalList = snapshot.toObjects(Proposal::class.java)
            proposals.clear()
            proposals.addAll(proposalList)
            adapterP.notifyDataSetChanged()
            for (proposal in proposalList) {
                Log.i(TAG, "View ${proposal}")
            }
        }
    }

}
