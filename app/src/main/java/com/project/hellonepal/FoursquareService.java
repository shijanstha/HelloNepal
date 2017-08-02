
package com.project.hellonepal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {

    // A request to snap the current user to a place via the Foursquare API.
//    20161101
    @GET("venues/search?v=20170731&limit=50")
    Call<FoursquareJSON> snapToPlace(@Query("client_id") String clientID,
                                     @Query("client_secret") String clientSecret,
                                     @Query("ll") String ll,
                                     @Query("llAcc") double llAcc);

    // A request to search for nearby coffee shop recommendations via the Foursquare API.
   @GET("search/recommendations?v=20170731&intent=coffee&limit=50")
    Call<FoursquareJSON> searchCoffee(@Query("client_id") String clientID,
                                   @Query("client_secret") String clientSecret,
                                     @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20170731&intent=food&limit=50")
    Call<FoursquareJSON> searchFood(@Query("client_id") String clientID,
                                    @Query("client_secret") String clientSecret,
                                    @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20170731&intent=sights&limit=50&radius=10000")
    Call<FoursquareJSON> sights(@Query("client_id") String clientID,
                                    @Query("client_secret") String clientSecret,
                                    @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20170731&intent=drinks&limit=50")
    Call<FoursquareJSON> drinks(@Query("client_id") String clientID,
                                @Query("client_secret") String clientSecret,
                                @Query("ll") String ll,
                                @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20170731&intent=arts&limit=50&radius=10000")
    Call<FoursquareJSON> arts(@Query("client_id") String clientID,
                                @Query("client_secret") String clientSecret,
                                @Query("ll") String ll,
                                @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20170731&intent=shops&limit=50&radius=10000")
    Call<FoursquareJSON> shops(@Query("client_id") String clientID,
                              @Query("client_secret") String clientSecret,
                              @Query("ll") String ll,
                              @Query("llAcc") double llAcc);

//    @GET("venues/search?v=20170731&intent=browse&radius=10000&limit=50&categoryId=56aa371be4b08b9a8d5734db")
//    Call<FoursquareJSON> shops(@Query("client_id") String clientID,
//                               @Query("client_secret") String clientSecret,
//                               @Query("ll") String ll,
//                               @Query("llAcc") double llAcc);
}