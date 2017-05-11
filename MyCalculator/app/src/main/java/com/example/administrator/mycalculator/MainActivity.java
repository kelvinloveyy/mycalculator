package com.example.administrator.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText show;  //显示在EditText
    private static String Number_st = "0";// 第一次输入的值
    private static String Number_nd = "0";// 第二次输入的值
    private static String num = "0";// 显示的结果
    private static int flg = 0;// 结果累加一次
    public boolean drg_flag = true;
    public Arithmetic take = null;

    private int[] button_symbol = { R.id.button_divide, R.id.button_multiply, R.id.button_cut,
            R.id.button_add ,R.id.button_sq,R.id.button_rt}; //加减乘除等两个数的算术

    private Button[] btsymbol = new Button[button_symbol.length];

    private int[] button_Num = { R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button0,
            R.id.button_point}; //数字及小数点
    private Button[] buttons = new Button[button_Num.length];

    private int[] btsymbol_2 = { R.id.button_cos, R.id.button_tan, R.id.button_sin,
            R.id.button_lg,R.id.button_pr,R.id.button_fac,R.id.button_ln};
    private Button[] btsymbol2 = new Button[btsymbol_2.length];//cos 阶乘等一个数的算术

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (EditText) findViewById(R.id.show_bt);
        show.setText("欢迎使用计算器");
        show.setEnabled(false);
        GetNumber get = new GetNumber();  //获取数字
        //事件监听
        for (int i = 0; i < button_Num.length; i++) {
            buttons[i] = (Button) findViewById(button_Num[i]);
            buttons[i].setOnClickListener(get);
        }
        Compute cm = new Compute();
        for (int i = 0; i < button_symbol.length; i++) {
            btsymbol[i] = (Button) findViewById(button_symbol[i]);
            btsymbol[i].setOnClickListener(cm);
        }
        for (int i = 0; i < btsymbol_2.length; i++) {
            btsymbol2[i] = (Button) findViewById(btsymbol_2[i]);
            btsymbol2[i].setOnClickListener(new OnTake());
        }

        Button eq = (Button) findViewById(R.id.button_equal);

        eq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flg == 0) {
                    Number_nd = show.getText().toString();
                    if (take == Arithmetic.DIVIDE && Number_nd.equals("0")) {
                        show.setText("0不能为被除数");
                    } else {
                        num = take.Values(Number_st, Number_nd);
                        Number_st = num;
                        Number_nd = "0";
                        show.setText(num);
                        flg = 1;
                    }
                }
            }
        });
        Button bt_delete = (Button) findViewById(R.id.button_delete);
        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (num.length() > 1) {
                    num = num.substring(0, num.length() - 1);
                } else {
                    num = "0";
                }
                show.setText(num);
            }
        });
        Button btt_delete = (Button) findViewById(R.id.button_delete);
        btt_delete.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                num = "0";
                Number_st = Number_nd = num;
                show.setText(num);
                flg = 0;
                return false;
            }
        });
        Button bt_drg = (Button) findViewById(R.id.button_drg);
        bt_drg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drg_flag == true) {
                    drg_flag = false;
                    Button btn = (Button)v;
                    btn.setText("RAD");
                    show.setText("RAD模式，既输入120为120");
                } else {
                    drg_flag = true;
                    Button btn = (Button)v;
                    btn.setText("DEG");
                    show.setText("DEG模式，既输入120为120度");
                }

            }
        });

    }


    // 给 EditText赋值
    class GetNumber implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (flg == 1)
                num = "0";
            if (num.equals("0")) {
                show.setText("0");
                num = v.getId() == R.id.button_point ? "0" : "";
            }
            String txt = ((Button) v).getText().toString();//View对象转换成Button对象.在转化为String
            boolean s = Pattern.matches("-*(\\d+).?(\\d)*", num + txt);//
            num = s ? (num + txt) : num;
            show.setText(num);
        }

    }

    // 根据条件计算，实现加减乘除开跟次幂的算术
    class Compute implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {

            Number_st = show.getText().toString();
            // TODO Auto-generated method stub
            switch (arg0.getId()) {
                case R.id.button_add:
                    take = Arithmetic.ADD;
                    break;
                case R.id.button_cut:
                    take = Arithmetic.MINUS;
                    break;
                case R.id.button_multiply:
                    take = Arithmetic.MULTIPLY;
                    break;
                case R.id.button_divide:
                    take = Arithmetic.DIVIDE;
                    break;
                case R.id.button_sq:
                    take=Arithmetic.SQUARE;
                case R.id.button_rt:
                    take =Arithmetic.ROOT;
            }
            num = "0";
            flg = 0;

        }


    }
    //实现cos，sin，tan，阶乘，%，以及π
    class OnTake implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Double num1;
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.button_cos:
                    if(drg_flag==true){
                    num1 =Math.cos((new BigDecimal(num).doubleValue()/180)*Math.PI); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                    num=num1.toString();
                    num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();}
                    else {
                        num1 =Math.cos(new BigDecimal(num).doubleValue()); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                        num=num1.toString();
                        num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();

                    }
                    //移除所有尾部零,并以String形式输出
                    break;
                case R.id.button_tan:
                    if(drg_flag==true){
                        num1 =Math.tan((new BigDecimal(num).doubleValue()/180)*Math.PI); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                        num=num1.toString();
                        num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();}
                    else {
                        num1 =Math.tan(new BigDecimal(num).doubleValue()); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                        num=num1.toString();
                        num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();

                    }
                    break;
                case R.id.button_sin:
                    if(drg_flag==true){
                        num1 =Math.sin((new BigDecimal(num).doubleValue()/180)*Math.PI); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                        num=num1.toString();
                        num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();}
                    else {
                        num1 =Math.sin(new BigDecimal(num).doubleValue()); //因为cos只可以算double类型，所以转化为BiDecimal在转化为double
                        num=num1.toString();
                        num=new BigDecimal(num).setScale(8,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();

                    }
                    break;
                case R.id.button_pr:
                    num = new BigDecimal(num).divide(BigDecimal.valueOf(100),8,BigDecimal.ROUND_UP).stripTrailingZeros()
                            .toPlainString();
                    break;
                case R.id.button_fac:
                    BigDecimal n= new BigDecimal(num);
                    BigDecimal bd1= new BigDecimal(1);
                    BigDecimal result=bd1;
                    BigDecimal bd2 = new BigDecimal(2);
                    while(n.compareTo(bd1) > 0){//参数大于1，进入循环
                        result =(result).multiply(n.multiply(n.subtract(bd1)));//实现result*（n*（n-1））
                        n = n.subtract(bd2);//n-2后继续
                    }
                    num = result.toPlainString();
                    break;
                case R.id.button_ln:
                    num1 =Math.log(new BigDecimal(num).doubleValue());
                    num=num1.toString();
                    num=new BigDecimal(num).stripTrailingZeros().toPlainString();
                    break;
                case R.id.button_lg:
                    num1 =Math.log10(new BigDecimal(num).doubleValue());
                    num=num1.toString();
                    num=new BigDecimal(num).stripTrailingZeros().toPlainString();
                    break;
            }
            show.setText(num);
            flg=0;
          num = "0";

        }

    }

}