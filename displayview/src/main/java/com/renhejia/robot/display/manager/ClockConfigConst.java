/**
 * 
 */
package com.renhejia.robot.display.manager;

/**
 *
 * @author liujunbin
 */
public interface ClockConfigConst {
	/**
	 * 初始化状态判断
	 */
	public static final String IS_ROBOT_CONFIG_INIT = "is_watch_config_init";
	public static boolean IS_WATCH_CONFIG_INIT_DONE = true;
	public static boolean IS_WATCH_CONFIG_INIT_UNDONE = false;


	public static final String KEY_LTP_CLOCK_SKIN = "ltp_clock_skin";
	public static final String KEY_LTP_CLOCK_SKIN_PATH = "ltp_clock_skin_path";

	public static final String KEY_IS_LONG_PRESS_IN_EDIT = "is_long_press_in_edit";
	public static boolean VALUE_IS_LONG_PRESS_IN_EDIT_TRUE = true;
	public static boolean VALUE_IS_LONG_PRESS_IN_EDIT_FALSE = false;


	public static final String KEY_WATCH_HEIGHT = "watch_height";
	public static int VALUE_WATCH_HEIGHT_DEFAULT = 240;

	public static final String KEY_WATCH_WIDTH= "watch_width";
	public static int VALUE_WATCH_WIDTH_DEFAULT  = 240;


	public static final String KEY_CLOCK_HEIGHT = "clock_height";
	public static int VALUE_CLOCK_HEIGHT_DEFAULT = 0;

	public static final String KEY_CLOCK_WIDTH= "clock_width";
	public static int VALUE_CLOCK_WIDTH_DEFAULT  = 0;

	public static final String KEY_WAETHER_UPDATE_TIME = "weather_update_time";

	public static final String KEY_IS_SHOW_DATE= "is_show_date";
	public static final String KEY_IS_SHOW_WEATHER= "is_show_weather";
	public static final String KEY_IS_RANDOM_CHANGE= "is_random_change";
	public static final String KEY_IS_SHOW_CUSTOM= "is_show_custom";
	public static int VALUE_IS_SHOW_OPENED  = 1;
	public static int VALUE_IS_SHOW_CLOSED  = 0;
	public static final String KEY_CUSTOM_SKIN_URL= "custom_skin_url";
	public static final String KEY_CUSTOM_SKIN_NAME= "custom_skin_name";
	public static final String KEY_TIME_ZONE= "time_zone";
	public static final String KEY_TEMP_MODE= "time_mode";
	public static final String KEY_TEMP_MODE_CEL= "cel";

}


