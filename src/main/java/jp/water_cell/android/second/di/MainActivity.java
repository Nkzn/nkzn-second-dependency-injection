package jp.water_cell.android.second.di;

import jp.water_cell.android.second.di.model.WeatherReport;
import jp.water_cell.android.second.di.model.WeatherReportSchedule;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

	/** 天気予報のスケジュールを管理するオブジェクト */
	 @Inject
	 private WeatherReportSchedule mWeatherReportSchedule;
//	private WeatherReportSchedule mWeatherReportSchedule = new FixedWeatherReportSchedule();

	/** 天気予報オブジェクト */
	 @Inject
	 private WeatherReport mWeatherReport;
//	private WeatherReport mWeatherReport = new ProductionWeatherReport();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		viewCreated();
	}

	/**
	 * ビューが作成された後の処理を行う
	 */
	private void viewCreated() {

		// 予報の対象時間
		long reportTimeMillis = mWeatherReportSchedule
				.getNextReportTimeMillis();

		// 川崎の天気を設定する
		TextView fieldKawasaki = viewById(R.id.weather_kawasaki);
		String addressKawasaki = getString(R.string.address_kawasaki);
		String weatherKawasaki = mWeatherReport.reportWeather(addressKawasaki,
				reportTimeMillis);
		fieldKawasaki.setText(weatherKawasaki);

		// 横浜の天気を設定する
		TextView fieldYokohama = viewById(R.id.weather_yokohama);
		String addressYokohama = getString(R.string.address_yokohama);
		String weatherYokohama = mWeatherReport.reportWeather(addressYokohama,
				reportTimeMillis);
		fieldYokohama.setText(weatherYokohama);
	}

	/** 指定のIDを持つUIコンポーネントを返す */
	@SuppressWarnings("unchecked")
	private <T> T viewById(int resId) {
		return (T) findViewById(resId);
	}
}
