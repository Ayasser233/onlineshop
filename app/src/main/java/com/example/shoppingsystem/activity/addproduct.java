package com.example.shoppingsystem.activity;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import com.example.shoppingsystem.Database.MyDataBase;
import com.example.shoppingsystem.Model.CategoryModel;
import com.example.shoppingsystem.Model.ProductModel;
import com.example.shoppingsystem.R;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addproduct extends AppCompatActivity {
    ImageView productimage;
    EditText productname, productprice, productquantity,idforupdateordalete;
    Spinner proCategory;
    ArrayAdapter adapter;
    Button upload_btn,updateproduct,daleteproduct,generate;
    TextView reset_btn,addCategory;
    MyDataBase database;
    String str1;

    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private static final int GALLERY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);


        intiView();

       /* SharedPreferences preferences=getSharedPreferences("addCategory1",MODE_PRIVATE);
        str1=preferences.getString("add1","show");
        if(str1.equals("hiddin2")){
            addCategory.setText("");
        }
        addCategory.setOnClickListener(view -> {
            if(!str1.equals("hiddin2")){
                addCategory();
            }
            SharedPreferences preferences1 =getSharedPreferences("addCategory1",MODE_PRIVATE);
            SharedPreferences.Editor editor= preferences1.edit();
            editor.putString("add1","hiddin2");
            editor.apply();
            addCategory.setText("");
        });*/


        getAllcategory();

        reset_btn.setOnClickListener(v -> Reset());
        productimage.setOnClickListener(v -> chooseImage());
        upload_btn.setOnClickListener(v -> addProduct());
        generate.setOnClickListener(v -> generate());
        daleteproduct.setOnClickListener(v -> deleteproduct());
        updateproduct.setOnClickListener(v -> updateproduct());

//        imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                this::handleImagePickerResult);
        }
    public void generate(){
        if(idforupdateordalete.getText().toString().replace(""," ").trim().isEmpty())
        {

            Toast.makeText(this, "Enter id to Edit or Delete", Toast.LENGTH_SHORT).show();
        }
        else{
            Cursor c =database.getProductById(idforupdateordalete.getText().toString());
            productname.setText(c.getString(1));
            productquantity.setText(c.getInt(4)+" ");
            productprice.setText(c.getDouble(3)+" ");
            InputStream images=new ByteArrayInputStream(c.getBlob(2));
            Bitmap bitmap= BitmapFactory.decodeStream(images);
            productimage.setImageBitmap(bitmap);
        }

    }

    protected void addCategory(){
        database.insertCategory(new CategoryModel("Women"),0);
        database.insertCategory(new CategoryModel("Men"),0);
        database.insertCategory(new CategoryModel("Children"),0);
        database.insertCategory(new CategoryModel("cats"),0);
        database.insertCost(0);
    }




//    private void handleImagePickerResult(ActivityResult result) {
//        if (result.getResultCode() == RESULT_OK) {
//            Intent data = result.getData();
//            if (data != null) {
//                Uri imageUri = data.getData();
//                // Display the image using the retrieved imageUri
//                ImageView imageView = findViewById(R.id.product_image);
//                imageView.setImageURI(imageUri);
//            }
//        }
//    }
        protected void chooseImage() {
            //Create an Intent with action as ACTION_PICK
            Intent intent = new Intent(Intent.ACTION_PICK);
            // Sets the type as image/*. This ensures only components of type image are selected
            intent.setType("image/*");
            // Launching the Intent
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, GALLERY_REQUEST_CODE);
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null&&data.getData()!=null) {

            Uri imageFilePath = data.getData();
            Bitmap imageToStore;
            try {
               imageToStore= MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
               productimage.setImageBitmap(imageToStore);

            } catch (IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }
    public void addProduct()
    {
      String name=productname.getText().toString(),
              price=productprice.getText().toString(),
              quantity=productquantity.getText().toString();
      int CategoryId=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
      byte []image=imageViewToByte(productimage);

      if(!name.isEmpty()&&!price.isEmpty()&&!quantity.isEmpty()&&!proCategory.getSelectedItem().toString().isEmpty()&&image.length!=0)
      {
          ProductModel productmodel=new ProductModel(getApplicationContext(),Integer.parseInt(quantity),CategoryId,name,image,Double.parseDouble(price));
          String msg=database.insertProduct(productmodel);
          Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
          Reset();
          Toast.makeText(this, ""+image, Toast.LENGTH_LONG).show();
      }
     else
     {
          Toast.makeText(this, "please fill the missing data...", Toast.LENGTH_LONG).show();
     }
    }
    public void updateproduct(){

        String name=productname.getText().toString();
        String price=productprice.getText().toString();
        String quan=productquantity.getText().toString();

        int catid=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
        byte [] image=imageViewToByte(productimage);
        if(!idforupdateordalete.getText().toString().isEmpty()) {
            if (!name.isEmpty() && !price.isEmpty() && !quan.isEmpty() && !proCategory.getSelectedItem().toString().isEmpty() && image.length != 0) {
                ProductModel productModel = new ProductModel(getApplicationContext(), Integer.parseInt(quan), catid, name, image, Double.parseDouble(price));
                String str = database.updateProduct(productModel, idforupdateordalete.getText().toString());
                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                Reset();
            } else {
                Toast.makeText(this, "please fill the missing data...", Toast.LENGTH_LONG).show();
            }
        }else
        {
            Toast.makeText(this, "Enter id to Edit ", Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteproduct(){
        if(idforupdateordalete.getText().toString().replace(""," ").trim().isEmpty())
        {

            Toast.makeText(this, "Enter id to Edit or Delete", Toast.LENGTH_SHORT).show();
        }else {
            String str = database.delete(idforupdateordalete.getText().toString());
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
        Reset();
    }

    @NonNull
    protected static byte[] imageViewToByte(@NonNull ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
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
        generate=(Button)findViewById(R.id.generate);
        database = new MyDataBase(this);
    }
}
