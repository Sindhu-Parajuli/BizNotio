package Fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.biznoti0.MainActivity

import com.example.biznoti0.R
import com.example.biznoti0.SettingActivity
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
