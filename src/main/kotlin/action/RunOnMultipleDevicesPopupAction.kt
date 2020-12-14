package action

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


class RunOnMultipleDevicesPopupAction : AnAction() {
    private val methods = arrayOf(this::tryMethod1, this::tryMethod2)
    override fun actionPerformed(e: AnActionEvent) {
        methods.first { method ->
            try{
                method.invoke(e)
                true
            } catch (ex: Exception){
                ex.printStackTrace()
                false
            }
        }
    }

    // Android Studio ~ 4.0
    private fun tryMethod1(e: AnActionEvent) {
        val deviceAction = ActionManager.getInstance().getAction("RunOnMultipleDevicesAction")
        deviceAction.actionPerformed(e)
    }

    // Android Studio 4.1 ~
    private fun tryMethod2(e: AnActionEvent) {
        val deviceAction = ActionManager.getInstance().getAction("DeviceAndSnapshotComboBox")
        val field = deviceAction.javaClass.declaredFields.first { it.name == "myRunOnMultipleDevicesAction" }
        field.isAccessible = true
        val targetAction = field.get(deviceAction) as AnAction
        targetAction.actionPerformed(e)
    }

    // show context menu
    // not use
    private fun tryMethod3(e: AnActionEvent) {
        val deviceAction = ActionManager.getInstance().getAction("DeviceAndSnapshotComboBoxAction")
        deviceAction.actionPerformed(e)
    }

}