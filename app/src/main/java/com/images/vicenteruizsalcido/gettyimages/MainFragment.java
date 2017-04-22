package com.images.vicenteruizsalcido.gettyimages;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.images.vicenteruizsalcido.gettyimages.response.DisplaySize;
import com.images.vicenteruizsalcido.gettyimages.response.Image;
import com.images.vicenteruizsalcido.gettyimages.response.PayLoad;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    public interface FragmentCallback {
        void onTaskDone(PayLoad example);
    }

    private GridView gridView;
    private EditText editText;
    private OnFragmentInteractionListener mListener;
    private ProgressBar progressBar;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) view.findViewById(R.id.search_button);
        gridView = (GridView) view.findViewById(R.id.images_gridview);
        editText = (EditText) view.findViewById(R.id.search_edit_text);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    public void performSearch() {
        performSearch(editText.getText().toString());
    }

    public void performSearch(String search) {
        progressBar.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.GONE);
        hideKeyboard();
        new AsyncCallImage(new FragmentCallback() {
            @Override
            public void onTaskDone(PayLoad payLoad) {
                updateAdapter(payLoad);
            }
        }).execute(search);
    }

    public void updateAdapter(PayLoad payLoad) {
        progressBar.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        gridView.setAdapter(new ImageAdapter(getActivity(), gridView, getListImages(payLoad)));
    }

    private List<String> getListImages(PayLoad payLoad) {
        List<String> result = new ArrayList<>();
        if (payLoad != null && payLoad.getImages() != null &&
                payLoad.getImages().size() > 0) {
            for (Image image : payLoad.getImages()) {
                for (DisplaySize displaySize : image.getDisplaySizes()) {
                    if (!displaySize.getUri().isEmpty()) {
                        result.add(displaySize.getUri());
                    }
                }
            }
        } else {
            if (payLoad == null) {
                Toast.makeText(getActivity(), "There was an error with the service",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "The service return 0 images",
                        Toast.LENGTH_LONG).show();
            }
        }
        return result;
    }

    public void hideKeyboard() {
        if (mListener != null) {
            mListener.hideKeyboard();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void hideKeyboard();
    }

    public void onClickBtn(View v) {
        progressBar.setVisibility(View.VISIBLE);
        performSearch();
    }
}
