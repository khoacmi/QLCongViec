package com.bvcm.qlcongviec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
        TextView txtten;
        TextView txtTT;
        ImageView imageDelete,imageEdit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txtten =(TextView) convertView.findViewById(R.id.textviewTen);
            holder.txtTT  =(TextView) convertView.findViewById(R.id.textviewTT);
            holder.imageEdit=(ImageView) convertView.findViewById(R.id.imageviewEdit);
            holder.imageDelete=(ImageView) convertView.findViewById(R.id.imageviewDelete);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
        }
        CongViec congViec=congViecList.get(position);
        holder.txtten.setText(congViec.getTenCV());
        holder.txtTT.setText(congViec.getTTCV());
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCongviec(congViec.getTenCV(),congViec.getTTCV(),congViec.getIdCV());
            }
        });
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogXoaCongviec(congViec.getTenCV(),congViec.getIdCV());
            }
        });
        return convertView;
    }
}
