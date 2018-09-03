package com.study.audio.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.study.audio.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private int mPage;
    private String mTitle;

    public static Fragment newInstance(int page, String title) {
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        args.putString("Title", title);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt("PAGE");
        mTitle = getArguments().getString("Title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        //TextView textView = (TextView) view;textView.setText(new StringBuffer().append("Fragment ").append(mPage).toString());

        RecyclerView rv = view.findViewById(R.id.fragment_rv);
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) rv.getLayoutParams();
        //Adapter
        AlbumGalleryAdapter albumAdapter = new AlbumGalleryAdapter();
        SongListAdapter songAdapter = new SongListAdapter();

        //LayoutManager
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), GridLayoutManager.VERTICAL);
        gridLayoutManager.setSpanCount(2);

        switch (mTitle) {
            case "Linear":
                marginLayoutParams.setMargins(75, 0, 0, 0);
                rv.setLayoutParams(marginLayoutParams);
                rv.setAdapter(songAdapter);
                rv.setLayoutManager(linearLayoutManager);
                break;
            case "Grid":
                marginLayoutParams.setMargins(30, 0, 0, 0);
                rv.setLayoutParams(marginLayoutParams);
                rv.setAdapter(albumAdapter);
                rv.setLayoutManager(gridLayoutManager);

            default:
                rv.setLayoutManager(gridLayoutManager);
                break;
        }
        return view;
    }

}
