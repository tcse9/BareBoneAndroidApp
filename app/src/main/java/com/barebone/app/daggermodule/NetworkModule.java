package com.barebone.app.daggermodule;

import com.barebone.app.core.ApplicationSingleton;
import com.barebone.app.network.ApiClient;
import com.barebone.app.network.NetworkCallInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    private NetworkCallInterface networkCallInterface;

    @Provides
    @Singleton
    public NetworkModule call(){
        Retrofit retrofit = ApiClient.getInstance(ApplicationSingleton.getInstance());
        networkCallInterface = retrofit.create(NetworkCallInterface.class);

        return this;
    }


    public NetworkCallInterface getNetworkCallInterface(){

        return networkCallInterface;
    }


}
