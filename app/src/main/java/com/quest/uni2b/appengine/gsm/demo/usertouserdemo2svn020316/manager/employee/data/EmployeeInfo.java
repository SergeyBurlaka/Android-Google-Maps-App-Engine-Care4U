package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.employee.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Operator on 01.08.2016.
 */
public class EmployeeInfo {

        private static final String USER_PREFS_NAME = "user_cash";
        private static EmployeeInfo sInstance;
        private static SharedPreferences sSharedPreferences;
        private static HashMap <Params, Object> paramsValues;

        private EmployeeInfo(Context context) {
            sSharedPreferences = context.getSharedPreferences(USER_PREFS_NAME, 0);
        }


        public static EmployeeInfo getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new EmployeeInfo(context);
            }
            return sInstance;
        }

        public Object getParam(Params parameter) {
            Object value = null;
            if (paramsValues != null) {
                value = paramsValues.get(parameter);
            }
            if (value == null) {
                switch (parameter) {
                    case NAME:
                        value = sSharedPreferences.getString(parameter.name(), "");
                        break;
                    case SOME_BOOL:
                        value = sSharedPreferences.getBoolean(parameter.name(), false);
                        break;
                    case SOME_INT:
                        value = sSharedPreferences.getInt(parameter.name(), 0);
                        break;
                    default:
                        return null;
                }
            }
            return value;
        }

        @SuppressWarnings({
                "rawtypes", "unchecked"
        })
        public void setParam(Params parameter, Object value) {
            if (paramsValues == null) {
                paramsValues = new HashMap<>();
            }
            paramsValues.put(parameter, value);
        }

        public void apply() {
            if (paramsValues != null) {
                SharedPreferences.Editor editor = sSharedPreferences.edit();
                Set<Params> keys = paramsValues.keySet();
                for (Params key : keys) {
                    Object value = paramsValues.get(key);
                    if (value instanceof Boolean) {
                        editor.putBoolean(key.name(), (Boolean) value);
                    } else if (value instanceof String) {
                        editor.putString(key.name(), (String) value);
                    } else if (value instanceof Float) {
                        editor.putFloat(key.name(), (Float) value);
                    } else if (value instanceof Integer) {
                        editor.putInt(key.name(), (int) value);
                    } else if (value instanceof Long) {
                        editor.putLong(key.name(), (Long) value);
                    }
                }
                editor.apply();
            }
        }

        // Enum list of available parameters
        public enum Params {
            NAME, SOME_BOOL, SOME_INT
        }


        public void delete(Params params){
            sSharedPreferences.edit().remove(params.name()).apply();
        }

    }





