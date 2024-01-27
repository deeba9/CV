package pk.edu.pucit.cv


import android.app.DatePickerDialog;
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pk.edu.pucit.cv.databinding.ActivityMainBinding
import java.util.Calendar

data class CVData(
    val rollNumber: String,
    val name: String,
    val cgpa: String,
    val degree: String,
    val gender: String,
    val academiaInterest: String,
    val businessInterest: String,
    val industryInterest: String,
    val dateOfBirth: String
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cvRecyclerView: RecyclerView
    private lateinit var adapter: CVDataAdapter
    private var selectedDate: String = ""
    private val degrees = arrayOf("Bachelor's", "Master's", "Ph.D.") // Your degree options
    private lateinit var databaseHelper: CVDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        databaseHelper = CVDatabaseHelper(this)

        cvRecyclerView = findViewById(R.id.cvRecyclerView)
        adapter = CVDataAdapter(emptyList()) // Initialize with an empty list for now
        cvRecyclerView.layoutManager = LinearLayoutManager(this)
        cvRecyclerView.adapter = adapter

// Load data from the database and set it in the adapter
        adapter.updateData(databaseHelper.getAllCVs())
        binding.createCv.setOnClickListener {
            try {
                val rollNumber = binding.rollNumber.text.toString()
                val name = binding.name.text.toString()
                val cgpa = binding.cgpa.text.toString()
                val selectedGender = when (binding.genderGroup.checkedRadioButtonId) {
                    binding.male.id -> "Male"
                    binding.female.id -> "Female"

                    else -> "Other"
                }
                val degreeSpinner = binding.degreeSpinner
                val selectedItem = degreeSpinner.selectedItem.toString()
                val selectedAcademiaInterest =
                    if (binding.academia.isChecked) "Interested" else "Not Interested"
                val selectedBusinessInterest =
                    if (binding.business.isChecked) "Interested" else "Not Interested"
                val selectedIndustryInterest =
                    if (binding.industry.isChecked) "Interested" else "Not Interested"
                val datePicker = binding.dateOfBirth
                selectedDate = datePicker.dayOfMonth.toString() + "/" + (datePicker.month + 1) + "/" + datePicker.year
                val cvData = CVData(
                    rollNumber = rollNumber,
                    name = name,
                    cgpa = cgpa,
                    gender = selectedGender,
                    degree = selectedItem,
                    academiaInterest = selectedAcademiaInterest,
                    industryInterest = selectedIndustryInterest,
                    businessInterest = selectedBusinessInterest,
                    dateOfBirth = selectedDate
                )

                // Add the new CV data to the database
                databaseHelper.addCV(cvData)

                // Update the adapter with the new data
                adapter.updateData(databaseHelper.getAllCVs())

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        binding.delete.setOnClickListener {
            val db = CVDatabaseHelper(this)

            // Delete all CVs from the database
            db.deleteAllCVs()

            // Perform UI refresh or provide user feedback as needed
            // For example, you can update the adapter with an empty list to clear the RecyclerView:
            adapter.updateData(emptyList())

            // Show a Toast message to inform the user that all CVs have been deleted
            Toast.makeText(this, "All CVs have been deleted", Toast.LENGTH_SHORT).show()
        }
    }
}
