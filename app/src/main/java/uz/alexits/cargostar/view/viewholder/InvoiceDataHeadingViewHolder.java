package uz.alexits.cargostar.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import uz.alexits.cargostar.R;
import uz.alexits.cargostar.view.callback.InvoiceDataCallback;

public class InvoiceDataHeadingViewHolder extends RecyclerView.ViewHolder {
    public TextView headingTextView;
    public ImageView arrowImageView;

    public InvoiceDataHeadingViewHolder(@NonNull View itemView) {
        super(itemView);
        headingTextView = itemView.findViewById(R.id.heading_text_view);
        arrowImageView = itemView.findViewById(R.id.arrow_image_view);
    }

    public void bindArrow(final InvoiceDataCallback callback, final int position) {
        arrowImageView.setOnClickListener(v -> {
            callback.onArrowTapped(position);
        });
    }
}
