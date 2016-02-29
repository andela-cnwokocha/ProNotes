package com.example.andela.pronotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.model.NoteModel;


import java.util.List;

/**
 * Created by andela on 2/9/16.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.view.ActionMode;

import com.example.andela.pronotes.utils.FontManager;
import com.vstechlab.easyfonts.EasyFonts;

/**
 * Created by Chidi on 2/22/16.
 */
public class TrashNotesAdapter extends RecyclerView.Adapter<TrashNotesAdapter.PlayViewHolder> {
  private static List<NoteModel> notes;
  private View viewing;
  private LinearLayout layout;
  private GridLayoutManager layoutManager;

  public TrashNotesAdapter(List<NoteModel> noteModels, LinearLayout layout, GridLayoutManager layoutManager) {
    this.notes = noteModels;
    this.layout = layout;
    this.layoutManager = layoutManager;
  }

  @Override
  public PlayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playview, parent, false);
    viewing = view;
    return new PlayViewHolder(view.getContext(),view);
  }

  @Override
  public void onBindViewHolder(PlayViewHolder holder, int position) {
    holder.message.setText(notes.get(position).note_text);
    holder.title.setText(notes.get(position).note_title);
    holder.date.setText(notes.get(position).currentTime);
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  @Override
  public int getItemCount() {
    return notes.size();
  }

  public class PlayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, FontManager {
    private CardView cardView;
    private TextView title;
    private TextView message;
    private TextView date;
    private Context context;
    private ActionMode actionMode;
    private long itemId;
    private SharedPreferences preferences;

    PlayViewHolder(Context context,View itemView) {
      super(itemView);
      this.context = context;
      cardView = (CardView) itemView.findViewById(R.id.mycard);
      title = (TextView) itemView.findViewById(R.id.listTitle);
      message = (TextView) itemView.findViewById(R.id.listMessage);
      date = (TextView) itemView.findViewById(R.id.listDate);
      itemView.setOnClickListener(this);

      setFontType(title, "titleFontType");
      setFontType(message,"bodyFontType");
    }

    @Override
    public void setFontType(TextView view, String font) {
      preferences = PreferenceManager.getDefaultSharedPreferences(context);
      String fontType = preferences.getString(font, "no selection");
      switch (fontType) {
        case "RobotoMedium":
          view.setTypeface(EasyFonts.robotoMedium(context));
          break;
        case "Funraiser":
          view.setTypeface(EasyFonts.funRaiser(context));
          break;
        case "Ostrichbold":
          view.setTypeface(EasyFonts.ostrichBold(context));
          break;
        case "RobotoBlack":
          view.setTypeface(EasyFonts.robotoBlack(context));
          break;
        case "DroidSerifItalic":
          view.setTypeface(EasyFonts.droidSerifItalic(context));
          break;
        case "DroidSerifRegular":
          view.setTypeface(EasyFonts.droidSerifRegular(context));
          break;
        case "Tangerinebold":
          view.setTypeface(EasyFonts.tangerineBold(context));
          break;
        case "Windsong":
          view.setTypeface(EasyFonts.windSong(context));
          break;
        case "CavierDreams":
          view.setTypeface(EasyFonts.caviarDreams(context));
          break;
        case "CaptureIt":
          view.setTypeface(EasyFonts.captureIt(context));
          break;
        default:
          view.setTypeface(EasyFonts.ostrichBold(context));
      }
    }

    @Override
    public void onClick(final View view) {
      final int pos = getLayoutPosition();
      final NoteModel note = notes.get(pos);
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("What to do");
      builder.setPositiveButton("Restore", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          NoteModel noteModel = NoteModel.load(NoteModel.class, note.getId());
          noteModel.trashId = 0;
          noteModel.save();
          notes.remove(pos);
          notifyItemRemoved(pos);
          notifyDataSetChanged();
          Snackbar.make(view,"Successfully restored note", Snackbar.LENGTH_LONG).show();
        }
      });
      builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          NoteModel noteModel = NoteModel.load(NoteModel.class, note.getId());
          noteModel.delete();
          notes.remove(pos);
          notifyItemRemoved(pos);
          setView();
          layoutManager.requestLayout();
          notifyDataSetChanged();
          Snackbar.make(view,"Note Deleted", Snackbar.LENGTH_LONG).show();
        }
      });
      builder.create();
      builder.show();
    }

    private void setView() {
      if (notes.size() < 1) {
        layout.setVisibility(View.VISIBLE);
      }
    }
  }
}
