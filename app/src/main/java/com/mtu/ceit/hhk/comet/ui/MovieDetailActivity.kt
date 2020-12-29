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
import com.mtu.ceit.hhk.comet.data_models.Credits
import com.mtu.ceit.hhk.comet.data_models.DetailedMovie


import com.mtu.ceit.hhk.comet.databinding.MovieDetailBinding
import com.mtu.ceit.hhk.comet.ui.adapters.paging_adapter.MediaPagerAdapter
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_pagers.CastPager
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_pagers.ReviewPager
import com.mtu.ceit.hhk.comet.ui.fragments.movie_detail_pagers.InfoPager
import com.mtu.ceit.hhk.comet.ui.viewmodels.DetailedMovieViewModel
import com.mtu.ceit.hhk.comet.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private var _binding:MovieDetailBinding ? = null
    private val binding:MovieDetailBinding get () = _binding !!

    @ExperimentalCoroutinesApi
    val detailedVM:DetailedMovieViewModel by viewModels()

    private lateinit var detailedMovie:DetailedMovie
    private lateinit var credits: Credits

    private lateinit var mediaPagerAdapter: MediaPagerAdapter


    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        _binding = MovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)


        setUpToolbarback()

        collectDetailedMovie()

        collectCredits()


        setUpPager()



    }

    private fun setUpToolbarback(){
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }


    @ExperimentalCoroutinesApi
    private fun collectDetailedMovie(){
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

                        setImages(detailedMovie.poster_path,detailedMovie.backdrop_path)

                        setInfo(detailedMovie)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setImages(posterPath:String,backdrop: String){
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

    private fun setInfo(detailMovie : DetailedMovie){

        binding.apply {

            movieDetailTitle.text = detailMovie.title
            val year = detailMovie.release_date.split("-")[0]
            movieDetailTitle.append(" ( $year )")



            val genres  = detailMovie.genres
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

    private fun setCredits(credits: Credits){
        binding.apply {

            movieDetailDirectorText.append ("Director - ")
            for(i in credits.crews){



                if(i.job == "Director"){

                    movieDetailDirectorText.visibility= View.VISIBLE
                    movieDetailDirectorText.append("${i.name} and ")
                }

            }

        }
    }

    @ExperimentalCoroutinesApi
    private fun collectCredits(){

       lifecycleScope.launchWhenCreated {
           detailedVM.creditsFlow.collect {
               when(it) {
                   is Resource.Success -> {

                       credits = it.value
                       setCredits(credits)

                   }
                   is Resource.ERROR -> {
                       Toast.makeText(applicationContext,"${it.message}",
                               Toast.LENGTH_LONG).show()
                   }
                   else -> {

                   }


               }
           }
       }

    }

    private fun setUpPager(){
        mediaPagerAdapter = MediaPagerAdapter(supportFragmentManager,0)
        mediaPagerAdapter.addFragment(InfoPager()," Info ")
        mediaPagerAdapter.addFragment(CastPager()," Cast ")
        mediaPagerAdapter.addFragment(ReviewPager(), " Reviews ")


        binding.apply {

            movieDetailViewpager.adapter = mediaPagerAdapter
            movieDetailTablayout.setupWithViewPager(movieDetailViewpager)

        }

    }

    companion object{

        private const val EXTRAS_MOVIE_ID = "movie_id"

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