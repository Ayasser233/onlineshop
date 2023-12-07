package com.example.shoppingsystem.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.shoppingsystem.Database.MyDataBase;
import com.example.shoppingsystem.Model.ProductModel;
import com.example.shoppingsystem.R;



import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingsystem.R;

import java.io.ByteArrayOutputStream;

public class addproduct extends AppCompatActivity {
    ImageView productimage;
    EditText productname, productprice, productquantity,idforupdateordalete;
    Spinner proCategory;
    ArrayAdapter adapter;
    Button upload_btn,updateproduct,daleteproduct,Generate;
    TextView reset_btn,addCategory;
    MyDataBase database;
    String str1;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private static final int GALLERY_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);


        intiView();

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reset();
            }

        });
        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleImagePickerResult);}


    protected void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void handleImagePickerResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri imageUri = data.getData();
                // Display the image using the retrieved imageUri
                ImageView imageView = findViewById(R.id.product_image);
                imageView.setImageURI(imageUri);
            }
        }
    }
    public void Add_product()
    {
      String name=productname.getText().toString().trim(),
              price=productprice.getText().toString().trim(),
              quantity=productquantity.getText().toString().trim();
      int CategoryId=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
      byte []image=imageViewToByte(productimage);

      if(!name.isEmpty()&&!price.isEmpty()&&!quantity.isEmpty()&&!proCategory.getSelectedItem().toString().isEmpty()&&image.length!=0)
      {
          ProductModel productmodel=new ProductModel(getApplicationContext(),Integer.parseInt(quantity),CategoryId,name,image,Double.parseDouble(price));
          String msg=database.insertProduct(productmodel);
          Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
          Reset();
      }
     else{
          Toast.makeText(this, "please fill the missing data...", Toast.LENGTH_SHORT).show();

      }

    }
    protected static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void Reset(){
        productimage.setImageResource(R.drawable.proimg);
        productname.setText("");
        productprice.setText("");
        productquantity.setText("");
        idforupdateordalete.setText("");
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
