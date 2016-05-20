package in.ureport.tasks;

import android.content.Context;
import android.util.Log;

import java.util.List;

import in.ureport.R;
import in.ureport.helpers.AnalyticsHelper;
import in.ureport.helpers.ContactBuilder;
import in.ureport.models.User;
import in.ureport.flowrunner.models.Contact;
import in.ureport.models.rapidpro.Field;
import in.ureport.network.RapidProServices;
import in.ureport.tasks.common.ProgressTask;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class SaveContactTask extends ProgressTask<User, Void, Contact> {

    private static final String TAG = "SaveContactTask";

    private RapidProServices rapidProServices;

    public SaveContactTask(Context context) {
        super(context, R.string.load_message_save_user);
    }

    @Override
    protected Contact doInBackground(User... params) {
        try {
            User user = params[0];

            String rapidproEndpoint = getContext().getString(R.string.rapidpro_host_address1);
            String countryToken = getContext().getString(R.string.rapidpro_token);
            rapidProServices = new RapidProServices(rapidproEndpoint);

            Contact contact = getContactForUser(countryToken, user, getRegistrationDate(), countryToken);
            return rapidProServices.saveContact(countryToken, contact);
        } catch (Exception exception) {
            AnalyticsHelper.sendException(exception);
            Log.e(TAG, "doInBackground ", exception);
        }
        return null;
    }

    private Contact getContactForUser(String token, User user, Date registrationDate, String countryCode) {
        List<Field> fields = rapidProServices.loadFields(token);

        ContactBuilder contactBuilder = new ContactBuilder(fields);
        return contactBuilder.buildContactWithFields(user, registrationDate, countryCode);
    }

	private Date getRegistrationDate() {
        if(!newUser) return null;

        Date now = new Date();
        try {
            SntpClient client = new SntpClient();
            if (client.requestTime("pool.ntp.org", 4000)) {
                long ntpTimeMillis = client.getNtpTime();
                Log.i(TAG, "getRegistrationDate: ntpTimeMillis: " + new Date(ntpTimeMillis));
                if (ntpTimeMillis > 0)
                    now = new Date(ntpTimeMillis);
            }
        } catch(Exception exception) {
            Log.e(TAG, "getRegistrationDate: ", exception);
        }
        return now;
    }

    private List<CountryInfo> getCountryInfoList() {
        try {
            String json = IOHelper.loadJSONFromAsset(getContext(), "countryInfo.json");

            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT).create();
            Type type = new TypeToken<List<CountryInfo>>(){}.getType();

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(json);

            return gson.fromJson(jsonObject.get("geonames"), type);
        } catch (Exception exception) {
            Log.e(TAG, "doInBackground: ", exception);
        }
        return null;
    }

}
