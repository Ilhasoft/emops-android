package in.ureport.pref;

import android.content.Context;

import br.com.ilhasoft.support.preferences.Preferences;

/**
 * Created by johncordeiro on 7/9/15.
 */
public class SystemPreferences extends Preferences {

    public static final String USER_NO_LOGGED_ID = "NONE";
    public static final String USER_LANGUAGE_NOT_DEFINED = "NOT_DEFINED";

    private enum Fields {
        UserLoggedId,
        UserLoggedRapidUuid,
        UserLanguage,
        MissionKey,
        MissionName,
        TutorialView,
        Moderator,
        Master
    }

    public SystemPreferences(Context context) {
        super(context, SystemPreferences.class.getName());
    }

    public void setUserLoggedRapidUuid(String id) {
        setValue(Fields.UserLoggedRapidUuid, id);
    }

    public String getUserLoggedRapidUuid() {
        return getValue(Fields.UserLoggedRapidUuid, USER_NO_LOGGED_ID);
    }

    public void setUserLoggedId(String id) {
        setValue(Fields.UserLoggedId, id);
    }

    public String getUserLoggedId() {
        return getValue(Fields.UserLoggedId, USER_NO_LOGGED_ID);
    }

    public void setUserLanguage(String userLanguage) {
        setValue(Fields.UserLanguage, userLanguage);
    }

    public String getUserLanguage() {
        return getValue(Fields.UserLanguage, USER_LANGUAGE_NOT_DEFINED);
    }

    public void setMissionKey(String key) {
        setValue(Fields.MissionKey, key);
    }

    public String getMissionKey() {
        return getValue(Fields.MissionKey, "");
    }

    public void setMissionName(String name) {
        setValue(Fields.MissionName, name);
    }

    public String getMissionName() {
        return getValue(Fields.MissionName, "");
    }

    public void setTutorialView(boolean tutorialView) {
        setValue(Fields.TutorialView, tutorialView);
    }

    public boolean getTutorialView() {
        return getValue(Fields.TutorialView, false);
    }

    public void setModerator(boolean moderator) {
        setValue(Fields.Moderator, moderator);
    }

    public boolean isModerator() {
        return getValue(Fields.Moderator, false);
    }

    public void setMaster(boolean master) {
        setValue(Fields.Master, master);
    }

    public boolean isMaster() {
        return getValue(Fields.Master, false);
    }
}
