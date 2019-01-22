package co.amdit.taitask.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import co.amdit.taitask.Adapters.ImageGridAdapter;
import co.amdit.taitask.Models.ImageListModel;
import co.amdit.taitask.Network.APIClient;
import co.amdit.taitask.Network.ApiInterface;
import co.amdit.taitask.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<ImageListModel> imageListModelList;
    private RecyclerView rv_gridImages;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_gridImages = findViewById(R.id.rv_imageGrid);
        apiInterface = APIClient.getClient();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        rv_gridImages.setHasFixedSize(true);
        rv_gridImages.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        rv_gridImages.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_gridImages.setLayoutManager(gridLayoutManager);
        imageListModelList = new ArrayList<>(50);
        Call<List<ImageListModel>> call = apiInterface.photos();
        final ProgressDialog progressDialog  = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Fetching");
        progressDialog.show();
        call.enqueue(new Callback<List<ImageListModel>>() {
            @Override
            public void onResponse(Call<List<ImageListModel>> call, Response<List<ImageListModel>> response) {
                if(!response.isSuccessful()){
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();
                imageListModelList = response.body();

                ImageGridAdapter imageGridAdapter = new ImageGridAdapter(imageListModelList, getApplicationContext(),
                        new ImageGridAdapter.ItemClickEvent() {
                            @Override
                            public void callback(int position) {
                                showImage(imageListModelList.get(position).getUrl(),
                                        imageListModelList.get(position).getTitle());
                                /*Intent i = new Intent(getApplicationContext(),FullscreenActivity.class);
                                i.putExtra("url",imageListModelList.get(position).getUrl());
                                i.putExtra("title",imageListModelList.get(position).getTitle());
                                startActivity(i);*/
                            }
                        });
                rv_gridImages.setAdapter(imageGridAdapter);

            }

            @Override
            public void onFailure(Call<List<ImageListModel>> call, Throwable t) {
            t.printStackTrace();
            }
        });

    }

    public void showImage(String url,String title) {
        final Dialog showImageDialod = new Dialog(this,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        showImageDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        showImageDialod.setCancelable(false);
        showImageDialod.setContentView(R.layout.image_view);
        Button btnClose = showImageDialod.findViewById(R.id.btnIvClose);
        TextView tv_title = showImageDialod.findViewById(R.id.title);
        tv_title.setText(title);
        ImageView ivPreview = showImageDialod.findViewById(R.id.iv_preview_image);
        Glide.with(getApplicationContext()).load(url).into(ivPreview);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                showImageDialod.dismiss();
            }
        });
        showImageDialod.show();
    }
}
