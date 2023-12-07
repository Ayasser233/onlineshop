package com.example.shoppingsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shoppingsystem.R;

public class activity_Addproduct extends AppCompatActivity {
    ImageView productimage;
    EditText productname, productprice, productquantity,idforupdateordalete;
    Spinner proCategory;
    ArrayAdapter adapter;
    Button upload_btn,updateproduct,daleteproduct,Generate;
    TextView reset_btn,addCategory;
    //MyDatabase database;
    String str1;
    final static int GALLERY_REQUEST_CODE = 101;
TextView reset=(TextView)findViewById(R.id.reset);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        getSupportActionBar().setTitle("upload product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intiView();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productimage.setImageResource(R.drawable.proimg);
                productname.setText("");
                productprice.setText("");
                productquantity.setText("");
            }
        });

    }

    protected void intiView() {
        productimage =(ImageView) findViewById(R.id.product_image);
        productname =(EditText) findViewById(R.id.product_name);
        productprice =(EditText) findViewById(R.id.product_price);
        productquantity =(EditText) findViewById(R.id.product_quantity);
        proCategory =(Spinner) findViewById(R.id.category);
        upload_btn =(Button) findViewById(R.id.btn_upload);
        updateproduct=(Button)findViewById(R.id.UpdateProduct);
        daleteproduct=(Button)findViewById(R.id.DeleteProduct);
        idforupdateordalete=(EditText) findViewById(R.id.id_for_update_del);
        reset_btn =(TextView) findViewById(R.id.reset);
        //addCategory=(TextView) findViewById(R.id.);
        Generate=(Button)findViewById(R.id.generate);
        //database = new MyDatabase(this);
    }
}