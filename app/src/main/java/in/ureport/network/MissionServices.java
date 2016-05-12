package in.ureport.network;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import in.ureport.managers.FirebaseManager;
import in.ureport.models.Mission;

/**
 * Created by johncordeiro on 14/08/15.
 */
public class MissionServices extends ProgramServices {

    private static final String informationPath = "information";

    public void loadInformation(ValueEventListener listener) {
        FirebaseManager.getReference().child(informationPath).addValueEventListener(listener);
    }

    public void saveMission(Mission mission) {
        FirebaseManager.getReference().child(informationPath)
                .child(mission.getKey()).setValue(mission);
    }

    public void removeMission(Mission mission) {
        FirebaseManager.getReference().child(informationPath)
                .child(mission.getKey()).removeValue();
    }

    public static Firebase getMissionsInformationsReference() {
        return FirebaseManager.getReference().child(informationPath);
    }

}
