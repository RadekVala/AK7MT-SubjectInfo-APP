package cz.utb.fai.subjectinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import cz.utb.fai.subjectinfo.databinding.ActivitySubjectinfoBinding

class SubjectInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubjectinfoBinding
    private lateinit var viewModel: SubjectInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjectinfo)

        binding = ActivitySubjectinfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // get application instance to have app.repository for viewModel
        val app = application as MyApplication

        // create viewModel using factory to pass repository in an argument
        viewModel = ViewModelProvider(this, SubjectInfoViewModelFactory(app.repository))
            .get(SubjectInfoViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Observing change of zkratkaMutable property for hiding of Hint text
        viewModel.zkratkaMutable.observe(this, { zkratkaMutable ->
            if (zkratkaMutable != null && !zkratkaMutable.isEmpty()) {
                // if zkratka is not null or empty value
                viewModel.hideHintAndNotFound() // hide the hint text
            }
        })

        // Observing change of progress property for hiding or showing Progressbar
        viewModel.progress.observe(this, { progress ->
            if (progress) {
                // Show loading spinner
                binding.progressBar.visibility = View.VISIBLE
            } else {
                // Hide loading spinner
                binding.progressBar.visibility = View.GONE
            }
        })

    }
}