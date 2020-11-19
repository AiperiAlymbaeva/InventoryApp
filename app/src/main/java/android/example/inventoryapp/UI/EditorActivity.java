package android.example.inventoryapp.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.inventoryapp.R;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "package android.example.inventoryapp.EXTRA_ID";
    public static final String EXTRA_NAME = "package android.example.inventoryapp.EXTRA_NAME";
    public static final String EXTRA_MODEL = "package android.example.inventoryapp.EXTRA_MODEL";
    public static final String EXTRA_PRICE = "package android.example.inventoryapp.EXTRA_PRICE";
    public static final String EXTRA_QUANTITY = "package android.example.inventoryapp.EXTRA_QUANTITY";
    public static final String EXTRA_SUPPLIER = "package android.example.inventoryapp.EXTRA_SUPPLIER";
    public static final String EXTRA_IMAGE = "package android.example.inventoryapp.EXTRA_IMAGE";

    public static int CAMERA_INTENT = 51;
    final int TYPE_PHOTO = 1;
    final int TYPE_VIDEO = 2;

    private boolean mInventoryHasChanged = false;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    File dir;
    File img;
    final String TAG = "myLog";
    String currentPhotoPath;
    Uri photoURI;


    private EditText editText_title;
    private EditText editText_model;
    private EditText editText_quantity;
    private EditText editText_supplier;
    private Bitmap bitmapImage;
    private EditText editText_price;
    private Button button_takePhoto;
    private ImageView image_view_takePhoto;
    private String pictureImagePath = "";

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mInventoryHasChanged = true;
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        editText_title = findViewById(R.id.edit_inventory_name);
        editText_model = findViewById(R.id.edit_model);
        editText_price = findViewById(R.id.edit_inventory_price);
        editText_supplier = findViewById(R.id.edit_inventory_supplier);
        button_takePhoto = findViewById(R.id.button_takePhoto);
        bitmapImage = null;
        editText_quantity = findViewById(R.id.edit_quantity);
        image_view_takePhoto = findViewById(R.id.image_view_takePhoto);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editText_title.setText(intent.getStringExtra(EXTRA_NAME));
            editText_model.setText(intent.getStringExtra(EXTRA_MODEL));
            editText_supplier.setText(intent.getStringExtra(EXTRA_SUPPLIER));
            editText_price.setText(String.valueOf(intent.getIntExtra(EXTRA_PRICE, 1)));
            editText_quantity.setText(String.valueOf(intent.getIntExtra(EXTRA_QUANTITY, 1)));
        } else {
            setTitle("Add Note");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveNote();
                return true;
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mInventoryHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void saveNote() {
        String title = "";
        String model = "";
        String supplier = "";
        int price = 0;
        int quantity = 0;

        if (editText_title.getEditableText().toString().length() == 0 || editText_model.getEditableText()
                .toString()
                .length() == 0) {
            Toast.makeText(this, "Please input title and description", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (editText_price.getEditableText().toString().length() == 0) {
            Toast.makeText(this, "Please input title and description", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        title = editText_title.getText().toString();
        model = editText_model.getText().toString();
        supplier = editText_supplier.getText().toString();
        price = Integer.parseInt(editText_price.getText().toString());
        quantity = Integer.parseInt(editText_quantity.getText().toString());
        String picture = photoURI.toString();



        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, title);
        data.putExtra(EXTRA_MODEL, model);
        data.putExtra(EXTRA_SUPPLIER, supplier);
        data.putExtra(EXTRA_QUANTITY, quantity);
        data.putExtra(EXTRA_PRICE, price);
        data.putExtra(EXTRA_IMAGE, picture);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Glide.with(this).load(img.getAbsolutePath()).into(image_view_takePhoto);
        }
    }






    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "android.example.fileProviders",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

            }
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }







    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        img = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                dir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = img.getAbsolutePath();
        return img;
    }


}
