package com.opensymphony.webwork.portlet.util;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.ServletActionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Encoder;

import javax.mail.internet.MailDateFormat;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.*;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeneralUtil {
    private static final Log log;

    private static String DEFAULT_FORMATTING_PROPERTIES_FILE_NAME = "default-formatting.properties";

    private static DecimalFormat defaultDecimalNumberFormatter;

    private static DecimalFormat defaultLongNumberFormatter;

    private static SimpleDateFormat defaultDateFormatter;

    private static SimpleDateFormat defaultDateTimeFormatter;

    private static SimpleDateFormat defaultTimeFormatter;

    private static Properties formattingProperties = new Properties();

    private static final String EMAIL_PATTERN_STRING = "([\\w-%\\+\\.]+@[\\w-%\\.]+\\.[\\p{Alpha}]+)";

    private static final Pattern EMAIL_PATTERN = Pattern.compile("([\\w-%\\+\\.]+@[\\w-%\\.]+\\.[\\p{Alpha}]+)");

    static {
        log = LogFactory.getLog(com.opensymphony.webwork.portlet.util.GeneralUtil.class);
        loadDefaultProperties();
        try {
            saveDefaultFormattingPropertiesFile();
        } catch (IOException e) {
            log.error("Error while trying to store the default formatting properties!", e);
        }
    }

    public GeneralUtil() {
    }

    public static void loadDefaultProperties() {
        try {

            InputStream inputStream = ClassLoaderUtils.getResourceAsStream(DEFAULT_FORMATTING_PROPERTIES_FILE_NAME,
                    com.opensymphony.webwork.portlet.util.GeneralUtil.class);

            formattingProperties.load(inputStream);
            setDefaultDecimalNumberFormatterPattern(getDefaultDecimalNumberFormatterPattern());
            setDefaultLongNumberFormatterPattern(getDefaultLongNumberFormatterPattern());
            setDefaultDateFormatterPattern(getDefaultDateFormatterPattern());
            setDefaultDateTimeFormatterPattern(getDefaultDateTimeFormatterPattern());
            setDefaultTimeFormatterPattern(getDefaultTimeFormatterPattern());
        } catch (Exception e) {
            log.error("Error while trying to load the object formatting properties!", e);
        }
    }

    public static Date convertToDateWithEnglishLocale(String buildDateString) {
        DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        formatter.setLenient(false);
        Date date = null;
        try {
            date = formatter.parse(buildDateString.toString());
        } catch (ParseException e) {
            log.info("Could not parse : " + buildDateString + " : " + e, e);
        }
        return date;
    }

    public static void saveDefaultFormattingPropertiesFile() throws IOException {
        File defaultFormattingPropertiesFile = new File(DEFAULT_FORMATTING_PROPERTIES_FILE_NAME);
        FileOutputStream out = new FileOutputStream(defaultFormattingPropertiesFile);
        formattingProperties.store(out, null);
    }

    public static String getDefaultTimeFormatterPattern() {
        return formattingProperties.getProperty("time.format", "HH:mm:ss");
    }

    public static void setDefaultTimeFormatterPattern(String defaultTimeFormatterPattern) {
        formattingProperties.setProperty("time.format", defaultTimeFormatterPattern);
        defaultTimeFormatter = (SimpleDateFormat) createDateFormatter(defaultTimeFormatterPattern);
    }

    public static String getDefaultDateTimeFormatterPattern() {
        return formattingProperties.getProperty("datetime.format", "MMM dd, yyyy HH:mm");
    }

    public static void setDefaultDateTimeFormatterPattern(String defaultDateTimeFormatterPattern) {
        formattingProperties.setProperty("datetime.format", defaultDateTimeFormatterPattern);
        defaultDateTimeFormatter = (SimpleDateFormat) createDateFormatter(defaultDateTimeFormatterPattern);
    }

    public static String getDefaultDateFormatterPattern() {
        return formattingProperties.getProperty("date.format", "MMM dd, yyyy");
    }

    public static void setDefaultDateFormatterPattern(String defaultDateFormatterPattern) {
        formattingProperties.setProperty("date.format", defaultDateFormatterPattern);
        defaultDateFormatter = (SimpleDateFormat) createDateFormatter(defaultDateFormatterPattern);
    }

    public static String getDefaultLongNumberFormatterPattern() {
        return formattingProperties.getProperty("long.number.format", "###############");
    }

    public static void setDefaultLongNumberFormatterPattern(String defaultLongNumberFormatterPattern) {
        formattingProperties.setProperty("long.number.format", defaultLongNumberFormatterPattern);
        defaultLongNumberFormatter = new DecimalFormat(defaultLongNumberFormatterPattern);
    }

    public static String getDefaultDecimalNumberFormatterPattern() {
        return formattingProperties.getProperty("decimal.number.format", "###############.##########");
    }

    public static void setDefaultDecimalNumberFormatterPattern(String defaultDecimalNumberFormatterPattern) {
        formattingProperties.getProperty("decimal.number.format", defaultDecimalNumberFormatterPattern);
        defaultDecimalNumberFormatter = new DecimalFormat(defaultDecimalNumberFormatterPattern);
    }

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return "";
        } else {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            return sw.toString();
        }
    }

    public static String format(Number num) {
        try {
            if ((num instanceof Double) || (num instanceof BigDecimal) || (num instanceof Float))
                return defaultDecimalNumberFormatter.format(num);
            else
                return defaultLongNumberFormatter.format(num);
        } catch (Exception e) {
            return null;
        }
    }

    public static String format(Date date) {
        try {
            return defaultDateFormatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String format(String str) {
        return str == null ? "" : str;
    }

    public static String format(Object obj) {
        try {
            if (obj instanceof Number)
                return format((Number) obj);
            if (obj instanceof Date)
                return format((Date) obj);
            if (obj instanceof String)
                return format((String) obj);
            return obj.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatDateTime(Date date) {
        try {
            return defaultDateTimeFormatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatTime(Date date) {
        try {
            return defaultTimeFormatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date convertMailFormatDate(String date) throws ParseException {
        return (new MailDateFormat()).parse(date);
    }

    public static Date convertToDate(Object obj) {
        if (obj instanceof Date)
            return (Date) obj;
        Date date = null;
        if (date == null)
            try {
                date = defaultDateFormatter.parse(obj.toString());
            } catch (ParseException e) {
                log.info("Could not parse : " + obj + " : " + e, e);
            }
        return date;
    }

    public static Long convertToLong(Object obj) {
        try {
            if (obj instanceof Long)
                return (Long) obj;
            return new Long(defaultLongNumberFormatter.parse(obj.toString()).longValue());
        } catch (Exception e) {

            return null;
        }
    }

    public static Character convertToCharacter(Object obj) {
        try {
            if (obj instanceof Character)
                return (Character) obj;
            return new Character(obj.toString().charAt(0));
        } catch (Exception e) {
            return null;
        }
    }

    public static BigDecimal convertToBigDecimal(Object obj) {
        try {
            if (obj instanceof BigDecimal)
                return (BigDecimal) obj;
            return new BigDecimal(defaultDecimalNumberFormatter.parse(obj.toString()).doubleValue());
        } catch (Exception e) {
            return null;
        }
    }

    public static Double convertToDouble(Object obj) {
        try {
            if (obj instanceof Double)
                return (Double) obj;
            return new Double(defaultDecimalNumberFormatter.parse(obj.toString()).doubleValue());
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer convertToInteger(Object obj) {
        try {
            if (obj instanceof Integer)
                return (Integer) obj;
            return new Integer(defaultLongNumberFormatter.parse(obj.toString()).intValue());
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean convertToBoolean(Object obj) {
        try {
            if (obj instanceof Boolean)
                return (Boolean) obj;
            return new Boolean(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToString(Object obj) {
        try {
            String result = obj.toString();
            if (result.equals(""))
                result = null;
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    private static DateFormat createDateFormatter(String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        formatter.setLenient(false);
        return formatter;
    }

    public static String urlEncode(String url) {
        try {
            if (url == null)
                return null;
            return URLEncoder.encode(url, getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            log.error("Error while trying to encode the URL!", e);
            return url;
        }
    }

    public static String urlDecode(String url) {
        try {
            if (url == null)
                return null;
            return URLDecoder.decode(url, getCharacterEncoding());
        } catch (Exception e) {
            log.error("Error while trying to decode url" + url, e);
            return url;
        }
    }

    private static boolean hasFormattingCharacters(String text) {
        if (!TextUtils.stringSet(text))
            return false;
        String illegalChars[] = {"+", "-"};
        for (int i = 0; i < illegalChars.length; i++) {
            String illegalChar = illegalChars[i];
            if (text.indexOf(illegalChar) != -1)
                return true;
        }

        return false;
    }

    public static String appendAmpsandOrQuestionMark(String str) {
        if (!TextUtils.stringSet(str))
            return str;
        if (str.indexOf("?") != -1)
            return str + "&";
        else
            return str + "?";
    }

    public static String summarise(String content) {
        if (!TextUtils.stringSet(content))
            return content;
        content = content.replaceAll("h[0-9]\\.", " ");
        content = content.replaceAll("[\\[\\]\\*_\\^\\-\\~\\+]", "");
        content = content.replaceAll("\\|", " ");
        content = content.replaceAll("\\{([^:\\}\\{]+)(?::([^\\}\\{]*))?\\}(?!\\})", " ");
        content = content.replaceAll("\\n", " ");
        content = content.replaceAll("\\r", " ");
        content = content.replaceAll("bq.", " ");
        content = content.replaceAll("  ", " ");
        int urlIdx = content.indexOf("http://");
        if (urlIdx > 0)
            content = content.substring(0, urlIdx);
        return summariseWithoutStrippingWikiCharacters(content).trim();
    }

    public static String summariseWithoutStrippingWikiCharacters(String content) {
        if (content.length() > 255)
            return TextUtils.trimToEndingChar(content, 251) + "...";
        else
            return content;
    }

    public static String wordWrap(String str, int max) {
        if (!TextUtils.stringSet(str))
            return str;
        StringBuffer sb = new StringBuffer(str);
        int nonSpaceChars = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (Character.isWhitespace(sb.charAt(i)))
                nonSpaceChars = 0;
            else
                nonSpaceChars++;
            if (nonSpaceChars > max) {
                nonSpaceChars = 0;
                sb.insert(i, " ");
                i++;
            }
        }

        return sb.toString().trim();
    }

    public static String highlight(String content, String searchwords) {
        if (!TextUtils.stringSet(content) || !TextUtils.stringSet(searchwords))
            return content;
        StringTokenizer st = new StringTokenizer(searchwords, ", ");
        do {
            if (!st.hasMoreTokens())
                break;
            String token = st.nextToken();
            if (!token.equalsIgnoreCase("span") && !token.equalsIgnoreCase("class") && !token.equalsIgnoreCase("search")
                    && !token.equalsIgnoreCase("highlight"))
                content = Pattern.compile("(" + token + ")", 2).matcher(content).replaceAll("<span class=\"search-highlight\">$0</span>");
        } while (true);
        return content;
    }

    public static String doubleUrlEncode(String s) {
        return urlEncode(urlEncode(s));
    }

    public static boolean isAllAscii(String s) {
        char sChars[] = s.toCharArray();
        for (int i = 0; i < sChars.length; i++) {
            char sChar = sChars[i];
            if (sChar > '\177')
                return false;
        }

        return true;
    }

    public static boolean isAllLettersOrNumbers(String s) {
        char sChars[] = s.toCharArray();
        for (int i = 0; i < sChars.length; i++) {
            char sChar = sChars[i];
            if (!Character.isLetterOrDigit(sChar))
                return false;
        }

        return true;
    }

    public static boolean stringSet(String str) {
        return str != null && str.length() > 0;
    }

    public static String formatLongTime(long time) {
        StringBuffer result = new StringBuffer();
        if (time > 3600000L) {
            time = scaleTime(time, 3600000L, result);
            result.append(":");
        }
        time = scaleTime(time, 60000L, result);
        result.append(":");
        time = scaleTime(time, 1000L, result);
        result.append(".").append(time);
        return result.toString();
    }

    private static long scaleTime(long time, long scale, StringBuffer buf) {
        long report = time / scale;
        time -= report * scale;
        String result = Long.toString(report);
        if (report < 10L)
            result = "0" + result;
        buf.append(result);
        return time;
    }

    public static String formatDateFull(Date date) {
        return DateFormat.getDateInstance(0).format(date);
    }

    public static String getCharacterEncoding() {
        return "UTF-8";
    }

    public static String escapeCDATA(String s) {
        if (s.indexOf("]]") < 0)
            return s;
        else
            return s.replaceAll("\\]\\]", "]] ");
    }

    public static String unescapeCDATA(String s) {
        if (s.indexOf("]] ") < 0)
            return s;
        else
            return s.replaceAll("\\]\\] ", "]]");
    }

    public static File createTempFile(String directory) {
        Date date = new Date();
        String pattern = "_{0,date,MMddyyyy}_{1,time,HHmmss}";
        String uniqueRandomFileName = MessageFormat.format(pattern, new Object[]{date, date});
        return new File(directory, uniqueRandomFileName);
    }

    public static String unescapeEntities(String str) {
        Pattern hexEntityPattern = Pattern.compile("&([a-fA-F0-9]+);");
        Pattern decimalEntityPattern = Pattern.compile("&#([0-9]+);");
        str = replaceNumericEntities(str, hexEntityPattern, 16);
        return replaceNumericEntities(str, decimalEntityPattern, 10);
    }

    private static String replaceNumericEntities(String str, Pattern pattern, int base) {
        Matcher matcher = pattern.matcher(str);
        StringBuffer buf = new StringBuffer(str.length());
        for (; matcher.find(); matcher.appendReplacement(buf, Character.toString((char) Integer.parseInt(matcher.group(1), base))))
            ;
        matcher.appendTail(buf);
        return buf.toString();
    }

    public static String base64Decode(String s) {
        try {
            String s1 = s.replaceAll("_", "/");
            String s2 = s1.replaceAll("-", "+");
            return new String((new sun.misc.BASE64Decoder()).decodeBuffer(s), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("This Java installation doesn't support UTF-8. Call Mulder");
            return s;
        } catch (IOException e) {
            log.error("IOException from base64Decode " + e);
            return s;
        }
    }

    public static String base64Encode(String s) {
        try {
            byte sBytes[] = s.getBytes("UTF-8");
            BASE64Encoder encoder = new sun.misc.BASE64Encoder();
            return (encoder.encode(sBytes)).replaceAll("\\n", "").replaceAll("/", "_").replaceAll("\\+", "-").trim();
        } catch (UnsupportedEncodingException e) {
            log.error("This Java installation doesn't support UTF-8. Call Mulder");
            return s;
        }
    }

    public static String hackSingleQuotes(String s) {
        if (TextUtils.stringSet(s))
            return s.replaceAll("'", "' + '\\\\'' + '");
        else
            return s;
    }

    public boolean isInLastDays(Date date, int maxDays) {
        if (date == null) {
            return false;
        } else {
            long tstamp = date.getTime();
            long t0 = System.currentTimeMillis();
            long dt = t0 - tstamp;
            long secs = dt / 1000L;
            long mins = secs / 60L;
            long hours = mins / 60L;
            long days = hours / 24L;
            return days < (long) maxDays;
        }
    }

    public String getRelativeTime(Date date) {
        if (date == null)
            return "No timestamp.";
        long tstamp = date.getTime();
        long t0 = System.currentTimeMillis();
        long dt = t0 - tstamp;
        long secs = dt / 1000L;
        long mins = secs / 60L;
        long hours = mins / 60L;
        long days = hours / 24L;
        StringBuffer ret = new StringBuffer();
        if (days != 0L)
            ret.append(days + " day" + (days != 1L ? "s " : " "));
        hours -= days * 24L;
        if (hours != 0L)
            ret.append(hours + " hour" + (hours != 1L ? "s " : " "));
        mins -= (days * 24L + hours) * 60L;
        if (mins != 0L)
            ret.append(mins + " min" + (mins != 1L ? "s " : " "));
        if (days != 0L || hours != 0L || mins != 0L)
            ret.append(" ago");
        else
            ret.append("less than a minute ago");
        return ret.toString();
    }

    public String getFormatDateSimple(Date date) {
        DateFormat df = new SimpleDateFormat("dd MMM");
        return df.format(date);
    }

    public static Cookie setCookie(String key, String value) {
        HttpServletRequest request = ServletActionContext.getRequest();
        javax.servlet.http.HttpServletResponse response = ServletActionContext.getResponse();
        int cookieAge = 31104000;
        String path = request.getContextPath();
        if (!TextUtils.stringSet(path))
            path = "/";
        return CookieUtils.setCookie(request, response, key, value, cookieAge, path);
    }

    public static String getCookieValue(String key) {
        HttpServletRequest request = ServletActionContext.getRequest();
        return CookieUtils.getCookieValue(request, key);
    }

    public static String htmlEncode(String s) {
        if (!TextUtils.stringSet(s))
            return "";
        StringBuffer str = new StringBuffer();
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            if (c < '\200')
                switch (c) {
                    case 34: // '"'
                        str.append("&quot;");
                        break;

                    case 38: // '&'
                        str.append("&amp;");
                        break;

                    case 60: // '<'
                        str.append("&lt;");
                        break;

                    case 62: // '>'
                        str.append("&gt;");
                        break;

                    default:
                        str.append(c);
                        break;
                }
            else
                str.append(c);
        }

        return str.toString();
    }

    public static String plain2html(String text) {
        return TextUtils.plainTextToHtml(text);
    }

    public static Properties getProperties(String resource, Class callingClass) {
        return getPropertiesFromStream(ClassLoaderUtils.getResourceAsStream(resource, callingClass));
    }

    public static Properties getPropertiesFromFile(File file) {
        try {
            return getPropertiesFromStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            log.error("Error loading properties from file: " + file.getPath() + ". File does not exist.", e);
            return null;
        }
    }

    public static Properties getPropertiesFromStream(InputStream is) {
        if (is == null)
            return null;
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            log.error("Error loading properties from stream.", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (Exception ignore) {
                }
        }
        return props;
    }

    public static void unzipFile(File zipFile, File dirToExtractTo) throws Exception {
        if (!zipFile.isFile()) {
            throw new IOException("Zip file doesn't exist or Confluence doesn't have read access to it. backupedFile=" + zipFile);
        } else {
            Unzipper fileUnzipper = new FileUnzipper(zipFile, dirToExtractTo);
            fileUnzipper.unzip();
            return;
        }
    }

    public static void unzipUrl(URL zipUrl, File dirToExtractTo) throws Exception {
        Unzipper urlUnzipper = new UrlUnzipper(zipUrl, dirToExtractTo);
        urlUnzipper.unzip();
    }

    private static String extractGoogleUrl(String url, int indexOfQuery) {
        try {
            int indexOfAmpersand = url.indexOf("&", indexOfQuery);
            String googleQueryPhrase;
            if (indexOfAmpersand > -1)
                googleQueryPhrase = url.substring(indexOfQuery + 2, indexOfAmpersand);
            else
                googleQueryPhrase = url.substring(indexOfQuery + 2);
            url = "Google: " + URLDecoder.decode(googleQueryPhrase);
        } catch (Exception e) {
        }
        return url;
    }

}

