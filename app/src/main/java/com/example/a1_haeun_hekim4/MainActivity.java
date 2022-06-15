package com.example.a1_haeun_hekim4;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a1_haeun_hekim4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final double CURRENT_MORNING_RATE = 0.132;
    private static final double CURRENT_AFTERNOON_RATE = 0.065;
    private static final double CURRENT_EVENING_RATE = 0.094;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View view) {
                Log.d("ABC", "Calculate button was pressed");

                // get the value from input box
                String morningUsageFromUI = binding.etMorningUsage.getText().toString();
                String afternoonUsageFromUI = binding.etAfternoonUsage.getText().toString();
                String eveningUsageFromUI = binding.etEveningUsage.getText().toString();


                //validate that the form fields were filled on
                if (morningUsageFromUI.isEmpty() || afternoonUsageFromUI.isEmpty() || eveningUsageFromUI.isEmpty()) {
                    Log.d("ABC", "It's required field");
                    binding.tvError.setText("Please enter your usage");
                    binding.tvError.setTextColor(Color.parseColor("#FF00FF"));
                    binding.tvError.setVisibility(View.VISIBLE);
                    return;
                }
                // once you input, the error should be gone
                binding.tvError.setVisibility(View.GONE);

                // calculate logic function + 2 decimal double digits
                double morning = Double.parseDouble(morningUsageFromUI) * CURRENT_MORNING_RATE;
                morning = Math.round(morning * 100.0) / 100.0;

                double afternoon = Double.parseDouble(afternoonUsageFromUI) * CURRENT_AFTERNOON_RATE;
                afternoon = Math.round(afternoon * 100.0) / 100.0;

                double evening = Double.parseDouble(eveningUsageFromUI) * CURRENT_EVENING_RATE;
                evening = Math.round(evening * 100.0) / 100.0;

                double totalUsage = morning + afternoon + evening;
                totalUsage = Math.round(totalUsage * 100.0) / 100.0;

                double salesTax = totalUsage * 0.13;
                salesTax = Math.round(salesTax * 100.0) / 100.0;

                double envRebateDeducted;

                if (!binding.swRenewableEnergySources.isChecked()) {
                    envRebateDeducted = 0;
                } else {
                    envRebateDeducted = Math.round(totalUsage * 0.08 * 100.0) / 100.0;
                }

                double totalRegulatoryCharges = Math.round((salesTax - envRebateDeducted) * 100.0) / 100.0;
                double totalBillAmount = Math.round((totalUsage + totalRegulatoryCharges) * 100.0) / 100.0;


                binding.tvResults.setText(
                        "Morning usage charges: $" + morning + "\nAfternoon usage charges: $" + afternoon + "\nEvening usage charges: $" + evening);

                binding.tvTotalUsage.setText(
                        "Total Usage Charges: $" + totalUsage);

                binding.tvSalesTax.setText(
                        "Sales Tax charges: $" + salesTax + "\nEnvironment Rebate: -$" + envRebateDeducted
                );

                binding.tvTotalRegulatoryCharges.setText(
                        "Total Regulatory charges: $" + totalRegulatoryCharges
                );

                binding.tvTotalBillAmount.setText(
                        "Total Bill Amount: $" + totalBillAmount
                );
            }
        });

        binding.btnRest.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Log.d("ABC", "Reset button was pressed");
                binding.etMorningUsage.setText("");
                binding.etAfternoonUsage.setText("");
                binding.etEveningUsage.setText("");
                binding.swRenewableEnergySources.setChecked(false);
                binding.tvError.setText("");
                binding.tvResults.setText("Morning + Afternoon + Evening Usages");
                binding.tvTotalUsage.setText("Total Usage Charges");
                binding.tvSalesTax.setText("Sales Tax + Environment Rebate");
                binding.tvTotalRegulatoryCharges.setText("Total Regulatory Charges");
                binding.tvTotalBillAmount.setText("Total Bill Amount");
            }
        });
    }
}