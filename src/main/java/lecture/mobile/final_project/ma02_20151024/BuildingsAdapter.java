package lecture.mobile.final_project.ma02_20151024;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BuildingsAdapter extends ArrayAdapter {
    private Context context;
    private int layout;
    private ArrayList<Gogung> gogungDataList;
    private LayoutInflater layoutInflater;

    public BuildingsAdapter(Context context, int layout, ArrayList<Gogung> gogungDataList) {
        super(context, layout, gogungDataList);
        this.context = context;
        this.layout = layout;
        this.gogungDataList = gogungDataList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gogungDataList.size();
    }

    @Override
    public Object getItem(int pos) {
        return gogungDataList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return gogungDataList.get(pos).get_id();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);
        }

        TextView textNo = (TextView)view.findViewById(R.id.textView_no);
        TextView textName = (TextView)view.findViewById(R.id.textView_name);

        textNo.setText(String.valueOf(pos + 1));
        textName.setText(gogungDataList.get(pos).getContents());

        return view;
    }
}
