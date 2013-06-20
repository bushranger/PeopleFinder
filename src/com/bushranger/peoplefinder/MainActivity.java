package com.bushranger.peoplefinder;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.SelectRequest;
import com.amazonaws.services.simpledb.model.SelectResult;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
private final String Tag2="Main Activity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button playButton = (Button) findViewById(R.id.call_button);
	    playButton.setVisibility(View.INVISIBLE);
		//new PopulateHighScoresTask().execute();
		Log.i(Tag2,"And I'm outta there");
		Button myButton=(Button)findViewById(R.id.searchButton);
		
		
			//sdbClient.createDomain( cdr );

	}
	public void cont(View v)
	{
	  Intent intent=new Intent(this,List_display.class);
	  startActivity(intent);
	}
	public void sea(View v){
		EditText ed=(EditText)findViewById(R.id.id1);
		String s=ed.getText().toString();
		Paramn ob=new Paramn();
		ob.execute(s);
		/*TextView v1=(TextView)findViewById(R.id.textView1);
		TextView v2=(TextView)findViewById(R.id.textView2);
		TextView v3=(TextView)findViewById(R.id.textView3);
		TextView v4=(TextView)findViewById(R.id.textView4);
		TextView v5=(TextView)findViewById(R.id.textView5);
		v1.setText(ob2.id);
		v2.setText(ob2.name);
		v3.setText(ob2.desi);
		v4.setText(ob2.ph);
		v5.setText(ob2.email);*/
	}
	public void call(View v) {
	    try {
	        Intent callIntent = new Intent(Intent.ACTION_CALL);
	        TextView vx=(TextView)findViewById(R.id.textView4);
	        String s=vx.getText().toString();
	        s="tel:"+s;
	        
	        callIntent.setData(Uri.parse(s));
	        startActivity(callIntent);
	        //s="";
	    } catch (Exception e) {
	         Log.e("helloandroid dialing example", "Call failed", e);
	    }
	}
	public void pubo(List<Param> ob3)
	{
		TextView v1=(TextView)findViewById(R.id.textView1);
		TextView v2=(TextView)findViewById(R.id.textView2);
		TextView v3=(TextView)findViewById(R.id.textView3);
		TextView v4=(TextView)findViewById(R.id.textView4);
		TextView v5=(TextView)findViewById(R.id.textView5);
		Log.i("Dash", "Aa gaya main yahan ()Y");
		for ( Param ob2 : ob3 ) {
		v1.setText(ob2.id);
		v2.setText(ob2.name);
		v3.setText(ob2.desi);
		v4.setText(ob2.ph);
		v5.setText(ob2.email);
		
		}
		Button playButton = (Button) findViewById(R.id.call_button);
	    playButton.setVisibility(View.VISIBLE);
	}
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}








 private class Paramn extends AsyncTask<String, Void, List<Param>>{
	 private static final String APP_DOMAIN = "peoplefinder";
		private static final String ID_ATTRIBUTE = "id";
		private static final String NAME_ATTRIBUTE = "name";
		private static final String DES_ATTRIBUTE = "des";
		private static final String PHONE_ATTRIBUTE = "phone";
		private static final String EMAIL_ATTRIBUTE = "email";
		protected Param wht;
		protected AmazonSimpleDBClient sdbClient;
public String id,name,desi,ph,email;
protected List<Param> doInBackground(String... params) {
	String ids=params[0];

	
	AWSCredentials credentials = new BasicAWSCredentials( Constants.ACCESS_KEY_ID, Constants.SECRET_KEY );
   this.sdbClient = new AmazonSimpleDBClient( credentials);
   //GetAttributesRequest gar = new GetAttributesRequest( APP_DOMAIN, ids );
	//GetAttributesResult response = this.sdbClient.getAttributes(gar);
	SelectRequest selectRequest = new SelectRequest( "select * from peoplefinder where name like'"+ids+"%'" ).withConsistentRead( true );
	SelectResult response2 = this.sdbClient.select( selectRequest );
	 List<Item> items=response2.getItems();
	 int c=items.size();
	 String h="Yeah these many"+c;
	 Log.i("deva",h);
	 List<Param> scores = new ArrayList<Param>( items.size() );
	 
	 for ( Item item : items ) {
		 wht=new Param();
	 List<Attribute> attributes=item.getAttributes();
	for ( Attribute attribute : attributes ) {
		if ( attribute.getName().equals( ID_ATTRIBUTE ) ) {
			wht.id=attribute.getValue();
		}
	}
	for ( Attribute attribute : attributes ) {
		if ( attribute.getName().equals( NAME_ATTRIBUTE ) ) {
			wht.name=attribute.getValue();
		}
	}
	for ( Attribute attribute : attributes ) {
		if ( attribute.getName().equals( DES_ATTRIBUTE ) ) {
			wht.desi=attribute.getValue();
		}
	}
	for ( Attribute attribute : attributes ) {
		if ( attribute.getName().equals( PHONE_ATTRIBUTE ) ) {
			wht.ph=attribute.getValue();
		}
	}
	for ( Attribute attribute : attributes ) {
		if ( attribute.getName().equals( EMAIL_ATTRIBUTE ) ) {
			wht.email=attribute.getValue();
		}
	}
	scores.add(wht);
	 }
	return scores;
	
}
protected void onPostExecute(List<Param> obs){
	pubo(obs);
	
}

}
}

