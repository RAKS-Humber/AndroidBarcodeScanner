package com.humber.barcodepos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.humber.barcodepos.adapter.OrderAdapter;
import com.humber.barcodepos.fragment.OrderListFragment;
import com.humber.barcodepos.models.Order;
import com.humber.barcodepos.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;



public class MainActivity extends FragmentActivity {
    private Timer scanTimer = new Timer();
    ArrayList<String> al=new ArrayList<String>();
    private boolean isProcessing = false;
    private boolean isScanningEnabled = true;
    private long SCAN_DELAY = 2000;
    public static Order mOrder = new Order();
    public static ArrayList<Product> m1Order = new ArrayList<Product>();
    private static final String TAG = "MLKit Barcode";
    private static final int PERMISSION_CODE = 1001;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private PreviewView previewView;
    private CameraSelector cameraSelector;
    private ProcessCameraProvider cameraProvider;
    private Preview previewUseCase;
    private ImageAnalysis analysisUseCase;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private OrderAdapter mAdapter;
    OrderListFragment fragment;
    FirebaseAuth mAuth;

    FirebaseUser user;

    Button logoutBtn;
    TextView tv;
    Button btn_checkout;

    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user==null)
        {
            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);
        context = this;
        previewView = findViewById(R.id.previewView);
        //tv=findViewById(R.id.textView);
        logoutBtn=findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        fragment = (OrderListFragment) fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = OrderListFragment.newInstance();

            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
        btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                //startActivity(intent);


                Intent intent=new Intent(getApplicationContext(), ViewPaymentDetailsActivity.class);
                Bundle extra = new Bundle();
                List<Product> products=mOrder.getOrder();
                intent.putExtra("productList", (Serializable) products);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isScanningEnabled) {
            startCamera();
            isScanningEnabled = false; // Prevent starting the camera again until the delay is over
        }
    }

    public void startCamera() {
        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            setupCamera();
        } else {
            getPermissions();
        }
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA_PERMISSION}, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (requestCode == PERMISSION_CODE) {
            setupCamera();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        int lensFacing = CameraSelector.LENS_FACING_BACK;
        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindAllCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "cameraProviderFuture.addListener Error", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindAllCameraUseCases() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        builder.setTargetRotation(getRotation());

        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());

        try {
            cameraProvider
                    .bindToLifecycle(this, cameraSelector, previewUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind preview", e);
        }
    }

    private void bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }

        Executor cameraExecutor = Executors.newSingleThreadExecutor();

        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
        builder.setTargetRotation(getRotation());

        analysisUseCase = builder.build();
        analysisUseCase.setAnalyzer(cameraExecutor, this::analyze);

        try {
            cameraProvider
                    .bindToLifecycle(this, cameraSelector, analysisUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind analysis", e);
        }
    }

    protected int getRotation() throws NullPointerException {
        return previewView.getDisplay().getRotation();
    }

    @SuppressLint("UnsafeOptInUsageError")
    private void analyze(@NonNull ImageProxy image) {
        //System.out.println("Analyze Called");
        //System.out.println(isProcessing);
        //System.out.println(image.getImage()==null);
        if (isProcessing || image.getImage() == null) {
            image.close();
            return;
        }

        isProcessing = true;


        //if (image.getImage() == null) return;

        InputImage inputImage = InputImage.fromMediaImage(
                image.getImage(),
                image.getImageInfo().getRotationDegrees()
        );

       BarcodeScanner barcodeScanner = BarcodeScanning.getClient();

         /*barcodeScanner.process(inputImage)
                .addOnSuccessListener(this::onSuccessListener)
                .addOnFailureListener(e -> Log.e(TAG, "Barcode process failure", e))
                .addOnCompleteListener(task -> image.close());*/

        barcodeScanner.process(inputImage)
                .addOnSuccessListener(barcodes -> {
                    onSuccessListener(barcodes);
                    startScanDelay();
                    image.close();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Barcode process failure", e);
                    startScanDelay();
                    image.close();
                });
    }


    private void startScanDelay() {
        scanTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                isProcessing = false;
                isScanningEnabled = true; // Allow scanning again after the delay
            }
        }, SCAN_DELAY);
    }

    private void closeCamera() {

        cameraProvider.unbindAll();

        //cameraProvider.close();
    }


    private void onSuccessListener(List<Barcode> barcodes) {
        if (barcodes.size() > 0) {
            Toast.makeText(this, barcodes.get(0).getDisplayValue(), Toast.LENGTH_LONG).show();
            //finish();
            //closeCamera();

            al.add(barcodes.get(0).getDisplayValue());
            addProduct(barcodes.get(0).getDisplayValue());
//            Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
            //finish();
            //closeCamera();
        }

        //tv.setText(al.toString());
    }


    public void addProduct(String product_id)
    {
        al.add(product_id);
        DocumentReference docRef = db.collection("products").document(Objects.requireNonNull(product_id));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        boolean alreadyExists = false;
                        for (int i = 0; i < mOrder.getOrder().size(); i++) {
                            if(Objects.equals(mOrder.getOrder().get(i).getBarcode(), product_id)){
                                int tempQty = mOrder.getOrder().get(i).getQuantity() + 1;
                                mOrder.getOrder().get(i).setQuantity(tempQty);
                                mOrder.setSubTotal(mOrder.getSubTotal() + (Double) document.getData().get("price"));
                                if((Boolean) document.getData().get("isTaxable")){
                                    mOrder.setTaxableSubTotal(mOrder.getTaxableSubTotal() + (Double) document.getData().get("price"));
                                }
                                mOrder.setTotal(mOrder.getTaxableSubTotal() * mOrder.getTax() + (mOrder.getSubTotal() - mOrder.getTaxableSubTotal()));
                                alreadyExists = true;
                                Log.d(TAG, String.valueOf(mOrder.getOrder().get(i).getQuantity()));
                            }
                        }
                        if (!alreadyExists) {
                            Product mProduct = new Product();
                            mProduct.setName((String) document.getData().get("name"));
                            mProduct.setQuantity(1);
                            mProduct.setPrice((Double) document.getData().get("price"));
                            mProduct.setBarcode(product_id);
                            mProduct.setTaxable((Boolean) document.getData().get("isTaxable"));
                            mOrder.addProduct(mProduct);
                            mOrder.setSubTotal(mOrder.getSubTotal() + (Double) document.getData().get("price"));
                            if((Boolean) document.getData().get("isTaxable")){
                                mOrder.setTaxableSubTotal(mOrder.getTaxableSubTotal() + (Double) document.getData().get("price"));
                            }
                            mOrder.setTotal(mOrder.getTaxableSubTotal() * mOrder.getTax() + (mOrder.getSubTotal() - mOrder.getTaxableSubTotal()));
                        }
                        fragment.mAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Product is not in our inventory!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }



}