/*
 * Copyright (C) 2011 Prasanta Paul, http://prasanta-paul.blogspot.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shyam.rollcall.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.pras.auth.Authenticator;


public class AndroidAuthenticator implements Authenticator {

	private final String TAG = "AndroidAuthenticator";
	Activity activity;
	AccountManager manager;
	private String mService = null;
	private String auth_token = "";
	
	public AndroidAuthenticator(Activity activity){
		this.activity = activity;
		manager = AccountManager.get(activity.getApplicationContext());
	}
	
	public String getAuthToken(String service) 
	{
		if(service == null){
			throw new IllegalAccessError("No Service name defined, Can't create Auth Token...");
		}
		
		if(mService != null && !mService.equals(service)){
			// Reset previous Token
			manager.invalidateAuthToken("com.google", auth_token);
		}
			
		Account[] acs = manager.getAccountsByType("com.google");
		Log.i(TAG, "Num of Matching account: "+ acs.length);
		
		if(acs == null || acs.length == 0){
			Toast.makeText(this.activity.getApplicationContext(), "No Google Account Added...", Toast.LENGTH_LONG).show();
			return "";
		}
		
		for(int i=0; i<acs.length; i++){
			if(acs[i].type.equals("com.google"))
			{
				// The first Gmail Account will be selected
				Log.i(TAG, "Selected Google Account "+ acs[i].name);
				AccountManagerFuture result = (AccountManagerFuture)(manager.getAuthToken(acs[i], service, null, activity, null, null));
				
				try{
					Bundle b = (Bundle)result.getResult();
					auth_token = b.getString(AccountManager.KEY_AUTHTOKEN);
					Log.i(TAG, "Auth_Token: "+ auth_token);
					return auth_token;
				}catch(Exception ex){
					Log.i(TAG, "Error: "+ ex.toString());
				}
			}
		}
		Log.i(TAG, "Problem in getting Auth Token...");
		return "";
	}
}
