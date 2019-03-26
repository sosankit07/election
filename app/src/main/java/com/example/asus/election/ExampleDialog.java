package com.example.asus.election;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExampleDialog extends AppCompatDialogFragment {
    EditText agent;
    EditText edittime;
    Calendar calendar;
    SimpleDateFormat simpledateformat;
    String Date;
    ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.popup,null);
        calendar = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpledateformat.format(calendar.getTime());
        builder.setView(view)
                .setTitle("Confirmation")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.cancel();
                    }
                })
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String date = edittime.getText().toString();
                        String agents = agent.getText().toString();
                        listener.applyTexts(date,agents);
                    }
                });
        edittime = view.findViewById(R.id.edittime);
        agent =  view.findViewById(R.id.agents);
        edittime.setText(Date);
        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        }catch (Exception e){
            throw new ClassCastException(context.toString() + "must implement");
        }

    }

    public interface ExampleDialogListener{
        void applyTexts(String date,String agents);
        void cancel();
    }
}
