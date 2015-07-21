package in.ureport.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.ureport.R;
import in.ureport.models.User;
import in.ureport.tasks.GetUserLoggedTask;

/**
 * Created by johncordeiro on 7/13/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private AppBarLayout appBar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private NavigationView menuNavigation;
    private FloatingActionButton mainActionButton;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        setupBaseView();
        loadUser();
    }

    private void setupBaseView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBar = (AppBarLayout) findViewById(R.id.appbar);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setVisibility(hasTabLayout() ? View.VISIBLE : View.GONE);

        mainActionButton = (FloatingActionButton) findViewById(R.id.mainActionButton);
        mainActionButton.setVisibility(hasMainActionButton() ? View.VISIBLE : View.GONE);

        menuNavigation = (NavigationView) findViewById(R.id.menuNavigation);
        menuNavigation.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout
                , toolbar, R.string.base_menu_open_drawer, R.string.base_menu_close_drawer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        actionBarDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(int layoutResID) {
        ViewGroup content = (ViewGroup) findViewById(R.id.content);

        View view = getLayoutInflater().inflate(layoutResID, null);
        content.addView(view);
    }

    private void loadUser() {
        loadUserTask.execute();
    }

    private GetUserLoggedTask loadUserTask = new GetUserLoggedTask(this) {
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            updateUserInfo(user);
            setUser(user);
        }
    };

    private void updateUserInfo(User user) {
        BaseActivity.this.user = user;
        if(user != null) {
            View menuHeader = getLayoutInflater().inflate(R.layout.view_header_menu, null);
            menuHeader.setOnClickListener(onMenuHeaderClickListener);

            TextView name = (TextView) menuHeader.findViewById(R.id.name);
            name.setText("@" + user.getUsername());

            TextView points = (TextView) menuHeader.findViewById(R.id.points);
            points.setText(getString(R.string.menu_points, user.getPoints()));

            TextView polls = (TextView) menuHeader.findViewById(R.id.polls);
            polls.setText(getString(R.string.profile_polls, user.getPolls()));

            TextView stories = (TextView) menuHeader.findViewById(R.id.stories);
            stories.setText(getString(R.string.profile_stories, user.getStories()));

            menuNavigation.addHeaderView(menuHeader);
        }
    }

    protected User getLoggedUser() {
        return user;
    }

    protected void setUser(User user){}

    protected Toolbar getToolbar() {
        return toolbar;
    }

    protected TabLayout getTabLayout() {
        return tabLayout;
    }

    protected FloatingActionButton getMainActionButton() {
        return mainActionButton;
    }

    protected boolean hasTabLayout() {
        return true;
    }

    protected boolean hasMainActionButton() {
        return false;
    }

    protected NavigationView getMenuNavigation() {
        return menuNavigation;
    }

    protected AppBarLayout getAppBar() {
        return appBar;
    }

    protected void openEndDrawer() {
        drawerLayout.openDrawer(GravityCompat.END);
    }

    private View.OnClickListener onMenuHeaderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent profileIntent = new Intent(BaseActivity.this, ProfileActivity.class);
            startActivity(profileIntent);
        }
    };

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            Intent navigationIntent;
            switch(menuItem.getItemId()) {
                case R.id.home:
                    navigationIntent = new Intent(BaseActivity.this, MainActivity.class);
                    break;
                case R.id.chat:
                    navigationIntent = new Intent(BaseActivity.this, ChatActivity.class);
                    break;
                case R.id.about:
                    navigationIntent = new Intent(BaseActivity.this, AboutActivity.class);
                    break;
                case R.id.makeDonations:
                    navigationIntent = new Intent(BaseActivity.this, DonationActivity.class);
                    break;
                default:
                    return true;
            }

            menuItem.setChecked(true);
            startActivity(navigationIntent);
            finish();
            return true;
        }
    };
}