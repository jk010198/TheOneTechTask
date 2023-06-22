package com.technologies.theone

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.technologies.theone.databinding.FragmentOnBoardBinding
import com.technologies.theone.utils.Constance


class OnBoard : Fragment() {

    private var _binding: FragmentOnBoardBinding? = null
    val binding get() = _binding!!
    var sp: SharedPreferences? = null
    var editor: Editor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        sp = context?.getSharedPreferences(Constance.ONBOARD_SP_NAME, Context.MODE_PRIVATE)
        val firstStart = sp?.getBoolean("first_start", true)

        if (firstStart == true) {
            editor = sp?.edit()?.apply {
                this.putBoolean("first_start", false)
                this.apply()
            }
        } else {
            checkUserLogin()
        }

        binding.btnLoginPage.setOnClickListener({
            checkUserLogin()
        })

        return binding.root
    }

    fun checkUserLogin() {
        val sp = context?.getSharedPreferences(Constance.SP_NAME, Context.MODE_PRIVATE)
        val username = sp?.getString("username", "defaultName")

        if (!username.equals("testapp@google.com")) {
            findNavController().navigate(R.id.action_onBoard_to_login)
        } else {
            findNavController().navigate(R.id.action_onBoard_to_userLists)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}