package com.bappi.groupchat.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bappi.groupchat.ui.MainActivity
import com.bappi.groupchat.R
import com.bappi.groupchat.data.entity.User
import com.bappi.groupchat.databinding.FragmentAddUserOrGroupBinding
import com.bappi.groupchat.ui.ViewModelFactory
import com.bappi.groupchat.utils.Status
import com.bappi.groupchat.ui.adapters.UserAdapter
import com.google.android.material.textfield.TextInputEditText


private const val ARG_PARAM1 = "userId"

private lateinit var adapter: UserAdapter

class AddUserOrGroupFragment : Fragment() {
    private var userLoggedInId: Int = 0
    private lateinit var binding: FragmentAddUserOrGroupBinding
    private lateinit var viewModel: AddUserOrGroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userLoggedInId = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUserOrGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(requireActivity())
            ).get(AddUserOrGroupViewModel::class.java)
        viewModel.setUserId(userLoggedInId.toString())
        setUpUI()
        setupObserver()
    }

    private fun setUpUI() {
        initRecyclerView()

        binding.createGroup.setOnClickListener {
            updateUI()
        }

        binding.createUser.setOnClickListener {
            showAddDialog(USER_DIALOG)
        }

        binding.fab.setOnClickListener {
            fabOnClickListener()
        }
    }

    private fun fabOnClickListener() {
        val users = adapter.getUsers()
        val selectedUserList = ArrayList<User>()
        for (user in users) {
            if (user.isSelected) {
                selectedUserList.add(user)
            }
        }
        if (selectedUserList.isEmpty()) {
            showToast("Please select at least one user to create a group")
        } else {
            viewModel.selectedUserList = selectedUserList
            showAddDialog(GROUP_DIALOG)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun updateUI() {
        adapter.showCheckbox(true)
        adapter.notifyDataSetChanged()
        binding.fab.visibility = View.VISIBLE
    }

    private fun setupObserver() {
        viewModel.getUsers().observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.isGroupCreated().observe(requireActivity(), {
            if (it) {
                (activity as MainActivity).onBackPressed()
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addUsers(users)
        adapter.notifyDataSetChanged()
    }


    private fun showAddDialog(dialogType: Int) {
        val addUserDialogView: View = LayoutInflater.from(context).inflate(R.layout.add_user, null)
        val addUserDialog: AlertDialog = AlertDialog.Builder(requireContext()).create()
        addUserDialog.setView(addUserDialogView)
        addUserDialogView.findViewById<AppCompatButton>(R.id.addButton)
            .setOnClickListener {
                handleDialogAddButton(addUserDialogView, dialogType, addUserDialog)
            }
        addUserDialogView.findViewById<View>(R.id.cancel)
            .setOnClickListener {
                addUserDialog.dismiss()
            }

        addUserDialog.show()
    }

    private fun handleDialogAddButton(
        addUserDialogView: View,
        dialogType: Int,
        addUserDialog: AlertDialog
    ) {
        val inputText =
            addUserDialogView.findViewById<TextInputEditText>(R.id.inputText)
        val input = inputText.text
        input?.let {
            if (dialogType == USER_DIALOG) {
                viewModel.insertUser(input.toString())
                showToast("User Created")
                (activity as MainActivity).onBackPressed()
            } else if (dialogType == GROUP_DIALOG) {
                viewModel.createGroup(input.toString())
                showToast("Group Created")
            }
            addUserDialog.dismiss()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val USER_DIALOG = 1
        const val GROUP_DIALOG = 2
    }
}