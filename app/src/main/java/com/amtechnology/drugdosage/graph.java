package com.amtechnology.drugdosage;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class graph extends Fragment {
    PieChart chart;


    public graph() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_graph, container, false);
        chart = v.findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);


        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        SharedPreferences sharedPreferences1 = v.getContext().getSharedPreferences("Counter", Context.MODE_PRIVATE);
        setData(sharedPreferences1.getInt("TakenCount",0),sharedPreferences1.getInt("NotTakenCount",0));
    return v;
    }
    private void setData(int taken, int nottaken) {
        ArrayList<PieEntry> entries = new ArrayList<>();



        entries.add(new PieEntry(taken,"Taken"));
        entries.add(new PieEntry(nottaken,"Not Taken"));

        PieDataSet dataSet = new PieDataSet(entries, "Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();

        /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
*/

        colors.add(ColorTemplate.getHoloBlue());
        colors.add(ColorTemplate.rgb("#67EFC8"));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

}
