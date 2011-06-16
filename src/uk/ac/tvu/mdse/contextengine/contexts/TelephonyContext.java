package uk.ac.tvu.mdse.contextengine.contexts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import uk.ac.tvu.mdse.contextengine.Component;

public class TelephonyContext extends Component {

	private boolean roaming = false;
	private int connection = 0;
	private static final long serialVersionUID = -6310232951343360172L;
	public TelephonyManager tm;

	public TelephonyContext(String name, Context c) {
		super("TELEPHONY", c);
		setupMonitor();
		checkContext();
	}

	private void setupMonitor() {
		// TODO Auto-generated method stub
		contextMonitor = new BroadcastReceiver() {

			@Override
			public void onReceive(Context c, Intent in) {
				checkContext();
			}
		};
		context.registerReceiver(contextMonitor, new IntentFilter(
				"android.net.conn.CONNECTIVITY_CHANGE"));
	}

	protected void checkContext() {
		// TODO Auto-generated method stub
		checkRoaming();
		checkConnectionState();

	}

	private void checkConnectionState() {
		int v = tm.getDataState();
		if (v != connection){
			if(v == 2)
				sendNotification("telephonyConnectedON", true);
			else
				sendNotification("telephonyConnectedON", false);
			connection = v;
		}
		
	}

	private void checkRoaming() {
		boolean r = tm.isNetworkRoaming();
		if (r && (!roaming)) {
			sendNotification("roamingON", true);
			roaming = true;
		} else if ((!r) && (roaming)) {
			sendNotification("roamingON", false);
			roaming = false;
		}
	}

}
