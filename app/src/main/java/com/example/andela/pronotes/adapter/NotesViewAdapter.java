package com.example.andela.pronotes.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.activities.CreateNewNote;
import com.example.andela.pronotes.activities.ReadNoteActivity;
import com.example.andela.pronotes.model.NoteModel;
import com.example.andela.pronotes.utils.FontManager;
import com.example.andela.pronotes.utils.IntentRunner;
import com.example.andela.pronotes.utils.ViewConstants;
import com.vstechlab.easyfonts.EasyFonts;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by Chidi on 2/22/16.
 */
public class NotesViewAdapter extends RecyclerView.Adapter<NotesViewAdapter.PlayViewHolder> {
  private static List<NoteModel> notes;
  private View viewing;
  private LayoutInflater inflater;

  public NotesViewAdapter( List<NoteModel> noteModels) {
    notes = noteModels;

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

  public class PlayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
      View.OnLongClickListener, FontManager {
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
      itemView.setOnLongClickListener(this);

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
    public void onClick(View view) {
      int pos = getLayoutPosition();
      NoteModel note = notes.get(pos);
      Intent readNoteIntent = new Intent(context, ReadNoteActivity.class);
      readNoteIntent.putExtra("Note", Parcels.wrap(note));
      readNoteIntent.putExtra("ID", Parcels.wrap(note.getId()));
      context.startActivity(readNoteIntent);
    }

    @Override
    public boolean onLongClick(View v) {
      if (actionMode != null) {
        return true;
      }
      int pos = getLayoutPosition();
      NoteModel note = notes.get(pos);
      itemId = note.getId();
      ((AppCompatActivity) context).startSupportActionMode(modeCallBack);
      v.setSelected(true);
      return true;
    }

    private ActionMode.Callback modeCallBack = new ActionMode.Callback() {
      @Override
      public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle(ViewConstants.HOME_SCREEN.toString());
        mode.getMenuInflater().inflate(R.menu.listitem_trash_menu, menu);
        return true;
      }

      @Override
      public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
      }

      @Override
      public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
          case R.id.edit:
            IntentRunner.startIntentWithData(context, CreateNewNote.class, "NoteId", itemId);
            mode.finish();
            return true;
          case R.id.trash_note:
            NoteModel trash = NoteModel.load(NoteModel.class, itemId);
            trash.trashId = 1;
            trash.save();
            notes.remove(getLayoutPosition());
            notifyItemRemoved(getLayoutPosition());
            notifyDataSetChanged();
            mode.finish();
          default:
            return false;
        }
      }
      @Override
      public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
      }
    };
  }
}
