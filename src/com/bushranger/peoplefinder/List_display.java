package com.bushranger.peoplefinder;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
public class List_display extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_display);
	}

	public void buttClicked(View v)
	{
		Param obj=new Param();

		EditText x1=(EditText) findViewById(R.id.personIn);
		EditText x2=(EditText) findViewById(R.id.NameIn);
		EditText x3=(EditText) findViewById(R.id.DesignationIn);
		EditText x4=(EditText) findViewById(R.id.PhoneIn);
		EditText x5=(EditText) findViewById(R.id.EmailIn);
        obj.id=x1.getText().toString();
        obj.name=x2.getText().toString();
        obj.desi=x3.getText().toString();
        obj.ph=x4.getText().toString();
        obj.email=x5.getText().toString();
        new AddContactMethod().execute(obj);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_display, menu);
		return true;
	}

}

 class AddContactMethod extends AsyncTask<Param, Void, Void> {
	 //String message="Hello baba";String str;String str2="";
	 private final String TAG="Pop ";
	 private static final String APP_DOMAIN = "peoplefinder";
		private static final String ID_ATTRIBUTE = "id";
		private static final String NAME_ATTRIBUTE = "name";
		private static final String DES_ATTRIBUTE = "des";
		private static final String PHONE_ATTRIBUTE = "phone";
		private static final String EMAIL_ATTRIBUTE = "email";
		protected AmazonSimpleDBClient sdbClient;
		protected Void doInBackground(Param... params) {
			Param s=params[0];
			AWSCredentials credentials = new BasicAWSCredentials( Constants.ACCESS_KEY_ID, Constants.SECRET_KEY );
		    this.sdbClient = new AmazonSimpleDBClient( credentials); 
           Log.i(TAG,"after creating client");
		    CreateDomainRequest cdr = new CreateDomainRequest( APP_DOMAIN );
			this.sdbClient.createDomain( cdr );
			
			
			ReplaceableAttribute idAttribute = new ReplaceableAttribute( ID_ATTRIBUTE,s.id,Boolean.TRUE );
			ReplaceableAttribute nameAttribute = new ReplaceableAttribute( NAME_ATTRIBUTE,s.name,Boolean.TRUE );
			ReplaceableAttribute desAttribute = new ReplaceableAttribute( DES_ATTRIBUTE,s.desi,Boolean.TRUE );
			ReplaceableAttribute phoneAttribute = new ReplaceableAttribute( PHONE_ATTRIBUTE,s.ph,Boolean.TRUE );
			ReplaceableAttribute emailAttribute = new ReplaceableAttribute( EMAIL_ATTRIBUTE,s.email,Boolean.TRUE );

			
			List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>(5);
			attrs.add( idAttribute );
			attrs.add( nameAttribute );
			attrs.add( desAttribute );
			attrs.add( phoneAttribute );
			attrs.add( emailAttribute );

			
			PutAttributesRequest par = new PutAttributesRequest( APP_DOMAIN, s.id, attrs);		
			try {
				this.sdbClient.putAttributes( par );
			}
			catch ( Exception exception ) {
				System.out.println( "EXCEPTION = " + exception );
			}
			
           Log.i(TAG,"after putting");

			



              // Log.i(TAG,);

         

			return null;
		}

		protected void onPostExecute(Void result) {

		}
	}
