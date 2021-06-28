package com.example.loadmorelinechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.loadmorelinechart.bean.DevicePowerItem;
import com.example.loadmorelinechart.bean.MeasureStatusItem;
import com.example.loadmorelinechart.util.ChartFingerTouchListener;
import com.example.loadmorelinechart.util.CoupleChartGestureListener;
import com.example.loadmorelinechart.util.ListUtils;
import com.example.loadmorelinechart.util.MyMarkerView;
import com.example.loadmorelinechart.util.ReflectUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.BarLineChartTouchListener;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 设备历史数据-横屏
 */
public class LoadMoreDataActivity extends AppCompatActivity implements
        CoupleChartGestureListener.OnEdgeListener {

    private Context mContext;

    private LineChart chart;
    private ArrayList<MeasureStatusItem> mList;
    private List<Integer> colorList = new ArrayList<>();//曲线颜色
    /**
     * 滑动到边缘后加载更多
     */
    boolean _left = false;
    private long requestStartTime = 0;
    private long requestendTime = 0;
    private ArrayList<MeasureStatusItem> mListAdd;//后面插入数据


    private SimpleDateFormat ymd_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");

    //2021-06-22 00：00：00
    //2021-06-22 23：59：59
    private long startTime = 1624291200000l;
    private long endTime = 1624377599000l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_history);
        mContext = this;
        mList = new ArrayList<>();
        colorList.addAll(initCurveColor());//初始化10个颜色
        initView(savedInstanceState);
    }

    public  List<Integer> initCurveColor() {
        List<Integer> colorList = new ArrayList<>();//曲线颜色
        colorList.add(R.color.color_FF2661);
        colorList.add(R.color.color_3CC0FF);
        colorList.add(R.color.color_F8E700);
        colorList.add(R.color.color_00FFB0);
        colorList.add(R.color.color_FFA900);
        return colorList;
    }

    protected void initView(@Nullable Bundle savedInstanceState) {
        chart = findViewById(R.id.device_list_chart);
        initChart();
        requestData();
    }



    @Override
    public void edgeLoad(float x, boolean left) {
        int v = (int) x;
        _left = left;
        if (left) {
            //加载时间点后的
            if (startTime < 1624204800001l) {
                Toast.makeText(mContext, "已展示到最初一天数据曲线",Toast.LENGTH_LONG).show();
                return;
            }
            //加载时间点前的
            requestStartTime = startTime - (1000 * 60 * 60 * 24);
            requestendTime = startTime - 1;
            startTime = requestStartTime;

            loadMore();
        } else {
            //加载时间点后的
            if (endTime > (1624463940000l)) {
                Toast.makeText(mContext, "已展示到最新一天数据曲线",Toast.LENGTH_LONG).show();
                return;
            }
            //加载时间点后的
            requestStartTime = endTime + 1;
            requestendTime = endTime + (1000 * 60 * 60 * 24);
            endTime = requestendTime;
            loadMore();
        }

    }

    private void requestData() {
        showWaitLoadingDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                mList.addAll(getALL());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       setData(mList);
                    }
                });
            }
        }).start();
    }
    private void loadMore(){
        showWaitLoadingDialog("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                loadMoreALL();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addData(mListAdd);
                    }
                });
            }
        }).start();
    }

    //读取json数据
    public static String getStringFromAssert(Context context, String fileName) {
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            return new String(buffer, 0, buffer.length, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //初始化2021-06-22号数据，一天数据
    public  List<MeasureStatusItem>  getALL() {
       String  resultJson = getStringFromAssert(mContext, "device_power_data.json");
        List<MeasureStatusItem> allDataList = new ArrayList<>();
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject dataMap = new JSONObject(resultJson);
                if (dataMap == null) return allDataList;
                if (dataMap != null) {
                    Iterator<String> keys = dataMap.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String value = dataMap.optString(key);
                        List<DevicePowerItem> list = null;
                        if (!TextUtils.isEmpty(value) && !"[]".equals(value)) {
                            list = new Gson().fromJson(value, new TypeToken<List<DevicePowerItem>>() {
                            }.getType());
                        }else {
                            //防止其他日期有数据，切换日期之后不清空，导致还是展示前数据
                            list = new ArrayList<>();
                        }
                        MeasureStatusItem item = new MeasureStatusItem();
                        item.setFmeasureId(key);
                        item.setResColorId(colorList.get(0));
                        colorList.remove(0);
                        item.setDataList(list);
                        allDataList.add(item);
                    }
                }
            } catch (JSONException e) {
            }
        }
        dismissDialog();
        return allDataList;
    }


    //加载更多数据，前一天或者后一天
    public void loadMoreALL() {
        String resultJson ;
        if (_left){
            resultJson = getStringFromAssert(mContext, "device_power_data_21.json");
        }else {
            resultJson = getStringFromAssert(mContext, "device_power_data_23.json");
        }
        mListAdd = new ArrayList<>();
        if (!TextUtils.isEmpty(resultJson)) {
            try {
                JSONObject dataMap = new JSONObject(resultJson);
                if (dataMap == null) return;
                if (dataMap != null) {
                    Iterator<String> keys = dataMap.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        String value = dataMap.optString(key);
                        List<DevicePowerItem> list = null;
                        if (!TextUtils.isEmpty(value)) {
                            list = new Gson().fromJson(value, new TypeToken<List<DevicePowerItem>>() {
                            }.getType());
                        }
                        MeasureStatusItem item = new MeasureStatusItem();
                        item.setFmeasureId(key);
                        item.setDataList(list);

                        for (int i = 0; i < mList.size(); i++) {
                            if (key.equals(mList.get(i).getFmeasureId())) {
                                item.setFname(mList.get(i).getFname());
                                item.setfUnit(mList.get(i).getfUnit());
                                mListAdd.add(item);
                            }
                        }
                    }

                }
            } catch (JSONException e) {

                return;
            }
            dismissDialog();
            addData(mListAdd);
        }
    }

    //加载更多数据，插入形式
    private void addData(List<MeasureStatusItem> items) {
        if (ListUtils.isEmpty(items)) return;
        for (int i = 0; i < items.size(); i++) {
            MeasureStatusItem measureStatusItem = items.get(i);
            List<ILineDataSet> dataSets = chart.getLineData().getDataSets();
            List<Entry> values = new ArrayList<>();
            for (int j = 0; j < dataSets.size(); j++) {
                LineDataSet iLineDataSet = (LineDataSet) dataSets.get(j);
                if (measureStatusItem.getFmeasureId().equals(iLineDataSet.getLabel())) {
                    for (int k = 0; k < measureStatusItem.getDataList().size(); k++) {
                        DevicePowerItem devicePowerItem = measureStatusItem.getDataList().get(k);

                        float val = 0;
                       /* if (!StringUtils.isEmpty(devicePowerItem.getValue())) {
                            val = Float.parseFloat(devicePowerItem.getValue());
                        }*/
                        val = Float.parseFloat(!TextUtils.isEmpty(devicePowerItem.getValue()) ? devicePowerItem.getValue() : "0");
                        if (_left) {
                            devicePowerItem.setIndex(k);
                        } else {
                            int startIndex = iLineDataSet.getValues().size() + k;
                            devicePowerItem.setIndex(startIndex);
                        }
                        values.add(new Entry(Long.parseLong(devicePowerItem.getName()) - startTime, val, devicePowerItem));
                    }

                    for (int k = 0; k < iLineDataSet.getValues().size(); k++) {
                        Entry entry_o = iLineDataSet.getValues().get(k);
                        //重新把旧数据的x轴数据更新
                        iLineDataSet.getValues().get(k).setX(Long.parseLong(((DevicePowerItem) entry_o.getData()).getName()) - startTime);
                        if (_left) {
                            //重新把旧数据的entry的data对象里面的index字段赋值
                            ((DevicePowerItem) (iLineDataSet.getValues().get(k).getData())).setIndex(values.size() + k);
                        }
                    }
                    if (_left) {
                        iLineDataSet.getValues().addAll(0, values);
                    } else {
                        iLineDataSet.getValues().addAll(values);
                    }
                }
            }
        }

        //保存插入的数据list
        for (int i = 0; i < items.size(); i++) {
            MeasureStatusItem measureStatusItem = items.get(i);
            for (int j = 0; j < mList.size(); j++) {
                if (measureStatusItem.getFmeasureId().equals(mList.get(j).getFmeasureId())) {
                    if (_left) {
                        mList.get(j).getDataList().addAll(0, measureStatusItem.getDataList());
                    } else {
                        mList.get(j).getDataList().addAll(measureStatusItem.getDataList());
                    }
                }
            }
        }

        ((MyMarkerView) chart.getMarker()).setLineData(mList);
        ((MyMarkerView) chart.getMarker()).setStartTime(startTime);


        long interval = endTime - startTime;//x轴跨度
        chart.getXAxis().setAxisMaximum(interval);
        chart.getAxisLeft().resetAxisMaximum();
        chart.getAxisLeft().resetAxisMinimum();
        chart.getAxisLeft().setLabelCount(4, true);
        //插入数据后，需要刷新下数据
        chart.getData().notifyDataChanged();//刷新数据
        chart.notifyDataSetChanged();//刷新数据
        chart.setVisibleXRangeMaximum(3600000 * 8);

        //为了解决不能移动曲线moveViewToX指定位置，每次都是在开始位置，
        // （我也想不明白）statck overflow给出的解决方案
        BarLineChartTouchListener listener = (BarLineChartTouchListener) ReflectUtils.getField(chart, "mChartTouchListener");
        Method method = ReflectUtils.getMethod(listener.getClass(), "stopDeceleration", null);
        try {
            method.invoke(listener, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (_left) {
            chart.moveViewToX(1000 * 60 * 60 * 24);
        } else {
            chart.moveViewToX(interval - (1000 * 60 * 60 * 24) - (3600000 * 8));
        }
    }

    //初始化数据
    private void setData(List<MeasureStatusItem> items) {
        if (ListUtils.isEmpty(items)) return;
        ArrayList<ILineDataSet> dataSets = createLineDataSet(items);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view, items, startTime);
        mv.setChartView(chart);
        mv.setCOME_FROM_PAGE("DeviceHistoryDataLandActivity");
        chart.setMarker(mv);
        // create a data object with the data sets
        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }


    private void initChart() {
        {
            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setDrawGridBackground(false);
            // enable scaling and dragging
            chart.setTouchEnabled(true);
            chart.setDragEnabled(true);
            chart.setScaleEnabled(false);

            // force pinch zoom along both axis
            chart.setPinchZoom(false);
            //  chart.setDragDecelerationEnabled(false);//不允许甩动惯性滑动  和moveView方法有冲突 设置为false
            chart.setAutoScaleMinMaxEnabled(true);//自适应最大最小值
            chart.setOnChartGestureListener(new CoupleChartGestureListener(this, chart));//设置手势联动监听
            chart.setOnTouchListener(new ChartFingerTouchListener(chart, new ChartFingerTouchListener.HighlightListener() {
                @Override
                public void enableHighlight() {

                }

                @Override
                public void disableHighlight() {
                   /* if (ccGesture != null) {
                        ccGesture.setHighlight(true);
                    }*/
                }
            }));//手指长按滑动高亮
            // chart.setRenderer(new BaseLineChartRenderer( chart, chart.getAnimator(), chart.getViewPortHandler()));

        }

        XAxis xAxisPower;
        {
            xAxisPower = chart.getXAxis();
            xAxisPower.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxisPower.enableGridDashedLine(10f, 10f, 0f);//虚线
            chart.setExtraBottomOffset(2 * 8f);
            chart.setExtraRightOffset(20f);
            xAxisPower.setTextSize(10f);
            chart.setXAxisRenderer(
                    new CustomXAxisRenderer(
                            chart.getViewPortHandler(),
                            chart.getXAxis(),
                            chart.getTransformer(YAxis.AxisDependency.LEFT)));

            chart.setAutoScaleMinMaxEnabled(true);

            long interval = endTime - startTime;
            xAxisPower.setAxisMaximum(interval);
            xAxisPower.setAxisMinimum(0f);
            xAxisPower.setGranularity(1f);
            xAxisPower.setGranularityEnabled(true);
            int middleIndex = (int) (interval / 2);
         /*   index1 = 0;
            index2 = middleIndex;
            chart.setVisibleXRange(index1, index2);
            xAxisPower.setLabelCount(8, true);*/
            chart.setVisibleXRangeMaximum(3600000 * 8);
            xAxisPower.setValueFormatter(new IndexAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int index = Math.round(value);//取整
                    SimpleDateFormat ymd2 = new SimpleDateFormat("MM-dd");
                    long timeMills = startTime + index;
                    Log.e("timeMills=", timeMills + "");
                    String time = hms.format(timeMills);
                    String date = "";
                    StringBuilder sb = new StringBuilder();
                 /*   switch (timeInterval) {
                        case 2:
                        case 3:
                            break;
                        case 4:
                            date = ymd2.format(timeMills);
                            sb.append(date);
                            sb.append("\n");
                            break;
                    }*/
                    date = ymd2.format(timeMills);
                    sb.append(date);
                    sb.append("\n");
                    sb.append(time);
                    return sb.toString();
                }
            });
            xAxisPower.setTextColor(getResources().getColor(R.color.color_9BA1B5));

        }

        YAxis yAxisLeftPower;
        {
            yAxisLeftPower = chart.getAxisLeft();
            yAxisLeftPower.setLabelCount(4, true);
            yAxisLeftPower.setDrawGridLines(true);//隐藏横线
            yAxisLeftPower.enableGridDashedLine(10f, 10f, 0f);
            // yAxisLeftPower.setAxisMinimum(0);
            yAxisLeftPower.setTextColor(getResources().getColor(R.color.color_9BA1B5));
            yAxisLeftPower.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    DecimalFormat decimalFormat = new DecimalFormat("0.0");
                    return decimalFormat.format(value);
                }
            });
            YAxis yAxisRightPower = chart.getAxisRight();
            yAxisRightPower.setEnabled(false);//去掉右边Y轴显示
        }

        // draw points over time
        chart.animateX(100);
        chart.getLegend().setEnabled(false);
        chart.setNoDataText("无曲线数据");
    }

    public class CustomXAxisRenderer extends XAxisRenderer {

        public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
            super(viewPortHandler, xAxis, trans);
        }

        @Override
        protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
