package com.forcepermission

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.Manifest

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    val r = PermissionChecker(this, Manifest.permission.ACCESS_COARSE_LOCATION)

    val cameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){it ->
        if(it){
           toast("permnission granted")
        }else{
            toast( "No permnission")
        }
    }

    val myPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){isGranted->
        when{
            isGranted -> toast("CONCEDIDO")
            else -> toast("DENEGADO")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //        .setAction("Action", null).show()
            r.runWithPermission()
            //cameraPermission.launch(Manifest.permission.CAMERA)
            //myPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class PermissionChecker(
    val context : ComponentActivity,
    val permiso: String
)
{

    val myPermission = context.registerForActivityResult(
        ActivityResultContracts.RequestPermission()){isGranted->
        when{
            isGranted -> context.toast("CONCEDIDO")
            context.shouldShowRequestPermissionRationale(permiso) -> context.toast("DAME EL PERMISO JODER")
            else -> runWithPermission()
        }
    }

    fun runWithPermission()
    {
        myPermission.launch(permiso)
    }

}