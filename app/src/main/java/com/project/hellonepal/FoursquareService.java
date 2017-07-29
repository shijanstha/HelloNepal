
package com.project.hellonepal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoursquareService {

    // A request to snap the current user to a place via the Foursquare API.
    @GET("venues/search?v=20161101&limit=50")
    Call<FoursquareJSON> snapToPlace(@Query("client_id") String clientID,
                                     @Query("client_secret") String clientSecret,
                                     @Query("ll") String ll,
                                     @Query("llAcc") double llAcc);

    // A request to search for nearby coffee shop recommendations via the Foursquare API.
   @GET("search/recommendations?v=20161101&intent=coffee")
    Call<FoursquareJSON> searchCoffee(@Query("client_id") String clientID,
                                   @Query("client_secret") String clientSecret,
                                     @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=food")
    Call<FoursquareJSON> searchFood(@Query("client_id") String clientID,
                                    @Query("client_secret") String clientSecret,
                                    @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=sights")
    Call<FoursquareJSON> sights(@Query("client_id") String clientID,
                                    @Query("client_secret") String clientSecret,
                                    @Query("ll") String ll,
                                    @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=drinks")
    Call<FoursquareJSON> drinks(@Query("client_id") String clientID,
                                @Query("client_secret") String clientSecret,
                                @Query("ll") String ll,
                                @Query("llAcc") double llAcc);

    @GET("search/recommendations?v=20161101&intent=arts")
    Call<FoursquareJSON> arts(@Query("client_id") String clientID,
                                @Query("client_secret") String clientSecret,
                                @Query("ll") String ll,
                                @Query("llAcc") double llAcc);
}