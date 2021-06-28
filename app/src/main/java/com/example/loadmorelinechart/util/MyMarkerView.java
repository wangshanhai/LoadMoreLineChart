
package com.example.loadmorelinechart.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.loadmorelinechart.bean.DevicePowerItem;
import com.example.loadmorelinechart.bean.MeasureStatusItem;
import com.example.loadmorelinechart.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;


import java.util.List;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ViewConstructor")
public class MyMarkerView extends MarkerView {

    private final TextView tvContent;

    private List<MeasureStatusItem> lineData;

    private String COME_FROM_PAGE = "";

    private long startTime;//历史数据和横屏归一化显示

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setCOME_FROM_PAGE(String COME_FROM_PAGE) {
        this.COME_FROM_PAGE = COME_FROM_PAGE;
    }

    public void setLineData(List<MeasureStatusItem> lineData) {
        this.lineData = lineData;
    }

    public MyMarkerView(Context context, int layoutResource, List<MeasureStatusItem> lineData) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
        this.lineData = lineData;
    }

    public MyMarkerView(Context context, int layoutResource, List<MeasureStatusItem> lineData, long startTime) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
        this.lineData = lineData;
        this.startTime = startTime;
    }

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
      if ("DeviceHistoryDataLandActivity".equals(COME_FROM_PAGE)) {
          StringBuilder sb = new StringBuilder();
            DevicePowerItem devicePowerItem = (DevicePowerItem) (e.getData());
            long time = Long.parseLong(devicePowerItem.getName());
            sb.append(DateUtils.formatDateByYYMMDDHHmmss((time)));
            int index = devicePowerItem.getIndex();
            for (int i = 0; i < lineData.size(); i++) {
                MeasureStatusItem item = lineData.get(i);
                String unit = item.getfUnit();
                if (ListUtils.isEmpty(lineData.get(i).getDataList())) continue;
                sb.append(System.getProperty("line.separator"));
                if (index > 0 && index < item.getDataList().size()) {
                    String value = TextUtils.isEmpty(item.getDataList().get(index).getValue())?"--":item.getDataList().get(index).getValue();
                    sb.append(item.getFname() + ": "+value);
                }else {
                    sb.append(item.getFname() + ": --");
                }

                sb.append(TextUtils.isEmpty(unit) ? "" : " " + unit);
            }
            String str = sb.toString();
            tvContent.setText(str);

        } else {
            String str = e.getY() + "";
            tvContent.setText(str);
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
