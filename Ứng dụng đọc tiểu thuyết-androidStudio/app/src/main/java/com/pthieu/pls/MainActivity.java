package com.pthieu.pls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<Model> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post");

        mRecyclerView =findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("Dataa");

        showData();


    }


    private void showDeleteDataDialog(final String currentTitle, final String currentImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xóa truyện");
        builder.setMessage("Bạn có chắc muốn xóa truyện?");

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query mQuery = mRef.orderByChild("title").equalTo(currentTitle);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                StorageReference mPictureRefe = FirebaseStorage.getInstance().getReferenceFromUrl(currentImage);
                mPictureRefe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Xoá thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showData(){

        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(mRef, Model.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

                ViewHolder viewHolder = new ViewHolder(itemView);

                viewHolder.setOnClickListener(new ViewHolder.clickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String mTitle = getItem(position).getTitle();
                        String mDesc = getItem(position).getDescription();
                        String mImage = getItem(position).getImage();

                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        intent.putExtra("title", mTitle);
                        intent.putExtra("description", mDesc);
                        intent.putExtra("image", mImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongclick(View view, int position) {
                        String currentTitle = getItem(position).getTitle();
                        String currentImage = getItem(position).getImage();
                        showDeleteDataDialog(currentTitle, currentImage);
                    }
                });

                return viewHolder;
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    private void firebaseSearch(String searchText){

        String query = searchText.toLowerCase();

        Query firebaseSearchQuery = mRef.orderByChild("search").startAt(query).endAt(query + "\uf8ff");


        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(firebaseSearchQuery, Model.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
                holder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

                ViewHolder viewHolder = new ViewHolder(itemView);

                viewHolder.setOnClickListener(new ViewHolder.clickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String mTitle = getItem(position).getTitle();
                        String mDesc = getItem(position).getDescription();
                        String mImage = getItem(position).getImage();

                        Intent intent = new Intent(view.getContext(), PostDetailActivity.class);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        intent.putExtra("title", mTitle);
                        intent.putExtra("description", mDesc);
                        intent.putExtra("image", mImage);
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongclick(View view, int position) {
                        String currentTitle = getItem(position).getTitle();
                        String currentImage = getItem(position).getImage();
                        showDeleteDataDialog(currentTitle, currentImage);
                    }
                });

                return viewHolder;
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort){
            return true;
        }
        if (id == R.id.action_add){
            startActivity(new Intent(MainActivity.this, AddMainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}