package com.example.myguider.ui.explore

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myguider.R
import com.example.myguider.adapters.PlacesListAdapter
import com.example.myguider.adapters.TypeExploreListAdapter
import com.example.myguider.models.Place
import com.example.myguider.models.TypeExplore
import kotlinx.android.synthetic.main.fragment_explore.*
import org.json.JSONException
import org.json.JSONObject

class ExploreFragment : Fragment(), TypeExploreListAdapter.OnActionListener, PlacesListAdapter.OnActionListener {

    private lateinit var dashboardViewModel: ExploreViewModel

    internal var typeArray = ArrayList<TypeExplore>()
    internal var placesArray = ArrayList<Place>()
    private val URL_PLACES = "https://napps.pt/myguider/api/places/read.php"
    private val URL_TYPES = "https://napps.pt/myguider/api/type/read.php"
    internal var request: StringRequest? = null

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(ExploreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        return root
    }

    override fun onResume() {
        super.onResume()
        getTypes()
        getPlaces()
    }


    fun getTypes(){
        val requestQueue: RequestQueue

        requestQueue = Volley.newRequestQueue(context)

        request = object : StringRequest(Request.Method.POST, URL_TYPES,
            Response.Listener { response ->

                if (response.length > 0) {

                    try {

                        typeArray.clear()

                        val jsonObject = JSONObject(response)

                        val jsonArray = jsonObject.getJSONArray("records")

                        for (i in 0 until jsonArray.length()) {

                            val experienciaObj = jsonArray.getJSONObject(i)

                            val nome = experienciaObj.getString("name")
                            val id = experienciaObj.getInt("id")
                            val urlImage = experienciaObj.getString("photo")

                            typeArray.add(
                                TypeExplore(
                                    id,
                                    nome,
                                    urlImage
                                )
                            )
                        }

                        if (typeArray.size > 0) {
                            val adapterExperiencia =
                                TypeExploreListAdapter(context!!, typeArray, 0, this)
                            categoryList!!.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    RecyclerView.HORIZONTAL,
                                    false
                                ) as RecyclerView.LayoutManager?
                            categoryList!!.adapter = adapterExperiencia
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }, Response.ErrorListener {
                Log.d("error volley", it.toString())

            }) {
        }

        requestQueue.add<String>(request)
    }

    fun getPlaces(){
        val requestQueue: RequestQueue

        requestQueue = Volley.newRequestQueue(context)

        request = object : StringRequest(Request.Method.POST, URL_PLACES,
            Response.Listener { response ->

                if (response.length > 0) {

                    try {

                        typeArray.clear()

                        val jsonObject = JSONObject(response)

                        val jsonArray = jsonObject.getJSONArray("records")

                        for (i in 0 until jsonArray.length()) {

                            val experienciaObj = jsonArray.getJSONObject(i)

                            val nome = experienciaObj.getString("name")
                            val description = experienciaObj.getString("description")
                            val price = experienciaObj.getString("price")
                            val id = experienciaObj.getInt("id")
                            val urlImage = experienciaObj.getString("photo_url")

                            placesArray.add(
                                Place(id, nome, urlImage, description, price)
                            )
                        }

                        if (placesArray.size > 0) {
                            val adapterExperiencia =PlacesListAdapter(context!!, placesArray, 0, this)
                            placesList!!.layoutManager =
                                LinearLayoutManager(
                                    context,
                                    RecyclerView.HORIZONTAL,
                                    false
                                ) as RecyclerView.LayoutManager?
                            placesList!!.adapter = adapterExperiencia
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            }, Response.ErrorListener {
                Log.d("error volley", it.toString())

            }) {
        }

        requestQueue.add<String>(request)
    }

    override fun startActivity(context: Context, typeItem: TypeExplore, position: Int) {

    }

    override fun startActivity(context: Context, place: Place, position: Int) {

    }


}