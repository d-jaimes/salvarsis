package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dave on 25/03/2017.
 */
public static class DateUtils
{	
	private static final String dateTimeStringFormat = "yyyy-MM-dd HH:mm";
	
	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat( dateTimeStringFormat );
	
	public static String timeToString( Date date )
	{
		return dateTimeFormat.format( date );
	}
}
