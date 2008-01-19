package com.opensymphony.webwork.util;

import java.io.IOException;
import javax.servlet.jsp.JspWriter;

/**
 * A test class for {@link webwork.util.FastByteArrayOutputStream}
 *
 * @author Brad Baker (Atlassian)
 * @since $Date$ $Id$
 */
public class FastByteArrayOutputStreamTestCase extends AbstractEncodingTestCase
{

    public void testLatinCharsets() throws Exception
    {
        assertEncoding(ASCII_TEXT, LATIN);
        assertEncoding(ASCII_TEXT, ASCII);
    }

    public void testRussianCharsets() throws Exception
    {
        assertEncoding(RUSSIAN_DESC_SHORT, KOI8_R);
        assertEncoding(RUSSIAN_DESC1, KOI8_R);

        assertEncoding(RUSSIAN_DESC_SHORT, WINDOWS_CYRILLIC);
        assertEncoding(RUSSIAN_DESC1, WINDOWS_CYRILLIC);
    }

    public void testUnicodeCharsets() throws Exception
    {
        String[] testStrs = {ASCII_TEXT_SHORT, ASCII_TEXT, RUSSIAN_DESC_SHORT, RUSSIAN_DESC1, CHINESE_TIMETRACKING, HUNGRIAN_APPLET_PROBLEM, };
        String[] encodings = { UTF_8, UTF_16, UBIG_ENDIAN, ULITTLE_ENDIAN, UBIG_ENDIAN_UNMARKED, ULITTLE_ENDIAN_UNMARKED };

        assertEncodings(testStrs, encodings);
    }

    protected void implementEncodingTest(final String srcStr, final String encoding, final int bufferSize)
            throws Exception
    {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream(bufferSize);

        byte[] bytes = srcStr.getBytes(encoding);
        bout.write(bytes);

        JspWriter writer = new StringCapturingJspWriter();
        bout.writeTo(writer, encoding);

        String actualStr = writer.toString();
        String expectedStr = new String(bytes, encoding);
        assertTheyAreEqual(expectedStr, actualStr, encoding);
    }


    /**
     * Before it was changed to use {@link java.nio.charset.CharsetDecoder} is took an this time
     * <p/>
     * Total Call Time = 1112.0ms
     * Average Call Time = 0.001112ms
     * <p/>
     * Now with the change it takes this time
     * <p/>
     * <p/>
     * The idea is that it did not get significantly worse in performance
     *
     * @throws IOException
     */
    public void testPerformanceOfWriteToJspWriter() throws IOException
    {
        final String NINEK_STR = makeRoughly(ASCII, 9 * K);
        final String FIFTYK_STR = makeRoughly(ASCII, 50 * K);
        final String ONEHUNDREDK_STR = makeRoughly(ASCII, 100 * K);

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With < than 8K of data";
            }

