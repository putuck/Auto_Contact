package rajiv.ekansh.autocontact

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class UserSignupActivity : AppCompatActivity() {
    private lateinit var mobileNoText: EditText
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_form)
        mobileNoText = findViewById(R.id.text_mobile)
        firstName = findViewById(R.id.text_firstName)
        lastName = findViewById(R.id.text_lastName)

    }

    fun submitForm(view: View) {
        Log.i("Ekansh", "Submit Button")

        verifyMobileNumber()
        /*auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userData: MutableMap<String, String?> = HashMap()
                    val userId = user!!.uid
                    userData["userId"] = user?.uid
                    userData["firstName"] = firstName.toString()
                    userData["lastName"] = lastName.toString()
                    userData["mobileNo"] = mobileNo.toString()

                    db.collection("users")
                        .document(userId)
                        .set(userData)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Ekansh", "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }

                // ...
            }*/



    }

    private fun verifyMobileNumber(){
        var storedVerificationId: String? = null
        val mobileNo = mobileNoText.text
        val firstName = firstName.text
        val lastName = lastName.text
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Log.d("Ekansh", "onVerificationCompleted:$credential")
                // [START_EXCLUDE silent]
                //verificationInProgress = false
                //val code = credential.smsCode
                //val credential1 = PhoneAuthProvider.getCredential(storedVerificationId!!, code!!)
                auth.signInAnonymously()
                    .addOnCompleteListener(this@UserSignupActivity) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            val userData: MutableMap<String, String?> = HashMap()
                            val userId = user!!.uid
                            userData["userId"] = user?.uid
                            userData["firstName"] = firstName.toString()
                            userData["lastName"] = lastName.toString()
                            userData["mobileNo"] = mobileNo.toString()

                            db.collection("users")
                                .document(userId)
                                .set(userData)

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Ekansh", "signInAnonymously:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }

            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w("Ekansh", "onVerificationFailed", e)
                // [START_EXCLUDE silent]
                //verificationInProgress = false
                // [END_EXCLUDE]


                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request

                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]

                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]

                // [END_EXCLUDE]
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("Ekansh", "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                //resendToken = token


            }
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mobileNo.toString(), // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks


    }
}
