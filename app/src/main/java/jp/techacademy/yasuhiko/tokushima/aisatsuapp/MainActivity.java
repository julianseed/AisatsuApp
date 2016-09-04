package jp.techacademy.yasuhiko.tokushima.aisatsuapp;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTextHour;
    TextView mTextMinute;
    TextView mTextAisatsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_timePicker = (Button) findViewById(R.id.timePickerButton);
        button_timePicker.setOnClickListener(this);

        mTextHour = (TextView) findViewById(R.id.textHour);
        mTextMinute = (TextView) findViewById(R.id.textMinute);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        mTextAisatsu = (TextView) findViewById(R.id.textAisatsu);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.timePickerButton) {
            showTimePickerDialog();
        } else if (v.getId() == R.id.button) {
            showAisatsu();
        }
    }

    // TimePickerDialogを表示して選択結果をtextHourとtextminuteに表示する
    private void showTimePickerDialog() {
        // ２回目以降のTimePickerDialog表示時に初期値が固定だと違和感があるので、画面の値を
        // 初期値としてセットするために使う変数
        int hour = Integer.parseInt(mTextHour.getText().toString());
        int minute = Integer.parseInt(mTextMinute.getText().toString());

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        if(hourOfDay > 10) {
                            mTextHour.setText(String.valueOf(hourOfDay));
                        } else {
                            String str_wk = "0" + String.valueOf(hourOfDay);
                            mTextHour.setText(str_wk);
                        }
                        if (minute > 10) {
                            mTextMinute.setText(String.valueOf(minute));
                        } else {
                            String str_wk = "0" + String.valueOf(minute);
                            mTextMinute.setText(str_wk);
                        }
                    }
                },
                hour,       // 初期値（時）と（分）は画面のものをセット
                minute,
                true
        );
        timePickerDialog.show();
    }

    // 選択された時刻を元に挨拶を表示する
    // 今回の要件では時刻だけを見れば良いので、分は使用しないということでも良いのだけど、
    // 分も考慮するよう、時間を分に変換して条件分枝する
    //  2:00 = (60 * 2) + 00 = 120
    // 10:00 = (60 * 10) + 00 = 600
    // 18:00 = (60 * 18) + 00 = 1080
    // 1080以上・・・「こんばんは」
    // else 600以上・・・「こんにちは」
    // else 120以上・・・「おはよう」
    // else ・・・「こんばんは」
    // 今回は、TextViewにTimePickerDialogからしかセットされないので、数値以外が入ることは考慮しない
    private void showAisatsu() {
        // 時間を分に変換する
        int hour = Integer.parseInt(mTextHour.getText().toString());
        int minute = Integer.parseInt(mTextMinute.getText().toString());
        int totalmin = (60 * hour) + minute;

        // 分の値を元に表示内容を判定する（判定基準は上記参照）
        if (totalmin >= 1080) {
            mTextAisatsu.setText("こんばんは");
        } else if (totalmin >= 600) {
            mTextAisatsu.setText("こんにちは");
        } else if (totalmin >= 120) {
            mTextAisatsu.setText("おはよう");
        } else {
            mTextAisatsu.setText("こんばんは");
        }
    }
}
