package in.ureport.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.ureport.models.geonames.CountryInfo;
import in.ureport.models.geonames.Location;
import in.ureport.models.holders.LocationInfo;
import in.ureport.network.GeonamesServices;

/**
 * Created by johncordeiro on 10/09/15.
 */
public class LocationInfoLoader extends AsyncTaskLoader<LocationInfo> {

    private static final String TAG = "StatesLoader";

    private final CountryInfo countryInfo;

    public LocationInfoLoader(Context context, CountryInfo countryInfo) {
        super(context);
        this.countryInfo = countryInfo;
    }

    @Override
    public LocationInfo loadInBackground() {
        return new LocationInfo(loadGeonamesStates(), null);
    }

    private List<Location> loadGeonamesStates() {
        try {
            GeonamesServices services = new GeonamesServices();

            if (countryInfo != null) {
                return services.getStates(countryInfo.getGeonameId());
            }
        } catch(Exception exception) {
            Log.e(TAG, "loadGeonamesStates ", exception);
        }
        return new ArrayList<>();
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

}
