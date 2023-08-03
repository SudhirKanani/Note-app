package com.my.note_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.my.note_app.Utils.Constant.TAG
import com.my.note_app.Utils.NetworkResult
import com.my.note_app.ViewModel.AuthViewModel
import com.my.note_app.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.imgSubmit.setOnClickListener {
           val media = "multipart/form-data".toMediaTypeOrNull()

            val mobile = RequestBody.create(media, "91${binding.edtMobileNo.text}")
            val name = RequestBody.create(media, "${binding.edtFullname.text}")
            val gender = RequestBody.create(media, "Male")
            val ispublic = RequestBody.create(media, "1")
            val pass = RequestBody.create(media, "${binding.edtCrePass.text}")
            val firebase_token = RequestBody.create(media, "1234567890avbcd")

            authViewModel.SignUpUser(mobile,name,gender,ispublic, pass, firebase_token)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Loading ->{}
                is NetworkResult.Success -> {
                    findNavController().navigate(R.id.action_signUpFragment_to_mainFragment)
                }
                is NetworkResult.Exeption -> {
                    Log.e(TAG,"Exeption => ${it.exception}")
                }
                is NetworkResult.Error -> {
                    Log.e(TAG,"Error => ${it.error}")
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}