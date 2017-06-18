package us.fellowtraveller.presentation.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import us.fellowtraveller.R;
import us.fellowtraveller.presentation.utils.FieldUtils;
import us.fellowtraveller.presentation.utils.exceptions.BadFieldDataException;

/**
 * Created by arkadius on 6/18/17.
 */

public class CommentDialogFragment extends DialogFragment {

    public static final String TAG = "comment_dialog";
    private EditText etCommentText;
    private RadioGroup rgRecipientType;
    private RatingBar ratingBar;

    public static CommentDialogFragment newInstance() {

        Bundle args = new Bundle();

        CommentDialogFragment fragment = new CommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_comment, null);
        etCommentText = ((EditText) view.findViewById(R.id.et_comment_text));
        rgRecipientType = ((RadioGroup) view.findViewById(R.id.rg_recipient_type));
        ratingBar = ((RatingBar) view.findViewById(R.id.rating_bar));
        ratingBar.setRating(4);
        etCommentText.setMinLines(3);
        rgRecipientType.check(R.id.rb_driver);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(R.string.action_send, null)
                .setNegativeButton(R.string.action_cancel, null)
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                String text = FieldUtils.getNonEmptyText(etCommentText);
                FragmentActivity activity = getActivity();
                boolean isDriver = rgRecipientType.getCheckedRadioButtonId() == R.id.rb_driver;
                int rating = (int) ratingBar.getRating();

                if (activity instanceof CommentCreatedListener) {
                    ((CommentCreatedListener) activity).onCommentCreated(text, isDriver, rating);
                }
                dismiss();
            } catch (BadFieldDataException e) {
                e.editText.setError(getString(R.string.error_empty_field));
            }
        });
    }

    public interface CommentCreatedListener {
        void onCommentCreated(String text, boolean isDriver, int rating);
    }

}
