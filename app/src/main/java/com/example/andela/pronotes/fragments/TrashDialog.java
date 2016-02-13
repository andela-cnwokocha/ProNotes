package com.example.andela.pronotes.fragments;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import com.example.andela.pronotes.model.NoteModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrashDialog extends DialogFragment {

  public TrashDialog() {
  }

  public static TrashDialog newInstance(String title) {
    TrashDialog trash = new TrashDialog();
    Bundle args = new Bundle();
    args.putString("title", title);
    trash.setArguments(args);
    return trash;

  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    String title = getArguments().getString("title");
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setTitle(title);
    alertDialogBuilder.setMessage("Are you sure you want to empty trash?");

    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        NoteModel.deleteRecords(1);
        dialog.dismiss();
      }
    });
    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
      }
    });
    return alertDialogBuilder.create();
  }




}
