package ro.upb.cs.thesis.icarte.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ro.upb.cs.thesis.icarte.activities.MainActivity;
import ro.upb.cs.thesis.icarte.R;

/**
 * Created by Paun on 31.05.2017.
 */

public class ProductListFragment extends Fragment {
    private ListView listview;
    MainActivity mainActivity;
    private String productCategory;

    ProgressDialog progessDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater
                .inflate(R.layout.productlist, container, false);
        productCategory = mainActivity.getProducts_category();

        listview = (ListView) rootView.findViewById(R.id.ListViewCatalog);

        mainActivity.getSupportActionBar().setTitle(productCategory);
        // selecting single ListView item
        ListView lv = listview;
        // listening to single listitem click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
        return rootView;
    }

    private boolean isProductlistRetrieved = false;

    public boolean isProductlistRetrieved() {
        return isProductlistRetrieved;
    }

    public void setProductlistRetrieved(boolean isProductlistRetrieved) {
        this.isProductlistRetrieved = isProductlistRetrieved;
    }
}
