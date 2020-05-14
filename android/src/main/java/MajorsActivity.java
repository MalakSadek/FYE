package com.example.baher.fyeapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MajorsActivity extends AppCompatActivity {

    String[] academics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_majors);

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter;
        academics = new String[5];

        ListView v = (ListView) findViewById(R.id.lv_guide);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list){ @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = super.getView(position, convertView, parent);
            TextView text = (TextView) view.findViewById(android.R.id.text1);

            text.setTextColor(0xFFFFFFFF);
            text.setTextSize(25);

            return view;
        }};
        v.setAdapter(adapter);

        list.add("Major");
        academics[0] = "Major";
        list.add("Minor");
        academics[1] = "Minor";
        list.add("Core");
        academics[2] = "Core";

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), SelectedAcademic.class);
                i.putExtra("Selected", academics[position]);
                if (academics[position].equals("Core")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://documents.aucegypt.edu/docs/academics_undergrad_ODUS/1742_NewCoreCurriculumBrochure_update_R7.pdf"));
                startActivity(browserIntent); }
                    else
                startActivity(i);

            }
        });

    }
}
