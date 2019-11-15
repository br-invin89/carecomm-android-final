package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.merculia.carecomm.Model.EventCategoryModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class EventCategoryItemAdapter extends RecyclerView.Adapter<EventCategoryItemAdapter.ItemView> {

    private List<EventCategoryModel> dataSet;
    private Context mContext;
    private int selectedPosition;

    private ItemClickListener onItemClickListener;

    public EventCategoryItemAdapter(Context context, List<EventCategoryModel> dataSet,int selectedPosition) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.selectedPosition = selectedPosition;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(ItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_event_category, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventCategoryItemAdapter.ItemView holder, int position) {
        EventCategoryModel categoryModel = dataSet.get(position);

        int background = selectedPosition!=
                position?R.drawable.ractangle_edit_text_whitedull:
                R.drawable.ractangle_edit_text_whitedull_border;

        holder.frameLayout.setBackground(mContext.getDrawable(background));
        holder.tvEventTitle.setText(categoryModel.getEventCategory());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        TextView tvEventTitle;
        FrameLayout frameLayout;

        private ItemView(View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.framelayout);
            tvEventTitle = itemView.findViewById(R.id.tv_category_type);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedPosition = getAdapterPosition();
                    onItemClickListener.onItemClick(view, getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
