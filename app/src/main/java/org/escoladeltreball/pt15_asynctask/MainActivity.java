package org.escoladeltreball.pt15_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int lCount;
    EditText number;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fib = findViewById(R.id.fibb);
        Button fibAsync = findViewById(R.id.fibAsync);
        number = findViewById(R.id.numero);
        result = findViewById(R.id.result);
        fib.setOnClickListener(this);
        fibAsync.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fibb:
                if (number.getText().toString() == null || number.getText().toString() == "") {
                    Toast.makeText(this, "Inserta un número vàlid", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int n = Integer.parseInt(number.getText().toString());
                        for (int i = 0; i <= n; i++) {
                            Hilos(n);
                        }

                        result.setText(String.valueOf((int) n));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Error", e.getMessage() + " " + e.getStackTrace());
                    }
                }
                break;
            case R.id.fibAsync:
                CalculaFibonacci cF = new CalculaFibonacci();
                cF.execute();
                break;


        }
    }

    private void Hilos(int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //fibonacci(num);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Tasca Thread finalitzada", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }


    public long fibonacci(long n) {
        if (n == 0)
            return 0;
        else if (n == 1)
            return 1;
        else {
            lCount = +1;
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }


    public class CalculaFibonacci extends AsyncTask<Integer, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(Integer... integers) {
            //return Long.valueOf(fibonacci(params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}
