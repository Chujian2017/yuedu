package com.example.wen.yuedu.adapter;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.wen.yuedu.activity.BookActivity;
import com.example.wen.yuedu.dialog.InputDialog;
import com.example.wen.yuedu.R;
import com.example.wen.yuedu.activity.ReadPDFActivity;
import com.example.wen.yuedu.model.Book;
import com.example.wen.yuedu.db.MyDatabaseHelper;


import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by wen on 2018/3/5.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> mBookList;
    private Context context;
    private MyDatabaseHelper dHelper;
    protected int progress;
    protected ContentValues ReadNumvalues;
    protected Book readNum;
    protected SQLiteDatabase readNumdb;


    static class ViewHolder extends RecyclerView.ViewHolder{
        View bookView;
        ImageView bookImage;
        TextView bookName;
        ProgressBar progressBar;
        public ViewHolder(View view){
            super(view);
            bookView=view;
            bookImage=(ImageView)view.findViewById(R.id.book_image);
            bookName=(TextView)view.findViewById(R.id.book_name);
            progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
        }
    }
    public BookAdapter(List<Book> bookList,Context context){
        mBookList=bookList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.bookView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dHelper=new MyDatabaseHelper(context,"BookStore.db",null,1);
                readNumdb=dHelper.getWritableDatabase();
                ReadNumvalues=new ContentValues();
                int  position=holder.getAdapterPosition();
                readNum=mBookList.get(position);
                InputDialog dialog = new InputDialog(context, new InputDialog.OnEditInputFinishedListener() {
                    @Override
                    public void editInputFinished(String password) {
                        progress = Integer.parseInt(password);
                        holder.progressBar.setProgress(progress);
                        ReadNumvalues.put("readNum",progress);
                        String[]Name={readNum.getName()};
                        readNumdb.update("Book",ReadNumvalues,"bookName=?",Name);
                    }
                });
                dialog.show();

            }
        });
        holder.bookImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReadPDFActivity.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mBookList.get(position);
        if (book.getImageId() != -1) {
            holder.bookImage.setImageResource(book.getImageId());
        } else {
            holder.bookImage.setImageBitmap(book.getBitmap().getBitmap());
        }
            holder.bookName.setText(book.getName());
        Log.d(TAG, book.getName());
            holder.progressBar.setMax(book.getNum());
            holder.progressBar.setProgress(book.getReadNum());
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }


}