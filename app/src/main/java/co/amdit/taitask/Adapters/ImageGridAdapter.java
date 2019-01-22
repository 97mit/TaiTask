package co.amdit.taitask.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.amdit.taitask.Models.ImageListModel;
import co.amdit.taitask.R;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageGridViewHolder> {
    private List<ImageListModel> imageListModelList;
    private Context context;
    public interface ItemClickEvent{
        void callback(int position);
    }
    private ItemClickEvent itemClickEvent;
    public ImageGridAdapter(List<ImageListModel> imageListModelList, Context context,ItemClickEvent itemClickEvent) {
        this.imageListModelList = imageListModelList;
        this.context = context;
        this.itemClickEvent = itemClickEvent;
    }

    @NonNull
    @Override
    public ImageGridAdapter.ImageGridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_grid_item,viewGroup,false);

        return new ImageGridAdapter.ImageGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGridAdapter.ImageGridViewHolder imageGridViewHolder, final int i) {
        Glide.with(context).load(imageListModelList.get(i).getThumbnailUrl()).into(imageGridViewHolder.iv_gridImage);
        imageGridViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickEvent.callback(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageListModelList.size();
    }

    public class ImageGridViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_gridImage;
        public ImageGridViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_gridImage = itemView.findViewById(R.id.iv_gridItemImage);
        }
    }
}
