package com.fadli.aplikasistoryapp.ui.authentication.register

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fadli.aplikasistoryapp.utils.models.ViewModelGeneralFactory
import com.fadli.aplikasistoryapp.R
import com.fadli.aplikasistoryapp.databinding.FragmentRegisterBinding
import com.fadli.aplikasistoryapp.ui.authentication.AuthViewModel
import com.fadli.aplikasistoryapp.ui.authentication.AuthenticationActivity
import com.fadli.aplikasistoryapp.ui.authentication.login.LoginFragment
import com.fadli.aplikasistoryapp.utils.Constanta
import com.fadli.aplikasistoryapp.utils.Helper

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private var viewModel: AuthViewModel? = null
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelGeneralFactory((activity as AuthenticationActivity))
        )[AuthViewModel::class.java]
        viewModel?.let { vm ->
            vm.registerResult.observe(viewLifecycleOwner) { register ->
                if (!register.error) {
                    val dialog = Helper.dialogInfoBuilder(
                        (activity as AuthenticationActivity),
                        getString(R.string.UI_info_successful_register_user)
                    )
                    val btnOk = dialog.findViewById<Button>(R.id.button_ok)
                    btnOk.setOnClickListener {
                        dialog.dismiss()
                        switchLogin()
                    }
                    dialog.show()
                }
            }
            vm.error.observe(viewLifecycleOwner) { error ->
                if (error.isNotEmpty()) {
                    Helper.showDialogInfo(requireContext(), error)
                }
            }
            vm.loading.observe(viewLifecycleOwner) { state ->
                binding.loading.root.visibility = state
            }
        }
        binding.btnLogin.setOnClickListener {
            switchLogin()
        }
        binding.btnAction.setOnClickListener {
            val nama = binding.edName.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            when {
                email.isEmpty() or password.isEmpty() or nama.isEmpty() -> {
                    Helper.showDialogInfo(
                        requireContext(),
                        getString(R.string.UI_validation_empty_email_password)
                    )
                }
                !email.matches(Constanta.emailPattern) -> {
                    Helper.showDialogInfo(
                        requireContext(),
                        getString(R.string.UI_validation_invalid_email)
                    )
                }
                password.length < 6 -> {
                    Helper.showDialogInfo(
                        requireContext(),
                        getString(R.string.UI_validation_password_rules)
                    )
                }
                else -> {
                    viewModel?.register(nama, email, password)
                }
            }
        }
    }

    private fun switchLogin() {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.container, LoginFragment(), LoginFragment::class.java.simpleName)
            addSharedElement(binding.labelAuth, "auth")
            addSharedElement(binding.edEmail, "email")
            addSharedElement(binding.edPassword, "password")
            addSharedElement(binding.containerMisc, "misc")
            commit()
        }
    }

}