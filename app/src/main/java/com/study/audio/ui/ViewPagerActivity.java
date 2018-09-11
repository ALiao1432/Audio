package com.study.audio.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.study.audio.MediaData;
import com.study.audio.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {

    private final String TAG = "ViewPagerActivity";
    private final int MY_WRITE_EXTERNAL_REQUEST_CODE = 999;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    public static List<MediaData> mediaPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        if (needToAskPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                || needToAskPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showPermissionDialog();
        } else {
            if (getAllMediaFile()) {
                initLayout();
            }
        }
    }

    private void initLayout() {
        Log.d(TAG, "initLayout: mediaPathList : " + mediaPathList.size());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(), ViewPagerActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar_pager);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    private boolean needToAskPermission(String p) {
        return this.checkPermission(p, Process.myPid(), Process.myUid())
                == PackageManager.PERMISSION_DENIED;
    }

    private void showPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.need_permission)
                .setMessage(R.string.permission_dialog_content)
                .setPositiveButton(R.string.permission_dialog_accept, (dialogInterface, i) -> askPermission())
                .setNegativeButton(R.string.permission_dialog_cancel, (dialogInterface, i) -> finish())
                .setCancelable(false)
                .show();
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(
                ViewPagerActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MY_WRITE_EXTERNAL_REQUEST_CODE
        );
    }

    private boolean getAllMediaFile() {
        List<MediaData> tempList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
        };
        String order = MediaStore.Audio.Media.ARTIST;

        try {
            Cursor mediaCursor = getContentResolver().query(
                    uri,
                    projections,
                    null,
                    null,
                    order
            );
            if (mediaCursor != null) {
                while (mediaCursor.moveToNext()) {
                    tempList.add(new MediaData(
                            mediaCursor.getString(0),
                            mediaCursor.getString(1),
                            mediaCursor.getString(2),
                            "",
                            mediaCursor.getString(3),
                            mediaCursor.getString(4)
                    ));
                }
                mediaCursor.close();
            }

            mediaPathList = new ArrayList<>(tempList);
            getAlbumArtPath();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void getAlbumArtPath() {
        String order = MediaStore.Audio.AlbumColumns.ARTIST;
        String[] projections = {
                MediaStore.Audio.AlbumColumns.ALBUM_ART,
                MediaStore.Audio.AlbumColumns.ALBUM
        };
        String ALBUM_URI = "content://media/external/audio/albums";

        Cursor cursor = getContentResolver().query(
                Uri.parse(ALBUM_URI),
                projections,
                null,
                null,
                order
        );

        ArrayList<String> tempAlbumArtList = new ArrayList<>();
        ArrayList<String> tempAlbumList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                tempAlbumArtList.add(cursor.getString(0));
                tempAlbumList.add(cursor.getString(1));
            }
        }

        mediaPathList.forEach(mediaData -> {
            int i = tempAlbumList.indexOf(mediaData.getAlbum());
            mediaData.setAlbumId(tempAlbumArtList.get(i));
        });

        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (drawerToggle != null) {
            drawerToggle.syncState();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (getAllMediaFile()) {
                    initLayout();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;
        private String tabTitles[] = new String[]{"Linear", "Grid", "Tab3"};
        private Context context;

        SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (tabTitles[position]) {
                case "Linear":
                    return "Song";
                case "Grid":
                    return "Album";
                default:
                    return "TAB";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(position + 1, tabTitles[position]);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    //selectDrawerItem(menuItem);
                    return true;
                });
    }
}
