/*
MIT License

Copyright (c) 2016 Ashar Khan

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.softminds.matrixcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ViewCreatedMatrix extends AppCompatActivity {

    ViewMatrixFragment viewMatrixFragment = new ViewMatrixFragment();
    Menu ActionMenu;
    final int ChangedOrder=12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isDark=preferences.getBoolean("DARK_THEME_KEY",false);
        if(isDark)
            setTheme(R.style.AppThemeDark_NoActionBar);
        else
            setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_created_matrix);
        Toolbar actionBar = (Toolbar) findViewById(R.id.viewAction);
        int index =getIntent().getExtras().getInt("INDEX",-1);
        if(index!=(-1))
            actionBar.setTitle(((GlobalValues)getApplication()).GetCompleteList().get(index).GetName());
        else
            actionBar.setTitle("Error");
        actionBar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(actionBar);

        viewMatrixFragment.setArguments(getIntent().getExtras());
        if(savedInstanceState==null){
            FragmentTransaction  transaction= getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.FragmentContainer,viewMatrixFragment,"VIEW_TAG").commit();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.onclick_view,menu);
        ActionMenu=menu;
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        EditFragment t=new EditFragment();
        int index =getIntent().getExtras().getInt("INDEX",-1);
        switch (item.getItemId())
        {
            case R.id.EditEntered:

                t.setArguments(getIntent().getExtras());
                FragmentTransaction FragmentTR=getSupportFragmentManager().beginTransaction();
                FragmentTR.replace(R.id.FragmentContainer,t,"FRAGMENTEDIT");
                FragmentTR.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                FragmentTR.commit();
                ActionMenu.findItem(R.id.EditEntered).setVisible(false);
                ActionMenu.findItem(R.id.OrderChange).setVisible(false); //Todo Changing is Causing Carsh
                ActionMenu.findItem(R.id.DeleteCreated).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                ActionMenu.findItem(R.id.DeleteCreated).setTitle(R.string.Delete2);
                ActionMenu.findItem(R.id.ConfirmChanges).setVisible(true);
                ActionMenu.findItem(R.id.RevertChanges).setVisible(true);
                return  true;
            case R.id.DeleteCreated :
                if(index!=(-1))
                {
                    ((GlobalValues)getApplication()).GetCompleteList().remove(index);
                    ((GlobalValues)getApplication()).matrixAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),R.string.Deleted,Toast.LENGTH_SHORT).show();
                finish();
                }
                return true;
            case R.id.OrderChange :
                Intent intent4=new Intent(getApplicationContext(),OrderChanger.class);
                intent4.putExtra("INDEX_OF_SELECTED_MATRIX",index);
                startActivityForResult(intent4,ChangedOrder);
                return true;
            case R.id.Rename :
                Intent intent =new Intent(getApplicationContext(),RenameCreated.class);
                intent.putExtra("TITLE_OF_THIS_FORMATION",((GlobalValues)getApplication()).GetCompleteList().get(index).GetName());
                startActivityForResult(intent,100);
                return  true;
            case R.id.RevertChanges:
                FragmentTransaction transaction1= getSupportFragmentManager().beginTransaction();
                Fragment fg;
                fg=getSupportFragmentManager().findFragmentByTag("FRAGMENTEDIT");
                transaction1.detach(fg);
                transaction1.attach(fg);
                transaction1.commit();

                Toast.makeText(getApplicationContext(),R.string.RevertSuccess,Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ConfirmChanges:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.detach(t);
                transaction.commit();
                finish();
                Toast.makeText(getApplicationContext(),R.string.ConfirmSuccess,Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestcode,int resultCode,Intent data)
    {
        super.onActivityResult(requestcode,resultCode,data);
        if(resultCode==100)
        {
            int index =getIntent().getExtras().getInt("INDEX",-1);
            if(index!=(-1))
            {
                ((GlobalValues)getApplication()).GetCompleteList().get(index).
                        SetName(data.getStringExtra("NEW_NAME_FOR_THIS_MATRIX"));
                ((GlobalValues)getApplication()).matrixAdapter.notifyDataSetChanged();
                finish();
            }


        }
        if(resultCode==ChangedOrder)
        {
            Toast.makeText(getApplication(),R.string.ChangedOrder,Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
