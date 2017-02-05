package br.ufscar.connect;

import android.app.Application;

import br.ufscar.connect.interfaces.ConnectUFSCarApi;

/**
 * Created by bruno on 04/02/17.
 */

public class ConnectApplication extends Application {

    ConnectUFSCarApi api = null;

    @Override
    public void onCreate() {
        super.onCreate();
        getApi();
    }

    public ConnectUFSCarApi getApi() {
        if (this.api == null) {
            this.api = ConnectUFSCarApi.RETROFIT.create(ConnectUFSCarApi.class);
        }
        return this.api;
    }
}
