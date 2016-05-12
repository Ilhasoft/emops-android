package in.ureport.network;

import com.firebase.client.Firebase;

import in.ureport.managers.MissionManager;
import in.ureport.managers.FirebaseManager;

/**
 * Created by johncordeiro on 05/09/15.
 */
public abstract class ProgramServices {

    public static final String missionPath = "mission";

    protected Firebase getMission() {
        return FirebaseManager.getReference().child(missionPath);
    }

    protected Firebase getDefaultRoot() {
        return FirebaseManager.getReference().child(missionPath)
                .child(MissionManager.getCurrentMission().getKey());
    }

    protected Firebase getRootByCode(String countryCode) {
        return FirebaseManager.getReference().child(missionPath)
                .child(countryCode);
    }

}
