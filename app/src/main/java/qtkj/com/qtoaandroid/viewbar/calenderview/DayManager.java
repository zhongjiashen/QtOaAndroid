package qtkj.com.qtoaandroid.viewbar.calenderview;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 日期的管理类
 * Created by xiaozhu on 2016/8/7.
 */
public class DayManager {
    /**
     * 记录当前的时间
     */
    public static String currentTime;

    /**
     * 当前的日期
     */
    private static int current = -1;
    /**
     * 储存当前的日期
     */
    private static int tempcurrent=-1;
    /**
     *
     */
    static String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
    static String[] dayArray = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * 设置当前的时间
     * @param currentTime
     */
    public static void setCurrentTime(String currentTime) {
        DayManager.currentTime = currentTime;
    }

    /**
     * 获取当前的时间
     * @return
     */
    public static String getCurrentTime() {
        return currentTime;
    }


    /**
     * 储存正常天数
     */
    static Set<Integer> normalDays = new HashSet<>();

    public static void setNormalDays(Set<Integer> normalDays) {
        DayManager.normalDays = normalDays;
    }

    /**
     * 储存迟到/早退天数
     */
    static Set<Integer> latearrivalDays = new HashSet<>();
    public static void setLatearrivalDays(Set<Integer> latearrivalDays) {
        DayManager.latearrivalDays = latearrivalDays;
    }

    /**
     * 储存忘记打卡天数
     */
    static Set<Integer> forgetclockDays = new HashSet<>();

    public static void setForgetclockDays(Set<Integer> forgetclockDays) {
        DayManager.forgetclockDays = forgetclockDays;
    }
    /**
     * 储存缺勤天数
     */
    static Set<Integer> absenteeismDays = new HashSet<>();

    public static void setAbsenteeismDays(Set<Integer> absenteeismDays) {
        DayManager.absenteeismDays = absenteeismDays;
    }

    /**
     * 储存当前的日期
     */
    public static void setTempcurrent(int tempcurrent) {
        DayManager.tempcurrent = tempcurrent;
    }

    public static int getTempcurrent() {
        return tempcurrent;
    }

    /**
     * 设置当前的日期
     */
    public static void setCurrent(int current) {
        DayManager.current = current;

    }

    private static int select = -1;

    public static void setSelect(int select) {
        DayManager.select = select;
    }

    /**
     * 根据日历对象创建日期集合
     *
     * @param calendar 日历
     * @param width 控件的宽度
     * @param heigh 控件的高度
     * @return 返回的天数的集合
     */
    public static List<Day> createDayByCalendar(Calendar calendar, int width, int heigh) {

        //初始化休息的天数
        initRestDays(calendar);
        //模拟数据
        imitateData();

        List<Day> days = new ArrayList<>();


        Day day = null;


        int dayWidth = width / 7;
        int dayHeight = heigh / (calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1);
        //添加星期标识，
        for (int i = 0; i < 7; i++) {
            day = new Day(dayWidth, dayHeight);
            //为星期设置位置，为第0行，
            day.location_x = i;
            day.location_y = 0;
            day.text = weeks[i];
            days.add(day);
        }
        int count = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeekCount = calendar.getActualMaximum(Calendar.DAY_OF_WEEK);
        //生成每一天的对象，其中第i次创建的是第i+1天
        for (int i = 0; i < count; i++) {
            day = new Day(dayWidth, dayHeight);
            day.text = dayArray[i];

            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
            //设置每个天数的位置
            day.location_y = calendar.get(Calendar.WEEK_OF_MONTH);
            day.location_x = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            //设置日期选择状态
            if(i == current - 1&&i == select - 1) {//即是当前日期，也被选中
                day.backgroundStyle = 3;
            }else if (i == select - 1) {//选择日期
                day.backgroundStyle = 2;
            }else if (i == current - 1) {//当前日期
                day.backgroundStyle = 1;
            } else {//无状态
                day.backgroundStyle = 0;
            }
            //设置工作状态
            if (normalDays.contains(1 + i)) {//正常
                day.workState = 1;
                day.textClor=0xFFFFFFFF;
            } else if (latearrivalDays.contains(i + 1)) {//储存迟到/早退天数
                day.workState = 2;
                day.textClor=0xFFFFFFFF;
            } else if (forgetclockDays.contains(i + 1)) {//忘记打卡
                day.workState = 3;
                day.textClor=0xFFFFFFFF;
            }  else if (absenteeismDays.contains(i + 1)) {
                day.workState = 4;
                day.textClor=0xFFFFFFFF;
            }else {
                day.workState = 0;
            }
            days.add(day);
        }

        return days;
    }

    /**
     * 模拟数据
     */
    private static void imitateData() {


    }

    /**
     * 初始化休息的天数  计算休息的天数
     *
     * @param calendar
     */
    private static void initRestDays(Calendar calendar) {
//        int total = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        for (int i = 0; i < total; i++) {
//            calendar.set(Calendar.DAY_OF_MONTH, i + 1);
//            if (calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7) {
//                restDays.add(i + 1);
//            }
//        }
    }

}
