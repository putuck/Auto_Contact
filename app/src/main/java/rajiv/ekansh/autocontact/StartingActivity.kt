package rajiv.ekansh.autocontact

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class StartingActivity : AppCompatActivity() {
     private lateinit var auth: FirebaseAuth
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_starting)
         auth = FirebaseAuth.getInstance()
         if(auth.currentUser == null) {
             val intentUserSignup = Intent(this, UserSignupActivity::class.java)
             startActivity(intentUserSignup)
         }
         else{
             val intentMainActivity = Intent(this, MainActivity::class.java)
             startActivity(intentMainActivity)
         }
     }
}
