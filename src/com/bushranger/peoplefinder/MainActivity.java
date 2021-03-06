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
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MainActivity extends Activity {
	ListView listView;
private final String Tag2="Main Activity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button playButton = (Button) findViewById(R.id.call_button);
	    playButton.setVisibility(View.INVISIBLE);
		
		
		Button myButton=(Button)findViewById(R.id.searchButton);
		

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
		
	}
	public void call(View v) {
	    try {
	        Intent callIntent = new Intent(Intent.ACTION_CALL);
	       
	       String s="abc";
	        s="tel:"+s;
	        
	        callIntent.setData(Uri.parse(s));
	        startActivity(callIntent);
	       
	    } catch (Exception e) {
	         Log.e("helloandroid dialing example", "Call failed", e);
	    }
	}
	public void pubo(List<Param> ob3)
	{
		int i=ob3.size();int j=0;
		String arr[]=new String[i];
		 listView=(ListView)findViewById(R.id.listView);
		
		for ( Param ob2 : ob3 ) {
			arr[j]=ob2.name+"                              "+ob2.ph;j++;
		
		
		}
		ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,R.layout.list_element,R.id.list_item,arr);
		listView.setAdapter(adapter);
	    final SwipeDetector swipeDetector = new SwipeDetector();
	    listView.setOnTouchListener(swipeDetector);
		
	    listView.setOnItemClickListener(new OnItemClickListener() {


	        @Override
	        public void onItemClick(AdapterView <? > parent, View view,
	            int position, long id) {

	            if (swipeDetector.swipeDetected()) {
	            	TextView listText=(TextView)view.findViewById(R.id.list_item);
            		String num=listText.getText().toString();
            		String s="";String s2="";
            		int len=num.length();
            		for(int i=0;i<len;i++)
            		{
            			
            			char z=num.charAt(i);
            			if((z==' ')&&(num.charAt(i-1)==' '))
            					{
            				s2=num.substring(0,i-1);
            					}
            			if((z=='0')||(z=='1')||(z=='2')||(z=='3')||(z=='4')||(z=='5')||(z=='6')||
            					(z=='7')||(z=='8')||(z=='9'))//||(z=='7)||(z=='8')||(z=='9'))
            			{s=num.substring(i);break;}
            		}

	                if (swipeDetector.getAction() == SwipeDetector.Action.LR) {

	                   
	                	
	                	Log.i("Dash", "Aa gaya main yahan3 (inside if)");
	            			            		
	            		 try {
	            		        Intent callIntent = new Intent(Intent.ACTION_CALL);
	            		       s="tel:"+s;
	            		        
	            		        callIntent.setData(Uri.parse(s));
	            		        startActivity(callIntent);
	            		        
	            		    } catch (Exception e) {
	            		         Log.e("helloandroid dialing example", "Call failed", e);
	            		    }
	            		 
	            		 
	            		 
	            		 

	                }
	                if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
	                	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
	                    int rawContactInsertIndex = ops.size();

	                    ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
	                            .withValue(RawContacts.ACCOUNT_TYPE, null)
	                            .withValue(RawContacts.ACCOUNT_NAME, null).build());
	                    ops.add(ContentProviderOperation
	                            .newInsert(Data.CONTENT_URI)
	                            .withValueBackReference(Data.RAW_CONTACT_ID,rawContactInsertIndex)
	                            .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
	                            .withValue(StructuredName.DISPLAY_NAME, s2) // Name of the person
	                            .build());
	                    ops.add(ContentProviderOperation
	                            .newInsert(Data.CONTENT_URI)
	                            .withValueBackReference(
	                                    ContactsContract.Data.RAW_CONTACT_ID,   rawContactInsertIndex)
	                            .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
	                            .withValue(Phone.NUMBER, s) // Number of the person
	                            .withValue(Phone.TYPE, Phone.TYPE_MOBILE).build()); // Type of mobile number                    
	                    try
	                    {
	                        ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
	                        Toast.makeText(getApplicationContext(), "Contact added", Toast.LENGTH_SHORT).show();
	                    }
	                    catch (RemoteException e)
	                    { 
	                        // error
	                    }
	                    catch (OperationApplicationException e) 
	                    {
	                        // error
	                    }  
	                  
	                }
	            }
	        }
	    });
		
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
   
	SelectRequest selectRequest = new SelectRequest( "select * from peoplefinder where name like'"+ids+"%'" ).withConsistentRead( true );
	SelectResult response2 = this.sdbClient.select( selectRequest );
	 List<Item> items=response2.getItems();
	 int c=items.size();
	 String h="Yeah these many"+c;
	
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

