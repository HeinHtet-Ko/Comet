package com.mtu.ceit.hhk.comet.network

import com.mtu.ceit.hhk.comet.BuildConfig
import com.mtu.ceit.hhk.comet.data_models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.annotation.Generated

interface TMDB_API {


    private val TMDB_KEY: String
        get() = BuildConfig.TMDB_KEY

    @GET("3/movie/now_playing")
    suspend fun getNowMovies(@Query("api_key")key : String =BuildConfig.TMDB_KEY):MovieResponse

    @GET("3/movie/popular")
    suspend fun getComingMovies(@Query("api_key")key : String = TMDB_KEY):MovieResponse




    @GET("3/search/movie")
    suspend fun searchMovies(@Query("api_key") key:String = TMDB_KEY,
                             @Query ("query") query:String ,
                             @Query("page") page:Int ):MovieResponse


    @GET("3/search/tv")
    suspend fun searchTVs(@Query("api_key") key:String = TMDB_KEY,
                          @Query ("query") query:String ,
                          @Query("page") page:Int ):TVResponse


    @GET("3/movie/{movie_id}")
    suspend fun getDetailedMovie(@Path("movie_id")movID:Int ,
                                    @Query("api_key") key:String = TMDB_KEY):DetailedMovie



    @GET("3/movie/{movie_id}/credits")
    suspend fun getCastAndCrew(@Path("movie_id") movID:Int ,
                               @Query("api_key") key:String = TMDB_KEY):Credits

    @GET("3/person/{person_id}")
    suspend fun getPersonDetail(@Path("person_id") personID:Int ,
                                @Query("api_key") key:String = TMDB_KEY):PersonDetail

    @GET("3/person/{person_id}/tv_credits")
    suspend fun getTVCredits(@Path("person_id") personID: Int,
    @Query("api_key") key: String = TMDB_KEY):TVCredits

    @GET("3/person/{person_id}/movie_credits")
    suspend fun getMovieCredits(@Path("person_id") personID: Int,
                             @Query("api_key") key: String = TMDB_KEY):MovieCredits


    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
            @Path("movie_id") movID: Int,
            @Query("api_key") key:String = TMDB_KEY):ReviewResult




    @GET("3/tv/on_the_air")
    suspend fun getTVOTA(@Query("api_key")key : String =BuildConfig.TMDB_KEY):TVResponse

    @GET("3/tv/popular")
    suspend fun getTVPopular(@Query("api_key")key : String =BuildConfig.TMDB_KEY):TVResponse

    @GET("3/tv/{tv_id}")
    suspend fun getDetailedTV(@Path("tv_id") tv_id:Int ,
                              @Query("api_key")key : String =BuildConfig.TMDB_KEY):DetailedTV
}