//        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees);//注释掉这个，否则坐标标签复写两次
            String[] lines = formattedLabel.split("\n");
            for (int i = 0; i < lines.length; i++) {
                float vOffset = i * mAxisLabelPaint.getTextSize();
                Utils.drawXAxisValue(c, lines[i], x, y + vOffset, mAxisLabelPaint, anchor, angleDegrees);
            }
        }
    }


    private ArrayList<ILineDataSet> createLineDataSet(List<MeasureStatusItem> list) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Entry> values = new ArrayList<>();
            MeasureStatusItem item = list.get(i);
            if (!ListUtils.isEmpty(item.getDataList())) {
                for (int j = 0; j < item.getDataList().size(); j++) {
                    DevicePowerItem devicePowerItem = item.getDataList().get(j);
                    devicePowerItem.setIndex(j);
                    float val = Float.parseFloat(!TextUtils.isEmpty(devicePowerItem.getValue()) ? devicePowerItem.getValue() : "0");
                    values.add(new Entry(Long.parseLong(devicePowerItem.getName()) - startTime, val, devicePowerItem));
                }
            }

            LineDataSet set1 = new LineDataSet(values, item.getFmeasureId());
            set1.setDrawValues(false);// 是否显示数值
            set1.setDrawIcons(false);
            set1.setMode(LineDataSet.Mode.LINEAR);//设置曲线模式
            // set1.setCubicIntensity(0.2f);//设置曲线的平滑度
            // draw dashed line
            //   set1.enableDashedLine(10f, 5f, 0f);

            int resColorId = item.getResColorId();
            if (resColorId <= 0) {
                resColorId = R.color.color_00FFB0;
            }
            // black lines and points
            set1.setColor(mContext.getResources().getColor(resColorId));
            set1.setCircleColor(mContext.getResources().getColor(resColorId));
            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);
            set1.setDrawCircles(false);
            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                int resId = item.getResColorId();
                switch (resId) {
                    case R.color.color_FF2661:
                        resColorId = R.drawable.devices_detail_history_curve_ff2661;
                        break;
                    case R.color.color_3CC0FF:
                        resColorId = R.drawable.devices_detail_history_curve_3cc0ff;
                        break;
                    case R.color.color_F8E700:
                        resColorId = R.drawable.devices_detail_history_curve_f8e700;
                        break;
                    case R.color.color_00FFB0:
                        resColorId = R.drawable.devices_detail_history_curve_00ffb0;
                        break;
                    case R.color.color_FFA900:
                        resColorId = R.drawable.devices_detail_history_curve_ffa900;
                        break;
                    default:
                        resColorId = R.drawable.devices_detail_history_curve_00ffb0;
                        break;
                }
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(mContext, resColorId);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(resColorId);
            }
            dataSets.add(set1); // add the data sets
        }
        return dataSets;

    }


    private LoadingDialogFragment mLoadingDialogFragment;

    protected void showWaitLoadingDialog(String loadingText) {
        if (mLoadingDialogFragment == null)
            mLoadingDialogFragment = LoadingDialogFragment.newInstance(loadingText);

        if (mLoadingDialogFragment.isAdded()) {
            // mLoadingDialogFragment.dismiss();
            mLoadingDialogFragment.dismissAllowingStateLoss();
        }

        //   mLoadingDialogFragment.show(getSupportFragmentManager(), "loading_dialog");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(mLoadingDialogFragment, "loading_dialog");
        ft.commitAllowingStateLoss();//注意这里使用commitAllowingStateLoss()
    }

    protected void dismissDialog() {
        if (mLoadingDialogFragment != null) {
            // mLoadingDialogFragment.dismiss();
            mLoadingDialogFragment.dismissAllowingStateLoss();
            mLoadingDialogFragment = null;
        }
    }
}
