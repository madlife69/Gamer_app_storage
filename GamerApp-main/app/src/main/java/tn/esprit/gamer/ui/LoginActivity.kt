package tn.esprit.gamer.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.snackbar.Snackbar
import tn.esprit.gamer.R
import tn.esprit.gamer.databinding.ActivityLoginBinding
import tn.esprit.gamer.utils.AppDatabase
import tn.esprit.gamer.utils.MyStatics

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var keep: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { keep }
        Handler(Looper.getMainLooper()).postDelayed({
            keep = false
        }, 1000)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contextView = binding.contextView
        val sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        binding.tiEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })

        binding.tiPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })
        val email = binding.tiEmail.text.toString()
        val password = binding.tiPassword.text.toString()
        binding.btnLogin.setOnClickListener {
            MyStatics.hideKeyboard(this, binding.btnLogin)
            if (!validateEmail() || !validatePassword()) {
                Snackbar.make(binding.contextView, getString(R.string.msg_error_inputs), Snackbar.LENGTH_SHORT).show()
            } else {
                val count = AppDatabase.getDatabase().UserDao()?.verifUser(email, password)
                if (count != null && count > 0) {
                    val rememberMe = binding.btnRememberMe.isChecked()
                    if (rememberMe) {
                        val username = binding.tiEmail.text.toString()
                        val password = binding.tiPassword.text.toString()

                        val editor = sharedPreferences.edit()
                        editor.putBoolean("rememberMe", rememberMe)
                        editor.putString("username", username)
                        editor.putString("password", password)
                        editor.commit()


                        finish()}
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    // L'utilisateur n'existe pas
                }
            }
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

        binding.btnFacebookLogin.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT)
//                .setAction("ACTION") {
//                    // Responds to click on the action
//                }
                .show()
        }

        binding.btnGoogleLogin.setOnClickListener {
            Snackbar.make(contextView, getString(R.string.msg_coming_soon), Snackbar.LENGTH_SHORT)
//                .setAction("ACTION") {
//                    // Responds to click on the action
//                }
                .show()
        }

        binding.btnCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        startActivity(Intent(this, HomeActivity::class.java))
    }

    private fun validateEmail(): Boolean {
        binding.tiEmailLayout.isErrorEnabled = false

        if (binding.tiEmail.text.toString().isEmpty()) {
            binding.tiEmailLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiEmail.requestFocus()
            return false
        }else{
            binding.tiEmailLayout.isErrorEnabled = false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(binding.tiEmail.text.toString()).matches()) {
            binding.tiEmailLayout.error = getString(R.string.msg_check_your_email)
            binding.tiEmail.requestFocus()
            return false
        }else{
            binding.tiEmailLayout.isErrorEnabled = false
        }

        return true
    }

    private fun validatePassword(): Boolean {
        binding.tiPasswordLayout.isErrorEnabled = false

        if (binding.tiPassword.text.toString().isEmpty()) {
            binding.tiPasswordLayout.error = getString(R.string.msg_must_not_be_empty)
            binding.tiPassword.requestFocus()
            return false
        }else{
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        if (binding.tiPassword.text.toString().length < 6) {
            binding.tiPasswordLayout.error = getString(R.string.msg_check_your_characters)
            binding.tiPassword.requestFocus()
            return false
        }else{
            binding.tiPasswordLayout.isErrorEnabled = false
        }

        return true
    }

}