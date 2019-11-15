package com.merculia.carecomm.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.Model.CallModel;
import com.merculia.carecomm.Model.EventModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class EventItemAdapter extends RecyclerView.Adapter<EventItemAdapter.ItemView> {

    private List<EventModel> dataSet;
    private Context mContext;

    public OnItemClickLinter onItemClickLinter;

    public EventItemAdapter(Context context, List<EventModel> dataSet) {
        this.dataSet = dataSet;
        this.mContext = context;

    }

    public interface OnItemClickLinter{
        public void onItemClick(int postion);
    }


    public void setItemClickLinter(OnItemClickLinter onItemClickLinter){
        this.onItemClickLinter = onItemClickLinter;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_all_events, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventItemAdapter.ItemView holder, int position) {
        EventModel menuItemModel = dataSet.get(position);


        holder.tvEventTitle.setText(menuItemModel.getEventTitle());
        holder.tvEventStart.setText(menuItemModel.getEventStart());
        holder.tvEventEnd.setText(menuItemModel.getEventEnd());


    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {

        TextView tvEventTitle,tvEventStart,tvEventEnd;

        private ItemView(View itemView) {
            super(itemView);
            tvEventTitle = itemView.findViewById(R.id.txt_event_name);
            tvEventStart = itemView.findViewById(R.id.txt_event_start);
            tvEventEnd = itemView.findViewById(R.id.txt_event_end);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickLinter != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            onItemClickLinter.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
