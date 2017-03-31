package practicaltest01var02.eim.system.cs.pub.ro.practicaltest01var02;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private EditText editText1, editText2;
    private Button button_plus, button_minus, button_second;
    private TextView textView;
    private Context context;
    private int serviceStatus = Constants.SERVICE_STOPPED;
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                int left_nr = Integer.parseInt(editText1.getText().toString());
                int right_nr = Integer.parseInt(editText2.getText().toString());

                int result;
                String str_result = "";
                switch (view.getId()) {
                    case R.id.button_minus:
                        result = left_nr - right_nr;
                        str_result = new Integer(result).toString();
                        textView.setText(editText1.getText().toString() + "-" + editText2.getText().toString() + "=" + str_result);
                        break;
                    case R.id.button_plus:
                        result = left_nr + right_nr;
                        str_result = new Integer(result).toString();
                        textView.setText(editText1.getText().toString() + "+" + editText2.getText().toString() + "=" + str_result);
                        break;
                    case R.id.button_second:
                        Intent myIntent = new Intent(context, PracticalTest01Var02SecondaryActivity.class);
                        myIntent.putExtra("result", textView.getText().toString());
                        Toast.makeText(context, "start", Toast.LENGTH_SHORT);
                        startActivityForResult(myIntent, 1);
                        break;



                }
                if (serviceStatus == Constants.SERVICE_STOPPED) {
                    Intent intent = new Intent(getApplicationContext(), PracticalText01Var02Service.class);
                    intent.putExtra("firstNumber", left_nr);
                    intent.putExtra("secondNumber", right_nr);
                    getApplicationContext().startService(intent);
                    serviceStatus = Constants.SERVICE_STARTED;
                }
            } catch (NumberFormatException e) {
            Toast.makeText(context, "Numberss, fool !", Toast.LENGTH_SHORT).show();
        }

        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
            Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private IntentFilter intentFilter = new IntentFilter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);
        button_minus=  (Button)findViewById(R.id.button_minus);
        button_plus = (Button)findViewById(R.id.button_plus);
        button_plus.setOnClickListener(buttonClickListener);
        button_minus.setOnClickListener(buttonClickListener);
        button_second = (Button)findViewById(R.id.button_second);
        button_second.setOnClickListener(buttonClickListener);
        editText1 = (EditText)findViewById(R.id.edit_number1);
        editText2 = (EditText)findViewById(R.id.edit_number2);
        textView = (TextView)findViewById(R.id.text_result) ;
        editText1.setText("0");
        editText2.setText("0");
        context =getApplicationContext();
        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalText01Var02Service.class);
        stopService(intent);
        super.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("number1", editText1.getText().toString());
        savedInstanceState.putString("number2", editText2.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("number1")) {
            editText1.setText(savedInstanceState.getString("number1"));
        } else {
            editText1.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("number2")) {
            editText2.setText(savedInstanceState.getString("number2"));
        } else {
            editText2.setText(String.valueOf(0));
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                textView.setText(result);
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//o
}
