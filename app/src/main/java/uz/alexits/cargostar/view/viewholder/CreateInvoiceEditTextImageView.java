package uz.alexits.cargostar.view.viewholder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.security.auth.callback.Callback;

import uz.alexits.cargostar.R;
import uz.alexits.cargostar.view.callback.CreateInvoiceCallback;

public class CreateInvoiceEditTextImageView extends RecyclerView.ViewHolder {
    public TextView firstTextView;
    public TextView secondTextView;
    public RelativeLayout firstField;
    public RelativeLayout secondField;
    public EditText firstEditText;
    public EditText secondEditText;
    public ImageView secondImageView;
    public ImageView secondResultImageView;

    private TextWatcher firstTextWatcher;

    public CreateInvoiceEditTextImageView(@NonNull View itemView) {
        super(itemView);
        firstTextView = itemView.findViewById(R.id.first_text_view);
        firstEditText = itemView.findViewById(R.id.first_edit_text);
        secondTextView = itemView.findViewById(R.id.second_text_view);
        secondField = itemView.findViewById(R.id.second_field);
        secondEditText = itemView.findViewById(R.id.second_edit_text);
        secondImageView = itemView.findViewById(R.id.second_image_view);
        secondResultImageView = itemView.findViewById(R.id.second_result_image_view);
    }

    public void bindImageViews(final CreateInvoiceCallback callback) {
        secondImageView.setOnClickListener(v -> {
            callback.onSenderSignatureClicked();
        });
    }

    public void bindWatcherForFirstEditText(final int position, final CreateInvoiceCallback callback) {
        firstTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callback.afterFirstEditTextChanged(position, s);
            }
        };
        firstEditText.addTextChangedListener(firstTextWatcher);
    }

    public void bindEditTextImageView(final int position, final CreateInvoiceCallback callback) {
        callback.bindEditTextImageView(position, firstEditText, secondEditText);
    }

    public void unbindWatchers() {
        firstEditText.removeTextChangedListener(firstTextWatcher);
    }

    public void unbindImageViews() {
        secondImageView.setOnClickListener(null);
    }
}