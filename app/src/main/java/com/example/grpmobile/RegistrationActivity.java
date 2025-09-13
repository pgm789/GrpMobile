package com.example.grpmobile;  // 修改为你的包路径

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grpmobile.R;  // 修改为你的包路径

public class RegistrationActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvLocation, tvDate, tvStatus;
    private ImageView imageActivity;
    private EditText etName, etContact, etDonationAmount;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);  // 你的注册界面布局

        // 初始化控件
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvLocation = findViewById(R.id.tvLocation);
        tvDate = findViewById(R.id.tvDate);
        tvStatus = findViewById(R.id.tvStatus);
        imageActivity = findViewById(R.id.imageActivity);

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etDonationAmount = findViewById(R.id.etDonationAmount);
        btnSubmit = findViewById(R.id.btnSubmit);

        // 接收 Intent 传来的数据
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tvTitle.setText(extras.getString("title"));
            tvDescription.setText(extras.getString("description"));
            tvLocation.setText("Location: " + extras.getString("location"));
            tvDate.setText("Date: " + extras.getString("date"));
            tvStatus.setText(extras.getString("status"));
            imageActivity.setImageResource(extras.getInt("imageResId"));
        }

        // 设置提交按钮的点击监听
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取用户输入
                String name = etName.getText().toString().trim();
                String contact = etContact.getText().toString().trim();
                String donationStr = etDonationAmount.getText().toString().trim();

                // 输入验证
                if (name.isEmpty()) {
                    etName.setError("Please enter your name");
                    etName.requestFocus();
                    return;
                }

                if (contact.isEmpty()) {
                    etContact.setError("Please enter your contact number");
                    etContact.requestFocus();
                    return;
                }

                if (donationStr.isEmpty()) {
                    etDonationAmount.setError("Please enter donation amount");
                    etDonationAmount.requestFocus();
                    return;
                }

                double donation;
                try {
                    donation = Double.parseDouble(donationStr);
                } catch (NumberFormatException e) {
                    etDonationAmount.setError("Invalid amount");
                    etDonationAmount.requestFocus();
                    return;
                }

                // 格式化捐赠金额为2位小数
                String formattedAmount = String.format("%.2f", donation);

                // 显示捐赠成功的提示信息
                Toast.makeText(RegistrationActivity.this,
                        "Thank you " + name + " for donating RM" + formattedAmount +
                                ". We may contact you at " + contact,
                        Toast.LENGTH_LONG).show();

                // 提交后关闭当前页面
                finish();
            }
        });
    }
}
