package ru.evgeniy.androidacademy.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.evgeniy.androidacademy.R;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout mLinearLayout;
    private ImageButton mImBtn1, mImBtn2, mImBtn3;
    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();

        mImBtn1.setOnClickListener(view -> openLink("https://t.me/EvgeniyKam"));
        mImBtn2.setOnClickListener(view -> openLink("https://vk.com/id270708"));
        mImBtn3.setOnClickListener(view -> openLink("https://github.com/jonart"));
        mButton.setOnClickListener(view -> { if (isEmpty()) sendMessage();});
        createTextView();
    }

    private void init() {
        mImBtn1 = findViewById(R.id.ib_first);
        mImBtn2 = findViewById(R.id.ib_second);
        mImBtn3 = findViewById(R.id.ib_third);
        mButton = findViewById(R.id.btn_send_comment);
        mLinearLayout = findViewById(R.id.my_linear);
        mEditText = findViewById(R.id.et_message);
    }


    private boolean isEmpty() {
        return !mEditText.getText().toString().isEmpty();
    }

    private void openLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            showMessage(getString(R.string.no_apps_browser));
        }
    }

    private void sendMessage() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jonart2008@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
        intent.putExtra(Intent.EXTRA_TEXT, mEditText.getText().toString());
        if (isEmailInstalled(getApplicationContext())) {
            startActivity(Intent.createChooser(intent, getString(R.string.are_you_want)));
        } else {
            showMessage(getString(R.string.no_apps_email));
        }
    }

    private void showMessage(String msg) {
        Snackbar.make(this.mLinearLayout, msg, Snackbar.LENGTH_LONG).show();
    }

    private boolean isEmailInstalled(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 0);

        if (list.size() == 0)
            return false;
        else
            return true;
    }

    private void createTextView() {
        TextView tv = new TextView(this);
        tv.setText(R.string.disclaimer);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        mLinearLayout.addView(tv);
    }
}
