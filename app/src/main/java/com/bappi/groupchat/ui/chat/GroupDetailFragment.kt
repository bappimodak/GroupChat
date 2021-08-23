package com.bappi.groupchat.ui.chat

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bappi.groupchat.data.entity.Messages
import com.bappi.groupchat.databinding.FragmentGroupDetailBinding
import com.bappi.groupchat.ui.MainActivity
import com.bappi.groupchat.ui.ViewModelFactory
import com.bappi.groupchat.ui.adapters.ChatAdapter
import com.bappi.groupchat.utils.Status

private const val ARG_PARAM1 = "groupId"
private const val ARG_PARAM2 = "userId"

class GroupDetailFragment : Fragment() {
    private var groupId: String? = null
    private var userId: String? = null
    private lateinit var binding: FragmentGroupDetailBinding
    private lateinit var viewModel: GroupDetailViewModel
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getString(ARG_PARAM1)
            userId = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(requireActivity(), ViewModelFactory(requireActivity(), groupId)).get(
                GroupDetailViewModel::class.java
            )
        binding = FragmentGroupDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter =
            ChatAdapter(
                arrayListOf()
            )
        binding.recyclerView.adapter = adapter

        binding.enterButton.setOnClickListener {
            val message = binding.sendMessage.text
            if(!message.isNullOrEmpty()){
                viewModel.insertGroupMessage(Messages(groupId!!, userId!!, message.toString(), 0))
                binding.sendMessage.setText("")
            }
        }
    }

    private fun setupObserver() {
        viewModel.getGroupMessages().observe(requireActivity(), {
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
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(messages: List<Messages>) {
        adapter.addData(messages)
        adapter.notifyDataSetChanged()
    }

}