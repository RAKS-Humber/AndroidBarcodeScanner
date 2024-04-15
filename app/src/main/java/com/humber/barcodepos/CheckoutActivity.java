package com.humber.barcodepos;

import static com.humber.barcodepos.MainActivity.mOrder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.humber.barcodepos.adapter.CheckoutAdapter;
import com.humber.barcodepos.models.Order;
import com.humber.barcodepos.models.Product;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    public TextView subTotal;
    public TextView taxableTotal;
    public TextView total;
    public ImageView back;
    public ListView list;

    public Button generateInvoiceBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        back = findViewById(R.id.back_icon);
        list = (ListView) findViewById(R.id.list_checkout);
        subTotal = findViewById(R.id.sub_total);
        taxableTotal = findViewById(R.id.taxable_total);
        total = findViewById(R.id.total);
        generateInvoiceBtn =findViewById(R.id.btn_generate_invoice);
        ArrayAdapter<Product> adapter = new CheckoutAdapter(this, R.layout.list_tile, mOrder.getOrder());
        list.setAdapter(adapter);

        subTotal.setText(String.valueOf(mOrder.getSubTotal()));
        taxableTotal.setText(String.valueOf(mOrder.getTaxableSubTotal()));
        total.setText(String.valueOf(mOrder.getTotal()));


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


        generateInvoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()) {
                    ArrayList<Product> products = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
                    try {
                        createPDF(Invoice_number,products);
                        Invoice_number++;
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        mOrder=new Order();
                        startActivity(intent);
                        finish();
                    } catch (FileNotFoundException e) {

                        throw new RuntimeException(e);
                    }

                } else {
                    // Request storage permissions
                    requestStoragePermission();
                }
            }
        });
    }

    public static int Invoice_number=1;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private boolean checkStoragePermission() {

        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform file operations
                ArrayList<Product> products = (ArrayList<Product>) getIntent().getSerializableExtra("productList");
                try {
                    createPDF(Invoice_number,products);
                    Invoice_number++;
                } catch (FileNotFoundException e) {

                    throw new RuntimeException(e);
                }
            } else {
                // Permission denied, show a message to the user

                Toast.makeText(this, "Storage permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void createPDF(int Invoice_number, List<Product> products) throws FileNotFoundException {
        String pdfPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyyMMdd");

        // Get the current date
        Date today = new Date();

        // Format the date according to the pattern
        String formattedDate = dateFormatter.format(today);

        String formattedDate1 = dateFormatter2.format(today);
        int min = 1;
        int max = 100;
        int randomInt = (int) (Math.random() * (max - min + 1)) + min;

        String fileName = "Invoice_" + "_" +randomInt + "_" +formattedDate+".pdf";

        //File file = new File(getExternalFilesDir(null), fileName);

        File file=new File(pdfPath,fileName);

        if(file.exists())
            file.delete();
        OutputStream outputStream=new FileOutputStream(file);

        PdfWriter writer=new PdfWriter(file);

        PdfDocument pdfDocument=new PdfDocument(writer);

        Document document=new Document(pdfDocument);

        float columnWidth[]={140,140,140,140};

        Table detailstable=new Table(columnWidth);

        Drawable d1 = getDrawable(R.drawable.invoice);
        Bitmap bitmap1= ((BitmapDrawable)d1).getBitmap();

        ByteArrayOutputStream stream1= new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,stream1);
        byte[] bitmapData1=stream1.toByteArray();

        ImageData imageData1= ImageDataFactory.create(bitmapData1);

        com.itextpdf.layout.element.Image image1=new com.itextpdf.layout.element.Image(imageData1);
        image1.setHeight(100f);

        DeviceRgb invoiceGreen=new DeviceRgb(51,204,51);
        DeviceRgb invoiceGray=new DeviceRgb(220,220,220);

        //R1
        detailstable.addCell(new Cell(3,1).add(image1).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell(1,2).add(new Paragraph("Invoice").setFontSize(26f).setFontColor(invoiceGreen)).setBorder(Border.NO_BORDER));


        //R2
        //detailstable.addCell(new Cell().add(new Paragraph("")));
        detailstable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell().add(new Paragraph("Invoice No: ")).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell().add(new Paragraph(String.valueOf(Invoice_number))).setBorder(Border.NO_BORDER));

        //R3
        //detailstable.addCell(new Cell().add(new Paragraph("")));
        detailstable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell().add(new Paragraph("Invoice Date: ")).setBorder(Border.NO_BORDER));
        detailstable.addCell(new Cell().add(new Paragraph(formattedDate1)).setBorder(Border.NO_BORDER));


        //ArrayList<Product> products=new ArrayList<Product>();


        float columnWidthProducts[]={62,162,112,112,112};
        Table productsTable=new Table(columnWidthProducts);

        productsTable.addCell(new Cell().add(new Paragraph("Sr No.").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph("Product Name").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph("Product Quantity").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph("Product Price").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph("Total Price").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        double totalPrice=0.0f;
        for(int i=0;i<products.size();i++)
        {
            productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(i+1))).setBackgroundColor(invoiceGray));
            productsTable.addCell(new Cell().add(new Paragraph(products.get(i).getName())).setBackgroundColor(invoiceGray));
            productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(products.get(i).getQuantity()))).setBackgroundColor(invoiceGray));
            productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(products.get(i).getPrice()))).setBackgroundColor(invoiceGray));
            productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(products.get(i).getQuantity()*products.get(i).getPrice()))).setBackgroundColor(invoiceGray));
            totalPrice=totalPrice+products.get(i).getQuantity()*products.get(i).getPrice();
        }

        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("Sub-total").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(totalPrice)).setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));



        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("Tax(13%)").setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(0.13*totalPrice)).setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));

        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        productsTable.addCell(new Cell().add(new Paragraph("Grand Total").setBold().setFontSize(16).setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        productsTable.addCell(new Cell().add(new Paragraph(String.valueOf(totalPrice+(0.13*totalPrice))).setBold().setFontSize(16).setFontColor(ColorConstants.WHITE)).setBackgroundColor(invoiceGreen));
        //pdfDocument.add(table);

        float columnWidthContactUS[]={50,250,260};

        Table contactUsTable=new Table(columnWidthContactUS);

        Drawable d2 = getDrawable(R.drawable.contact_us);
        Bitmap bitmap2= ((BitmapDrawable)d2).getBitmap();

        ByteArrayOutputStream stream2= new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG,100,stream2);
        byte[] bitmapData2=stream2.toByteArray();

        ImageData imageData2= ImageDataFactory.create(bitmapData2);

        com.itextpdf.layout.element.Image image2=new com.itextpdf.layout.element.Image(imageData2);
        image2.setHeight(100);


        Drawable d3 = getDrawable(R.drawable.thanks);
        Bitmap bitmap3= ((BitmapDrawable)d3).getBitmap();

        ByteArrayOutputStream stream3= new ByteArrayOutputStream();
        bitmap3.compress(Bitmap.CompressFormat.PNG,100,stream3);
        byte[] bitmapData3=stream3.toByteArray();

        ImageData imageData3= ImageDataFactory.create(bitmapData3);

        com.itextpdf.layout.element.Image image3=new Image(imageData3);
        image3.setHeight(100);
        image3.setHorizontalAlignment(HorizontalAlignment.RIGHT);

        contactUsTable.addCell(new Cell(3,1).add(image2).setBorder(Border.NO_BORDER));
        contactUsTable.addCell(new Cell().add(new Paragraph("akshath.dsouza@gmail.com")).setBorder(Border.NO_BORDER));
        contactUsTable.addCell(new Cell(3,1).add(image3).setBorder(Border.NO_BORDER));
        contactUsTable.addCell(new Cell().add(new Paragraph("123-123-1234")).setBorder(Border.NO_BORDER));
        //contactUsTable.addCell(new Cell().add(new Paragraph("")));
        contactUsTable.addCell(new Cell().add(new Paragraph("Address")).setBorder(Border.NO_BORDER));
        //contactUsTable.addCell(new Cell().add(new Paragraph("")));


        document.add(detailstable);
        document.add(new Paragraph("\n"));
        document.add(productsTable);
        document.add(new Paragraph("\n\n\n\n\n\n(Authorised Signatory)\n\n\n").setTextAlignment(TextAlignment.RIGHT));
        document.add(contactUsTable);
        pdfDocument.close();
        System.out.println("Invoice Location:");
        System.out.println(""+file.getPath());
        System.out.println(""+file.getAbsolutePath());
        System.out.println(""+fileName);
        Toast.makeText(this,"Invoice  created Successfully",Toast.LENGTH_LONG).show();
    }


}
