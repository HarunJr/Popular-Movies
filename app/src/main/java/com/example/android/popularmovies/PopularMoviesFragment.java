package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link PopularMoviesFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link PopularMoviesFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class PopularMoviesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

//    private OnFragmentInteractionListener mListener;

    public PopularMoviesFragment() {
        // Required empty public constructor
    }


//    // TODO: Rename and change types and number of parameters
//    public static PopularMoviesFragment newInstance(String param1, String param2) {
//        PopularMoviesFragment fragment = new PopularMoviesFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public void onStart() {
        super.onStart();

//        Bus bus = ((MainActivity) getActivity()).getBus();
//        bus.post(new MovieService.MovieServerRequest("popular"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_popular_movies_frament, container, false);
        declareViews(rootView);

        return rootView;
    }

    private void declareViews(View rootView){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), GridLayoutManager.DEFAULT_SPAN_COUNT);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_movies);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
