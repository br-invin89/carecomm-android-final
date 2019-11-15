package com.merculia.carecomm.Adapters2;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.merculia.carecomm.Logics.CallData;
import com.merculia.carecomm.Logics.Chat.ChatGroupedItemModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatLogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private List<ChatGroupedItemModel.ChatLogModel> dataLogs;
    private Context mContext;
    public ChatLogsAdapter(List<ChatGroupedItemModel.ChatLogModel> dataLogs, Context context) {
        this.dataLogs = dataLogs;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_MESSAGE_SENT) {
            View view = layoutInflater.inflate(R.layout.row_iten_send, parent, false);
            return new SelfViewHolder(view);
        } else if (viewType == TYPE_MESSAGE_RECEIVED) {
            View view = layoutInflater.inflate(R.layout.row_item_recieved, parent, false);
            return new OtherViewHolder(view);
        }
        /*
        if (viewType == TYPE_PICTURE){
            View view = layoutInflater.inflate(R.layout.image_item, parent, false);
            return new ImageRecycleGridView(view);
        }

         */
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChatGroupedItemModel.ChatLogModel log =  dataLogs.get(i);
        if (log.type.contentEquals("message")) {
            if (log.senderRef.contentEquals(CallData.partnerData._id)) {
                ((OtherViewHolder)viewHolder).other2.setText(log.messageText);
                ((OtherViewHolder)viewHolder).time.setText(log.time);
            } else {
                ((SelfViewHolder)viewHolder).mine.setText(log.messageText);
                ((SelfViewHolder)viewHolder).time.setText(log.time);
            }
        } else if (log.type.contentEquals("shared image")) {
            /*
            ((ImageRecycleGridView)viewHolder).recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        ((ImageRecycleGridView)viewHolder).recyclerView.setVerticalScrollBarEnabled(false);

        ImageAdapter adapter = new ImageAdapter(dataLogs.get(i).getArrayListImages(),mContext);
        ((ImageRecycleGridView)viewHolder).recyclerView.setAdapter(adapter);
             */
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatGroupedItemModel.ChatLogModel log =  dataLogs.get(position);
        if (log.type.contentEquals("message")) {
            if (log.senderRef.contentEquals(CallData.partnerData._id)) {
                return TYPE_MESSAGE_RECEIVED;
            } else {
                return TYPE_MESSAGE_SENT;
            }
        }
        return TYPE_MESSAGE_SENT;
    }

    @Override
    public int getItemCount() {
        return dataLogs.size();
    }

    public class SelfViewHolder extends RecyclerView.ViewHolder {
        TextView mine;
        TextView time;
        public SelfViewHolder(@NonNull View itemView) {
            super(itemView);
            mine = itemView.findViewById(R.id.self_message);
            time = itemView.findViewById(R.id.tv_time);
        }
    }
    public class OtherViewHolder extends RecyclerView.ViewHolder {
        TextView other2;
        TextView time;
        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            other2 = itemView.findViewById(R.id.other_text);
            time = itemView.findViewById(R.id.tv_time);
        }
    }
    public class ImageRecycleGridView extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public ImageRecycleGridView(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.image_recycle_view);
        }
    }
}
