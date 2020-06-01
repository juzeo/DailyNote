package com.example.middleexam;

        import android.content.Context;
        import android.content.res.Resources;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;

        import org.w3c.dom.Text;

        import java.util.ArrayList;

public class Adapter extends ArrayAdapter<ThemeList> {
    Context context;
    int resId;
    private ArrayList<ThemeList> ThemeArrayList;



    public Adapter(Context context, int resId, ArrayList<ThemeList>ThemeArrayList){
        super(context,resId);
        this.context=context;
        this.resId=resId;
        this.ThemeArrayList=ThemeArrayList;
    }

    @Override
    public int getCount(){
        return ThemeArrayList.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(resId,null);
            DriveHolder holder=new DriveHolder(convertView);
            convertView.setTag(holder);
        }
        DriveHolder holder=(DriveHolder)convertView.getTag();

        TextView data=holder.data;
        TextView theme=holder.theme;

        final  ThemeList day=ThemeArrayList.get(pos);

        data.setText(day.data);
        theme.setText(day.theme);


        return convertView;


    }
}
