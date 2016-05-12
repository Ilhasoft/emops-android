package in.ureport.managers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import in.ureport.helpers.ValueEventListenerAdapter;
import in.ureport.models.Mission;
import in.ureport.network.MissionServices;

/**
 * Created by johncordeiro on 7/23/15.
 */
public class MissionManager {

    private static Mission mission;
    private static List<Mission> missions = new ArrayList<>();

    private static Mission globalMission = new Mission("GLOBAL");

    public static void init() {
        MissionServices services = new MissionServices();
        services.loadInformation(onLoadInformationListener);
    }

    public static void switchMission(Mission mission) {
        MissionManager.mission = mission;
    }

    public static void switchMission(String missionKey) {
        MissionManager.mission = getMissionForCode(missionKey);
    }

    public static void switchToUserMission() {
        MissionManager.mission = getMissionForCode(UserManager.getMission());
    }

    public static void setThemeIfNeeded(Activity activity) {}

    @NonNull
    public static Mission getCurrentMission() {
        return mission != null ? mission : missions.get(0);
    }

    public static Mission getGlobalMission() {
        return globalMission;
    }

    public static Mission getMissionForCode(String missionKey) {
        Mission mission = new Mission(missionKey);
        int indexOfMission = missions.indexOf(mission);
        indexOfMission = indexOfMission > 0 ? indexOfMission : 0;

        return indexOfMission >= 0 ? missions.get(indexOfMission) : missions.get(0);
    }

    public static List<Mission> getAvailableMissions() {
        return missions;
    }

    private static ValueEventListenerAdapter onLoadInformationListener = new ValueEventListenerAdapter() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            super.onDataChange(dataSnapshot);
            missions.clear();

            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                Mission mission = snapshot.getValue(Mission.class);
                mission.setKey(snapshot.getKey());
                missions.add(mission);
            }
        }
    };

    public interface OnMissionsLoadedListener {
        void onMissionsLoaded(List<Mission> missions);
    }

}
