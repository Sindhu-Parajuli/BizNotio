package Fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView

import android.graphics.drawable.BitmapDrawable



import com.example.biznoti0.SignInActivity
import com.example.biznoti0.R
import com.example.biznoti0.SettingActivity
import kotlinx.android.synthetic.main.fragment_profile.*

import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_profile.view.*

import java.util.*


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

        // load the current image from firebase to imageview

        var currentProfileImage = loadImageFromFirebaseDatabase()



            Glide.with(view)
                .load(currentProfileImage)
                .into(view.circle_image_profile)

            imageView.alpha = 0f


        // Profile button
        val profileButton = view.findViewById<ImageView>(R.id.imageView)

        profileButton?.setOnClickListener{
            val intent = Intent (Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)


        }



        // Settings Button
        val button = view.findViewById<Button>(R.id.editbutton)
        button?.setOnClickListener {
            val intent = Intent (this@ProfileFragment.context, SettingActivity::class.java)
            startActivity(intent)
        }

        // Logout Button
        imagelogoutbutton?.setOnClickListener {

            val progressDialog = ProgressDialog(this@ProfileFragment.context)
            progressDialog.setMessage("Logging out")
            progressDialog.show()

            FirebaseAuth.getInstance().signOut()

            val intent = Intent (this@ProfileFragment.context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK).or(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
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



        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        // make a file name that is a random string, the name will be long
        val filename = UUID.randomUUID().toString()

        // get the location of your firebase storage by giving it the name of the directory you use
        // in our case we use the "user Info"
        val ref = FirebaseStorage.getInstance().getReference("/userStorage/$filename")

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

    private fun saveImageToFirebaseDatabase(proposalName: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/usersID/$uid")

        ref.child("profileImageUrl").setValue(proposalName)
            .addOnSuccessListener {
                Log.d("ProfileFragment", "Finally we saved the profile image to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("ProfileFragment", "Failed to set value to database: ${it.message}")
            }
    }

    private fun loadImageFromFirebaseDatabase(): String? {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/usersID/$uid")

        var profileImageUrl: String? = null

        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                profileImageUrl = snapshot.child("profileImageUrl").value.toString()

                Log.d("ProfileFragment", "currently set to: $profileImageUrl")
            }
        })
        return profileImageUrl
    }

}
