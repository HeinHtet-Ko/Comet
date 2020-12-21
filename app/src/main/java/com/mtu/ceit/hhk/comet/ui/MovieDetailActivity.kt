package com.mtu.ceit.hhk.comet.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.mtu.ceit.hhk.comet.R
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie


import com.mtu.ceit.hhk.comet.databinding.MovieDetailBinding
import com.mtu.ceit.hhk.comet.ui.fragments.MediaPagerAdapter
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments.CastPager
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments.CommentPager
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_fragments.InfoPager
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private var _binding:MovieDetailBinding ? = null
    private val binding:MovieDetailBinding get () = _binding !!

    val detailedVM:DetailedMovieViewModel by viewModels()
    private var movieID:Int = 0
    private lateinit var detailedMovie:DetailedMovie

    private lateinit var mediaPagerAdapter: MediaPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = MovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        movieID = intent.getIntExtra(EXTRAS_MOVIE_ID,0)

        /* set up back button */
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)




        fetchFlow()
        collect()
        setUpPager()

    }


    private fun collect(){
        lifecycleScope.launchWhenCreated {
            detailedVM.movieFlow.collect { it ->
                when(it){
                    is Resource.ERROR -> {
                        Toast.makeText(applicationContext,"There was an error . Try Again",Toast.LENGTH_LONG).show()
                    }
                    is Resource.Success -> {

                        detailedMovie = it.value

                        binding.movieDetailShimmer.stopShimmer()

                        binding.shimmerLayout.apply {
                           shimmerMain.visibility = View.GONE

                        }

                        collectImages(detailedMovie.poster_path,detailedMovie.backdrop_path)

                        collectInfo()

                        this@MovieDetailActivity.title = detailedMovie.title



                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.movieDetailShimmer.startShimmer()

    }

    override fun onPause() {
        super.onPause()
        binding.movieDetailShimmer.stopShimmer()
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

    private fun collectImages(posterPath:String,backdrop: String){
        Glide.with(this@MovieDetailActivity)
                .load("http://image.tmdb.org/t/p/w500${posterPath}")
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.error)
                .into(binding.movieDetailPoster)

        Glide.with(this@MovieDetailActivity)
            .load("http://image.tmdb.org/t/p/original${backdrop}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(RequestOptions.bitmapTransform(VignetteFilterTransformation()))
            .error(R.color.comet)
            .into(binding.movieDetailBackdrop)
    }

    private fun collectInfo(){

        binding.apply {

            movieDetailTitle.text = detailedMovie.title
            val year = detailedMovie.release_date.split("-")[0]
            movieDetailTitle.append(" ( $year )")



            val genres  = detailedMovie.genres
            movieDetailGenresText.append(" Genres - ")
            for(i in genres)
            {
                movieDetailGenresText.append(i.name)
                movieDetailGenresText.append(" | ")

                if(genres.indexOf(i) == 2)
                    return

            }
        }


    }

    private fun setUpPager(){
        mediaPagerAdapter = MediaPagerAdapter(supportFragmentManager,0)
        mediaPagerAdapter.addFragment(InfoPager()," Info ")
        mediaPagerAdapter.addFragment(CastPager()," Cast ")
        mediaPagerAdapter.addFragment(CommentPager(), " Comment ")


        binding.apply {

            movieDetailViewpager.adapter = mediaPagerAdapter
            movieDetailTablayout.setupWithViewPager(movieDetailViewpager)

        }

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