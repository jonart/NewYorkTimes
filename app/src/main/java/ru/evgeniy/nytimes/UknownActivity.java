package ru.evgeniy.nytimes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import ru.evgeniy.nytimes.Activity.PreviewActivity;

public class UknownActivity extends AppCompatActivity {

    public String EMAIL_KEY = "EMAIL_KEY";
    private EditText mEditText;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uknown);
        mEditText = findViewById(R.id.et_email);
        mButton = findViewById(R.id.btn);
        final Intent intent = new Intent(this, PreviewActivity.class);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        mButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mButton.setOnClickListener(v -> {
                intent.putExtra(EMAIL_KEY, mEditText.getText().toString());
                startActivity(intent);
        });
    }
}
