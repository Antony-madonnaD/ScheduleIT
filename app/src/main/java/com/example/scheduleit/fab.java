package com.example.scheduleit;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import java.text.BreakIterator;
import java.util.Calendar;

public class fab extends AppCompatActivity implements
        View.OnClickListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_VEHICLE_LOADER =0 ;
    Button btnDatePicker, imgbutton, btnTimePicker, btnevent, btncancel,btndone;
    EditText txtDate, txtTime, txtEvent;
    ImageView imageView;



    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";

    private static final String KEY_ACTIVE = "active_key";


    private String mTime;
    private String mDate;
    private String mTitle;
    private Uri mImage;
    Bitmap image;
    Bitmap bitmap;


    public fab(Uri mImage) {
        this.mImage = mImage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        btnevent =  findViewById(R.id.btn_event);


        txtDate = findViewById(R.id.in_date);
        txtTime =  findViewById(R.id.in_time);
        txtEvent =  findViewById(R.id.event);
        imageView = findViewById(R.id.image);
        imgbutton = findViewById(R.id.btn_image);
        btncancel = findViewById(R.id.btn_cancel);
        btndone = findViewById(R.id.btn_done);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btncancel.setOnClickListener(this);
        btndone.setOnClickListener(this);
        imgbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        txtEvent.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                txtEvent.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        if (savedInstanceState != null) {
            String savedTitle = savedInstanceState.getString(KEY_TITLE);
            txtEvent.setText(savedTitle);
            mTitle = savedTitle;

            String savedTime = savedInstanceState.getString(KEY_TIME);
            txtTime.setText(savedTime);
            mTime = savedTime;

            String savedDate = savedInstanceState.getString(KEY_DATE);
            txtDate.setText(savedDate);
            mDate = savedDate;

            image = savedInstanceState.getParcelable("BitmapImage");
            imageView.setImageBitmap(image);
            BreakIterator textTargetUri = null;
            textTargetUri.setText(savedInstanceState.getString("path_to_picture"));
        }

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v == btncancel) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

        if(v == btndone){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }

    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}