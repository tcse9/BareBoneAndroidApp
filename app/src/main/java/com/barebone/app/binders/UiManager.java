package com.barebone.app.binders;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.view.View;

import com.barebone.app.BR;

public class UiManager extends BaseObservable {


    public int isLoadingProgressBar = View.VISIBLE;


    /**
     * Method to set progressbar visibility statusToday
     * @param isLoading
     */
    public void setLoadingProgressBarVisibility(int isLoading){
        this.isLoadingProgressBar = isLoading;
        notifyPropertyChanged(BR._all);
    }


    /**
     * Returns an integer value which determines if the progressbar will be shown or not
     * @return
     */
    @Bindable
    public int getLoadingProgressBarVisibility(){
        return this.isLoadingProgressBar;
    }

}
