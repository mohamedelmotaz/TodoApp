package com.example.moataz.todoapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

Context context;
ArrayList<Item>arrayList;
Item item;

    public ItemAdapter(Context context, ArrayList<Item> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_row,viewGroup,false);
        MyViewHolder myViewHolder=new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

item=arrayList.get(i);
myViewHolder.note.setText(item.getNote());
myViewHolder.date1.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date1,note,ediet,delete,save,cancle;
        RelativeLayout rel;
        int id1;
        AlertDialog alertDialog;
        AlertDialog alertDialog1;

        EditText editText;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            date1=itemView.findViewById(R.id.date);
            note=itemView.findViewById(R.id.note);
            rel=itemView.findViewById(R.id.rel);
      rel.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              final AlertDialog.Builder builder=new AlertDialog.Builder(itemView.getContext());
              final View ve=LayoutInflater.from(itemView.getContext()).inflate(R.layout.edit_menu,null);
              ediet=ve.findViewById(R.id.ediet);
              delete=ve.findViewById(R.id.delete);
              builder.setView(ve);
              alertDialog=builder.create();
              alertDialog.show();
              delete.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      AlertDialog.Builder builder2=new AlertDialog.Builder(v.getContext(),R.style.AlertDialogTheme);
                      builder2.setTitle("Delete");
                      builder2.setMessage("Are you sure");

                      builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              Item item=arrayList.get(getAdapterPosition());
                              id1=item.getId();
                              MyHelper db=new MyHelper(ve.getContext());
                              db.delete(id1);
                              arrayList.remove(getAdapterPosition());
                              notifyItemRemoved(getAdapterPosition());
                              notifyItemRangeChanged(getAdapterPosition(),arrayList.size());
                              alertDialog.dismiss();
                              dialog.dismiss();
                          }
                      });
                      builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
                              alertDialog.dismiss();
                          }
                      });
                      builder2.create();
                      builder2.show();

                      }
              });
              ediet.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      AlertDialog.Builder builder1=new AlertDialog.Builder(v.getContext());
                      View view=LayoutInflater.from(v.getContext()).inflate(R.layout.update_data,null);
                      builder1.setView(view);
                      alertDialog1=builder1.create();
                      alertDialog1.show();
                       editText=view.findViewById(R.id.edit_note);
                      save=view.findViewById(R.id.save_edit_note);
                       cancle=view.findViewById(R.id.cancle_edit_note);
                      String new_note=editText.getText().toString();
                      Calendar calendar = Calendar.getInstance();
                      String new_date=  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String new_note=editText.getText().toString();
        Calendar calendar = Calendar.getInstance();
        String new_date=  DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Item item=arrayList.get(getAdapterPosition());
        int id=item.getId();
        Item newItem=new Item(new_note,new_date,id);
        MyHelper myHelper=new MyHelper(v.getContext());
        myHelper.update(newItem);
arrayList.set(getAdapterPosition(),newItem);
notifyItemChanged(getAdapterPosition());
        alertDialog1.dismiss();
        alertDialog.dismiss();
    }
});
cancle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alertDialog1.dismiss();
        alertDialog1.dismiss();
    }
});
} });
              return false;
          }
      });
      }}
}

