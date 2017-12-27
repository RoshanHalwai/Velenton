package com.example.rhalwai.velenton.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rhalwai.velenton.R;

import java.util.List;

public class SubProductAdapter extends RecyclerView.Adapter<SubProductAdapter.ProductViewHolder> {

    Context mCtx;
    List<SubProduct> subProductList;
    OnItemClickListener mItemClickListener;

    public SubProductAdapter(Context mCtx, List<SubProduct> subProductList) {
        super();
        this.mCtx = mCtx;
        this.subProductList = subProductList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_list_subproduct, null);
        SubProductAdapter.ProductViewHolder subProductViewHolder = new SubProductAdapter.ProductViewHolder(view);
        return subProductViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        SubProduct subProduct = subProductList.get(position);

        Glide.with(mCtx)
                .load(subProduct.getProductImage())
                .override(800, 600)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(holder.imageView);
        holder.productCode.setText("Product Code :- " + subProduct.getProductId());
        holder.productPrice.setText("Price Rs." + String.valueOf(subProduct.getProductPrice()));
        holder.productSize.setText("Available in " + subProduct.getProductSize());
    }

    @Override
    public int getItemCount() {
        return subProductList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView productCode, productPrice, productSize;
        Button viewProduct, share;

        public ProductViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.subProductImage);
            productCode = itemView.findViewById(R.id.subProductCode);
            productPrice = itemView.findViewById(R.id.subProductPrice);
            productSize = itemView.findViewById(R.id.subProductSize);

            viewProduct = itemView.findViewById(R.id.subProductViewProduct);
            share = itemView.findViewById(R.id.subProductShare);

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
