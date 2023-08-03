package com.my.note_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.my.note_app.Model.LoginRequest
import com.my.note_app.Utils.Constant
import com.my.note_app.Utils.NetworkResult
import com.my.note_app.ViewModel.AuthViewModel
import com.my.note_app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.imgLogin.setOnClickListener {
            authViewModel.LoginUser(
                LoginRequest(
                    "91${binding.edtPhone.text}",
                    "${binding.edtCrePass.text}",
                    "123456"
                )
            )
        }
        binding.txtSignin.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }

                is NetworkResult.Exeption -> {
                    Log.e(Constant.TAG, "Exeption => ${it.exception}")
                }

                is NetworkResult.Error -> {
                    Log.e(Constant.TAG, "Exeption => ${it.error}")
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}