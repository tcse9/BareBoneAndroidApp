package com.barebone.app.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.barebone.app.core.ApplicationSingleton;
import com.barebone.app.daggermodule.NetworkModule;
import com.barebone.app.utils.IAppConstants;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {

    @Inject
    NetworkModule networkModule;

    private MutableLiveData<Object> fakeObjectLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * View model constructor
     * @param application of type {@link Application}
     */
    public LoginViewModel(Application application){
        super(application);

        //Injecting ApiCallerModule
        ApplicationSingleton.getInstance().getBaseComponents().inject(this);
    }


    /**
     * Calls the Login api
     */
    public void callApiLogin(){

        disposables.add(networkModule.call().getNetworkCallInterface().getWeatherBase(IAppConstants.API_KEY, IAppConstants.LOCATION_LAT+","+IAppConstants.LOCATION_LONG)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()));

    }


    /**
     * Returns an {@link DisposableObserver} type object
     * @return
     */
    private DisposableObserver<Object> getObserver(){

        return new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                Log.e("OBJ", "is: "+o);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.e("OBJ", "is: ");
            }
        };
    }



    public MutableLiveData<Object> getFakeObjectLiveData() {
        return fakeObjectLiveData;
    }


}
