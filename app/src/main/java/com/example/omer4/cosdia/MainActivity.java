package com.example.omer4.cosdia;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.widget.CompoundButton.*;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    AlertDialog.Builder adb;
    LinearLayout mydialog;
    EditText D_M, Fn;
    ToggleButton tb;


    TextView X1, Dorm, Num, Sn;
    ListView lv;

    String[] st = new String[20];
    double[] Series = new double[20];

    double Fnum;
    double Formu;
    int x = 1;
    boolean bo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        X1 = findViewById(R.id.tv1);
        Dorm = findViewById(R.id.tv2);
        Num = findViewById(R.id.tv3);
        Sn = findViewById(R.id.tv4);


        lv = findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        tb = findViewById(R.id.tb);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    DialogInterface.OnClickListener myclick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String str1 = Fn.getText().toString();
            String str2 = D_M.getText().toString();

            if (str1.equals("") || (str2.equals("") || str1.equals(".") || str1.equals("-") || str1.equals("-.") || str2.equals(".") || str2.equals("-") || str2.equals("-."))) {
               dialog.cancel();
                Toast.makeText(MainActivity.this, "One of the values is incorrect or missing...",Toast.LENGTH_SHORT).show();


            }
            else {

                x = 1;

                X1.setText("a1=" + str1);
                Dorm.setText("d/q=" + str2);

                double FirstNum = Double.parseDouble(str1);
                double Formula = Double.parseDouble(str2);

                Fnum = FirstNum;
                Formu = Formula;

                Series[0] = FirstNum;

                if (!bo) {
                    for (int i = 1; i < 20; i++) {
                        Series[i] = Series[i - 1] + Formula;
                    }
                } else {
                    for (int i = 1; i < 20; i++) {
                        Series[i] = Series[i - 1] * Formula;

                    }
                }
                for (int j = 0; j < 20; j++) {
                    st[j] = String.valueOf(Series[j]);
                }
                ArrayAdapter<String> adp;
                adp = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, st);
                lv.setAdapter(adp);
                bo = false;

            }
        }

    };

    public void GetData(View view) {
        mydialog = (LinearLayout) getLayoutInflater().inflate(R.layout.customized, null);
        Fn = mydialog.findViewById(R.id.et1);
        D_M = mydialog.findViewById(R.id.et2);

        adb = new AlertDialog.Builder(this);

        adb.setView(mydialog);
        adb.setPositiveButton("Ok", myclick);
        adb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i== Dialog.BUTTON_NEGATIVE) {
                    dialogInterface.cancel();
                }

            }
        });


        adb.show();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int location, long id) {
        location = location + 1;
        Num.setText("location:" + location);
        double SN;
        if (bo == false) {
            SN = location * (2 * Fnum + Formu * (location - 1)) / 2;
        } else {
            SN = Fnum * (Math.pow(Formu, (location)) - 1) / (Formu - 1);
        }
        Sn.setText("Sn=" + SN);

    }


    public void click(View view) {
        if (x == 1) {
            bo = true;
            x=0;
        } else {
            bo = false;
            x = 1;
        }
    }
}



