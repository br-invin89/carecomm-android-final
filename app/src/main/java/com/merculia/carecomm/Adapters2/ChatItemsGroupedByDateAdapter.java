package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.merculia.carecomm.Logics.Chat.ChatGroupedItemModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatItemsGroupedByDateAdapter extends RecyclerView.Adapter<ChatItemsGroupedByDateAdapter.ItemView> {

    private List<ChatGroupedItemModel> dataSet;
    private Context mContext;

    public OnItemClickLinter onItemClickLinter;

    public ChatItemsGroupedByDateAdapter(Context context, List<ChatGroupedItemModel> dataSet) {
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
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item_chat_date, viewGroup, false);
        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ChatItemsGroupedByDateAdapter.ItemView holder, int position) {
        ChatGroupedItemModel chatItem = dataSet.get(position);

        holder.tvDate.setText(chatItem.dateExpression);
        setChatAdapter(holder.rvChatType, chatItem.logs);
    }
    private void setChatAdapter(RecyclerView rvChat, List<ChatGroupedItemModel.ChatLogModel> logs){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL,false);
        rvChat.setLayoutManager(linearLayoutManager);
        ChatLogsAdapter chatLogsAdapter = new ChatLogsAdapter(logs, mContext);
        rvChat.setAdapter(chatLogsAdapter);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        TextView tvDate;
        RecyclerView rvChatType;

        private ItemView(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            rvChatType = itemView.findViewById(R.id.rv_chat_type);
        }
    }
}
