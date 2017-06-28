package com.jc.flora.apps.app.grace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jc.flora.R;

/**
 * Created by Samurai on 2017/6/24.
 */
public class GraceActivity extends AppCompatActivity{

    private RadioGroup rg;
    private EditText edHeight;
    private EditText edBody;
    private Button btn;
    private int sex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("一表身材");
        setContentView(R.layout.activity_grace);
        findViews();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_girl){
                    sex = 1;
                }else{
                    sex = 0;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int height = Integer.parseInt(edHeight.getText().toString());
                    int weight=0,bust=0,waistline=0,hip=0;
                    switch (sex) {
                        case 0:
                            weight = (int) ((height-100)*0.9);
                            bust = (int) (height * 0.48);
                            waistline = (int) (height * 0.47);
                            hip = (int) (height * 0.51);
                            break;
                        case 1:
                            weight = (int) ((height-95)*0.75);
                            bust = (int) (height * 0.535);
                            waistline = (int) (height * 0.365);
                            hip = (int) (height * 0.565);
                            break;
                        default:
                            break;
                    }
                    edBody.setText(new StringBuffer("您的标准体重：").append(
                            weight - 1 + "").append("~").append(weight + 1 + "")
                            .append("公斤\n\n您的标准胸围：").append(bust - 3 + "").append(
                                    "~").append(bust + 3 + "").append(
                                    "厘米\n\n您的标准腰围：").append(waistline - 3 + "")
                            .append("~").append(waistline + 3 + "").append(
                                    "厘米\n\n您的标准臀围：").append(hip - 3 + "").append(
                                    "~").append(hip + 3 + "").append("厘米"));
                    edBody.setVisibility(View.VISIBLE);
                } catch(Exception e){
                    Toast.makeText(GraceActivity.this, "请输入正确的身高", 4000).show();
                }
            }});

    }

    private void findViews(){
        rg = (RadioGroup) findViewById(R.id.rg);
        edHeight = (EditText) findViewById(R.id.ed_height);
        edBody = (EditText) findViewById(R.id.ed_body);
        btn = (Button) findViewById(R.id.btn);
    }

}
