package cs490.git.labCheckUI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.io.*;



import cs490.git.labCheckUI.LabCheckUI2.EfficientAdapter;
import cs490.git.labCheckUI.LabCheckUI2.EfficientAdapter.ViewHolder;

//import cs490.git.labCheckUI2.LabCheckUIActivity.EfficientAdapter;

public class LabCheckUIActivity extends ListActivity {

	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setListAdapter(new EfficientAdapter(this));

	        // Request the progress bar to be shown in the title
	        //requestWindowFeature(Window.FEATURE_PROGRESS);
	       // setContentView(R.layout.progressbar_1);
	       // setProgressBarVisibility(true);
	        
	        //final ProgressBar progressHorizontal = (ProgressBar) findViewById(R.id.progress_horizontal);
	        //setProgress(progressHorizontal.getProgress() * 100);
	        //setSecondaryProgress(progressHorizontal.getSecondaryProgress() * 100);
	        
	  
	        
	    }
}