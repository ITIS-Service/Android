package com.itis.itisservice.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.itis.itisservice.R
import com.itis.itisservice.model.Point
import com.itis.itisservice.model.UserPoints
import com.itis.itisservice.mvp.presenter.ProgressPresenter
import com.itis.itisservice.mvp.view.ProgressView
import com.itis.itisservice.ui.activity.MainActivity
import com.itis.itisservice.ui.view.holder.PointAdapter
import com.itis.itisservice.utils.Constants.COURSE_ID
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_progress.*

class ProgressFragment : BaseFragment(), ProgressView {

    @InjectPresenter
    lateinit var presenter: ProgressPresenter

    private var adapter: PointAdapter? = null

    private var courseId: Int? = null

    companion object {
        fun newInstance(courseId: Int?): ProgressFragment {
            val args = Bundle()
            courseId?.let { args.putInt(COURSE_ID, it) }

            val fragment = ProgressFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.setBackArrow(true)
        baseActivity.fragmentOnScreen(this)
        baseActivity.supportActionBar?.show()

        courseId = arguments?.getInt(COURSE_ID)

        setUpRecyclerView()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_progress

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_progress
    }

    override fun getCourseId() {
        presenter.loadPoints(courseId)
    }

    override fun showPoints(points: UserPoints) {
        val list: MutableList<Point> = ArrayList()
        points.points.forEach {
            list.add(it)
            Log.d("count: ", it.count.toString())
        }
        tv_total_count.text = "+${points.total}"
        adapter?.setItems(list)
    }

    override fun onCodeInvalid() {
        Toast.makeText(baseActivity, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionError(error: Throwable) {
        Toast.makeText(baseActivity, error.message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        (activity as? MainActivity)?.progressBar2?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.progressBar2?.visibility = View.GONE
    }

    private fun setUpRecyclerView() {
        adapter = PointAdapter()
        rv_list_points.layoutManager = LinearLayoutManager(baseActivity)
        rv_list_points.adapter = adapter
    }
}