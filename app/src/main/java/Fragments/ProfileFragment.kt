package Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.biznoti0.MainActivity

import com.example.biznoti0.R
import com.example.biznoti0.SettingActivity
import com.example.biznoti0.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers.Main

/**
 * A simple [Fragment] subclass.
 */

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imagelogoutbutton?.setOnClickListener {

            val progressDialog = ProgressDialog(this@ProfileFragment.context)
            progressDialog.setMessage("Logging out")
            progressDialog.show()

            FirebaseAuth.getInstance().signOut();

            val intent = Intent (this@ProfileFragment.context, SignInActivity::class.java)
            startActivity(intent)

        }




        //using destination
        val button = view.findViewById<Button>(R.id.editbutton)
        button?.setOnClickListener {
            val intent = Intent (this@ProfileFragment.context, SettingActivity::class.java)
            startActivity(intent)
//            val intent = Intent (getActivity(), Main::class.java)
//            getActivity()?.startActivity(intent)
//            findNavController().navigate(R.id.settingActivity, null)
        }


    }
}
