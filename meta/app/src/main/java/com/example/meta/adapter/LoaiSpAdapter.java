package com.example.meta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meta.R;
import com.example.meta.model.LoaiSp;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView textensp;
        ImageView imghinhanh;
        //public/images/logo/

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder = null;
       if (view == null){
           viewHolder = new ViewHolder();
           LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view = layoutInflater.inflate(R.layout.item_danhmuc,null);
           viewHolder.textensp = view.findViewById(R.id.itemdm_tensp);
           viewHolder.imghinhanh = view.findViewById(R.id.item_imglogo);
           view.setTag(viewHolder);

       }else{
           viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textensp.setText(array.get(i).getTensanpham());
        Glide.with(context).load("http://192.168.1.7/n8.com/public/images/logo/"+array.get(i).getAnhLogo()).into(viewHolder.imghinhanh);


        return view;
    }
}
