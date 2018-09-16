package com.topcoder.vakidney.model;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Abinash Neupane on 2/7/2018.
 * This model class is used to store data for different meals created by the user.
 */

public class Meal extends SugarRecord<Meal> implements Serializable {

    public final static String MEAL_TYPE_BREAKFAST = "Breakfast";
    public final static String MEAL_TYPE_LUNCH = "Lunch";
    public final static String MEAL_TYPE_DINNER = "Dinner";
    public final static String MEAL_TYPE_SNACK = "Snack";
    public final static String MEAL_TYPE_CASUAL = "Casual";

    public final static int MEAL_BREAKFAST = 0;
    public final static int MEAL_LUNCH = 1;
    public final static int MEAL_DINNER = 2;
    public final static int MEAL_SNACK = 3;
    public final static int MEAL_CASUAL = 4;

    long mealId;
    String name;
    String desc;
    Date date;
    String type;
    boolean hasDrug;

    public long getMealId() {
        return mealId;
    }

    public void setMealId(long mealId) {
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<MealDrug> getMealDrugs() {
        return MealDrug.find(MealDrug.class, "meal_id = ?", String.valueOf(this.getMealId()));
    }

    public List<MealDrugImage> getMealDrugImages() {
        return getMealDrugImages(-1);
    }

    public List<MealDrugImage> getMealDrugImages(int limit) {
        Select<MealDrugImage> select = Select.from(MealDrugImage.class)
                .where(Condition.prop("meal_id").eq(getMealId()));
        if (limit > 0) {
            select.limit(String.valueOf(limit));
        }
        return select.list();
    }

    public static List<Meal> getMealUsingFilter(Date date, String type) {
        if (type.length() == 0) {
            return Meal.find(Meal.class, "date >= ?  and date <= ?", String.valueOf(getStartOfDay(date))
                    , String.valueOf(getEndOfDay(date)));
        }
        if (date == null) {
            return Meal.find(Meal.class, "type IN (" + type + ")");
        }
        return Meal.find(Meal.class, "date >= ?  and date <= ? and type IN (" + type + " )", String.valueOf(getStartOfDay(date))
                , String.valueOf(getEndOfDay(date)));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isHasDrug() {
        return hasDrug;
    }

    public void setHasDrug(boolean hasDrug) {
        this.hasDrug = hasDrug;
    }

    private static long getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    private static long getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        System.out.println(calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }
}
