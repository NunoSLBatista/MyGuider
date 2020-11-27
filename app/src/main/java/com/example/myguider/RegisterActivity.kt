package com.example.myguider

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import jp.wasabeef.blurry.Blurry
import kotlinx.android.synthetic.main.activity_first.*

import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.mainImage
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private val url = "https://napps.pt/myguider/api/user/create.php"
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        backLogin.setOnClickListener(View.OnClickListener {
            this.finish()
        })

        val image = BitmapFactory.decodeResource(
            applicationContext.getResources(),
            R.drawable.bg_03
        )

        Blurry.with(applicationContext).radius(25)
            .sampling(1)
            .color(Color.argb(20, 0, 0, 0)).from(image).into(mainImage)

        registBtn.setOnClickListener(View.OnClickListener {
            val email = emailTextField.text.toString()
            val password = passwordTextField.text.toString()
            val password2 = passwordTextField2.text.toString()
            val name = nameTextField.text.toString()
            registerUser(email, name, password, password2)

        })

    }

    fun registerUser(email: String, name: String, password: String, password2: String){
        if(!checkRegisterInfo(email, name, password, password2)){

        } else {
            val postObject = JSONObject()
            val queue = Volley.newRequestQueue(this)

            try {
                //historyObject.put("id","1");
                postObject.put("email", email)
                postObject.put("password", password)
                postObject.put("name", name)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val objRequest = JsonObjectRequest(
                Request.Method.POST, url, postObject,
                Response.Listener { response ->
                    val responseObject = JSONObject(response.toString())
                    val message = responseObject.getString("message")
                    if(message.contains("created")){
                        //Toast.makeText(applicationContext, "Conta criada com sucesso.", Toast.LENGTH_LONG).show()
                        val intentLogin= Intent(applicationContext, LoginActivity::class.java)
                        intentLogin.putExtra("emailRegisted", emailTextField.text.toString())
                        startActivity(intentLogin)

                    } else if (message.contains("email")){
                        Snackbar.make(backLogin, "O email já está em uso.", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.themeRegister))
                            .setTextColor(resources.getColor(R.color.white))
                            .show()
                        //Toast.makeText(applicationContext, "O email já está em uso.", Toast.LENGTH_LONG).show()

                    } else {
                        Snackbar.make(backLogin, "Tente novamente.", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.themeRegister))
                            .setTextColor(resources.getColor(R.color.white))
                            .show()
                        //  Toast.makeText(applicationContext, "Tente novamente", Toast.LENGTH_LONG).show()
                    }

                },
                Response.ErrorListener { error ->
                    Log.e("OnError", error.toString())
                })

            queue.add(objRequest)
        }

    }

    fun checkRegisterInfo(email : String, name: String, password: String, password2: String) : Boolean{

        if(!email.isEmailValid()){
            Snackbar.make(backLogin, "O formato do email esta errado", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.themeRegister))
                .setTextColor(resources.getColor(R.color.white))
                .show()
            //Toast.makeText(applicationContext, "O formato do email esta errado.", Toast.LENGTH_LONG).show()
            return false
        } else if(password.length < 6){
            Snackbar.make(backLogin, "A password tem que ter 6 caractéres", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.themeRegister))
                .setTextColor(resources.getColor(R.color.white))
                .show()
            //Toast.makeText(applicationContext, "A password tem que ter 6 caractéres", Toast.LENGTH_LONG).show()
            return false
        }else if(password != password2){
            Snackbar.make(backLogin, "As passwords não coincidem", Snackbar.LENGTH_SHORT)
                .setBackgroundTint(resources.getColor(R.color.themeRegister))
                .setTextColor(resources.getColor(R.color.white))
                .show()
        }
        return true
    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }



}
