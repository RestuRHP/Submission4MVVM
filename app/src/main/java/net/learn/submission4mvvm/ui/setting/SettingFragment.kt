package net.learn.submission4mvvm.ui.setting


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import net.learn.submission4mvvm.R
import net.learn.submission4mvvm.notification.Receiver

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : PreferenceFragmentCompat() {
    private var receiver = Receiver()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val sharedPref = SharedPrefManager(
            context as Context
        ).getInstance(context as Context)

        val dailyReminderSwitch = findPreference<SwitchPreferenceCompat>("daily_reminder")
        val releaseReminderSwitch = findPreference<SwitchPreferenceCompat>("release_reminder")
        val languagePreference = findPreference<Preference>("preference_language")

        dailyReminderSwitch?.isChecked = sharedPref.checkDailyReminder() == true
        releaseReminderSwitch?.isChecked = sharedPref.checkReleaseReminder() == true
        dailyReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val dailySwitch = preference as SwitchPreferenceCompat

                if (dailySwitch.isChecked) {
                    sharedPref.setDailyReminder(false)
                    receiver.cancelAlarm(context as Context, Receiver().TYPE_DAILY)

                } else {
                    sharedPref.setDailyReminder(true)
                    receiver.setRepeatingAlarm(
                        context as Context, Receiver().TYPE_DAILY, "07:00",
                        getString(R.string.daily_notif_message)
                    )

                }
                true
            }
        releaseReminderSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { preference, _ ->
                val releaseSwitch = preference as SwitchPreferenceCompat

                if (releaseSwitch.isChecked) {
                    sharedPref.setReleaseReminder(false)
                    receiver.cancelAlarm(context as Context, Receiver().TYPE_RELEASE)


                } else {
                    sharedPref.setReleaseReminder(true)
                    receiver.setRepeatingAlarm(
                        context as Context, Receiver().TYPE_RELEASE, "08:00",
                        getString(R.string.release_notif_message)
                    )
                }
                true
            }

        languagePreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
            true
        }
    }

}
