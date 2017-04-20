package com.images.vicenteruizsalcido.gettyimages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.images.vicenteruizsalcido.gettyimages.response.DisplaySize;
import com.images.vicenteruizsalcido.gettyimages.response.Example;
import com.images.vicenteruizsalcido.gettyimages.response.Image;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    public interface FragmentCallback {
        void onTaskDone(Example example);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GridView gridView;
    private EditText editText;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);


        Button button = (Button) view.findViewById(R.id.search_button);
        gridView = (GridView) view.findViewById(R.id.images_gridview);
        editText = (EditText) view.findViewById(R.id.search_edit_text);
/*
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setClickable(true);
        editText.setFocusableInTouchMode(true);
        editText.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );
*/
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        /*
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(v.getText().toString());
                    return true;
                } else if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER ||
                        event.getAction() == KeyEvent.ACTION_DOWN) {
                    performSearch(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
*/
        return view;
    }

    public void performSearch() {
        //Create instance for AsyncCallWS
        AsyncCallImage task = new AsyncCallImage(new FragmentCallback() {

            @Override
            public void onTaskDone(Example example) {
                updateAdapter(example);
            }
        });

        //Call execute
        String s = editText.getText().toString();
        task.execute(s);

    }

    public void performSearch(String search) {
        //Create instance for AsyncCallWS
        AsyncCallImage task = new AsyncCallImage(new FragmentCallback() {

            @Override
            public void onTaskDone(Example example) {
                updateAdapter(example);
            }
        });

        //Call execute
        //String s = editText.getText().toString();
        task.execute(search);

    }

    public void updateAdapter(Example example) {
        gridView.setVisibility(View.VISIBLE);
        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), gridView, getListImages(example));
        gridView.setAdapter(imageAdapter);
    }

    private List<String> getListImages(Example example) {
        List<String> result = new ArrayList<>();
        if (example != null && example.getImages() != null &&
                example.getImages().size() > 0) {
            for (Image image : example.getImages()) {
                for (DisplaySize displaySize : image.getDisplaySizes()) {
                    if (!displaySize.getUri().isEmpty()) {
                        result.add(displaySize.getUri());
                    }
                }
            }
        } else {
            if (example == null) {
                Toast.makeText(getActivity(), "There was an error with the service",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "The service return 0 images",
                        Toast.LENGTH_LONG).show();
            }
        }
        return result;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onClickBtn(View v) {
        performSearch();
    }
}