            public String getText(final int times)
            {
                return ASCII_TEXT;
            }
        });

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With > than 8K of data";
            }

            public String getText(final int times)
            {
                return NINEK_STR;
            }
        });

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With a 2/3 mix of small data and 1/3 > 8K of data";
            }

            public String getText(final int times)
            {
                if (times % 3 == 0)
                {
                    return NINEK_STR;
                }
                return ASCII_TEXT;
            }
        });

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With a 1/2 mix of small data and 1/2 > 8K of data";
            }

            public String getText(final int times)
            {
                if (times % 2 == 0)
                {
                    return NINEK_STR;
                }
                return ASCII_TEXT;
            }
        });

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With 50K of data";
            }

            public String getText(final int times)
            {
                return FIFTYK_STR;
            }
        });

        testPerformanceOfWriteToJspWriter(new TextSource()
        {
            public String getDesc()
            {
                return "With 100K of data";
            }

            public String getText(final int times)
            {
                return ONEHUNDREDK_STR;
            }
        });


    }

    
    public void testPerformanceOfWriteToJspWriter(TextSource textSource) throws IOException
    {
        NoopJspWriter noopJspWriter = new NoopJspWriter();
        String[] methods = {
                "writeTo (using hueristics)",
                "writeToViaSmoosh",
        };

        System.out.println("====================");
        System.out.println(textSource.getDesc());
        System.out.println();
        float bestTime = Float.MAX_VALUE;
        String bestMethod = methods[0];
        for (int methodIndex = 0; methodIndex < methods.length; methodIndex++)
        {
            String method = methods[methodIndex];

            float totalTime = 0;
            final int MAX_TIMES = 10;
            final int MAX_ITERATIONS = 100;
            for (int times = 0; times < MAX_TIMES; times++)
            {
                String srcText = textSource.getText(times);
                for (int i = 0; i < MAX_ITERATIONS; i++)
                {
                    FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
                    bout.write(srcText.getBytes(UTF_8));

                    // just time the JspWriter output. And let it warm u first as well
                    if (times > 3)
                    {
                        long then = System.currentTimeMillis();
                        switch (methodIndex)
                        {
                            case 0:
                                bout.writeTo(noopJspWriter, UTF_8);
                                break;
                            case 1:
                                bout.writeToViaSmoosh(noopJspWriter, UTF_8);
                                break;
                        }
                        long now = System.currentTimeMillis();
                        totalTime += (now - then);
                    }
                }
            }
            float avgTime = totalTime / MAX_TIMES / MAX_ITERATIONS;
            System.out.println(method + "  - Total Call Time = " + totalTime + "ms");
            System.out.println(method + " - Average Call Time = " + avgTime + "ms");
            System.out.println();

            if (avgTime < bestTime) {
                bestTime = avgTime;
                bestMethod = method;
            }
        }
        System.out.println(bestMethod + " was the best method - Average Call Time = " + bestTime + "ms");
        System.out.println("____________________\n");

    }

    interface TextSource
    {
        String getDesc();

        String getText(int times);
    }

    static class StringCapturingJspWriter extends NoopJspWriter
    {
        StringCapturingJspWriter()
        {
            super(true);
        }
    }


    static class NoopJspWriter extends JspWriter
    {
        final StringBuffer sb = new StringBuffer();
        final boolean capture;

        NoopJspWriter()
        {
            this(false);
        }

        NoopJspWriter(boolean capture)
        {
            super(0, false);
            this.capture = capture;
        }

        NoopJspWriter(final int i, final boolean b)
        {
            super(i, b);
            this.capture = false;
        }

        public String toString()
        {
            return sb.toString();
        }

        public void clear() throws IOException
        {
        }

        public void clearBuffer() throws IOException
        {
        }

        public void close() throws IOException
        {
        }

        public void flush() throws IOException
        {
        }

        public int getRemaining()
        {
            return 0;
        }

        public void newLine() throws IOException
        {
        }

        public void print(final char c) throws IOException
        {
            if (capture)
            {
                sb.append(c);
            }
        }

        public void print(final double v) throws IOException
        {
            if (capture)
            {
                sb.append(v);
            }
        }

        public void print(final float v) throws IOException
        {
            if (capture)
            {
                sb.append(v);
            }
        }

        public void print(final int i) throws IOException
        {
            if (capture)
            {
                sb.append(i);
            }
        }

        public void print(final long l) throws IOException
        {
            if (capture)
            {
                sb.append(l);
            }
        }

        public void print(final Object o) throws IOException
        {
            if (capture)
            {
                sb.append(o);
            }
        }

        public void print(final String s) throws IOException
        {
            if (capture)
            {
                sb.append(s);
            }
        }

        public void print(final boolean b) throws IOException
        {
            if (capture)
            {
                sb.append(b);
            }
        }

        public void print(final char[] chars) throws IOException
        {
            if (capture)
            {
                sb.append(chars);
            }
        }

        public void println() throws IOException
        {
            print('\n');
        }

        public void println(final char c) throws IOException
        {
            print(c);
            println();
        }

        public void println(final double v) throws IOException
        {
            print(v);
            println();
        }

        public void println(final float v) throws IOException
        {
            print(v);
            println();
        }

        public void println(final int i) throws IOException
        {
            print(i);
            println();
        }

        public void println(final long l) throws IOException
        {
            print(l);
            println();
        }

        public void println(final Object o) throws IOException
        {
            print(o);
            println();
        }

        public void println(final String s) throws IOException
        {
            print(s);
            println();
        }

        public void println(final boolean b) throws IOException
        {
            print(b);
            println();
        }

        public void println(final char[] chars) throws IOException
        {
            print(chars);
            println();
        }

        public void write(final char cbuf[], final int off, final int len) throws IOException
        {
            String s = new String(cbuf, off, len);
            print(s);
        }
    }
}
