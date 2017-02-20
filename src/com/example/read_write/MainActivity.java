package com.example.read_write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	EditText ed_Text;
	EditText ed_content;
	Button b_read;
	Button b_write;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = getPreferences(MODE_PRIVATE).getString("fpath", "/sdcard/nav_file1"); 
        ed_Text = (EditText) findViewById(R.id.ed_Text);
        ed_Text.setText(path);
        ed_content = (EditText) findViewById(R.id.ed_content);
        b_read = (Button) findViewById(R.id.b_read);
        b_write= (Button) findViewById(R.id.b_write);
  
    OnClickListener saveClickListener = new OnClickListener() {			
		@Override
		public void onClick(View v) {
			File file = new File(ed_Text.getText().toString());
			FileWriter writer=null;
			try {
				writer = new FileWriter(file);
				
				/** Saving the contents to the file*/
				writer.write(ed_content.getText().toString());
				
				/** Closing the writer object */
				writer.close();
				
				
				/** Getting sharedpreferences object to store the path of last saved file */
				SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
				
				/** Setting the last saved file's path in the shared preference */
		        editor.putString("fpath", file.getPath());
		        
		        /** Save the path to the shared preference */
		        editor.commit();
				
				Toast.makeText(getBaseContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
	
	
	/** Defining click listener event for the button btn_save 
     * of the layout activity_main
     * */
    OnClickListener readClickListener = new OnClickListener() {			
		@Override
		public void onClick(View v) {
			File file = new File(ed_Text.getText().toString());
							
			String strLine="";
			StringBuilder text = new StringBuilder();
			
			try {
				FileReader fReader = new FileReader(file);
				BufferedReader bReader = new BufferedReader(fReader);
				
				/** Reading the contents of the file , line by line */
				while( (strLine=bReader.readLine()) != null  ){
					text.append(strLine+"\n");						
				}
				
				Toast.makeText(getBaseContext(), "Successfully loaded", Toast.LENGTH_SHORT).show();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			ed_content.setText(text);
		}
	};
	 
      b_read.setOnClickListener(readClickListener);
      b_write.setOnClickListener(saveClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
