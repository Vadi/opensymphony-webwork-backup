package com.test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SubSeriesDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.DefaultWindDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.opensymphony.xwork.ActionSupport;

import de.laures.cewolf.ChartPostProcessor;
import de.laures.cewolf.DatasetProduceException;
import de.laures.cewolf.DatasetProducer;
import de.laures.cewolf.links.LinkGenerator;
import de.laures.cewolf.links.XYItemLinkGenerator;
import de.laures.cewolf.tooltips.CategoryToolTipGenerator;
import de.laures.cewolf.tooltips.PieToolTipGenerator;

public class CewolfAction extends ActionSupport {
	DatasetProducer timeData;

	DatasetProducer signalsData;

	DatasetProducer xyData;

	DatasetProducer windData;

	DatasetProducer pieData;

	DatasetProducer categoryData;

	DatasetProducer ganttData;

	DatasetProducer hiloData;

	DatasetProducer valueDatasetProducer;

	ChartPostProcessor meterPP;

	LinkGenerator xyLG;

	CategoryToolTipGenerator catTG;

	PieToolTipGenerator pieTG;

	ChartPostProcessor dataColor;

	public String execute() throws Exception {
		prepareData();
		return super.execute();
	}

	private void prepareData() {

		timeData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				TimeSeries ts = new TimeSeries("Cewolf Release Schedule",
						Month.class);
				ts.add(new Month(7, 2002), 0.1);
				ts.add(new Month(8, 2002), 0.4);
				ts.add(new Month(9, 2002), 0.9);
				ts.add(new Month(10, 2002), 1.0);
				return new TimeSeriesCollection(ts);
			}

