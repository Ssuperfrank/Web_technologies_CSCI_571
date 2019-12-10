package com.weather.frank;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotosTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotosTab extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String photos_links = "param1";

    private String links;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public PhotosTab() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PhotosTab.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotosTab newInstance(String param1) {
        PhotosTab fragment = new PhotosTab();
        Bundle args = new Bundle();
        args.putString(photos_links, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            links = getArguments().getString(photos_links);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_photos_tab, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        Gson gson = new Gson();
        photoTabLinks.photoLinks [] data = gson.fromJson(links, photoTabLinks.class).getItems();
        String [] links = new String [8];
        for (int i = 0; i< data.length; i++){
            links[i] = data[i].getLink();
        }

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(links);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }




    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
    {
        private String[] links;
        MyAdapter( String[]   data){
           links = data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.recycler_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {

            final ImageView imgV = holder.img;

            Transformation transformation = new Transformation() {
                @Override
                public Bitmap transform(Bitmap source) {
                    int targetWidth = imgV.getWidth();
                    if (source.getWidth() == 0) {
                        return source;
                    }
                    double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                    int targetHeight = (int) (targetWidth * aspectRatio);
                    if (targetHeight != 0 && targetWidth != 0) {
                        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                        if (result != source) {
                            // Same bitmap is returned if sizes are the same
                            source.recycle();
                        }
                        return result;
                    } else {
                        return source;
                    }
                }

                @Override
                public String key() {
                    return "photos fit";
                }
            };



            Picasso.with(getContext())
                    .load(links[position])
                    .transform(transformation)
                    .into(imgV);

        }

        @Override
        public int getItemCount()
        {
            return 8;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img ;

            public MyViewHolder(View view)
            {
                super(view);
                img = view.findViewById(R.id.photos_tab_img);
            }
        }

    }


}


