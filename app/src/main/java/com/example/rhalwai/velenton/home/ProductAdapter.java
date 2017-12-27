package com.example.rhalwai.velenton.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.rhalwai.velenton.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context mCtx;
    List<Product> productList;
    OnItemClickListener mItemClickListener;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        super();
        this.mCtx = mCtx;
        this.productList = productList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_list_home, null);
        ProductViewHolder productViewHolder = new ProductViewHolder(view);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        Glide.with(mCtx)
                .load(product.getProductImage()).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d("IMAGE_EXCEPTION", "Exception " + e.toString());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                return false;
            }


        })
                //.override(500, 350)
                .override(800, 600)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(holder.imageView);

        holder.productName.setText(product.getProductName());
        holder.productDesignNumber.setText(String.valueOf(product.getProductDesigns()) + " Designs");
        holder.productPrice.setText("Starting at Rs. " + String.valueOf(product.getProductPrice()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView productName, productDesignNumber, productPrice;
        Button viewProduct, share;

        public ProductViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            productName = itemView.findViewById(R.id.textViewTitle);
            productDesignNumber = itemView.findViewById(R.id.textViewDesign);
            productPrice = itemView.findViewById(R.id.textViewPrice);

            viewProduct = itemView.findViewById(R.id.viewProduct);
            share = itemView.findViewById(R.id.share);

            imageView.setOnClickListener(this);
            viewProduct.setOnClickListener(this);
            share.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
}
