package cat.uab.idt.rightsapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import cat.uab.idt.rightsapp.R;
import cat.uab.idt.rightsapp.models.EntityModel;

public class EntitiesViewAdapter extends RecyclerView.Adapter<EntitiesViewAdapter.ViewHolder> {
    private ArrayList<EntityModel> dataSet;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public EntitiesViewAdapter(Context context, ArrayList<EntityModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.dataSet = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_entity_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        EntityModel entity = dataSet.get(position);
        holder.tv_entity_name.setText(entity.getEntity_name());
        holder.tv_entity_description.setText(entity.getEntity_description());
        holder.tv_entity_address.setText(entity.getAddress());
        holder.tv_entity_phone.setText(entity.getPhone_number());
        holder.tv_entity_distance.setText(String.valueOf(entity.getDistance()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_entity_name;
        TextView tv_entity_description;
        TextView tv_entity_address;
        TextView tv_entity_phone;
        TextView tv_entity_distance;

        ViewHolder(View itemView) {
            super(itemView);
            tv_entity_name = itemView.findViewById(R.id.tv_rv_entity_name);
            tv_entity_description = itemView.findViewById(R.id.tv_rv_entity_description);
            tv_entity_address = itemView.findViewById(R.id.tv_rv_entity_address);
            tv_entity_phone = itemView.findViewById(R.id.tv_rv_entity_phone);
            tv_entity_distance = itemView.findViewById(R.id.tv_rv_entity_distance);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public EntityModel getItem(int pos) {
        return dataSet.get(pos);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
