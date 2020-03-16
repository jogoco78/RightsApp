package cat.uab.idt.rightsapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cat.uab.idt.rightsapp.ParticlesActivity;
import cat.uab.idt.rightsapp.R;

public class MyRecyclerViewAdapterGroups extends RecyclerView.Adapter<MyRecyclerViewAdapterGroups.MyViewHolder> {

    private Context ctx;
    private ArrayList<String> mDataset;
    private RecyclerViewAdapter.ItemClickListener mClickListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapterGroups (Context ctx, ArrayList<String> myDataset) {
        this.mDataset = myDataset;
        this.ctx = ctx;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewAdapterGroups.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_group_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(tv);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset.get(position));
        holder.textView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   // Here You Do Your Click Magic
                                                   Intent intent=new Intent(ctx, ParticlesActivity.class);
                                                   intent.putExtra("position", position);
                                                   ctx.startActivity(intent);
                                               }
                                               });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
