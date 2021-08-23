package com.bappi.groupchat.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bappi.groupchat.ui.adapters.GroupAdapter
import com.bappi.groupchat.ui.MainActivity
import com.bappi.groupchat.data.entity.Participants
import com.bappi.groupchat.databinding.FragmentHomeBinding
import com.bappi.groupchat.ui.ViewModelFactory
import com.bappi.groupchat.utils.Status

class HomeFragment : Fragment() {

    //    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: GroupAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(requireActivity())
            ).get(HomeViewModel::class.java)
        userId = (activity as MainActivity).userLoggedInId.toString()
        homeViewModel.setUserId(userId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
        addGroups()
    }

    private fun addGroups() {
        binding.fab.setOnClickListener {
            view?.let { it1 ->
                val action = HomeFragmentDirections.actionNavigationHomeToAddUserOrGroupFragment(
                    userId = userId.toInt()
                )
                Navigation.findNavController(it1)
                    .navigate(action)

            }
        }
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter =
            GroupAdapter(
                arrayListOf()
            )
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
        adapter.onItemClick = {
            view?.let { it1 ->
                val action = HomeFragmentDirections.actionNavigationHomeToGroupDetailFragment(it.groupId.toString(), userId)
                Navigation.findNavController(it1).navigate(action)
            }
        }
    }

    private fun setupObserver() {
        homeViewModel.getGroups().observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { groups -> renderList(groups) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    //
    private fun renderList(users: List<Participants>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}