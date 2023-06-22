package com.technologies.theone

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technologies.theone.adapter.UserAdapter
import com.technologies.theone.database.UserDatabase
import com.technologies.theone.databinding.FragmentUserListsBinding
import com.technologies.theone.model.User
import com.technologies.theone.repository.UserRepo
import com.technologies.theone.viewmodel.UserViewModel
import com.technologies.theone.viewmodel.UserViewModelFactory

class UserLists : Fragment() {

    private var _binding: FragmentUserListsBinding? = null
    val binding get() = _binding!!
    lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUserListsBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val dao = UserDatabase.roomInstance(requireContext().applicationContext).userDao()
        val repo = UserRepo(dao)
        viewModel =
            ViewModelProvider(this, UserViewModelFactory(repo)).get(UserViewModel::class.java)

        setHasOptionsMenu(true)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        viewModel.getUsers().observe(this, object : Observer<List<User>> {
            override fun onChanged(value: List<User>) {

                if (value.size == 0) {
                    binding.tvNoData.text = "No Data"
                    binding.recyclerView.visibility = View.GONE
                } else {
                    binding.tvNoData.text = ""
                    binding.tvNoData.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE

                    binding.recyclerView.adapter =
                        UserAdapter(value) { s: User -> userClick(s) }
                    value.forEach {

                        ItemTouchHelper(object :
                            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                            override fun onMove(
                                recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder,
                            ): Boolean {
                                return false
                            }

                            override fun onSwiped(
                                viewHolder: RecyclerView.ViewHolder,
                                direction: Int,
                            ) {

                                viewModel.deleteuser(it)
                            }
                        }).attachToRecyclerView(binding.recyclerView)
                    }
                }
            }
        })


        return binding.root
    }

    fun userClick(user: User) {
        findNavController().navigate(R.id.action_userLists_to_weather)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addUser -> {
                findNavController().navigate(R.id.action_userLists_to_userForm)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}