package com.technologies.theone

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.technologies.theone.database.UserDatabase
import com.technologies.theone.databinding.FragmentUserFormBinding
import com.technologies.theone.databinding.FragmentUserListsBinding
import com.technologies.theone.model.User
import com.technologies.theone.repository.UserRepo
import com.technologies.theone.viewmodel.UserViewModel
import com.technologies.theone.viewmodel.UserViewModelFactory

class UserForm : Fragment() {

    private var _binding: FragmentUserFormBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUserFormBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val dao = UserDatabase.roomInstance(requireContext().applicationContext).userDao()
        val repo = UserRepo(dao)
        viewModel =
            ViewModelProvider(this, UserViewModelFactory(repo)).get(UserViewModel::class.java)

        binding.btnSaveUser.setOnClickListener({
            val firstName = binding.etfirstName.text.toString()
            val lastName = binding.etLastname.text.toString()
            val email = binding.etEmail.text.toString()

            if (firstName.isEmpty()) {
                Toast.makeText(context, "Please enter first name.", Toast.LENGTH_LONG).show()
            } else if (lastName.isEmpty()) {
                Toast.makeText(context, "Please enter last name.", Toast.LENGTH_LONG).show()
            } else if (email.isEmpty()) {
                Toast.makeText(context, "Please enter email.", Toast.LENGTH_LONG).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Please enter valid email.", Toast.LENGTH_LONG).show()
            } else {
                viewModel.insertUser(User(0, firstName, lastName, email))
                Toast.makeText(context, "User added.", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_userForm_to_userLists)
            }
        })

        binding.btnCanel.setOnClickListener({
            findNavController().navigate(R.id.action_userForm_to_userLists)
        })
        return binding.root
    }
}