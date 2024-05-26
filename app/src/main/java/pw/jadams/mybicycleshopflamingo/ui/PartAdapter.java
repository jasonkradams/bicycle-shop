package pw.jadams.mybicycleshopflamingo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pw.jadams.mybicycleshopflamingo.R;
import pw.jadams.mybicycleshopflamingo.entities.Part;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.PartViewHolder> {

    private List<Part> mParts;
    private final Context context;
    private final LayoutInflater mInflater;

    public PartAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class PartViewHolder extends RecyclerView.ViewHolder {

        private final TextView partItemView;
        private final TextView partItemView2;

        public PartViewHolder(@NonNull View itemView) {
            super(itemView);
            partItemView = itemView.findViewById(R.id.textView6);
            partItemView2 = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Part current = mParts.get(position);
                Intent intent = new Intent(context, PartDetails.class);
                intent.putExtra("id", current.getPartId());
                intent.putExtra("name", current.getPartName());
                intent.putExtra("price", current.getPrice());
                intent.putExtra("productId", current.getProductId());
                context.startActivity(intent);
            });
        }
    }

    @NonNull
    @Override
    public PartAdapter.PartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.part_list_item, parent, false);
        return new PartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PartAdapter.PartViewHolder holder, int position) {
        if (mParts != null) {
            Part current = mParts.get(position);
            String name = current.getPartName();
            int productId = current.getProductId();
            holder.partItemView.setText(name);
            holder.partItemView2.setText(Integer.toString(productId));
        } else {
            holder.partItemView.setText("No part name");
            holder.partItemView2.setText("No product id");
        }
    }

    public void setParts(List<Part> parts) {
        mParts = parts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mParts == null) {
            return 0;
        }
        return mParts.size();
    }
}
