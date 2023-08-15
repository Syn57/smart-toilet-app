package com.ta.smile.ui.onboarding

import android.R.attr.password
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ta.smile.MainActivity
import com.ta.smile.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
//    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        firebaseRegister()
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun firebaseRegister(){
        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmailReg.text.toString()
            val pass = binding.edtPassReg.text.toString()
            val name = binding.edtNameReg.text.toString()
            if (email.isEmpty() || pass.isEmpty() || name.isEmpty()){
                Toast.makeText(requireContext(), "Please enter name, email and password", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task: Task<AuthResult?> ->
                        if (task.isSuccessful) {
                            // Sign-up success
                            val user = auth.currentUser
                            // You can perform additional actions here, such as saving user data to the Firebase Realtime Database or Firestore
                            user?.let {
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()
                                it.updateProfile(profileUpdates)
                                    .addOnCompleteListener { updateTask ->
                                        if (updateTask.isSuccessful) {
                                            // Display name added successfully
                                            // You can continue with other operations
//                                            Toast.makeText( requireContext(),"${name}",Toast.LENGTH_SHORT).show()
                                            updateUI(user)
                                        } else {
                                            // Failed to update the display name
                                        }
                                    }
                            }
                            Toast.makeText( requireContext(),"Sign-up succeed",Toast.LENGTH_SHORT).show()
                        } else {
                            // Sign-up failed
                            Toast.makeText( requireContext(),"Sign-up failed",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}