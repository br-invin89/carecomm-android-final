package com.merculia.carecomm.Adapters2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.merculia.carecomm.BaseActivity.BaseFragmentActivity;
import com.merculia.carecomm.Frgments.CalenderFrgment;
import com.merculia.carecomm.Frgments.CameraFrgment;
import com.merculia.carecomm.Frgments.CommFrgment;
import com.merculia.carecomm.MobileFragments.ConversationFragment;
import com.merculia.carecomm.MobileFragments.ContactMobileFragment;
import com.merculia.carecomm.MobileFragments.EventMobileFragment;
import com.merculia.carecomm.MobileFragments.SettingMobileFrgment;
import com.merculia.carecomm.Model.MenuItemModel;
import com.merculia.carecomm.R;

import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ItemView> {

    private List<MenuItemModel> dataSet;
    private Context mContext;
    private int seletedPostion;
    public OnItemClickLinter onItemClickLinter;
    private BaseFragmentActivity mainActivity;
    private RecyclerView.LayoutManager layoutManager;

    public MenuItemAdapter(BaseFragmentActivity mainActivity, Context context, List<MenuItemModel> dataSet, int seletedPostion) {
        this.dataSet = dataSet;
        this.mContext = context;
        this.seletedPostion = seletedPostion;
        this.mainActivity= mainActivity;
    }


    public interface OnItemClickLinter{
        public void onItemClick(int postion);
    }


    public void setSelectedPosition(int position){
        seletedPostion = position;
        notifyDataSetChanged();
    }
    public void setItemClickLinter(OnItemClickLinter onItemClickLinter){
        this.onItemClickLinter = onItemClickLinter;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_menu_item, viewGroup, false);

        return new ItemView(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MenuItemAdapter.ItemView holder, int position) {
        MenuItemModel menuItemModel = dataSet.get(position);
     if  (mainActivity.isTablet(mContext)){
            setTabletMenu(position,menuItemModel,holder);
        }else {
         setMobiletMenu(position,menuItemModel,holder);
     }
    }


    private void setTabletMenu(int position , MenuItemModel menuItemModel,MenuItemAdapter.ItemView holder){
        if (position == seletedPostion){

            Glide.with(mContext).load(menuItemModel.getImageSeleted()).into(holder.imageView);
            holder.tvMenuTitle.setText(menuItemModel.getTitle());
            holder.seperateView.setVisibility(View.GONE);
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = mContext.getResources().getFont(R.font.pt_sans_caption_bold);;
            }else {
                typeface = Typeface.createFromAsset(mContext.getAssets(), "pt_sans_caption_bold.ttf");
            }

            holder.tvMenuTitle.setTypeface(typeface);
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = mContext.getTheme();
            theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            @ColorInt int color = typedValue.data;
            holder.linearLayout.setBackgroundColor(color);
        }else {
            Glide.with(mContext).load(menuItemModel.getImage()).into(holder.imageView);
            holder.tvMenuTitle.setText(menuItemModel.getTitle());
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
            Typeface typeface = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                typeface = mContext.getResources().getFont(R.font.pt_sans_caption_regular);
            }else {
                typeface = Typeface.createFromAsset(mContext.getAssets(), "pt_sans_caption_regular.ttf");
            }
            holder.seperateView.setVisibility(View.VISIBLE);
            holder.tvMenuTitle.setTypeface(typeface);
        }

        if (position == dataSet.size()-1){
            holder.seperateView.setVisibility(View.GONE);
        }

    }
    private void setMobiletMenu(int position , MenuItemModel menuItemModel,MenuItemAdapter.ItemView holder){
        if (position == seletedPostion){
            Glide.with(mContext).load(menuItemModel.getImage()).into(holder.imageView);
        }else {
            Glide.with(mContext).load(menuItemModel.getImageSeleted()).into(holder.imageView);

        }
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ItemView  extends RecyclerView.ViewHolder {
        ImageView imageView;
        LinearLayout linearLayout;
        TextView tvMenuTitle;
        View seperateView;
        private ItemView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_menu_icon);
            tvMenuTitle = itemView.findViewById(R.id.tv_menu_title);
            linearLayout = itemView.findViewById(R.id.linear_menu);
            seperateView = itemView.findViewById(R.id.seperateView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickLinter != null){
                        int position = getAdapterPosition();
                        if (mainActivity.isTablet(mContext)){
                            openTabMenu(position);

                        }else {
                            openMobileMenu(position);
                        }
                    }
                }
            });
        }

        private void openTabMenu(int position){

            if (position != RecyclerView.NO_POSITION){
                if (position != seletedPostion){
                    seletedPostion = position;
                    if (position == 0){
                        mainActivity.ReplaceFragment(new CommFrgment());
                    }
                    if (position == 1){
                        mainActivity.ReplaceFragment(new CalenderFrgment());
                    }
                    if (position == 2){
                        mainActivity.ReplaceFragment(new CameraFrgment());
                    }
                    if (position == 3){
                        mainActivity.ReplaceFragment(mainActivity.settingFrgment);
                    }
                    notifyDataSetChanged();
                    // setAdapter(postion);
                }
                onItemClickLinter.onItemClick(position);
            }
        }

        private void openMobileMenu(int position){
            if (position != RecyclerView.NO_POSITION){
                if (position != seletedPostion){
                    seletedPostion = position;
                    if (position == 0){
                        mainActivity.ReplaceFragmentWithBackstack(new ConversationFragment());
                    }
                    if (position == 1){
                        mainActivity.ReplaceFragmentWithBackstack(new ContactMobileFragment());
                    }
                    if (position == 2){
                        mainActivity.ReplaceFragmentWithBackstack(new EventMobileFragment());
                    }
                    if (position == 3){
                        mainActivity.ReplaceFragmentWithBackstack(new CameraFrgment());
                    }
                    if (position == 4){
                        mainActivity.ReplaceFragmentWithBackstack(new SettingMobileFrgment());
                    }
                    notifyDataSetChanged();
                    // setAdapter(postion);
                }
                onItemClickLinter.onItemClick(position);
            }
        }
    }
}
