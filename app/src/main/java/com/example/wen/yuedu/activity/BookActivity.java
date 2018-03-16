package com.example.wen.yuedu.activity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import com.example.wen.yuedu.base.BaseActivity;
import com.example.wen.yuedu.R;
import com.example.wen.yuedu.model.Book;
import com.example.wen.yuedu.adapter.BookAdapter;
import com.example.wen.yuedu.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends BaseActivity {
    private List<Book> BookList = new ArrayList<>();
    MyDatabaseHelper dHelper;
    public BookActivity(){}
    public BookActivity(MyDatabaseHelper dbHelper){
        this.dHelper=dbHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        addToolBar();
        dHelper=new MyDatabaseHelper(this,"BookStore.db",null,1);

        initBooks();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BookAdapter adapter = new BookAdapter(BookList,BookActivity.this);
        recyclerView.setAdapter(adapter);

    }

    public void initBooks() {
        SQLiteDatabase db = dHelper.getWritableDatabase();
        String[] columns = {"bookName", "image", "bookNum"};
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("bookName"));
                int bookNum = cursor.getInt(cursor.getColumnIndex("bookNum"));
                int readNum = cursor.getInt(cursor.getColumnIndex("readNum"));
                byte[] blob = cursor.getBlob(cursor.getColumnIndex("image"));
                Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
                BitmapDrawable bd = new BitmapDrawable(bmp);
                Book book = new Book(name, -1, bookNum,readNum,bd);
                BookList.add(book);
            } while (cursor.moveToNext());
        }cursor.close();
    }
    public List<Book> getList(){
        return BookList;
    }

}