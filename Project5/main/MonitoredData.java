package main;

import static java.util.stream.Collectors.toMap;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonitoredData {
	private String start_time;
	private String end_time;
	private String activity;
	private List<MonitoredData> data;

	public MonitoredData(String start_time, String end_time, String activity) {
		this.start_time = start_time;
		this.end_time = end_time;
		this.activity = activity;
	}

	public MonitoredData() {
	}

	public List<MonitoredData> task1() {
		String file = "D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Activities.txt";
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			data = stream.map(line -> line.split("\\t\\t")).map(a -> new MonitoredData(a[0], a[1], a[2]))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_1.txt",
					false);
			for (MonitoredData m : data) {
				f.write(m.toString() + "\n");
			}
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return data;
	}

	public void task2() {
		int cnt = (int) data.stream().filter(distinctBy(d -> d.getDistinctStartDay())).count();
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_2.txt",
					false);
			f.write("	" + cnt + " distinct days appear in the monitoring data.\n");
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
	}

	public Map<String, Integer> task3() {
		Map<String, Integer> activities = new HashMap<String, Integer>();
		activities = data.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(e -> 1)));
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_3.txt",
					false);
			activities.forEach((k, v) -> {
				try {
					f.write(" No of apparitions: " + v + "| Activity: " + k + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return activities;
	}

	public Map<Integer, Map<String, Integer>> task4() {
		Map<Integer, Map<String, Integer>> activities = new HashMap<Integer, Map<String, Integer>>();
		activities = data.stream().collect(Collectors.groupingBy(t -> t.getDistinctStartDay(),
				Collectors.groupingBy(MonitoredData::getActivity, Collectors.summingInt(e -> 1))));
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_4.txt",
					false);
			activities.forEach((k, v) -> {
				v.forEach((v1, v2) -> {
					try {
						f.write(" No of the start day: " + k + "|| No of apparitions: " + v2 + "| Activity: " + v1
								+ "\n");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				});
				try {
					f.write("\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return activities;
	}

	public Map<String, Duration> task5() {
		Map<String, Duration> activities = data.stream()
				.collect(toMap(MonitoredData::getActivity, MonitoredData::getDuration, Duration::plus));
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_5.txt",
					false);
			activities.forEach((k, v) -> {
				try {
					f.write(" Duration: " + v + "| Activity: " + k + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return activities;
	}

	public List<String> task6() {
		List<String> act = data.stream()
				.collect(Collectors.groupingBy(MonitoredData::getActivity,
						Collectors.averagingDouble(e -> e.getDuration().toMinutes())))
				.entrySet().stream().filter(v -> v.getValue() <= 5.5).map(m -> m.getKey()).collect(Collectors.toList());
		try {
			FileWriter f = new FileWriter("D:\\PT2020\\pt2020_302210_strujan_florentina_assignment_5\\Task_6.txt",
					false);
			for (String a : act) {
				f.write("The activity: " + a
						+ " has more than 90% of the monitoring records with duration less than 5 minutes\n");
			}
			f.close();
		} catch (IOException e) {
			System.out.println("Error");
		}
		return act;
	}

	public static <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {

		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public int getDistinctStartDay() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(start_time, formatter);
		return start.getDayOfYear();
	}

	@Override
	public String toString() {
		return "MonitoredData : start_time=" + start_time + ", end_time=" + end_time + ", activity=" + activity;
	}

	public Duration getDuration() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime start = LocalDateTime.parse(start_time, formatter);
		LocalDateTime end = LocalDateTime.parse(end_time, formatter);
		Duration duration = Duration.between(start, end);
		return duration;
	}

	public String getActivity() {
		return activity;
	}

	public static void main(String[] args) {
		MonitoredData m = new MonitoredData();
		m.task1();
		m.task2();
		m.task3();
		m.task4();
		m.task5();
		m.task6();
	}
}
