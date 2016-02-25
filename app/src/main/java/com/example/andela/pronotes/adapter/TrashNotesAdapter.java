package com.example.andela.pronotes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.andela.pronotes.R;
import com.example.andela.pronotes.activities.TrashListActiviy;
import com.example.andela.pronotes.model.NoteModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by andela on 2/9/16.
 */
/*public class TrashNotesAdapter extends CursorAdapter {
  public TrashNotesAdapter(Context context, Cursor cursor) {
    super(context, cursor, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.note_list, parent, false);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    TextView title = (TextView) view.findViewById(R.id.listTitle);
    TextView date = (TextView) view.findViewById(R.id.listDate);
    TextView brief = (TextView) view.findViewById(R.id.listSomeMessage);

    String bodyTitle = cursor.getString(cursor.getColumnIndexOrThrow("Note_book_titles")).trim();
    String bookDate = cursor.getString(cursor.getColumnIndexOrThrow("Modified_time")).trim();
    String bookBrief = cursor.getString(cursor.getColumnIndexOrThrow("Note_text")).trim();
    if(bookBrief.length() > 60) {
      bookBrief = bookBrief.substring(0, 60).concat(" ...");
    }

    title.setText(bodyTitle);
    date.setText(bookDate);
    brief.setText(bookBrief);
  }
}*/


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
import com.example.andela.pronotes.utils.ViewConstants;
import com.vstechlab.easyfonts.EasyFonts;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by Chidi on 2/22/16.
 */
public class TrashNotesAdapter extends RecyclerView.Adapter<TrashNotesAdapter.PlayViewHolder> {

  private static List<NoteModel> notes;
  private View viewing;

  public TrashNotesAdapter(List<NoteModel> noteModels) {
    this.notes = noteModels;
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
          notifyDataSetChanged();
          Snackbar.make(view,"Note Deleted", Snackbar.LENGTH_LONG).show();
        }
      });
      builder.create();
      builder.show();
    }
  }

}
