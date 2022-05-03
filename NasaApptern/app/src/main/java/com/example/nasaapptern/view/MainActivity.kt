package com.example.nasaapptern.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nasaapptern.R
import com.example.nasaapptern.adapter.RecyclerAdapter
import com.example.nasaapptern.databinding.ActivityMainBinding
import com.example.nasaapptern.model.Photo
import com.example.nasaapptern.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var nasaData=ArrayList<Photo>()
    var nasaFilter=ArrayList<Photo>()
    var pictureUrl=ArrayList<String>()
    var tempDataHolder=ArrayList<Photo>()
    var filter=ArrayList<Photo>()
    var currentTab:String="CURIOSITY"
    private lateinit var viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitializeEvents()

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    setRecyclerAdapter(it.text.toString())
                    currentTab=it.text.toString()

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.let {
                    setRecyclerAdapter(it.text.toString())
                    currentTab=it.text.toString()
                }

            }
        })

    }

    private fun InitializeEvents() {
        binding.tvError.visibility=View.INVISIBLE
        binding.tbMain.setTitle(R.string.title)
        binding.tbMain.setTitleTextColor(resources.getColor(android.R.color.white))
        setSupportActionBar(binding.tbMain)
        SetRecycleConfigs()
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        getDataFromViewModel()
        binding.tabLayout.tabGravity=TabLayout.GRAVITY_FILL

    }

    private fun getDataFromViewModel() {
        viewModel.getAllDatas()
        viewModel.dataList.observe(this@MainActivity, Observer {
            tempDataHolder.addAll(it!!.photos!!)
            addToList()
            setRecyclerAdapter(getString(R.string.curiosity))
        })
    }

    private fun addToList() {
        try {
            for(i in nasaData.size..(nasaData.size+20)){
                nasaData.add(tempDataHolder.get(i))
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    fun setRecyclerAdapter(cameraName:String){
        if(nasaData.size==0){
            binding.tvError.visibility= View.VISIBLE
        }else{
            binding.tvError.visibility=View.INVISIBLE



            when(cameraName){
                getString(R.string.curiosity)->{
                    nasaFilter=nasaData.filter {
                        it.camera!!.name.equals(getString(R.string.fhaz)) ||
                                it.camera!!.name.equals(getString(R.string.rhaz)) ||
                                it.camera!!.name.equals(getString(R.string.mast)) ||
                                it.camera!!.name.equals(getString(R.string.chemcam)) ||
                                it.camera!!.name.equals(getString(R.string.mahli)) ||
                                it.camera!!.name.equals(getString(R.string.navcam))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.spirit) ->{
                    nasaFilter=nasaData.filter {
                        it.camera!!.name.equals(getString(R.string.fhaz))   ||
                                it.camera!!.name.equals(getString(R.string.rhaz))   ||
                                it.camera!!.name.equals(getString(R.string.navcam)) ||
                                it.camera!!.name.equals(getString(R.string.pancam)) ||
                                it.camera!!.name.equals(getString(R.string.minites))

                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.opportunity)->{
                    nasaFilter=nasaData.filter {
                        it.camera!!.name.equals(getString(R.string.fhaz)) ||
                                it.camera!!.name.equals(getString(R.string.rhaz)) ||
                                it.camera!!.name.equals(getString(R.string.navcam)) ||
                                it.camera!!.name.equals(getString(R.string.pancam)) ||
                                it.camera!!.name.equals(getString(R.string.minites))
                    } as java.util.ArrayList<Photo>

                }

            }
            Toast.makeText(applicationContext,nasaFilter.size.toString()+" Anan",Toast.LENGTH_LONG).show()
            when(cameraName){
                getString(R.string.fhaz)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.fhaz))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.rhaz)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.rhaz))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.mast)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.mast))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.chemcam)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.chemcam))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.mardi)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.mardi))
                    } as java.util.ArrayList<Photo>
                }

                getString(R.string.navcam)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.navcam))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.pancam)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.pancam))
                    } as java.util.ArrayList<Photo>
                }
                getString(R.string.minites)->{
                    nasaFilter=nasaFilter.filter {
                        it.camera!!.name.equals(getString(R.string.minites))
                    } as java.util.ArrayList<Photo>
                }

            }

            convertToStringList()
            if(pictureUrl.size==0){
                binding.tvError.visibility= View.VISIBLE
            }

            //To send a function as a parameter
            val adapter=RecyclerAdapter(this::itemClick,pictureUrl)
            binding.rvPhotoList.adapter=adapter
            binding.rvPhotoList.adapter!!.notifyDataSetChanged()


        }

    }
    @SuppressLint("SetTextI18n")
    fun itemClick(index:Int){
        val iv = nasaFilter[index]
        val dialogBuilder=AlertDialog.Builder(this)
        val view=layoutInflater.inflate(R.layout.item_detail,null)
        val imageView:ImageView=view.findViewById(R.id.imageView)
        val EarthDate:TextView=view.findViewById(R.id.tvEarthDate)
        val RoverName:TextView=view.findViewById(R.id.tvRoverName)
        val CameraName:TextView=view.findViewById(R.id.tvCameraName)
        val Stats:TextView=view.findViewById(R.id.tvStats)
        val Launch:TextView=view.findViewById(R.id.tvLaunch)
        val Land:TextView=view.findViewById(R.id.tvLand)
        val exitButton:Button=view.findViewById(R.id.buttonExit)


        dialogBuilder.setView(view)
        val dialog=dialogBuilder.create()
        dialog.show()
        Picasso.get().load(iv.img_src).resize(275,175).into(imageView)
        EarthDate.text=getString(R.string.earth_date)+" : "+ iv.earth_date
        RoverName.text=getString(R.string.rover_name)+" : "+iv.rover!!.name
        CameraName.text=getString(R.string.camera_name)+" : "+iv.camera!!.name
        Stats.text=getString(R.string.status)+" : "+iv.rover!!.status
        Launch.text=getString(R.string.launch)+" : "+iv.rover!!.launch_date
        Land.text=getString(R.string.land)+" : "+ iv.rover!!.launch_date

        exitButton.setOnClickListener {
            dialog.dismiss()
        }

    }
    fun SetRecycleConfigs(){
        val layoutManager=GridLayoutManager(this,2)
        layoutManager.orientation=GridLayoutManager.VERTICAL
        layoutManager.scrollToPosition(0)
        binding.rvPhotoList.setHasFixedSize(true)
        binding.rvPhotoList.layoutManager=layoutManager

        binding.rvPhotoList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastItem=layoutManager.findLastVisibleItemPosition()

                if(lastItem>nasaData.size*0.8){
                    addToList()
                    setRecyclerAdapter(currentTab)
                }
            }
        })


    }
    fun convertToStringList(){
        pictureUrl.removeAll(pictureUrl)
        for(i in nasaFilter){
            pictureUrl.add(i.img_src!!)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      menuInflater.inflate(R.menu.item_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      //  nasaFilter.removeAll(nasaFilter)
        when(item.itemId){
            R.id.fhaz ->{
             setRecyclerAdapter(getString(R.string.fhaz))
                return true
            }
            R.id.rhaz ->{
                setRecyclerAdapter(getString(R.string.rhaz))
                return true
            }
            R.id.mast ->{
                setRecyclerAdapter(getString(R.string.mast))
                return true
            }
            R.id.chemcam ->{
                setRecyclerAdapter(getString(R.string.chemcam))
                return true
            }
            R.id.mahli ->{
                setRecyclerAdapter(getString(R.string.mahli))
                return true
            }
            R.id.navcam ->{
                setRecyclerAdapter(getString(R.string.navcam))
                return true
            }
            R.id.pancam ->{
                setRecyclerAdapter(getString(R.string.pancam))
                return true
            }
            R.id.minites ->{
                setRecyclerAdapter(getString(R.string.minites))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    
}