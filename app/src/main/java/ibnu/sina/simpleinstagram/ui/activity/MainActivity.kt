package ibnu.sina.simpleinstagram.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import ibnu.sina.simpleinstagram.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView(){
        setupActionBarWithNavController(findNavController(R.id.nav_host_feed_fragment))
        supportActionBar?.title = getString(R.string.feed_header_title)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_feed_fragment).navigateUp()
    }
}
