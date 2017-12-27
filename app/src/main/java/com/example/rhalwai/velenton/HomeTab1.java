package com.example.rhalwai.velenton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhalwai.velenton.home.Product;
import com.example.rhalwai.velenton.home.ProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class HomeTab1 extends android.support.v4.app.Fragment {

    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Product> productList;

    DatabaseReference databaseProducts;
    ProgressDialog progressDialog;

    Button viewProduct, share;
    ImageView productImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Retrieving Attractive Collections");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();

        productList = new ArrayList<>();
        databaseProducts = FirebaseDatabase.getInstance().getReference("ProductList");
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Product product = postSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                adapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_home, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ProductAdapter(getActivity(), productList);
        adapter.SetOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Product product = productList.get(position);
                productImage = view.findViewById(R.id.imageView);
                viewProduct = view.findViewById(R.id.viewProduct);
                share = view.findViewById(R.id.share);

                if (view == viewProduct || view == productImage) {
                    Intent intent = new Intent(getActivity(), SubProductActivity.class);
                    intent.putExtra("CatalogId", product.getProductId());
                    intent.putExtra("CatalogPrice", product.getProductPrice());
                    startActivity(intent);
                }
                if (view == share) {
                    showProfitMarginDialogBox(product.getProductPrice(), getActivity());
                }
            }
        });

        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void showProfitMarginDialogBox(final int actualProductPrice, Activity context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profit_margin_layout, null);
        dialogBuilder.setView(dialogView);

        final TextView profitMarginActualPrice = dialogView.findViewById(R.id.profitMarginActualPrice);
        final TextView profitMarginTotalPrice = dialogView.findViewById(R.id.profitMarginTotalPrice);

        final EditText plusBox = dialogView.findViewById(R.id.plusBox);
        final EditText percentageBox = dialogView.findViewById(R.id.percentageBox);

        Button profitMarginDone = dialogView.findViewById(R.id.profitMarginOk);
        Button profitMarginCancel = dialogView.findViewById(R.id.profitMarginCancel);

        profitMarginActualPrice.setText("Price: " + valueOf(actualProductPrice));
        profitMarginTotalPrice.setText(String.valueOf(actualProductPrice));

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        plusBox.addTextChangedListener(new TextWatcher() {

            int profitMargin;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                profitMargin = 0;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                if (!changedText.isEmpty()) {
                    percentageBox.setEnabled(false);
                    profitMargin = Integer.valueOf(changedText);
                } else {
                    percentageBox.setEnabled(true);
                    profitMargin = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int intAfterAddingMargin = actualProductPrice + profitMargin;
                String strAfterAddingMargin = valueOf(intAfterAddingMargin);
                profitMarginTotalPrice.setText(strAfterAddingMargin);
            }
        });

        percentageBox.addTextChangedListener(new TextWatcher() {

            int profitMargin;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                profitMargin = 0;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String changedText = charSequence.toString();
                if (!changedText.isEmpty()) {
                    plusBox.setEnabled(false);
                    profitMargin = Integer.valueOf(changedText);
                } else {
                    plusBox.setEnabled(true);
                    profitMargin = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                profitMargin = (profitMargin * actualProductPrice) / 100;

                int intAfterAddingMargin = actualProductPrice + profitMargin;
                String strAfterAddingMargin = valueOf(intAfterAddingMargin);
                profitMarginTotalPrice.setText(strAfterAddingMargin);
            }
        });

        profitMarginDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO To implement code when user has entered their profit margin
            }
        });
        profitMarginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
