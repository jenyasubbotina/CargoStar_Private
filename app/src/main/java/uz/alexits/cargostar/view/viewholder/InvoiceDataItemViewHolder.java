package uz.alexits.cargostar.view.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uz.alexits.cargostar.R;

public class InvoiceDataItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemKeyTextView;
    public TextView itemValueTextView;

    public InvoiceDataItemViewHolder(@NonNull View itemView) {
        super(itemView);
        itemKeyTextView = itemView.findViewById(R.id.item_key_text_view);
        itemValueTextView = itemView.findViewById(R.id.item_value_text_view);
    }
}
