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

import java.util.List;

public class CategoriesCardAdapter extends RecyclerView.Adapter<CategoriesCardAdapter.ViewHolder> {

    private static final String TAG = CategoriesCardAdapter.class.getSimpleName();
    public static final int CARD_CATEGORIES_1 = R.layout.card_categories;
    public static final int CARD_CATEGORIES_2 = R.layout.card_categories2;

    private static List<Product> dataset;
    private static Activity activity;
    private int valueOfCard = CARD_CATEGORIES_1;

    public CategoriesCardAdapter(FragmentActivity activity, List<Product> products) {
        this.activity =activity;
        dataset = products;
        valueOfCard = CARD_CATEGORIES_1;
    }

    public CategoriesCardAdapter(FragmentActivity activity, List<Product> products, int view) {
        this.activity =activity;
        dataset = products;
        valueOfCard = view;
    }

    @Override
    public CategoriesCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(valueOfCard, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final CategoriesCardAdapter.ViewHolder holder, int position) {
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
        holder.picture.setImageBitmap(product.getMainPicture());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addNewProducts(Product product) {
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
            priceNormal = v.findViewById(R.id.categ_normal_price);
            picture = v.findViewById(R.id.categ_imageView);

            //TODO on Card click action
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