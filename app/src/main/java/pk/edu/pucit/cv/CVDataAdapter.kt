package pk.edu.pucit.cv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CVDataAdapter(private var cvDataList: List<CVData>) :
    RecyclerView.Adapter<CVDataAdapter.CvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CvViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cv, parent, false)
        return CvViewHolder(view)
    }

    override fun onBindViewHolder(holder: CvViewHolder, position: Int) {
        val cvData = cvDataList[position]

        // Bind CV data to the views in the ViewHolder
        holder.rollNumberTextView.text = "Roll Number: ${cvData.rollNumber}"
        holder.nameTextView.text = "Name : ${cvData.name}"
        holder.cgpaTextView.text = "CGPA: ${cvData.cgpa}"
        holder.degreeTextView.text ="Degree: ${cvData.degree}"
        holder.genderTextView.text ="Gender: ${cvData.gender}"
        holder.dateOfBirthTextView.text = "Date of Birth: ${cvData.dateOfBirth}"
        holder.academiaInterestTextView.text = "Academia Interest: ${cvData.academiaInterest}"
        holder.businessInterestTextView.text = "Business Interest: ${cvData.businessInterest}"
        holder.industryInterestTextView.text = "Industry Interest: ${cvData.industryInterest}"
    }

    override fun getItemCount(): Int {
        println( cvDataList.size)
        return cvDataList.size
    }

    inner class CvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rollNumberTextView: TextView = itemView.findViewById(R.id.rollNumberTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val cgpaTextView: TextView = itemView.findViewById(R.id.cgpaTextView)
        val degreeTextView: TextView = itemView.findViewById(R.id.degreeTextView)
        val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        val academiaInterestTextView: TextView = itemView.findViewById(R.id.academiaInterestTextView)
        val industryInterestTextView: TextView = itemView.findViewById(R.id.industryInterestTextView)
        val businessInterestTextView: TextView = itemView.findViewById(R.id.businessInterestTextView)
        val dateOfBirthTextView: TextView = itemView.findViewById(R.id.dateOfBirthTextView)

        // Add similar views for other data fields
    }

    // Method to update the data in the adapter
    fun updateData(newData: List<CVData>) {
        cvDataList = newData
        notifyDataSetChanged()
    }
}
