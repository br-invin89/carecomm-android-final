package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.MobileFragments.UpcomingEventFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Event.UpcomingEventResponseModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class UpcomingEventItemAdapter extends RecyclerView.Adapter<UpcomingEventItemAdapter.ItemView> {

    private List<UpcomingEventResponseModel.EventModel> dataSet;
    private Context mContext;
    private BaseFragmentActivity mainActivity;
    public OnItemClickLinter onItemClickLinter;

    public UpcomingEventItemAdapter(Context context, List<UpcomingEventResponseModel.EventModel> dataSet, BaseFragmentActivity mainActivity) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.mainActivity = mainActivity;
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_upcoming_event, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull UpcomingEventItemAdapter.ItemView holder, int position) {
        UpcomingEventResponseModel.EventModel event = dataSet.get(position);

        if (event.relativeRef.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(holder.ivPhoto);
        else
            Picasso.get().load(event.relativeRef.photo).into(holder.ivPhoto);

        holder.tvEventName.setText(event.name);
        holder.tvRelationship.setText(event.relationship);
        holder.tvCallTime.setText(event.startTime);
        /*
        if (menuItemModel.getEventDate().equalsIgnoreCase("")){
            holder.tvCallDate.setVisibility(View.GONE);
        }

         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = dateFormat.parse(event.startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM d yyyy");
        String date = dateFormat1.format(parse);

        holder.tvCallDate.setText(date);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivPhoto;
        TextView tvEventName, tvRelationship,tvCallTime,tvCallDate;

        private ItemView(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.profile_image);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            tvRelationship = itemView.findViewById(R.id.tv_relationship);
            tvCallTime = itemView.findViewById(R.id.tv_call_time);
            tvCallDate = itemView.findViewById(R.id.tv_call_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == itemView){
                Data.selectedUpcomingEvent = dataSet.get(getAdapterPosition());
                mainActivity.ReplaceFragmentWithBackstack(new UpcomingEventFragment(), false, true);
                /*
                UpcomingEnventModel upcomingEnventModel = dataSet.get(getAdapterPosition());
                if (upcomingEnventModel.isChatScreen()){
                    mainActivity.ReplaceFragmentWithBackstack(new ChatRoomMobileFragment(),false,true);

                }else {
                    mainActivity.ReplaceFragmentWithBackstack(new UpcomingEventFragment(), false, true);
                }

                 */
            }
        }
    }
}
