package com.d.end_test_murphy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.d.end_test_murphy.model.Product;
import com.d.end_test_murphy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> products = new ArrayList<>();
    private OnItemClickListener listener;
    private Context context;

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    ProductAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.product_item_count, parent, false);
                return new ItemCountHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.product_item, parent, false);
                return new ProductHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == ITEM) {
            Product currentProduct = products.get(position);

            ((ProductHolder)holder).textViewName.setText(currentProduct.getName());
            ((ProductHolder)holder).textViewColor.setText("" + getRandomColor());
            ((ProductHolder)holder).textViewPrice.setText(currentProduct.getPrice());
            Picasso.with(context)
                    .load(currentProduct.getImage())
                    .fit()
                    .centerInside()
                    .into(((ProductHolder)holder).imageView);
        } else {
            // could also get this via response.body().setProductCount()
            ((ItemCountHolder)holder).textViewItemCount.setText(products.size() + " items");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return ITEM;
        }
    }

    void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    private String getRandomColor() {
        String[] arrayOfColors = {"Vast Grey", "Luminous Green",
                "Dark Grey Heather", "Indigo", "Black"};
        int number = new Random().nextInt(arrayOfColors.length);
        return arrayOfColors[number];
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewColor;
        private TextView textViewPrice;

        ProductHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image_view);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewColor = itemView.findViewById(R.id.text_view_color);
            textViewPrice = itemView.findViewById(R.id.text_view_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(products.get(position));
                    }
                }
            });
        }
    }

    class ItemCountHolder extends RecyclerView.ViewHolder {
        private TextView textViewItemCount;

        ItemCountHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemCount = itemView.findViewById(R.id.product_item_count);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
