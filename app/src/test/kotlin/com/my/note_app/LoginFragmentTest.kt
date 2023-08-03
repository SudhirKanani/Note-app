package com.my.note_app

import `package com`.my.`note_app;`
import androidx.fragment.app.Fragment
import java.util.Observer
import androidx.navigation.fragment.findNavController
import com.my.note_app.Model.LoginRequest
import com.my.note_app.Utils.NetworkResult
import com.my.note_app.ViewModel.AuthViewModel
import com.my.note_app.databinding.FragmentLoginBinding
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.my.note_app.Model.UserResponse
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import java.lang.Exception
import org.junit.`Test;`
import org.mockito.Mockito.`*;`

class LoginFragmentTest {

    @MockK
    lateinit var view: View

    @MockK
    lateinit var navController: NavController

    @MockK
    lateinit var authViewModel: AuthViewModel

    @MockK
    lateinit var binding: FragmentLoginBinding

    @MockK
    lateinit var edtPhone: EditText

    @MockK
    lateinit var edtCrePass: EditText

    @MockK
    lateinit var viewLifecycleOwner: LifecycleOwner

    private lateinit var loginFragment: LoginFragment

    @BeforeEach
    fun setUp() {

        MockKAnnotations.init(this)

        loginFragment = spyk(LoginFragment())

        loginFragment.viewLifecycleOwnerLiveData.value = viewLifecycleOwner

        loginFragment.findNavController = navController

        loginFragment._binding = binding

        whenever(binding.edtPhone).thenReturn(edtPhone)

        whenever(binding.edtCrePass).thenReturn(edtCrePass)

        whenever(binding.edtPhone.text).thenReturn(
            Editable.Factory.getInstance().newEditable("1234567890")
        )

        whenever(binding.edtCrePass.text).thenReturn(
            Editable.Factory.getInstance().newEditable("123456")
        )

        whenever(navController.popBackStack()).thenReturn(true)

    }

    @AfterEach
    fun tearDown() {

    }


    /**Should navigate to mainFragment when the login is successful*/
    @Test
    fun onViewCreatedNavigateToMainFragmentOnSuccessfulLogin() {
        val userResponse = UserResponse("John Doe", "john.doe@example.com")
        val successResult = NetworkResult.Success(userResponse)

        whenever(authViewModel.userResponseLiveData.value).thenReturn(successResult)

        loginFragment.onViewCreated(view, null)

        verify(navController).navigate(R.id.action_loginFragment_to_mainFragment)
    }

    /**Should log exception when there is an exception in the login process*/
    @Test
    fun onViewCreatedLogExceptionOnLoginException() {
        val exception = Exception("Test Exception")
        val networkResult = NetworkResult.Exception(exception)
        val observer = mock(Observer::class.java) as Observer<NetworkResult<UserResponse>>

        loginFragment.onViewCreated(view, null)

        verify(authViewModel).userResponseLiveData
        verify(authViewModel.userResponseLiveData).observe(viewLifecycleOwner, observer)
        verify(observer).onChanged(networkResult)
        verify(navController).navigate(R.id.action_loginFragment_to_mainFragment)
        verifyNoMoreInteractions(authViewModel, observer, navController)
    }

}