package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.MobileFragments.DailyEventFragment;
import com.merculia.carecomm.R;
import com.merculia.carecomm.Logics.Data;
import com.merculia.carecomm.Logics.Event.DailyEventResponseModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DailyEventItemAdapter extends RecyclerView.Adapter<DailyEventItemAdapter.ItemView> {

    private List<DailyEventResponseModel.EventModel> dataSet;
    private Context mContext;
    public OnItemClickListener onItemClickListener;
    private BaseFragmentActivity mainActivity;

    public DailyEventItemAdapter(Context context, List<DailyEventResponseModel.EventModel> dataSet, BaseFragmentActivity mainActivity) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.mainActivity= mainActivity;
    }

    public interface OnItemClickListener {
        void onItemClick(int postion);
    }

    public void setItemClickLinter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_events, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DailyEventItemAdapter.ItemView holder, int position) {
        DailyEventResponseModel.EventModel event = dataSet.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = null;
        try {
            parse = dateFormat.parse(event.endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(parse);
        int d = c.get(Calendar.DATE);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM");
        String m = dateFormat1.format(parse);

        holder.tvMonth.setText(m);
        holder.tvDate.setText(Integer.toString(d));
        holder.tvTitle.setText(event.name);
        holder.tvTime.setText(event.endTime);
        if (event.relativeRef.photo.contentEquals(""))
            Picasso.get().load("https://static.productionready.io/images/smiley-cyrus.jpg").into(holder.ivPhoto);
        else
            Picasso.get().load(event.relativeRef.photo).into(holder.ivPhoto);
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMonth,tvDate,tvTitle,tvTime;
        CircleImageView ivPhoto;

        private ItemView(View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tv_month);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v==itemView){
                Data.selectedDailyEvent = dataSet.get(this.getPosition());
                mainActivity.ReplaceFragmentWithBackstack(new DailyEventFragment(),false);
            }
        }
    }
}
