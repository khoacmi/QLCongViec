package com.bvcm.qlcongviec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongviec;
    ArrayList<CongViec> arrayCongviec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvCongviec=(ListView) findViewById(R.id.listviewCongviec);
        arrayCongviec=new ArrayList<>();
        adapter=new CongViecAdapter(this,R.layout.dong_congviec,arrayCongviec);
        lvCongviec.setAdapter(adapter);

        database=new Database(this,"ghichu.sqlite",null,1);
        //tạo bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200), TT VARCHAR(1))");
        //Thêm dữ liệu
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Ôn thi Android','S')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Ôn thi AI','S')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'Học WEB','S')");

        //Select
        GetdataCongviec();
    }
    public  void DialogXoaCongviec(String tencv,final int id){
        AlertDialog.Builder dialogXoa=new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có chắc xóa "+tencv+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id='"+id+"'");
                Toast.makeText(MainActivity.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                GetdataCongviec();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }
    public  void DialogSuaCongviec(String ten, String TT, int id){
        Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua_congviec);
        EditText editTextTenCV =(EditText) dialog.findViewById(R.id.editTextTenCVsua);
        EditText editTextTTCV=(EditText) dialog.findViewById(R.id.editTextTTCVsua) ;
        Button buttonSua=(Button) dialog.findViewById(R.id.bntSua);
        Button buttonDong=(Button) dialog.findViewById(R.id.bntDong);
        editTextTenCV.setText(ten);
        editTextTTCV.setText(TT);

        //Gán lệnh
        buttonSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi=editTextTenCV.getText().toString().trim();
                String TTMoi=editTextTTCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV='"+tenMoi+"',TT='"+TTMoi+"' WHERE Id='"+id+"'");
                Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetdataCongviec();
            }
        });
        buttonDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void GetdataCongviec(){
        Cursor dataCongViec=database.Getdata("SELECT * FROM CongViec");
        arrayCongviec.clear();
        while (dataCongViec.moveToNext()){
            String Ten=dataCongViec.getString(1);
            String TT=dataCongViec.getString(2);
            int id=dataCongViec.getInt(0);
            arrayCongviec.add(new CongViec(id,Ten,TT));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThem(){
        Dialog dialog =new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_congviec);

        EditText edtTen =(EditText) dialog.findViewById(R.id.editTextTenCV);
        EditText edtTT = (EditText) dialog.findViewById(R.id.editTextTTCV);
        Button bntThem=(Button) dialog.findViewById(R.id.bntThem);
        Button bntHuy=(Button) dialog.findViewById(R.id.bntHuy);
        //Viết lệnh
        bntThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv = edtTen.getText().toString();
                String ttcv=edtTT.getText().toString();
                if (tencv.equals("")&&ttcv.equals("")){
                    Toast.makeText(MainActivity.this,"Vui lòng nhập công việc",Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+tencv+"','"+ttcv+"')");
                    Toast.makeText(MainActivity.this,"Thêm mới công việc thành công",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetdataCongviec();
                }
            }
        });

        bntHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}