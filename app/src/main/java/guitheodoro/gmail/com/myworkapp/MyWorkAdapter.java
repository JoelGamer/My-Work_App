package guitheodoro.gmail.com.myworkapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class MyWorkAdapter extends ArrayAdapter<MyWorkAdapter>{
    private Context context;
    private ArrayList<String> nData;
    private ArrayList<Bitmap> iData;
    private int lblNome;
    private int imgEmp;

    public MyWorkAdapter(@NonNull Context context, int resource, int lblNome, int imgEmp, ArrayList<String> nData, ArrayList<Bitmap> iData) {
        super(context, resource);
        this.context = context;
        this.nData = nData;
        this.iData = iData;
        this.lblNome = lblNome;
        this.imgEmp = imgEmp;
    }

    public Context getContext(){
        return context;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public ArrayList<String> getnData(){
        return nData;
    }

    public void setnData(ArrayList<String> nData){
        this.nData = nData;
    }

    public ArrayList<Bitmap> getiData(){
        return iData;
    }

    public void setiData(ArrayList<Bitmap> iData){
        this.iData = iData;
    }

    public int getlblNome(){
        return lblNome;
    }

    public void setlblNome(int lblNome){
        this.lblNome = lblNome;
    }

    public int getimgEmp() {
        return imgEmp;
    }

    public void setimgEmp(int imgEmp) {
        this.imgEmp = imgEmp;
    }
}
