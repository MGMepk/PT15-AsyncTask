package org.escoladeltreball.pt15_asynctask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText number;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            Button fib = findViewById(R.id.fibb);
            Button fibAsync = findViewById(R.id.fibAsync);
            number = findViewById(R.id.numero);
            result = findViewById(R.id.result);
            fib.setOnClickListener(this);
            fibAsync.setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("onCreate: ", e.getMessage() + "  " + e.getCause());
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fibb:
                if (number.getText().toString().equals("")) {
                    Toast.makeText(this, "Inserta un número vàlid", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        long fib = 0;
                        int n = Integer.parseInt(number.getText().toString());
                        for (int i = 0; i <= n; i++) {
                            fib = fibonacci((long) n);
                        }
                        Hilos();
                        result.setText(String.valueOf(fib));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Error", e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                    }
                }
                break;
            case R.id.fibAsync:
                if (number.getText().toString().equals("")) {
                    Toast.makeText(this, "Inserta un número vàlid", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        CalculaFibonacci cF = new CalculaFibonacci();
                        int n = Integer.parseInt(number.getText().toString());
                        cF.execute(n);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("Error", e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
                    }
                }
                break;
        }
    }

    private void Hilos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Tasca finalitzada", Toast.LENGTH_LONG).show();
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
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }


    @SuppressLint("StaticFieldLeak")
    public class CalculaFibonacci extends AsyncTask<Integer, Void, Long> {

        @Override
        protected Long doInBackground(Integer... integers) {
            int number = integers[0];
            long fib = 0;
            for (int i = 0; i < number; i++) {
                fib = fibonacci(number);
            }
            return fib;

        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            String res = String.valueOf(aLong);
            result.setText(res);
            Toast.makeText(MainActivity.this, "Tasca finalitzada", Toast.LENGTH_LONG).show();
        }
    }
}
