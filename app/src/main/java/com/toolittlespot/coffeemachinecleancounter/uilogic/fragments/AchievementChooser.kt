package com.toolittlespot.coffeemachinecleancounter.uilogic.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.toolittlespot.coffeemachinecleancounter.R
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.Achievement
import com.toolittlespot.coffeemachinecleancounter.businesslogic.application.User
import com.toolittlespot.coffeemachinecleancounter.businesslogic.language.Dict
import com.toolittlespot.coffeemachinecleancounter.uilogic.MainActivity

class AchievementChooser : Fragment() {
    private lateinit var fragmentView: View
    private lateinit var achievementChooser: LinearLayout
    private lateinit var chooseAchieveTxt: TextView
    private lateinit var inflater: LayoutInflater

    companion object {
        var selectedUser: User? = null
        var selectedAchievement: Achievement? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.inflater = inflater
        fragmentView =  inflater.inflate(R.layout.fragment_achievement_chooser, container, false)
        displayPage()
        configView()

        return fragmentView
    }

    private fun displayPage() {
        if (selectedAchievement != null && selectedUser != null){
            if (selectedAchievement?.type == Achievement.Type.SECRET_MESSAGE){
                if (selectedAchievement?.message == null) {
                    (activity as MainActivity).changeMainLayout(AchieveMessageChooser())
                    return
                }
            }
            MainActivity.app.setNewAchievement(selectedUser!!, selectedAchievement!!, activity!!)
        }
    }

    private fun configView() {
        configTextViews()
        configAchievementChooser()
        configOkBtn()
    }

    private fun configOkBtn() {
        fragmentView.findViewById<Button>(R.id.choose_achievement_ok_btn).setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }
    }

    private fun configAchievementChooser() {
        achievementChooser = fragmentView.findViewById(R.id.achievement_chooser)
        MainActivity.app.getAchievements().forEach { achievement ->
            val item = inflater.inflate(R.layout.achievement_item, null)
            val checkedView = item.findViewById<ImageView>(R.id.checked_sign)
            setIfAchievementChecked(achievement, checkedView)
            item.findViewById<TextView>(R.id.achievement_name).text = achievement.name
            item.findViewById<TextView>(R.id.achievement_description).text = achievement.description
            item.setOnClickListener{
                selectedAchievement = achievement
                selectedUser = null
                if (achievement.type == Achievement.Type.NONE){
                    MainActivity.app.setNewAchievement(null, achievement, activity!!)
                    (activity as MainActivity).refreshFragment(this)
                }
                else (activity as MainActivity).changeMainLayout(AchieveUserChooser())
            }
            achievementChooser.addView(item)
        }

    }

    private fun setIfAchievementChecked(achievement: Achievement, view: ImageView) {
        val currentAchieve = MainActivity.app.getCurrentAchievement()
        println("!!!!!!!!!!!!!!!!!!!!!!!")
        println(currentAchieve?.type)
        println("!!!!!!!!!!!!!!!!!!!!!!!")
        if (currentAchieve?.type == achievement.type)
            view.visibility = View.VISIBLE

        else view.visibility = View.GONE
    }

    private fun configTextViews() {
        chooseAchieveTxt = fragmentView.findViewById(R.id.choose_achievement_txt)
        chooseAchieveTxt.text = MainActivity.app.getDict(Dict.CHOOSE_ACHIEVEMENT)
    }
}
