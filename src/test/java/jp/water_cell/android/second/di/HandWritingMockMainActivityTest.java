package jp.water_cell.android.second.di;

import static org.junit.Assert.assertEquals;
import jp.water_cell.android.second.di.model.WeatherReport;
import jp.water_cell.android.second.di.model.WeatherReportSchedule;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import roboguice.RoboGuice;
import android.app.Application;
import android.widget.TextView;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class HandWritingMockMainActivityTest {
	
	MainActivity activity;
	
	@Before
	public void setUp() {
		activity = new MainActivity();
	}
	
	@Test
	public void testShowUnknownOnActivityCreated() {
		 //-- Given
		
		//次の予報対象時間
        final long reportTimeMillis = 1L;
        //川崎の住所
        final String addressKawasaki = activity.getString(R.string.address_kawasaki);
        //横浜の住所
        final String addressYokohama = activity.getString(R.string.address_yokohama);

        //次の対象予報時間を返すようにモックオブジェクトを設定する
        final WeatherReportSchedule mockWeatherReportSchedule = new WeatherReportSchedule() {
            @Override
            public long getNextReportTimeMillis() {
                return reportTimeMillis;
            }
        };
        //川崎の予報として晴れを返すようにモックオブジェクトを設定する
        //横浜の予報として曇を返すようにモックオブジェクトを設定する
        final WeatherReport mockWeatherReport = new WeatherReport() {
            @Override
            public String reportWeather(String address,
                                        long date) {
                return address.equals(addressKawasaki) ? "晴れ" : address.equals(addressYokohama) ? "曇" : "まだわかんないよ";
            }
        };

        //モックオブジェクトをInjectionするためのモジュール
        Module mockModule = new AbstractModule() {
            @Override
            protected void configure() {
                bind(WeatherReportSchedule.class).toInstance(mockWeatherReportSchedule);
                bind(WeatherReport.class).toInstance(mockWeatherReport);
            }
        };

        Application app = Robolectric.application;
        Module module = Modules.override(RoboGuice.newDefaultRoboModule(app)).with(mockModule);
        RoboGuice.setBaseApplicationInjector(app, RoboGuice.DEFAULT_STAGE, module);
		RoboGuice.injectMembers(activity, this);
        
        //-- When

        //Activityを起動する
		activity.onCreate(null);

        //-- Then

        //川崎の天気の表示がわかんないんだよになっていること
        TextView weatherKawasaki = (TextView) activity.findViewById(R.id.weather_kawasaki);
        assertEquals("川崎の天気は晴れ", weatherKawasaki.getText(), "晴れ");

        //横浜の天気の表示がわかんないんだよになっていること
        TextView weatherYokohama = (TextView) activity.findViewById(R.id.weather_yokohama);
        assertEquals("横浜の天気は曇", weatherYokohama.getText(), "曇");
	}
	
	@After
	public void tearDownRoboGuice() {
		RoboGuice.util.reset();
	}
	
}
