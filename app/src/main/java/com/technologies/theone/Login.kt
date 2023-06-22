package com.technologies.theone

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.technologies.theone.databinding.FragmentLoginBinding
import com.technologies.theone.databinding.FragmentOnBoardBinding
import com.technologies.theone.utils.Constance

class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        binding.btnLogin.setOnClickListener({
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if(username.isEmpty()){
                Toast.makeText(context, "Please enter username.", Toast.LENGTH_LONG).show()
            } else if (password.isEmpty()) {
                Toast.makeText(context, "Please enter password.", Toast.LENGTH_LONG).show()
            } else if (!username.equals("testapp@google.com")) {
                Toast.makeText(context, "Please enter valid username.", Toast.LENGTH_LONG).show()
            } else if (!password.equals("Test@123456")) {
                Toast.makeText(context, "Please enter valid password.", Toast.LENGTH_LONG).show()
            } else {
                val sharedPreference = context?.getSharedPreferences(Constance.SP_NAME, Context.MODE_PRIVATE)
                var editor = sharedPreference?.edit()?.apply {
                    this.putString("username",username)
                    this.apply()
                }
                findNavController().navigate(R.id.action_login_to_userLists)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}