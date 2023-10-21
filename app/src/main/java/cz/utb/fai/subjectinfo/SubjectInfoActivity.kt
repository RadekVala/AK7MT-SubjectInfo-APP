package cz.utb.fai.subjectinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.utb.fai.subjectinfo.databinding.ActivitySubjectinfoBinding

class SubjectInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectinfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjectinfo)

        binding = ActivitySubjectinfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.txtLabel.text = "Hello UTB"



    }
}