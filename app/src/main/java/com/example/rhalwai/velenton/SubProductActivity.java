package com.example.rhalwai.velenton;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhalwai.velenton.home.ProductDetails;
import com.example.rhalwai.velenton.home.SubProduct;
import com.example.rhalwai.velenton.home.SubProductAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubProductActivity extends AppCompatActivity {

    public static final String AVAILABILITY = "Availability : ";
    public static final String FABRIC = "Fabric : ";
    public static final String LENGTH = "Length : ";
    public static final String OCCASION = "Occasion : ";
    public static final String SIZE = "Size : ";
    public static final String SLEEVES = "Sleeves : ";
    public static final String TYPE = "Type : ";
    public static final String WASHCARE = "WashCare : ";
    public static final String WORK = "Work : ";
    public static final String SINGLELINESPACING = "\n";
    public static final String DOUBLELINESPACING = "\n\n";

    static String CatalogId;
    static int CatalogPrice;

    DatabaseReference databaseSubProducts;
    DatabaseReference databaseProductDetails;
    ProgressDialog progressDialog;
    List<SubProduct> subProductList;
    ProductDetails productDetails;
    RecyclerView recyclerView;
    SubProductAdapter adapter;
    HomeTab1 homeTab1;

    private TextView productDescription;
    private Button viewProduct, share;
    private Button shareCatalog, otherShare;
    private ImageView copyText, productImage;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subproduct);

        setBackButtonForToolbar();

        if (getIntent().getExtras() != null) {
            CatalogId = (String) getIntent().getExtras().get("CatalogId");
            CatalogPrice = (int) getIntent().getExtras().get("CatalogPrice");
        }

        subProductList = new ArrayList<>();

        homeTab1 = new HomeTab1();

        setListenerForUIComponents();

        setListenerForFirebase();
    }

    private void setListenerForFirebase() {
        databaseSubProducts = FirebaseDatabase.
                getInstance()
                .getReference("ProductList")
                .child(CatalogId)
                .child("Subproducts");

        databaseSubProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subProductList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SubProduct product = postSnapshot.getValue(SubProduct.class);
                    subProductList.add(product);
                }
                adapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseProductDetails = FirebaseDatabase.
                getInstance()
                .getReference("ProductList")
                .child(CatalogId)
                .child("ProductDetails");

        databaseProductDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productDetails = dataSnapshot.getValue(ProductDetails.class);
                if (productDetails != null) {
                    String Availability = AVAILABILITY + productDetails.getAvailability();
                    String Fabric = FABRIC + productDetails.getFabric();
                    String Length = LENGTH + productDetails.getLength();
                    String Occasion = OCCASION + productDetails.getOccasion();
                    String Size = SIZE + productDetails.getSize();
                    String Sleeves = SLEEVES + productDetails.getSleeves();
                    String Type = TYPE + productDetails.getType();
                    String WashCare = WASHCARE + productDetails.getWashCare();
                    String Work = WORK + productDetails.getWork();
                    String productDesc = Fabric + DOUBLELINESPACING +
                            Size + DOUBLELINESPACING +
                            Length + DOUBLELINESPACING +
                            Work + DOUBLELINESPACING +
                            Type + DOUBLELINESPACING +
                            Availability + DOUBLELINESPACING +
                            Occasion + DOUBLELINESPACING +
                            WashCare + SINGLELINESPACING;

                    productDescription.setText(productDesc);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setListenerForUIComponents() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Retrieving Attractive Collections");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();

        shareCatalog = findViewById(R.id.shareCatalog);
        otherShare = findViewById(R.id.otherShare);
        productDescription = findViewById(R.id.productDescription);
        copyText = findViewById(R.id.copyText);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SubProductAdapter(this, subProductList);
        adapter.SetOnItemClickListener(new SubProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SubProduct product = subProductList.get(position);
                productImage = view.findViewById(R.id.subProductImage);
                viewProduct = view.findViewById(R.id.subProductViewProduct);
                share = view.findViewById(R.id.subProductShare);

                if (view == viewProduct || view == productImage) {

                    Intent intent = new Intent(SubProductActivity.this, ProductPurchaseActivity.class);
                    intent.putExtra("ProductDescription", productDetails);
                    intent.putExtra("ProductCode", product.getProductId());
                    intent.putExtra("ProductImageURI", product.getProductImage());
                    intent.putExtra("ProductPrice", String.valueOf(product.getProductPrice()));
                    startActivity(intent);
                }
                if (view == share) {
                    homeTab1.showProfitMarginDialogBox(product.getProductPrice(), SubProductActivity.this);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = productDescription.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();

                /* Code to get text from the Clip board */
                /*myClip = myClipboard.getPrimaryClip();
                String copiedText = myClip.getItemAt(0).getText().toString();*/
            }
        });

        shareCatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeTab1.showProfitMarginDialogBox(CatalogPrice, SubProductActivity.this);
            }
        });

        otherShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeTab1.showProfitMarginDialogBox(CatalogPrice, SubProductActivity.this);
            }
        });
    }

    public void setBackButtonForToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}