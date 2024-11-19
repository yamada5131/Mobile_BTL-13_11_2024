package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

  private lateinit var studentAdapter: StudentAdapter
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  private var recentlyDeletedStudent: StudentModel? = null
  private var recentlyDeletedPosition: Int = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    studentAdapter = StudentAdapter(students, ::editStudent, ::deleteStudent)

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showStudentDialog()
    }
  }

  private fun showStudentDialog(student: StudentModel? = null) {
    val dialog = StudentDialogFragment(student) { newStudent ->
      if (student == null) {
        students.add(newStudent)
      } else {
        val index = students.indexOf(student)
        students[index] = newStudent
      }
      studentAdapter.notifyDataSetChanged()
    }
    dialog.show(supportFragmentManager, "StudentDialogFragment")
  }

  private fun editStudent(student: StudentModel) {
    showStudentDialog(student)
  }

  private fun deleteStudent(position: Int) {
    recentlyDeletedStudent = students[position]
    recentlyDeletedPosition = position
    students.removeAt(position)
    studentAdapter.notifyItemRemoved(position)
    showUndoSnackbar()
  }

  private fun showUndoSnackbar() {
    Snackbar.make(
      findViewById(R.id.recycler_view_students),
      "Student deleted",
      Snackbar.LENGTH_LONG
    ).setAction("Undo") {
      recentlyDeletedStudent?.let {
        students.add(recentlyDeletedPosition, it)
        studentAdapter.notifyItemInserted(recentlyDeletedPosition)
      }
    }.show()
  }
}