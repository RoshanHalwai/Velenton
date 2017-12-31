package com.example.rhalwai.velenton;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rhalwai.velenton.home.ProductDetails;

import static com.example.rhalwai.velenton.SubProductActivity.AVAILABILITY;
import static com.example.rhalwai.velenton.SubProductActivity.DOUBLELINESPACING;
import static com.example.rhalwai.velenton.SubProductActivity.FABRIC;
import static com.example.rhalwai.velenton.SubProductActivity.LENGTH;
import static com.example.rhalwai.velenton.SubProductActivity.SIZE;
import static com.example.rhalwai.velenton.SubProductActivity.SLEEVES;
import static com.example.rhalwai.velenton.SubProductActivity.TYPE;
import static com.example.rhalwai.velenton.SubProductActivity.WORK;

public class ProductPurchaseActivity extends AppCompatActivity {

    public static final String PRODUCTCODE = "Product Code :- ";

    TextView productCode;
    TextView productDescription;
    TextView productPrice;
    TextView shippingPrice;
    TextView returnAccepted;
    TextView productAvailability;
    TextView dispatchTime;
    TextView cashOnDelivery;
    ImageView productImage;
    Button addToCart, share;

    ProductDetails productDetails;
    String strProductCode;
    String strProductImageURI;
    String strProductPrice;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    HomeTab1 homeTab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);

        setToolbar();

        getDataFromPreviousActivity();

        initializeUIComponents();

        homeTab1 = new HomeTab1();

        setListenerForUIComponents();
    }

    private void setToolbar() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getDataFromPreviousActivity() {
        if (getIntent().getExtras() != null) {
            productDetails = (ProductDetails) getIntent().getExtras().get("ProductDescription");
            strProductCode = getIntent().getExtras().getString("ProductCode");
            strProductImageURI = getIntent().getExtras().getString("ProductImageURI");
            strProductPrice = getIntent().getExtras().getString("ProductPrice");
        }
    }

    private void setListenerForUIComponents() {
        productCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String text = productDescription.getText().toString();
                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeTab1.showProfitMarginDialogBox(Integer.valueOf(strProductPrice), ProductPurchaseActivity.this);
            }
        });
    }

    private void initializeUIComponents() {
        productImage = findViewById(R.id.productImage);
        productCode = findViewById(R.id.productCode);
        productDescription = findViewById(R.id.productDescription);

        productPrice = findViewById(R.id.productPrice);
        shippingPrice = findViewById(R.id.shippingPrice);
        returnAccepted = findViewById(R.id.returnAccepted);
        productAvailability = findViewById(R.id.productAvailability);
        dispatchTime = findViewById(R.id.dispatchTime);
        cashOnDelivery = findViewById(R.id.cashOnDelivery);

        addToCart = findViewById(R.id.addToCart);
        share = findViewById(R.id.share);

        setProductCodeAndDescription();
        setProductImage();
        setProductDetails();
    }

    private void setProductDetails() {
        productPrice.setText(new StringBuilder().append("Price : Rs. ").append(strProductPrice).append(" /-").toString());
        shippingPrice.setText(new StringBuilder().append("Shipping : ").append("Rs. 75 /-").toString());
        returnAccepted.setText("Return Accepted");
        productAvailability.setText(new StringBuilder().append("Available in ").append(productDetails.getAvailability()).toString());
        dispatchTime.setText("Dispatched in 2-3 Days");
        cashOnDelivery.setText("No COD Available");
    }

    private void setProductCodeAndDescription() {
        String Availability = AVAILABILITY + productDetails.getAvailability();
        String Fabric = FABRIC + productDetails.getFabric();
        String Length = LENGTH + productDetails.getLength();
        String Size = SIZE + productDetails.getSize();
        String Sleeves = SLEEVES + productDetails.getSleeves();
        String Type = TYPE + productDetails.getType();
        String Work = WORK + productDetails.getWork();
        String productDesc = Fabric + DOUBLELINESPACING +
                Size + DOUBLELINESPACING +
                Length + DOUBLELINESPACING +
                Work + DOUBLELINESPACING +
                Type + DOUBLELINESPACING +
                Availability;

        productCode.setText(PRODUCTCODE + strProductCode);
        productDescription.setText(productDesc);
    }

    private void setProductImage() {
        Glide.with(this)
                .load(strProductImageURI)
                .override(800, 600)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(productImage);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), SubProductActivity.class));
    }

}
