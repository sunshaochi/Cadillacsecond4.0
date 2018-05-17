package cadillac.example.com.cadillac.fragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cadillac.example.com.cadillac.adapter.CaldroidSampleCustomAdapter;
import cadillac.example.com.cadillac.bean.Reportbean;
import cadillac.example.com.cadillac.bean.StateBean;
import cadillac.example.com.cadillac.view.caldroid.CaldroidFragment;
import cadillac.example.com.cadillac.view.caldroid.CaldroidGridAdapter;

public class CaldroidSampleCustomFragment extends CaldroidFragment {
	private List<StateBean>list=new ArrayList<>();
	@Override
	public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
		return new CaldroidSampleCustomAdapter(getActivity(),month,year,getCaldroidData(),extraData);
	}
}