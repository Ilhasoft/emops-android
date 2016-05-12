package in.ureport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

import in.ureport.R;
import in.ureport.fragments.MissionsFragment;
import in.ureport.fragments.SelectModeratorsFragment;
import in.ureport.fragments.StoriesModerationFragment;
import in.ureport.listener.OnUserStartChattingListener;
import in.ureport.managers.MissionManager;
import in.ureport.managers.UserManager;
import in.ureport.models.User;
import in.ureport.models.holders.NavigationItem;
import in.ureport.views.adapters.NavigationAdapter;

/**
 * Created by johncordeiro on 16/09/15.
 */
public class ModerationActivity extends BaseActivity implements OnUserStartChattingListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MissionManager.setThemeIfNeeded(this);
        setContentView(R.layout.activity_moderation);

        boolean hasPermission = checkModerationPermission();
        if(hasPermission) {
            setupView();
        }
    }

    private Boolean checkModerationPermission() {
        if(!UserManager.canModerate()) {
            exitFromModeration();
            return false;
        }
        return true;
    }

    private void exitFromModeration() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        finish();
    }

    private void setupView() {
        String countryProgramName = MissionManager.getCurrentMission().getName();
        setTitle(getString(R.string.title_moderation, countryProgramName));

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        NavigationItem[] navigationItems = getNavigationItems();

        NavigationAdapter adapter = new NavigationAdapter(getSupportFragmentManager(), navigationItems);
        pager.setAdapter(adapter);
        getTabLayout().setupWithViewPager(pager);
    }

    @Override
    protected void onMenuLoaded() {
        super.onMenuLoaded();
        getMenuNavigation().getMenu().findItem(R.id.moderation).setChecked(true);
    }

    @NonNull
    private NavigationItem[] getNavigationItems() {
        NavigationItem storiesModeration = new NavigationItem(new StoriesModerationFragment()
                , getString(R.string.main_stories));
        NavigationItem selectModerators = new NavigationItem(new SelectModeratorsFragment()
                , getString(R.string.label_country_moderator));
        NavigationItem missions = new NavigationItem(new MissionsFragment()
                , getString(R.string.label_missions));

        NavigationItem [] navigationItems;
        if(UserManager.isMaster()) {
            navigationItems = new NavigationItem[]{missions, storiesModeration, selectModerators};
        } else {
            navigationItems = new NavigationItem[]{missions, storiesModeration};
        }
        return navigationItems;
    }

    @Override
    public void onUserStartChatting(User user) {
        if(UserManager.validateKeyAction(this)) {
            Intent startChattingIntent = new Intent(this, MainActivity.class);
            startChattingIntent.putExtra(MainActivity.EXTRA_USER, user);
            startChattingIntent.setAction(MainActivity.ACTION_START_CHATTING);
            startChattingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(startChattingIntent);
        }
    }
}
