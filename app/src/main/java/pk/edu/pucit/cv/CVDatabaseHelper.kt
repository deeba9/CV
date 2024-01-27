package pk.edu.pucit.cv
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CVDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CVDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cv_table"

        private const val KEY_ID = "id"
        private const val KEY_ROLL_NUMBER = "roll_number"
        private const val KEY_NAME = "name"
        private const val KEY_CGPA = "cgpa"
        private const val KEY_DEGREE = "degree"
        private const val KEY_GENDER = "gender"
        private const val KEY_ACADEMIA_INTEREST = "academia_interest"
        private const val KEY_BUSINESS_INTEREST = "business_interest"
        private const val KEY_INDUSTRY_INTEREST = "industry_interest"
        private const val KEY_DATE_OF_BIRTH = "date_of_birth"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $KEY_ID INTEGER PRIMARY KEY,
                $KEY_ROLL_NUMBER TEXT,
                $KEY_NAME TEXT,
                $KEY_CGPA TEXT,
                $KEY_DEGREE TEXT,
                $KEY_GENDER TEXT,
                $KEY_ACADEMIA_INTEREST TEXT,
                $KEY_BUSINESS_INTEREST TEXT,
                $KEY_INDUSTRY_INTEREST TEXT,
                $KEY_DATE_OF_BIRTH TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addCV(cvData: CVData) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_ROLL_NUMBER, cvData.rollNumber)
            put(KEY_NAME, cvData.name)
            put(KEY_CGPA, cvData.cgpa)
            put(KEY_DEGREE, cvData.degree)
            put(KEY_GENDER, cvData.gender)
            put(KEY_ACADEMIA_INTEREST, cvData.academiaInterest)
            put(KEY_BUSINESS_INTEREST, cvData.businessInterest)
            put(KEY_INDUSTRY_INTEREST, cvData.industryInterest)
            put(KEY_DATE_OF_BIRTH, cvData.dateOfBirth)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllCVs(): List<CVData> {
        val cvList = mutableListOf<CVData>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val cvData = CVData(
                    rollNumber = cursor.getString(1),
                    name = cursor.getString(2),
                    cgpa = cursor.getString(3),
                    degree = cursor.getString(4),
                    gender = cursor.getString(5),
                    academiaInterest = cursor.getString(6),
                    businessInterest = cursor.getString(7),
                    industryInterest = cursor.getString(8),
                    dateOfBirth = cursor.getString(9)
                )
                cvList.add(cvData)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return cvList
    }
    fun deleteAllCVs() {
        val db = this.writableDatabase
        try {
            db.beginTransaction()
            db.execSQL("DELETE FROM $TABLE_NAME")
            db.setTransactionSuccessful() // Commit the transaction
        } catch (e: SQLiteException) {
            // Handle the exception, e.g., log an error or provide user feedback
            Log.e("DeleteAllCVs", "Error deleting CVs: ${e.message}")
        } finally {
            db.endTransaction() // End the transaction, regardless of success or failure
            db.close()
        }
    }
}
