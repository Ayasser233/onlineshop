package com.example.shoppingsystem.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.shoppingsystem.Database.MyDataBase;
import com.example.shoppingsystem.Model.CategoryModel;
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
import java.util.ArrayList;
import java.util.List;

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

        SharedPreferences preferences=getSharedPreferences("addCategory1",MODE_PRIVATE);
        str1=preferences.getString("add1","show");
        if(str1.equals("hiddin2")){
            addCategory.setText("");
        }
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!str1.equals("hiddin2")){
                    addCategory();
                }
                SharedPreferences preferences=getSharedPreferences("addCategory1",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("add1","hiddin2");
                editor.apply();
                addCategory.setText("");
            }
        });
        getAllcategory();

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
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleImagePickerResult);
    }

    protected void addCategory(){
        database.insertCategory(new CategoryModel("Women"),0);
        database.insertCategory(new CategoryModel("Men"),0);
        database.insertCategory(new CategoryModel("Children"),0);
        database.insertCost(0);
    }

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
    public void addProduct()
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
          Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
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
    protected void getAllcategory(){

        List<String> cate=new ArrayList<>();
        Cursor cursor=database.getCategory();
        if (cursor!=null){
            while (!cursor.isAfterLast()){
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,cate);
            proCategory.setAdapter(adapter);///Spinner
        }
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
        addCategory=(TextView) findViewById(R.id.addCategory);
        Generate=(Button)findViewById(R.id.generate);
        database = new MyDataBase(this);
    }
}
