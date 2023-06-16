package com.capstone.mymentor.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.mymentor.adapter.RecommendedMentorsAdapter
import com.capstone.mymentor.data.response.MentorResultResponseItem
import com.capstone.mymentor.databinding.FragmentHomeBinding
import com.capstone.mymentor.ui.profile.mentee.MenteeProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var auth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        if (uid != null) {

            val firebase = FirebaseFirestore.getInstance()
            val users = firebase.collection("users").document(uid)
            users.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val name = snapshot.getString("Name")
                    name?.let {
                        println("Field value: $it")
                        binding.name.text  = name
                        binding.ivButtonProfile.setOnClickListener {
                            val intent = Intent(requireActivity(), MenteeProfileActivity::class.java)
                            intent.putExtra("name", name)
                            startActivity(intent)
                        }
                    } ?: println("Field value is null")
                } else {
                    println("Document does not exist")
                }
            }
                .addOnFailureListener { exception ->
                    println("Error getting document: $exception")
            }
        }

        homeViewModel.listMentors.observe(viewLifecycleOwner) { email: List<MentorResultResponseItem> ->
            setMentorsData(email)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.ivButtonProfile.setOnClickListener {
            val intent = Intent(requireActivity(), MenteeProfileActivity::class.java)
            startActivity(intent)
        }

        showRecyclerList()
    }

    private fun setMentorsData(listMentors: List<MentorResultResponseItem>) {
//        listMentors.sortedByDescending {
//            it.name
//        }
        val adapter = RecommendedMentorsAdapter(listMentors)
        binding.rvMentors.adapter = adapter
    }

    private fun showRecyclerList() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvMentors.layoutManager = layoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}