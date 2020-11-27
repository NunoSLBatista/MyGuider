package com.example.myguider

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.wasabeef.blurry.Blurry

import android.graphics.BitmapFactory
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_first.mainImage
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.emailTextField
import kotlinx.android.synthetic.main.activity_login.passwordTextField
import kotlinx.android.synthetic.main.activity_login.skipLogin
import kotlinx.android.synthetic.main.activity_login.viewMain
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {

    private val url = "https://napps.pt/myguider/api/user/login.php"
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(intent.getStringExtra("emailRegisted") != null){
            Snackbar.make(viewMain, "Conta criada com sucesso", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.themeRegister))
                .setTextColor(resources.getColor(R.color.white))
                .show()
            emailTextField.setText(intent.getStringExtra("emailRegisted"))
        }

        sharedPreferences = getSharedPreferences("myguider", Context.MODE_PRIVATE)

        viewMain.setOnTouchListener(object : OnSwipeTouchListener(this@LoginActivity) {
            override fun onSwipeUp() {
                super.onSwipeUp()
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
            }
        })

        skipLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        })

        loginBtn.setOnClickListener(View.OnClickListener {
            loginRequest(emailTextField.text.toString(), passwordTextField.text.toString())
        })

        val image = BitmapFactory.decodeResource(
            applicationContext.getResources(),
            R.drawable.bg_03
        )
        Blurry.with(applicationContext).radius(25)
            .sampling(1)
            .color(Color.argb(20, 0, 0, 0)).from(image).into(mainImage)
    }


    fun loginRequest(email : String, password: String){

        val checkLogin = checkLoginInfo(email, password)

        if(checkLogin != "" ){
            Snackbar.make(loginBtn, checkLogin, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.white))
                .setTextColor(resources.getColor(R.color.black))
                .show()
        } else {
            val postObject = JSONObject()
            val queue = Volley.newRequestQueue(this)

            try {
                postObject.put("email", email)
                postObject.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val objRequest = JsonObjectRequest(
                Request.Method.POST, url, postObject,
                Response.Listener { response ->

                    if(response.has("message")){
                        Snackbar.make(loginBtn, "Dados incorretos", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.white))
                            .setTextColor(resources.getColor(R.color.black))
                            .show()
                    } else {
                        val jsonObject = JSONObject(response.toString())

                        val userId = jsonObject.getInt("id")
                        val name = jsonObject.getString("name")
                        val userEmail = jsonObject.getString("email")
                        val photo_url = jsonObject.getString("user_photo")

                        sharedPreferences!!.edit().putInt("userId", userId).apply()
                        sharedPreferences!!.edit().putString("name", name).apply()
                        sharedPreferences!!.edit().putString("email", userEmail).apply()
                        sharedPreferences!!.edit().putString("photo_url", photo_url).apply()

                        val intentMenu = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intentMenu)

                    }
                },
                Response.ErrorListener { error ->
                    Log.e("OnError", error.toString())
                })

            queue.add(objRequest)
        }

    }

    fun checkLoginInfo(email : String, password: String) : String{

        if(!email.isEmailValid()){
            return "Email incorreto"
        }else if(password.length < 6) {
            return "Password tem que ter mais que 6 caracteres"
        }

        return ""
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }


}
