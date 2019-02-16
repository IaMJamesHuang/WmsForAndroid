package com.kt.james.wmsforandroid.app.report;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.base.BaseFragment;
import com.kt.james.wmsforandroid.databinding.FragmentReportBinding;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends BaseFragment<ReportInfoViewModel, FragmentReportBinding> implements SeekBar.OnSeekBarChangeListener {

    private LineChart chart;
    private SeekBar seekBarX;
    private TextView tvX;
    private List<DaySaleItemBean> mData;

    private int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        initView();
        return view;
    }

    private void initView() {
        tvX = bindingView.tvXMax;
        seekBarX = bindingView.seekBar1;
        chart = bindingView.chart1;
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.rgb(104, 241, 175));
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        XAxis x = chart.getXAxis();
        x.setEnabled(false);

        YAxis y = chart.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);

        chart.getAxisRight().setEnabled(false);

        seekBarX.setOnSeekBarChangeListener(this);

        // lower max, as cubic runs significantly slower than linear
        seekBarX.setMax(7);

        seekBarX.setProgress(7);

        chart.getLegend().setEnabled(false);

        chart.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        chart.invalidate();
    }

    @Override
    public int setContent() {
        return R.layout.fragment_report;
    }

    public void loadData(int itemId) {
        currentItem = itemId;
        viewModel.getItemDaySale(itemId).observe(this, this::loadDataCallback);
    }

    public void reLoadData(int itemId) {
        showLoading();
        loadData(itemId);
    }

    public void loadDataCallback(List<DaySaleItemBean> data) {
        if (data != null) {
            //数据刷新
            mData = data;
            setData();
            showContentView();
        } else {
            showError();
        }
    }

    @Override
    protected void onRefresh() {
        showLoading();
        loadData(currentItem);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvX.setText(String.valueOf(seekBarX.getProgress()));
        setData();
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setData() {
        if (mData == null) {
            return;
        }
        //永远只有7个数据
        ArrayList<Entry> values = new ArrayList<>();
        int count = seekBarX.getProgress();
        if (count == 0) {
            count = 1;
        }

        for (int i = 0; i < count; i++) {
            float val = mData.get(i).getDaysale();
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }

}
