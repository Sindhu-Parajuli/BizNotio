package Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.EditText

import com.example.biznoti0.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_post.*
import java.util.*
import com.example.biznoti0.SettingActivity
import java.util.*
import com.example.biznoti0.Model.Proposal
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreatePost.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreatePost : Fragment() {
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
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreatePost.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreatePost().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sendPost = view.findViewById<MaterialButton>(R.id.CreatePost)
        sendPost?.setOnClickListener {
            saveProposalToFirebaseDatabase("proposal")
        }

        val proposalNameField = view.findViewById<EditText>(R.id.ProposalName)

        proposalNameField?.setOnClickListener {

        }




    }

    private fun saveProposalToFirebaseDatabase(temp: String) {
        val proposalId = UUID.randomUUID().toString()
        val ref = FirebaseDatabase.getInstance().getReference("/proposals/$proposalId")

        val uid = FirebaseAuth.getInstance().uid ?: ""

        val proposalName: String = ProposalName.text.toString()
        val proposalType: String = ProposalType.text.toString()
        val proposalDescription: String = ProposalDescription.text.toString()
        val minimumCase: String = MinimumCase.text.toString()
        val link: String = Link.text.toString()
        val owner: String = uid

        val proposal = Proposal(owner, proposalId, proposalName, proposalType, proposalDescription, minimumCase, link)


        ref.setValue(proposal)
        .addOnSuccessListener {
            Log.d("CreatePost", "Finally we saved the proposal to Firebase Database")
        }
        .addOnFailureListener {
            Log.d("CreatePost", "Failed to set value to database: ${it.message}")
        }
//        ref.child("proposalName").setValue(proposalName)
//            .addOnSuccessListener {
//                Log.d("CreatePost", "Finally we saved the profile image to Firebase Database")
//            }
//            .addOnFailureListener {
//                Log.d("CreatePost", "Failed to set value to database: ${it.message}")
//            }
    }

}
