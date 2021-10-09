package com.gietuportalofficial.demoapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gietuportalofficial.demoapp.Model.Model
import com.gietuportalofficial.demoapp.R
import com.gietuportalofficial.demoapp.UtilsC
import com.gietuportalofficial.demoapp.adapter.AdapterXt
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {


    var baseurl="https://localhost/poetryapis/postdata.php"
    lateinit var adapt: AdapterXt
    var modelss= arrayListOf<Model>()
    lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recyclerview)
        val lmger=LinearLayoutManager(activity)
        recyclerView.layoutManager=lmger

       // apicall() as i tested apicall on my localhost sothat i am using static response placed in the UtilsC
        datafetch()


    }


    fun datafetch(){
        val data=UtilsC.value.data
        val newdata=data.replace("\n","")
        val newaga=newdata.replace("\\","")

        try {

            val jsonobj=JSONArray(newaga)
            for (i in 0 until jsonobj.length())
            {
                val objs=jsonobj.get(i)
                val objj=JSONObject(objs.toString())
                val posttype=objj.get("posttype")
                val content=objj.get("content")
                val likes=objj.get("likes")
                val comments=objj.get("comments")
                val username=objj.get("userName")
                val userimage=objj.get("userImage")
                modelss.add(Model(posttype.toString(),content.toString(),likes.toString(),comments.toString(),username.toString(),userimage.toString()))
            }
            activity?.runOnUiThread {
                adapt=AdapterXt(requireActivity(),modelss)
                recyclerView.adapter=adapt
                adapt.notifyDataSetChanged()
            }
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    fun apicall(){

        try {
            val queue = Volley.newRequestQueue(requireContext())
            val stringRequest: StringRequest = object : StringRequest(
                Method.GET, baseurl,
                Response.Listener { response ->
                    try {

                        val jsonobj=JSONArray(response.toString())
                        for (i in 0 until jsonobj.length())
                        {
                            val objs=jsonobj.get(i)
                            val objj=JSONObject(objs.toString())
                            val posttype=objj.get("posttype")
                            val content=objj.get("content")
                            val likes=objj.get("likes")
                            val comments=objj.get("comments")
                            val username=objj.get("userName")
                            val userimage=objj.get("userImage")
                            modelss.add(Model(posttype.toString(),content.toString(),likes.toString(),comments.toString(),username.toString(),userimage.toString()))


                        }
                        activity?.runOnUiThread {
                            adapt=AdapterXt(requireActivity(),modelss)
                            recyclerView.adapter=adapt
                            adapt.notifyDataSetChanged()
                        }
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    Log.d("error", "onCreate: $error")
                }) {
            }
            stringRequest.retryPolicy = DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            queue.add(stringRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}