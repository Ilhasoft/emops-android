package in.ureport.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Locale;

import in.ureport.models.geonames.CountryInfo;
import in.ureport.models.geonames.Location;
import in.ureport.network.GeonamesServices;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class LoadStatesTask extends AsyncTask<Locale, Void, List<Location>> {

    private static final String TAG = "LoadStatesTask";

    @Override
    protected List<Location> doInBackground(Locale... params) {
        try {
            Locale locale = params[0];

            GeonamesServices services = new GeonamesServices();
            List<CountryInfo> countryInfos = services.getCountryInfo(locale.getCountry());

            Log.i(TAG, "doInBackground countryInfos: " + countryInfos);
            Log.i(TAG, "doInBackground locale: " + locale.getCountry());

            if (countryInfos.size() > 0) {
                CountryInfo countryInfo = countryInfos.get(0);
                return services.getStates(countryInfo.getGeonameId());
            }
        } catch(Exception exception) {
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }
}
