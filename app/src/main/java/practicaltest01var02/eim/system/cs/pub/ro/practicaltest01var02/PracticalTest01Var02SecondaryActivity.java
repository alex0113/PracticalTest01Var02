package practicaltest01var02.eim.system.cs.pub.ro.practicaltest01var02;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PracticalTest01Var02SecondaryActivity extends AppCompatActivity {

    TextView textView;
    Button button_correct, button_incorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_secondary);
        textView = (TextView) findViewById(R.id.textView);
        button_correct = (Button)findViewById(R.id.button_correct);
        button_incorrect = (Button)findViewById(R.id.button_incorrect);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        textView.setText(result);
        button_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Correct");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        button_incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Incorrect");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
