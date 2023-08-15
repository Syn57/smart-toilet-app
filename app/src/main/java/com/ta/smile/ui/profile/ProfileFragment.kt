package com.ta.smile.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ta.smile.R
import com.ta.smile.databinding.FragmentProfileBinding
import com.ta.smile.ui.onboarding.OnboardingActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val email =  currentUser?.email
        binding.tvNameProfile.text = currentUser?.displayName
        binding.tvEmailProfile.text = email
//        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        initView()
    }

    private fun initView() {
        binding.btnLogout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
        requireActivity().finish()
    }




}