package vn.edu.hust.studentman

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import vn.edu.hust.studentman.databinding.DialogStudentBinding

class StudentDialogFragment(
    private val student: StudentModel? = null,
    private val onSave: (StudentModel) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogStudentBinding.inflate(LayoutInflater.from(context))

        binding.editTextStudentName.setText(student?.studentName ?: "")
        binding.editTextStudentId.setText(student?.studentId ?: "")

        return AlertDialog.Builder(requireContext())
            .setTitle(if (student == null) "Add Student" else "Edit Student")
            .setView(binding.root)
            .setPositiveButton("Save") { _, _ ->
                val name = binding.editTextStudentName.text.toString()
                val id = binding.editTextStudentId.text.toString()
                onSave(StudentModel(name, id))
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
