

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_body_indexes.*


class BodyIndexes : AppCompatActivity() {

    private var activities = 0 // value will change when user clicked an activity level and we will increase to bmr up-to activity level
    private var idealBmr : Double = 0.0 // ideal bmr is up-to activity of user will incrase and will show daily ideal calorie needs.
    private var gender  = 0 // variable will change when user clicked gender
    private var bmr: Double = 0.0 // value is will show minimum calorie needs per day that will calculate up-to gender of user
    private val idealBmi : Double = 23.0 // ideal healthy Bodyy mass index for everyone.
    private var userAge : Int? = 0 // nullable integer variable for get user age.
    private  var userWeight : Int? = 0 // nullable variable for get user body weight
    private var userLenght : Int? = 0 // nullable variable for get user lenght with "CM"

    // all variables is equal to "0" for block to empty field from user


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_indexes)
    }

    fun getGender(view: View){
        if (view is RadioButton){ // will check user has check gender or not
            var genders = view.isChecked
            // radio button groups for gender assigned to variable

            when(view.getId()){ // will work an button when clicked

                R.id.femaleCheck ->
                    if (genders){ // if user clicked female button gender value will be 1
                        gender = 1
                        println(gender)
                    }
                R.id.maleCheck ->
                    if (genders){
                        gender = 2
                        println(gender)
                    }

            }

        }
    }


    // activity level of user will assign to following codes
    fun getActivity (view: View){

        if (view is RadioButton){
            var activityLevel = view.isChecked
           // here will assign to activity level to an variable by radio buttons

            when (view.getId()){ // when user cliced an activity level

                R.id.sedanter ->
                    if (activityLevel){
                        activities = 1 // activity level if sedanter clicked value will be 1
                    }
                R.id.activeNoTraining ->
                    if (activityLevel){
                        activities = 2 // activity level when clicked active but not trainer value will be 2
                    }

                R.id.activeTrainer ->
                    if (activityLevel){
                        activities = 3 // when clicked active and trainer button value will be 3
                    }


            }


        }




    }



    fun calculate(view : View){

        userAge = ageText.text.toString().toIntOrNull() // user age will change by user on textView
        userWeight = weightText.text.toString().toIntOrNull() // user body weight will change by user on textView
        userLenght = lenghtText.text.toString().toIntOrNull() // user lenght will change by user on textView


        if (userAge == null || userWeight == null || userLenght == null){
            Toast.makeText(applicationContext, "Fields are cannot Left Empty!!", Toast.LENGTH_LONG).show()

        }


        // if statement for control to any values on textViews or Not!
        // if user has no entered any value and user clict to calculate button =>

        else if ( activities == 0 || gender == 0 || userAge!! < 10 || userWeight!! < 35 || userLenght!! < 100) {
            Toast.makeText(applicationContext, "Age must be 10+, Weight must be 35+, Lenght must be 100+!!!", Toast.LENGTH_LONG).show()

        }



        else {
            //BMI (BODY MASS INDEX) CALCULATION MATH
            // bmi will calculate =>
            val lenght2 : Double = userLenght!!/100.0 * userLenght!!/100.0 // user lenght will assign from cm to metets ( like 1.57m)
            val bmi : Double = userWeight!!.div(lenght2) // body mass calculation
            val bmiRes = Math.round(bmi*100.0) / 100.0 // body mass index 2 desimal point assigment


            //IDEAL BODY WEIGHT CALCULATION MATH
            // ibw will calculate =>
            val bmiDifference : Double = bmiRes - idealBmi // will assing to difference of user body mass index to ideal body mass index
            val loseFact : Double = bmiDifference * lenght2 // lose factor is will asssign to user how many value must lose weight or must gain
            val idealBodyWeight : Double = userWeight!! - loseFact // lose weight or gainweight value will sub from user weignt
            val IBW : Double = Math.round(idealBodyWeight * 100.0) / 100.0 // ideal body weight with kg adn 2 desimal point assigned



            // Basal Metabolic Rate will find following codes
            if (gender == 1) { // when user is woman
                bmr = 655.1 + (9.563 * userWeight!!) + (1.85 * userLenght!!) - (4.676 * userAge!!)
            }

            if (gender == 2){ // when user is man
                bmr = 66.47 + (13.75 * userWeight!!) + (5.003 * userLenght!!) - (6.755 * userAge!!)

            }

            // minimum bmr value will assing as double as .2 desimal point
            var bmrRes = Math.round(bmr * 100.0) / 100.0


            // ideal basal metabolic rate will assign following codes
            if (activities == 1){ // when user clicked sedanter and office work button
                idealBmr = bmrRes + 225.7 // ideal bmr will increase to 250 calorie over basic bmr valu
            }

            if(activities == 2){ // when user clicked active but not trainer option
                idealBmr = bmrRes + 320.9 // ideal bmr will increase 300 calories
            }

            if(activities == 3){ // when user clicked active and trainer option
                idealBmr = bmrRes + 510.4 // ideal bmr will increase 400 calories
            }

            val idealBmrRes = Math.round(idealBmr * 100.0) / 100.0 // idel bmr value will asssign as 2 desimal point


            // values will put inside to an INTENT
            // Intent object will carry up values to next page by CALCULATE button =>
            var pick_up = Intent(applicationContext, BodyIndexesResult::class.java)
            pick_up.putExtra("ibw", IBW)
            pick_up.putExtra("bmi", bmiRes)
            pick_up.putExtra("bmr", bmrRes)
            pick_up.putExtra("idealBmrRes", idealBmrRes)
            startActivity(pick_up)

        }


    }





}