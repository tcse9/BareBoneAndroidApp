package com.barebone.app.components;

import com.barebone.app.daggermodule.NetworkModule;
import com.barebone.app.viewmodels.LoginViewModel;

import javax.inject.Singleton;

import dagger.Component;


/**
 * {@link BaseComponents} are the components throughout the whole project
 */
@Singleton
@Component (modules = {NetworkModule.class})
public interface BaseComponents {

    //Inject method for each of the module where they have been implemented
    void inject(LoginViewModel viewModel);

}
