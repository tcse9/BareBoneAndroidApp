package com.barebone.app.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.barebone.app.BuildConfig;
import com.barebone.app.components.BaseComponents;
import com.barebone.app.components.DaggerBaseComponents;
import com.barebone.app.daggermodule.NetworkModule;
import com.barebone.app.network.ApiClient;
import com.barebone.app.network.NetworkCallInterface;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Retrofit;

public class ApplicationSingleton extends Application {

    private static ApplicationSingleton sInstance;
    private SharedPreferences mPref;
    private NetworkCallInterface networkCallInterface;
    private BaseComponents baseComponents;

    public static ApplicationSingleton getInstance() {
        return sInstance;
    }

   /* @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }*/



    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();
    }


    /**
     * Initializes the singletone instance with other singletones.
     */
    private void initializeInstance() {

        //initializing the application preference file with MODE_PRIVATE
        mPref = this.getApplicationContext().getSharedPreferences(BuildConfig.APPLICATION_ID+"_pref", MODE_PRIVATE);
        baseComponents = DaggerBaseComponents.builder().networkModule(new NetworkModule()).build();
    }

    public SharedPreferences getSharedPreference()
    {
        return mPref;
    }





    /**
     * Returns true if internet is connected, false otherwise.
     * @return
     */

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    /**
     * Prints the SHA1 hash key, useful for Facebook, Google apis etc.
     * @param pContext
     * @return
     */
    public String printHashKey(Context pContext) {
        String hashKey = "";
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = new String(Base64.encode(md.digest(), 0));
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("KEY_HASH", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("KEY_HASH", "printHashKey()", e);
        }
        return  hashKey;
    }


    /**
     * Returns true if a URL is exists, false otherwise.
     * @param URLName
     * @return
     */
    public boolean isUrlExists(String URLName){
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Returns a {@link String} after parsing them in {@link Date} according to input and output format.
     * @param inputFormat
     * @param outputFormat
     * @param value
     * @return
     */

    public String getFormattedDateString(String inputFormat, String outputFormat, String value){

        SimpleDateFormat inputPattern = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outputPattern = new SimpleDateFormat(outputFormat);

        Date date = null;
        String str = null;

        try {
            date = inputPattern.parse(value);
            str = outputPattern.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * Returns int value in dp
     * @param px
     * @return
     */
    public int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Returns int value in px
     * @param dp
     * @return
     */
    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }




    /**
     * Returns a {@link String} as the device id i.e. ANDROID_ID.
     * @return
     */
    public String getDeviceID(){
        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }


    /**
     * Returns a {@link String} after converting a {@link JsonElement} object.
     * @param jsonElement
     * @return
     */
    public String convertJsonElementToString(JsonElement jsonElement){
        Gson gson = new Gson();
        JsonElement element = gson.fromJson (jsonElement.toString(), JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();

        return jsonObj.toString();
    }


    /**
     * Returns true if the param {@link CharSequence} is a valid email id.
     * @param target
     * @return
     */
    public boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }



    /**
     * Creates a networl call nterface based on retrofit default client.
     * @return
     */
    public NetworkCallInterface getNetworkCallInterface(){
        Retrofit retrofit = ApiClient.getInstance(this);
        networkCallInterface = retrofit.create(NetworkCallInterface.class);

        return networkCallInterface;
    }


    /**
     * Returns the base dagger component used in this app
     * @return
     */
    public BaseComponents getBaseComponents() {
        return baseComponents;
    }




}