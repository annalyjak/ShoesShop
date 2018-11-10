package com.example.anna.shoesshop.controller.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anna.shoesshop.R;
import com.example.anna.shoesshop.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private static final String TAG = FavouritesAdapter.class.getSimpleName();

    private static List<Product> dataset;
    private static Activity activity;

    public FavouritesAdapter(FragmentActivity activity, List<Product> favProducts) {
        this.activity =activity;
        dataset = favProducts;
    }

    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.card_fav_products, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final FavouritesAdapter.ViewHolder holder, int position) {
        Product product = dataset.get(position);
        Resources resources = activity.getApplicationContext().getResources();

        holder.position = position;
        holder.name.setText(product.getName());
        if(!product.onPromotion()){
            holder.priceActuall.setText(product.getNormalPrice().toString());
            holder.priceNormal.setText(product.getPrice().toString());
        } else {
            holder.priceNormal.setVisibility(View.INVISIBLE);
            holder.priceActuall.setText(product.getPrice().toString());
        }
        holder.priceNormal.setText(product.getPrice().toString());
        holder.picture.setImageBitmap(product.getMainPicture());

    }

    @Override
    public int getItemCount() {
        if(dataset == null) {
            dataset = new ArrayList<>();
            return 0;
        }
        return dataset.size();
    }

    public void addNewProducts(Product product) {
        if(dataset == null){
            dataset = new ArrayList<>();
        }
        dataset.add(product);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int position;
        public TextView name, priceActuall, priceNormal;
        public ImageView picture;
        private Context context;

        public ViewHolder(View v, final Context context) {
            super(v);
            this.context = context;
            name = v.findViewById(R.id.name_categ_prod);
            priceActuall = v.findViewById(R.id.categ_actuall_price);
            priceNormal = v.findViewById(R.id.fav_normal_price);
            picture = v.findViewById(R.id.categ_imageView);

//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final Dialog dialog = new Dialog(context, R.style.SettingsDialogStyle);
//                    dialog.setContentView(R.layout.dialog_planned_route_card_click);
////                    dialog.setTitle(R.string.dialog_add_new_planned_route_title_headline);
//
//                    Button generateReportButton = (Button) dialog.findViewById(R.id.buttonGenerateReport);
//                    generateReportButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                            final TransportSelectionFragment fragment = TransportSelectionFragment
//                                    .newInstance(getSelectedRoute(position));
//                            ((MainActivity)activity).attachNewFragment(fragment);
//                        }
//                    });
//
//                    Button showRouteOnMapButton = (Button) dialog.findViewById(R.id.buttonShowRouteOnMap);
//                    showRouteOnMapButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                            Intent openMapIntent = new Intent(((Dialog) dialog).getContext(),
//                                    MapsActivity.class);
//                            PlannedRoute route = getSelectedRoute(position);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("title", route.getTitle());
//                            bundle.putInt("duration", route.getDuration());
//                            bundle.putInt("distance", route.getDistance());
//                            openMapIntent.putExtras(bundle);
//                            activity.startActivity(openMapIntent);
//                        }
//
//                    });
//                    Button showPointsButton = (Button) dialog.findViewById(R.id.buttonShowAllPoints);
//                    showPointsButton.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v)
//                        {
//                            dialog.dismiss();
//                            Fragment newFragment = PointsFragment.newInstance(getSelectedRoute(position));
//                            ((MainActivity) activity).attachNewFragment(newFragment);
//                        }
//                    });
//
//                    Button deleteMarkerButton = (Button) dialog.findViewById(R.id.buttonDeleteRoute);
//                    deleteMarkerButton.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                            removeThisItemFromDatabase(position);
//                            if (activity instanceof MainActivity) {
//                                ((MainActivity) activity).notyfyDataSetChange();
//                            }
//                        }
//                    });
//
//                    Button anulujButton = (Button) dialog.findViewById(R.id.buttonAnuluj);
//                    anulujButton.setOnClickListener(new View.OnClickListener(){
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    dialog.show();
//                }
//            });
        }
    }

}
