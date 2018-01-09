package yalantis.com.sidemenu.sample.legacy;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.fragment.HomeFragment;

/**
 * @author Adil Soomro
 *
 */
@SuppressWarnings("deprecation")
public class AwesomeActivity extends TabActivity {
	TabHost tabHost;
	/** Called when the activity is first created. */
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		tabHost = getTabHost();
		setTabs();
	}
	private void setTabs()
	{
		addTab("soju", R.drawable.sojucol, soju.class);
		addTab("beer", R.drawable.beercol, beer.class);

		addTab("wine", R.drawable.winecol, wine.class);
		addTab("cognac", R.drawable.cognaccol, cognac.class);
	}
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
	public void openCameraActivity(View b)
	{
		Intent intent = new Intent(this, CameraActivity.class);
		startActivity(intent);
	}
}