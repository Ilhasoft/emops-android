package in.ureport.network;

import com.firebase.client.ValueEventListener;

import in.ureport.managers.FirebaseManager;

/**
 * Created by johncordeiro on 14/08/15.
 */
public class MissionServices extends ProgramServices {

    private static final String informationPath = "information";

    public void loadInformation(ValueEventListener listener) {
        FirebaseManager.getReference().child(informationPath).addValueEventListener(listener);
    }

}