			public String getProducerId() {
				return "TimeDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		signalsData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				TimeSeries ts = new TimeSeries("Cewolf Release Schedule",
						Month.class);
				ts.add(new Month(7, 2002), 0.1);
				ts.add(new Month(8, 2002), 0.4);
				ts.add(new Month(9, 2002), 0.9);
				ts.add(new Month(10, 2002), 1.0);
				TimeSeriesCollection col = new TimeSeriesCollection(ts);
				return new SubSeriesDataset(col, 0);
			}

			public String getProducerId() {
				return "SignalsDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		xyData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				XYSeries xys = new XYSeries("Example XY Dataset");
				double last = 0.0;
				for (int i = -50; i <= 50; i++) {
					double y = last + ((Math.random() * 100) - 50.0);
					xys.add((double) i, y);
					last = y;
				}
				return new XYSeriesCollection(xys);
			}

			public String getProducerId() {
				return "XYDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		windData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				int itemCount = 10;
				Object[][][] values = new Integer[itemCount][itemCount][itemCount];
				for (int i = 0; i < itemCount; i++) {
					for (int j = 0; j < itemCount; j++) {
						for (int k = 0; k < itemCount; k++) {
							values[i][j][k] = new Integer(
									(int) Math.random() * 5);
						}
					}
				}
				return new DefaultWindDataset(DefaultWindDataset
						.seriesNameListFromDataArray(values[0]), values);
			}

			public String getProducerId() {
				return "WindDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		pieData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				final String[] categories = { "apples", "pies", "bananas",
						"oranges" };
				DefaultPieDataset ds = new DefaultPieDataset();
				for (int i = 0; i < categories.length; i++) {
					int y = (int) (Math.random() * 10 + 1);
					ds.setValue(categories[i], new Integer(y));
				}
				return ds;
			}

			public String getProducerId() {
				return "PieDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		categoryData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				final String[] categories = { "apples", "pies", "bananas",
						"oranges" };
				final String[] seriesNames = { "Peter", "Helga", "Franz",
						"Olga" };
				final Integer[][] startValues = new Integer[seriesNames.length][categories.length];
				final Integer[][] endValues = new Integer[seriesNames.length][categories.length];
				for (int series = 0; series < seriesNames.length; series++) {
					for (int i = 0; i < categories.length; i++) {
						int y = (int) (Math.random() * 10 + 1);
						startValues[series][i] = new Integer(y);
						endValues[series][i] = new Integer(y
								+ (int) (Math.random() * 10));
					}
				}
				DefaultIntervalCategoryDataset ds = new DefaultIntervalCategoryDataset(
						seriesNames, categories, startValues, endValues);
				return ds;
			}

			public String getProducerId() {
				return "CategoryDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		ganttData = new DatasetProducer() {
			final private long now = System.currentTimeMillis();

			final private long day = 1000 * 60 * 60 * 24;

			final private String[] workflows = { "Analysis", "Design",
					"Implementation", "Test", "Rollout", "Operations" };

			final private String[] person = { "Frank", "Paul", "Daisy", "Chris" };

			public Object produceDataset(Map params) {
				TaskSeriesCollection ds = new TaskSeriesCollection();
				for (int j = 0; j < 4; j++) {
					TaskSeries ser = new TaskSeries(person[j]);
					long lastEnd = now + getRandomTime();
					for (int i = 0; i < workflows.length; i++) {
						long myEnd = lastEnd + getRandomTime();
						Task t = new Task(workflows[i], new SimpleTimePeriod(
								new Date(lastEnd), new Date(myEnd)));
						ser.add(t);
						lastEnd = myEnd;
					}
					ds.add(ser);
				}
				return ds;
			}

			private long getRandomTime() {
				return day * (long) (Math.random() * 31 + 15);
			}

			public String getProducerId() {
				return "GanttDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return true;
			}
		};

		hiloData = new DatasetProducer() {
			public Object produceDataset(Map params) {
				int itemCount = 100;
				Date[] dates = new Date[itemCount];
				double[] high = new double[itemCount];
				double[] low = new double[itemCount];
				double[] open = new double[itemCount];
				double[] close = new double[itemCount];
				double[] volume = new double[itemCount];
				Calendar cal = new GregorianCalendar();
				for (int i = 0; i < itemCount; i++) {
					cal.roll(Calendar.HOUR, 1);
					dates[i] = cal.getTime();
					high[i] = Math.random() * 100 + 100;
					low[i] = high[i] - (Math.random() * 30);
					open[i] = low[i] + (Math.random() * (high[i] - low[i]));
					close[i] = open[i] + (Math.random() * (high[i] - open[i]));
					volume[i] = Math.random() * 1000;
				}
				OHLCDataset ds = new DefaultHighLowDataset("Laures, Inc.",
						dates, high, low, open, close, volume);
				return ds;
			}

			public String getProducerId() {
				return "HiLoDataProducer";
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}
		};

		valueDatasetProducer = new DatasetProducer() {

			public Object produceDataset(Map params)
					throws DatasetProduceException {
				Integer val = new Integer(86);
				DefaultValueDataset data = new DefaultValueDataset(val);
				return data;
			}

			public boolean hasExpired(Map params, Date since) {
				return false;
			}

			public String getProducerId() {
				return "meterdata";
			}

		};

		meterPP = new ChartPostProcessor() {

			public void processChart(Object chart, Map params) {
				MeterPlot plot = (MeterPlot) ((JFreeChart) chart).getPlot();

				double min = 0;
				double max = 260;
				double val = 86;
				double minCrit = 187;
				double maxCrit = max;
				double minWarn = 164;
				double maxWarn = minCrit;
				double maxNorm = minCrit;
				double minNorm = min;

				plot.setRange(new Range(min, max));
				plot.setUnits("km/h");
			}
		};

		xyLG = new XYItemLinkGenerator() {
			public String generateLink(Object data, int series, int item) {
				return "#Series " + series;
			}
		};

		catTG = new CategoryToolTipGenerator() {
			public String generateToolTip(CategoryDataset dataset, int series,
					int index) {
				return String.valueOf(dataset.getValue(series, index));
			}
		};

		pieTG = new PieToolTipGenerator() {
			public String generateToolTip(PieDataset dataset,
					Comparable section, int index) {
				return String.valueOf(index);
			}
		};

		dataColor = new ChartPostProcessor() {
			public void processChart(Object chart, Map params) {
				CategoryPlot plot = (CategoryPlot) ((JFreeChart) chart)
						.getPlot();
				for (int i = 0; i < params.size(); i++) {
					String colorStr = (String) params.get(String.valueOf(i));
					plot.getRenderer().setSeriesPaint(i,
							java.awt.Color.decode(colorStr));
				}
			}
		};

    }


    public DatasetProducer getCategoryData() {
        return categoryData;
    }


    public CategoryToolTipGenerator getCatTG() {
        return catTG;
    }


    public ChartPostProcessor getDataColor() {
        return dataColor;
    }


    public DatasetProducer getGanttData() {
        return ganttData;
    }


    public DatasetProducer getHiloData() {
        return hiloData;
    }


    public ChartPostProcessor getMeterPP() {
        return meterPP;
    }


    public DatasetProducer getPieData() {
        return pieData;
    }


    public PieToolTipGenerator getPieTG() {
        return pieTG;
    }


    public DatasetProducer getSignalsData() {
        return signalsData;
    }


    public DatasetProducer getTimeData() {
        return timeData;
    }


    public DatasetProducer getValueDatasetProducer() {
        return valueDatasetProducer;
    }


    public DatasetProducer getWindData() {
        return windData;
    }


    public DatasetProducer getXyData() {
        return xyData;
    }


    public LinkGenerator getXyLG() {
        return xyLG;
    }
}