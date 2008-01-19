package com.opensymphony.webwork.util;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Holds test strings and encodings.  There was more test written hence the base class.
 *
 * @author Brad Baker (Atlassian)
 * @version $Date$ $Id$
 */
public abstract class EncodingTestCase extends TestCase
{

    static final int K = 1024;

    static final String ASCII = "ASCII";
    static final String UTF_8 = "UTF-8";
    static final String LATIN = "ISO8859_1";
    static final String UTF_16 = "UTF-16";
    static final String KOI8_R = "KOI8_R";
    static final String WINDOWS_CYRILLIC = "Cp1251";
    static final String UBIG_ENDIAN = "UnicodeBig";
    static final String ULITTLE_ENDIAN = "UnicodeLittle";
    static final String UBIG_ENDIAN_UNMARKED = "UnicodeBigUnmarked";
    static final String ULITTLE_ENDIAN_UNMARKED = "UnicodeLittleUnmarked";


    static final String RUSSIAN_DESC1 = "\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439\u0442\u0435 \u0434\u0430\u043d\u043d\u0443\u044e \u0444\u043e\u0440\u043c\u0443 \u0434\u043b\u044f \u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u043a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u044f";
    static final String RUSSIAN_DESC_SHORT = "\u0418\u0441\u043f\u043e";
    static final String ASCII_TEXT = "will this string be written to bytes and then back to chars as expected?";
    static final String ASCII_TEXT_SHORT = "ok";
    static final String CHINESE_TIMETRACKING = "\\u65f6\\u95f4\\u8ddf\\u8e2a\\u62a5\\u544a";
    static final String HUNGRIAN_APPLET_PROBLEM = "Az Applet biztons\\u00e1gi be\\u00e1ll\\u00edt\\u00e1sai helytelenek.  Fogadja el az Applet tan\\u00fas\\u00edtv\\u00e1ny\\u00e1t a futtat\\u00e1shoz.";


    protected abstract void implementEncodingTest(String expectedStr, String encoding, int bufferSize) throws Exception;

    void assertEncoding(String expectedStr, String encoding) throws Exception
    {
        doAssertionsOnCombinations(new String[] { expectedStr }, new String[] { encoding });
    }

    public void assertEncodings(String[] testStrs, String[] encodings) throws Exception
    {
        doAssertionsOnCombinations(testStrs, encodings);
    }

    private void doAssertionsOnCombinations(String[] testStrs, String[] encodings) throws Exception
    {
        int[] bufferSizes = { 1, 3, 5, 10, 101, 1024, 8192 };
        int[] outputLens = { 100 * K, 50 * K, 20 * K, 10 * K, 1 * K };

        // given a bunch  of output buffer sizes
        for (int i = 0; i < encodings.length; i++)
        {
            String encoding = encodings[i];

            // but only if we have the encoding in this JDK
            if (Charset.isSupported(encoding))
            {
                for (int j = 0; j < bufferSizes.length; j++)
                {
                    int bufferSize = bufferSizes[j];

                    // with a number of strings
                    for (int k = 0; k < testStrs.length; k++)
                    {
                        String testStr = testStrs[k];
                        implementEncodingTest(testStr, encoding, bufferSize);

                        // now make the strings bigger up to a bunch of sizes
                        for (int l = 0; l < outputLens.length; l++)
                        {
                            int outputLen = outputLens[l];
                            final String largeStr = makeRoughly(testStr, outputLen);
                            try
                            {
                                implementEncodingTest(largeStr, encoding, bufferSize);
                            }
                            catch (AssertionFailedError afe)
                            {
                                throw afe;
                            }
                            catch (Throwable t)
                            {
                                t.printStackTrace();
                                throw new RuntimeException("enc :" + encoding + " bufferSize:" + bufferSize + " outputlen:" + outputLen);
                            }
                        }
                    }
                }
            }
        }
    }

    protected String makeRoughly(String srcStr, int roughSize)
    {
        StringBuffer sb = new StringBuffer(srcStr);
        while (sb.length() < roughSize)
        {
            sb.append(srcStr);
        }
        return sb.toString();
    }


    protected void assertTheyAreEqual(Object expected, Object actual, String encoding)
            throws UnsupportedEncodingException
    {
        String expectedStr = expected.toString();
        String actualStr = actual.toString();
        try
        {

            Assert.assertEquals(expectedStr, actualStr);
        }
        catch (AssertionFailedError afe)
        {

            System.out.println("___ " + encoding + "__________");

            System.out.println("EL:|" + expectedStr.length() + "|");
            System.out.println("AL:|" + actualStr.length() + "|");


            System.out.println("E:|" + expectedStr + "|");
            System.out.println("A:|" + actualStr + "|");

            byte[] expectedBytes = expectedStr.getBytes(encoding);
            byte[] actualBytes = actualStr.getBytes(encoding);

            System.out.println("E:|" + toHex(expectedBytes) + "|");
            System.out.println("A:|" + toHex(actualBytes) + "|");

            throw afe;
        }
    }

    String toHex(byte[] bytes)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append(Integer.toHexString(bytes[i]).toUpperCase());
        }
        return sb.toString();
    }


}
