package com.mtu.ceit.hhk.comet.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie
import com.mtu.ceit.hhk.comet.databinding.ActivityMovieDetailBinding
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {


    private var _binding:ActivityMovieDetailBinding ? = null
    private val binding:ActivityMovieDetailBinding get () = _binding !!

    private  val detailedVM:DetailedMovieViewModel by viewModels()
    private var movieID:Int = 0
    private lateinit var detailedMovie:DetailedMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieID = intent.getIntExtra(EXTRAS_MOVIE_ID,0)

        setSupportActionBar(binding.mainDetailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        fetchFlow()


        lifecycleScope.launchWhenResumed {
            detailedVM.movieFlow.collect { it ->
                when(it){
                   is Resource.Success -> {

                       detailedMovie = it.value
                       detailedMovie
                       posterSetUp(detailedMovie.poster_path)
                       backdropSetup(detailedMovie.backdrop_path)

                       this@MovieDetailActivity.title = detailedMovie.title



                   }
                   else -> {

                   }
               }
            }
        }


    }

     private fun fetchFlow(){
         lifecycleScope.launchWhenCreated {
             detailedVM.fetchDetailedMovie(movieID)
         }
     }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    private fun posterSetUp(posterPath:String){
        Glide.with(this@MovieDetailActivity)
                .asBitmap()
                .load("http://image.tmdb.org/t/p/w500${posterPath}")
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.movieDetailPoster.setImageBitmap(resource)

                        val builder =  Palette.Builder(resource)

                        builder.generate {palette ->
                            // window.statusBarColor =palette?.darkVibrantSwatch!!.rgb
                            val darkMuted = palette?.darkMutedSwatch?.rgb
                            val darkVibrant = palette?.darkVibrantSwatch?.rgb
                            val dominant = palette?.dominantSwatch?.rgb
                            val lightVibrant = palette?.lightVibrantSwatch?.rgb
                            val lightMuted = palette?.lightMutedSwatch?.rgb


                            binding.mainDetailBack.setBackgroundColor( darkMuted ?: darkVibrant ?: dominant ?: lightVibrant ?: lightMuted ?: R.color.purple_500)


                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                })




    }

    private fun backdropSetup(backdrop:String){
        Glide.with(this@MovieDetailActivity)
                .load("http://image.tmdb.org/t/p/original${backdrop}")
                .apply(RequestOptions.bitmapTransform(VignetteFilterTransformation()))
                .into(binding.movieDetailBackdrop)

        binding.movieDetailOverview.text = detailedMovie.overview
    }

    companion object{

        internal const val EXTRAS_MOVIE_ID = "movie_id"

        fun navigate(context: Context,movID:Int):Intent{


            val intent = Intent(context,MovieDetailActivity::class.java)
            intent.putExtra(EXTRAS_MOVIE_ID,movID)
            return intent
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}