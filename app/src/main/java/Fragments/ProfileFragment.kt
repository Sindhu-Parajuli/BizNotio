package Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView


import com.example.biznoti0.SignInActivity
import com.example.biznoti0.R
import com.example.biznoti0.SettingActivity
import kotlinx.android.synthetic.main.fragment_profile.*

import android.net.Uri
import com.example.biznoti0.Model.ProfileUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

import java.util.*

/**
 * A simple [Fragment] subclass.
 */

class ProfileFragment : Fragment() {
    private lateinit var IDforprofile:String
    private lateinit var firebaseuser:FirebaseUser
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

        // Profile button
        val profileButton = view.findViewById<ImageView>(R.id.imageView)


        profileButton?.setOnClickListener{
            val intent = Intent (Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

        }



        // Settings Button
        val button = view.findViewById<Button>(R.id.edit_button)
        button?.setOnClickListener {
            val intent = Intent (this@ProfileFragment.context, SettingActivity::class.java)
            startActivity(intent)
        }

        // Logout Button
        imagelogoutbutton?.setOnClickListener {

            val progressDialog = ProgressDialog(this@ProfileFragment.context)
            progressDialog.setMessage("Logging out")
            progressDialog.show()

            FirebaseAuth.getInstance().signOut();

            val intent = Intent (this@ProfileFragment.context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK).or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        val preference = context?.getSharedPreferences("Preferences",Context.MODE_PRIVATE)
        if(preference!=null) {
            this.IDforprofile = preference.getString("IDforprofile", "none")!!
        }

        //Comparing online user and firebase current user if they are the same;ie own profile page.
        //if not same user, means re-directing from search to user's profile page

        firebaseuser = FirebaseAuth.getInstance().currentUser!!


        if (IDforprofile == firebaseuser.uid)
        {

             view.edit_button.text = "Edit Profile"
        }



        else if(IDforprofile != firebaseuser.uid)
        {
            connect()
        }

        getconnected()

        storeuserData()



    }

    private fun connect()
    {
        firebaseuser?.uid.let {
            FirebaseDatabase.getInstance().reference
                .child("Connect").child(it.toString())
                .child("Connected")
        }.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()) {
                    edit_button.text = "Connected"
                } else {
                    edit_button.text = "Connect"
                }

            }

        })

    }


    private fun getconnected()
    {
        val connected = FirebaseDatabase.getInstance().reference
                .child("Connect").child(IDforprofile)
                .child("Connected")


            connected.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        connectbutton.text = snapshot.childrenCount.toString()
                    }

                }


            })



            }





    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was

            Log.d("ProfileFragment", "Photo was selected")

            // where the image is stored on the machine
            selectedPhotoUri = data.data

//            val contentResolver = getActivity().getContentResolver()
            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedPhotoUri)

            // set the image to the circle viewholder
            circle_image_profile.setImageBitmap(bitmap)

            // make the button invisible to the updated image (circle_image_profile) visible
            imageView.alpha = 0f
            // set the background for the xml id element
            // val bitMapDrawable = BitmapDrawable(bitmap)
            // profile_image_clickable.setBackgroundDrawable(bitMapDrawable)

            uploadImageToFirebaseStorage()
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        // make a file name that is a random string, the name will be long
        val filename = UUID.randomUUID().toString()

        // get the location of your firebase storage by giving it the name of the directory you use
        // in our case we use the "user Info"
        val ref = FirebaseStorage.getInstance().getReference("/user%20Info/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("ProfileFragment", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("ProfileFragment", "File Download URL Location: $it")

                    saveImageToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d("ProfileFragment", "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveImageToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/usersID/$uid")

        ref.child("profileImageUrl").setValue(profileImageUrl)
            .addOnSuccessListener {
                Log.d("ProfileFragment", "Finally we saved the profile image to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("ProfileFragment", "Failed to set value to database: ${it.message}")
            }
    }


    private fun storeuserData()
    {
        val userdata = FirebaseDatabase.getInstance().getReference().child("usersID").child(IDforprofile)
        userdata.addValueEventListener(object : ValueEventListener

        {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
/*
                if(context!=null)
                {
                    return
                }
*/
                if(snapshot.exists())
                {
                    val newuser = snapshot.getValue<ProfileUser>(ProfileUser::class.java)
                    Picasso.get().load(newuser!!.getImage()).placeholder(R.drawable.profile).into(circle_image_profile)
                        view?.textView?.text = newuser!!.getFNAME()  + " " + newuser.getLName()
                    view?.Education?.text=newuser!!.getEducation()
                    view?.Goals?.text=newuser!!.getBizNotioGoals()
                    view?.Interests?.text=newuser!!.getInterests()
                    view?.profession?.text=newuser!!.getProfession()


                }

            }

        }


        )
    }


    override fun onStop() {
        super.onStop()

        val preference = context?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)?.edit()
        preference?.putString("IDforprofile", firebaseuser.uid)
        preference?.apply()
    }



    override fun onPause() {
        super.onPause()

        val preference = context?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)?.edit()
        preference?.putString("IDforprofile", firebaseuser.uid)
        preference?.apply()
    }

    override fun onDestroy() {
        super.onDestroy()

        val preference = context?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)?.edit()
        preference?.putString("IDforprofile", firebaseuser.uid)
        preference?.apply()
    }













}
