package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox isWhipped= (CheckBox) findViewById(R.id.cream);
        CheckBox isCholocate=(CheckBox) findViewById(R.id.Chocolate);
        EditText name=(EditText) findViewById(R.id.name);
        boolean hasWhipped=isWhipped.isChecked();
        boolean hasChocolate=isCholocate.isChecked();
        String getName=name.getText().toString();

        int price=calcutePrice(hasWhipped,hasChocolate);
        String message=createOrderSummary(getName, price, hasWhipped, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getName);
        intent.putExtra(Intent.EXTRA_TEXT,message );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(createOrderSummary(getName, price, hasWhipped, hasChocolate));
    }
    public void Plus(View view) {
        quantity=quantity+1;
        if(quantity>100){
            Toast.makeText(this,"You cannot have more than 100", Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }
    public void Rest(View view) {
        quantity=quantity-1;
        if(quantity<0){
            Toast.makeText(this,"You cannot have less than 1", Toast.LENGTH_LONG).show();
            return;
        }
        displayQuantity(quantity);
    }

    private int calcutePrice(boolean hasWhipped, boolean hasChocolate){
        int pricePerCup=5;

        if(hasWhipped) {
            pricePerCup=pricePerCup+1;
        }
        if(hasChocolate){
            pricePerCup=pricePerCup+2;
        }

        return pricePerCup*quantity;
    }
    private String createOrderSummary(String getname,int price, boolean hasWhipped, boolean hasChocolate){
        String priceMessage= getString(R.string.order_summary_name, getname)+"\nAdd Whipped cream? "+hasWhipped+"\nChocolate? "+hasChocolate+
            "\n"+  getString(R.string.quantity)+quantity+"\nTotal: $"+price+"\n"+getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderTextView = (TextView) findViewById(R.id.order);
        OrderTextView.setText(message);
    }

    }

    /**
     * This method displays the given price on the screen.
     *     private void displayPrice(int number) {
     *         TextView priceTextView = (TextView) findViewById(R.id.price);
     *         priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
     *     }
     */

